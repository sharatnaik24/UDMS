package com.project.UDMS.service;

import com.project.UDMS.dto.AttendanceDto;
import com.project.UDMS.dto.SubjectsDto;
import com.project.UDMS.entity.Attendance;
import com.project.UDMS.entity.Subjects;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.repository.AttendanceRepository;
import com.project.UDMS.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import javax.security.auth.Subject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;


    @InjectMocks
    private AttendanceService attendanceService;

    @Mock
    private SubjectService subjectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEntityToDto(){
        Attendance entity=new Attendance();
        entity.setAbsenteeId(1L);
        entity.setAbsent(10);
        entity.setStudentsAbsent(Arrays.asList("s30","s20"));

        AttendanceDto dto=attendanceService.entityToDto(entity);

        assertNotNull(dto);
        assertEquals(dto.getAbsent(),entity.getAbsent());
        assertEquals(dto.getAbsenteeId(),entity.getAbsenteeId());
        assertEquals(dto.getStudentsAbsent(),entity.getStudentsAbsent());

    }
    @Test
    public void testDtoToEntity(){
        AttendanceDto dto=new AttendanceDto();
        dto.setAbsenteeId(1L);
        dto.setAbsent(10);
        dto.setStudentsAbsent(Arrays.asList("s30","s20"));
        Attendance entity=attendanceService.dtoToEntity(dto);
        assertNotNull(entity);
        assertEquals(entity.getAbsent(),dto.getAbsent());
        assertEquals(entity.getAbsenteeId(),dto.getAbsenteeId());
        assertEquals(entity.getStudentsAbsent(),dto.getStudentsAbsent());

    }

    @Test
    public void testCreate_Success() {
        AttendanceDto attendanceDto = new AttendanceDto();
        attendanceDto.setAbsenteeId(1L);

        Attendance attendance = new Attendance();
        attendance.setAbsenteeId(1L);

        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);

        AttendanceDto result = attendanceService.create(attendanceDto);

        assertEquals(attendanceDto.getAbsenteeId(), result.getAbsenteeId());
        verify(attendanceRepository, times(1)).save(any(Attendance.class));
    }

    @Test
    public void testGetAll_Success() {
        List<Attendance> attendances = new ArrayList<>();
        Attendance attendance1 = new Attendance();
        attendance1.setAbsenteeId(1L);
        Attendance attendance2 = new Attendance();
        attendance2.setAbsenteeId(2L);
        attendances.add(attendance1);
        attendances.add(attendance2);

        when(attendanceRepository.findAll()).thenReturn(attendances);

        List<AttendanceDto> result = attendanceService.getAll();

        assertEquals(2, result.size());
        verify(attendanceRepository, times(1)).findAll();
    }

    @Test
    public void testGetByDateAndSub_Success() throws ParseException, UserNotFoundException {
        String dateStr = "2023-07-01";
        String subCode="CS101";
        SubjectsDto subjectdto = new SubjectsDto();
        subjectdto.setSubCode(subCode);
        Subjects subject = new Subjects();
        subject.setSubCode(subCode);
        Attendance attendance = new Attendance();
        attendance.setAbsenteeId(1L);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = formatter.parse(dateStr);
        when(subjectService.dtoToEntity(subjectdto)).thenReturn(subject);
        when((subjectService.getBySubCode(subCode))).thenReturn(subjectdto);
        when(attendanceRepository.findByDateAndSubject(date, subject)).thenReturn(attendance);

        AttendanceDto result = attendanceService.getByDateAndSub(dateStr, subCode);
        assertEquals(attendance.getAbsenteeId(), result.getAbsenteeId());
        verify(attendanceRepository, times(1)).findByDateAndSubject(date, subject);
    }

    @Test
    public void testGetByDateAndSub_NotFound() throws UserNotFoundException {
        String dateStr = "2023-07-01";
        String subCode="CS101";
        Subjects subject = new Subjects();
        subject.setSubCode(subCode);
        SubjectsDto subjectdto = new SubjectsDto();
        subjectdto.setSubCode(subCode);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        when(subjectService.dtoToEntity(subjectdto)).thenReturn(subject);
        when((subjectService.getBySubCode(subCode))).thenReturn(subjectdto);
        when(attendanceRepository.findByDateAndSubject(date, subject)).thenReturn(null);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            attendanceService.getByDateAndSub(dateStr, subCode);
        });

        assertEquals("Not Found", exception.getMessage());
    }

    @Test
    public void testAttendancePerc_Success() {
        String reg = "12345";
        Long expectedCount = 10L;

        when(attendanceRepository.countAbsencesByStudentRegNo(reg)).thenReturn(expectedCount);

        Long result = attendanceService.attendancePerc(reg);

        assertEquals(expectedCount, result);
        verify(attendanceRepository, times(1)).countAbsencesByStudentRegNo(reg);
    }

    @Test
    public void testDelete_Success() throws UserNotFoundException {
        Long id = 1L;
        Attendance attendance = new Attendance();
        attendance.setAbsenteeId(id);

        when(attendanceRepository.findById(id)).thenReturn(Optional.of(attendance));
        doNothing().when(attendanceRepository).deleteById(id);

        attendanceService.delete(id);

        verify(attendanceRepository, times(1)).findById(id);
        verify(attendanceRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDelete_NotFound() {
        Long id = 1L;

        when(attendanceRepository.findById(id)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            attendanceService.delete(id);
        });

        assertEquals("Attendance Id not Found", exception.getMessage());
        verify(attendanceRepository, times(1)).findById(id);
        verify(attendanceRepository, times(0)).deleteById(id);
    }
}
