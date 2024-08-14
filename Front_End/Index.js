let aboutVisible = false;
let contactVisible = false;

function showSection(section) {
    const aboutSection = document.getElementById('about-section');
    const contactSection = document.getElementById('contact-section');
    const loginContainer = document.getElementById('login-container');
    const signupContainer = document.getElementById('signup-container');

    if (section === 'about') {
        aboutVisible = true;
        // contactVisible = false;
        aboutSection.style.display = 'block';
        // contactSection.style.display = 'none';
    } else if (section === 'contact') {
        contactVisible = true;
        // aboutVisible = false;
        contactSection.style.display = 'block';
        // aboutSection.style.display = 'none';
    } else if (section === 'home') {
        aboutVisible = false;
        contactVisible = false;
        aboutSection.style.display = 'none';
        contactSection.style.display = 'none';
    }

    loginContainer.style.display = 'none';
    signupContainer.style.display = 'none';
}

function showLogin() {
    const loginContainer = document.getElementById('login-container');
    const signupContainer = document.getElementById('signup-container');
    const contactSection = document.getElementById('contact-section');
    const aboutSection = document.getElementById('about-section');

    loginContainer.style.display = 'block';
    signupContainer.style.display = 'none';
    aboutSection.style.display = 'none';
    contactSection.style.display = 'none';

    aboutVisible = false;
    contactVisible = false;
}

function showSignUp() {
    const loginContainer = document.getElementById('login-container');
    const signupContainer = document.getElementById('signup-container');
    const aboutSection = document.getElementById('about-section');
    const contactSection = document.getElementById('contact-section');

    loginContainer.style.display = 'none';
    signupContainer.style.display = 'block';
    aboutSection.style.display = 'none';
    contactSection.style.display = 'none';

    aboutVisible = false;
    contactVisible = false;
}

// Event listener for Home link
document.querySelector('.nav-left li:first-child a').addEventListener('click', function() {
    if (aboutVisible || contactVisible) {
        showSection('home');
    }
});



async function login(username,password){
    try{
        const response= await fetch("http://localhost:8080/login", {
        method: 'POST',
        body:JSON.stringify({"username":username,
            "password":password
        }),
        headers: {
            'Content-Type': 'application/json'
        } 
    });
    if(!response.ok){
        alert("invalid credentials");
        return;
}
alert(`Logged in with Username: ${username} and Password: ${password}`);
const data=await response.json();
console.log(data);
let token1=data.token;
localStorage.setItem('token', token1.token);
localStorage.setItem('username', username)
localStorage.setItem('role',data.role);
window.location.href="main.html"
}
catch(error){
throw new Error("there is an error");
}
}
async function signup(userId,username,password,role){
    try{
        const response= await fetch("http://localhost:8080/register", {
        method: 'POST',
        body:JSON.stringify({"userId":userId,
            "username":username,
            "password":password,
            "role":role
            
        }),
        headers: {
            'Content-Type': 'application/json'
        } 
    });
    if(!response.ok){
        alert("something went wrong")
        return;
}

const data=await response.json();
if(data.token=="UserId is already having an account!"){
    alert(data.token)
    return;
}
if(data.token=="This RegNo is Not admitted to University"){
    alert(data.token)
    return;
}
if(data.token=="This ProfessorId is Not a Faculty member of this University"){
    alert(data.token)
    return;
}
if(data.token=="Username is already taken!"){
    alert(data.token)
    return;
}if(data.token=="Password is already taken!"){
    alert(data.token)
    return;
}
else{
    console.log(data);
    alert(`Signed in with Username: ${username} and Password: ${password} as a :${role}`);
    let token1=data.token;
    localStorage.setItem('token', token1.token);
    localStorage.setItem('username', username)
    localStorage.setItem('role',data.role);
    window.location.href="main.html"
}
}
catch(error){
throw new Error("there is an error");
}
}

document.getElementById('login-form').addEventListener('submit', function(event) {
    event.preventDefault();
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;
    login(username,password);
});


document.getElementById('signup-form').addEventListener('submit', function(event) {
    event.preventDefault();
    const userId=document.getElementById('signup-userId').value;
    const username = document.getElementById('signup-username').value;
    const password = document.getElementById('signup-password').value;
    const confirmPassword = document.getElementById('confirm-password').value;
    const role = document.getElementById('signup-role').value;

    if (password !== confirmPassword) {
        alert('Passwords do not match!');
        return;
    }
    signup(userId,username,password,role);

});

function togglePasswordVisibility(inputId) {
    const inputField = document.getElementById(inputId);
    const type = inputField.getAttribute('type') === 'password' ? 'text' : 'password';
    inputField.setAttribute('type', type);
}



