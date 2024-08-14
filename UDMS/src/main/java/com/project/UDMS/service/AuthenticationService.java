package com.project.UDMS.service;

import com.project.UDMS.entity.ProfessorDetails;
import com.project.UDMS.entity.StudentEnrollment;
import com.project.UDMS.entity.UserEntity;
import com.project.UDMS.repository.ProfessorRepository;
import com.project.UDMS.repository.StudentEnrollmentRepository;
import com.project.UDMS.repository.UserRepository;
import com.project.UDMS.resource.Role;
import com.project.UDMS.response.AuthenticationResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
     private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private StudentEnrollmentRepository studentEnrollmentRepository;
    @Autowired
    private ProfessorRepository professorRepository;

    public AuthenticationResponse register(UserEntity request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            return new AuthenticationResponse("UserId is already having an account!");
        }
        if(request.getRole()== Role.STUDENT){
            if(!studentEnrollmentRepository.existsById(request.getUserId()))
                return new AuthenticationResponse("This RegNo is Not admitted to University");
        }
        if(request.getRole()== Role.PROFESSOR){
            if(!professorRepository.existsById(request.getUserId()))
                return new AuthenticationResponse("This ProfessorId is Not a Faculty member of this University");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            return new AuthenticationResponse("Username is already taken!");
        } else if (userRepository.existsByPassword(request.getPassword())) {
            return new AuthenticationResponse("Password is already taken!");
        } else {

            UserEntity user = new UserEntity();
            BeanUtils.copyProperties(request, user);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user = userRepository.save(user);

            String token = jwtService.generateToken(user);
            return new AuthenticationResponse(token);
        }
    }

    public AuthenticationResponse login(UserDetails request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserEntity user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.generateToken(user);

        return new AuthenticationResponse(token);

    }
    public Role findRole(UserDetails request){
        UserEntity user= userRepository.findByUsername(request.getUsername()).orElseThrow();
        return user.getRole();

    }
}
