// import { getAll } from "./attendance";
function showSection(sectionId) {
    // Hide all sections
    document.querySelectorAll('.content-section').forEach(section => {
        section.style.display = 'none';
    });

    // Show the selected section
    document.getElementById(sectionId).style.display = 'block';
}

// Function to toggle dropdown menu visibility
function toggleDropdown() {
    const dropdownContent = document.getElementById('dropdown-content');
    if (dropdownContent.style.display === 'block') {
        dropdownContent.style.display = 'none';
    } else {
        dropdownContent.style.display = 'block';
    }
}

// Event listeners for dropdown menu items
document.querySelectorAll('.dropdown-content a').forEach(anchor => {
    anchor.addEventListener('click', function(event) {
        event.preventDefault();
        const targetId = this.getAttribute('data-target');
        showSection(targetId);
        toggleDropdown(); // Close dropdown after selection
    });
});
document.getElementById('menu-btn').addEventListener('click', function(event) {
    toggleDropdown();
});

const modal = document.getElementById('responseModal');
    const modalBody = document.getElementById('modal-body');
    const closeModal = document.querySelector('.close');

    // Event listener for the modal close button
    closeModal.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    window.addEventListener('click', (event) => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });

    document.addEventListener('DOMContentLoaded', function() {
        // Get the role and username from localStorage
        const role = localStorage.getItem('role') || 'Role';
        const username = localStorage.getItem('username') || 'Guest';
        
        // Display the role and username in the profile section
        const roleDisplay = document.getElementById('role-display');
        const usernameDisplay = document.getElementById('username-display');
        
        roleDisplay.textContent = `${role} : `;
        usernameDisplay.textContent = username;
    });
    
    
    function displayResponse(data) {
        let content = '';

        if (Array.isArray(data)) {
            if (data.length === 0) {
                content = '<div class="message">There are 0 records.</div>';
            } else {
                content = createTableFromData(data).outerHTML;
            }
        } else if (typeof data === 'string' || typeof data === 'number') {
            content = `<div class="message">${data}</div>`;
        } else if (typeof data === 'object' && data !== null) {
            content = createTableFromDataObject(data).outerHTML;
        } else {
            content = '<div class="message">Unexpected response format</div>';
        }

        modalBody.innerHTML = content;
        showModal();
    }

    // Create a table from an array of data
    function createTableFromData(data) {
        const table = document.createElement('table');
        const thead = table.createTHead();
        const tbody = table.createTBody();
        const headers = Object.keys(data[0]);
    
        const headerRow = thead.insertRow();
        headers.forEach(header => {
            const th = document.createElement('th');
            th.textContent = header;
            th.style.padding = '8px';
            th.style.textAlign = 'center';
            th.style.color = 'red';
            headerRow.appendChild(th);
        });
    
        data.forEach(rowData => {
            const row = tbody.insertRow();
            headers.forEach(header => {
                const cell = row.insertCell();
    
                if (Array.isArray(rowData[header])) {
                    // If the value is an array, display each item
                    rowData[header].forEach(item => {
                        const itemDiv = document.createElement('div');
                        if (typeof item === 'object') {
                            const primaryKey = Object.keys(item)[0];
                            const link = document.createElement('a');
                            link.href = '#';
                            link.textContent = item[primaryKey];
                            link.dataset.object = JSON.stringify(item);
                            link.classList.add('primary-key-link');
                            itemDiv.appendChild(link);
                        } else {
                            itemDiv.textContent = item;
                        }
                        cell.appendChild(itemDiv);
                    });
                } else if (typeof rowData[header] === 'object' && rowData[header] !== null) {
                    // If the value is an object, extract the primary key and create a link
                    const primaryKey = Object.keys(rowData[header])[0];
                    const link = document.createElement('a');
                    link.href = '#';
                    link.textContent = rowData[header][primaryKey];
                    link.dataset.object = JSON.stringify(rowData[header]);
                    link.classList.add('primary-key-link');
                    cell.appendChild(link);
                } else {
                    cell.textContent = rowData[header];
                }
    
                cell.style.padding = '8px';
                cell.style.textAlign = 'center';
            });
        });
    
        return table;
    }
    
    function createTableFromDataObject(data) {
        const table = document.createElement('table');
        const tbody = table.createTBody();
    
        Object.keys(data).forEach(key => {
            const row = tbody.insertRow();
            const cellKey = row.insertCell();
            const cellValue = row.insertCell();
    
            cellKey.textContent = key;
            cellKey.style.fontWeight = 'bold';
            cellKey.style.color = 'darkblue';
            cellKey.style.textAlign = 'right';
            cellKey.style.paddingRight = '10px';
    
            let value = data[key];
    
            // If the value is an array, display each item
            if (Array.isArray(value)) {
                value.forEach(item => {
                    const itemDiv = document.createElement('div');
                    if (typeof item === 'object') {
                        const primaryKey = Object.keys(item)[0];
                        const link = document.createElement('a');
                        link.href = '#';
                        link.textContent = item[primaryKey];
                        link.dataset.object = JSON.stringify(item);
                        link.classList.add('primary-key-link');
                        itemDiv.appendChild(link);
                    } else {
                        itemDiv.textContent = item;
                    }
                    cellValue.appendChild(itemDiv);
                });
            } else if (typeof value === 'object' && value !== null) {
                // If the value is an object, extract the primary key and create a link
                const primaryKey = Object.keys(value)[0];
                const link = document.createElement('a');
                link.href = '#';
                link.textContent = value[primaryKey];
                link.dataset.object = JSON.stringify(value);
                link.classList.add('primary-key-link');
                cellValue.appendChild(link);
            } else {
                cellValue.textContent = value;
            }
    
            cellValue.style.padding = '8px';
            cellValue.style.textAlign = 'left';
        });
    
        return table;
    }
    
    // Event delegation to handle clicks on links
    document.addEventListener('click', function(event) {
        if (event.target.classList.contains('primary-key-link')) {
            event.preventDefault();  // Prevent default link action
            const objectData = JSON.parse(event.target.dataset.object);
            displayResponse(objectData);
        }
    });
    
    // Show the modal
    function showModal() {
        modal.style.display = 'block';
    }
    
async function getAll(src){
    let token = localStorage.getItem('token');
    console.log(token);
    if(token){
        try{
            const response = await fetch(`http://localhost:8080/${src}/getAll`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            if(!response.ok){
                alert("something went wrong");
                return;
            }
            const data = await response.json();
            console.log(data);
            displayResponse(data)
            return data;
        } catch(error){
            console.log(error.message);
            return;
            
        }
    }
}

async function getById(src,date,sub){
    let token=localStorage.getItem('token');
    console.log(token);
    if(token){
    try{
        const response= await fetch(`http://localhost:8080/${src}/getAttendanceByDateAndSubject/${date},${sub}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
    });
    if(!response.ok){
        alert("something went wrong")
        return;
}

const data=await response.json();
console.log("data: ",data);
displayResponse(data);
return data;
}
catch(error){
    console.log(error.message);
    return;
}
}
}

async function update(src,date, sub, body1) {
    let token = localStorage.getItem('token');
    console.log(token);
    if (token) {
        try {
            const response = await fetch(`http://localhost:8080/${src}/update/${date},${sub}`, {
                method: 'PUT',
                body: body1,
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            if (!response.ok) {
                alert("Something went wrong");
                return;
            }

            const data = await response.json();
            console.log(data);
            displayResponse(data);
            return data;
        } catch (error) {
            console.log(error.message);
            return;
        }
    }
}

async function create(src,body1){
    let token = localStorage.getItem('token');
    console.log(token);
    if(token){
        try{
            const response = await fetch(`http://localhost:8080/${src}/create`, {
                method: 'POST',
                body: body1,
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            if(!response.ok){
                alert("something went wrong");
                return;
            }
            const data = await response.json();
            console.log(data);
            displayResponse(data);
            return data;
        } catch(error){
            console.log(error.message);
            return;
        }
    }
}

async function deletefun(src,id){
    let token = localStorage.getItem('token');
    console.log(token);
    if(token){
        try{
            const response = await fetch(`http://localhost:8080/${src}/delete/${id}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            if(!response.ok){
                alert("something went wrong");
                return;
            }
            const data = await response.text();
            console.log(data);
            displayResponse(data);
            return data;
        } catch(error){
            console.log(error.message);
            return;
        }
    }
}

async function getByReg(src,reg){
    let token=localStorage.getItem('token');
    console.log(token);
    if(token){
    try{
        const response= await fetch(`http://localhost:8080/${src}/getRegPer/${reg}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
    });
    if(!response.ok){
        alert("something went wrong")
        return;
}

const data=await response.json();
console.log(data);
displayResponse(data);
return data;
}
catch(error){
    console.log(error.message);
    return;
}
}
}



document.getElementById('getall').addEventListener('click', function(event) {
    toggleInputFields([], true);
    setSubmitAction('getall');
});

document.getElementById('get').addEventListener('click', function(event) {
    toggleInputFields(['Date', 'SubId']);
    setSubmitAction('get');
});

document.getElementById('update').addEventListener('click', function(event) {
    toggleInputFields(['Date', 'SubId', 'course', 'present', 'absent', 'studentabsentList']);
    setSubmitAction('update');
});

document.getElementById('create').addEventListener('click', function(event) {
    toggleInputFields(['Date', 'SubId', 'course', 'present', 'absent', 'studentabsentList']);
    setSubmitAction('create');
});

document.getElementById('delete').addEventListener('click', function(event) {
    toggleInputFields(['AttendanceId']);
    setSubmitAction('delete');
});

document.getElementById('GetByReg').addEventListener('click', function(event) {
    toggleInputFields(['reg']);
    setSubmitAction('GetByReg');
});

document.getElementById('submit').addEventListener('click', function(event) {
    const action = this.getAttribute('data-action');
    let source="attendance"
    switch (action) {
        case 'getall':
            getAll(source);
            break;
        case 'get':
            let date = document.getElementById('Date').value;
            let sub = document.getElementById('SubId').value;
            getById(source,date, sub);
            break;
        case 'update':
            let updateDate = document.getElementById('Date').value;
            let updateSub = document.getElementById('SubId').value;
            let body =  JSON.stringify({
                'date': document.getElementById('Date').value,
                'course': {'courseName':document.getElementById('course').value},
                'subject': {'subCode':document.getElementById('SubId').value},
                'present':document.getElementById('present').value,
                'absent': document.getElementById('absent').value,
                'studentsAbsent': document.getElementById('studentabsentList').value.split(',')
            });
            update(source,updateDate, updateSub, body);
            break;
        case 'create':
            let body1 =  JSON.stringify({
                'date': document.getElementById('Date').value,
                'course': {'courseName':document.getElementById('course').value},
                'subject': {'subCode':document.getElementById('SubId').value},
                'present':document.getElementById('present').value,
                'absent': document.getElementById('absent').value,
                'studentsAbsent': document.getElementById('studentabsentList').value.split(',')
            });
            create(source,body1);
            break;
        case 'delete':
            let id = document.getElementById('AttendanceId').value;
            deletefun(source,id);
            break;
        case 'GetByReg':
            let reg = document.getElementById('reg').value;
            getByReg(source,reg);
            break;
    }
});

function toggleInputFields(fields, showSubmit = true) {
    const allFields = ['AttendanceId', 'Date', 'SubId', 'course', 'present', 'absent', 'studentabsentList', 'reg'];
    allFields.forEach(field => {
        const element = document.getElementById(field);
        if (fields.includes(field)) {
            element.style.display = 'block';
        } else {
            element.style.display = 'none';
        }
    });

    document.getElementById('attendance-input-fields').style.display = fields.length > 0 || showSubmit ? 'flex' : 'none';
    document.getElementById('submit').style.display = showSubmit ? 'block' : 'none';
}
function setSubmitAction(action) {
    document.getElementById('submit').setAttribute('data-action', action);
}

// main();

async function getById(src,id){
    let token = localStorage.getItem('token');
    console.log(token);
    if(token){
        try{
            const response = await fetch(`http://localhost:8080/${src}/getById/${id}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            if(!response.ok){
                alert("something went wrong");
                return;
            }
            const data = await response.json();
            console.log(data);
            displayResponse(data);
            return data;
        } catch(error){
            console.log(error.message);
            return;
        }
    }
}


async function update(src,id, body1){
    let token = localStorage.getItem('token');
    console.log(token);
    if(token){
        try{
            const response = await fetch(`http://localhost:8080/${src}/update/${id}`, {
                method: 'PUT',
                body: body1,
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            if(!response.ok){
                alert("something went wrong");
                return;
            }
            const data = await response.json();
            console.log(data);
            displayResponse(data);
            return data;
        } catch(error){
            console.log(error.message);
            return;
        }
    }
}


document.getElementById('getallFaculty').addEventListener('click', function(event) {
    toggleFacultyInputFields([], true);
    setFacultySubmitAction('getallFaculty');
});

document.getElementById('getFaculty').addEventListener('click', function(event) {
    toggleFacultyInputFields(['ProfessorId']);
    setFacultySubmitAction('getFaculty');
});

document.getElementById('createFaculty').addEventListener('click', function(event) {
    toggleFacultyInputFields(['ProfessorId','Name', 'Qualification', 'Email', 'Mobile']);
    setFacultySubmitAction('createFaculty');
});

document.getElementById('updateFaculty').addEventListener('click', function(event) {
    toggleFacultyInputFields(['ProfessorId', 'Name', 'Qualification', 'Email', 'Mobile']);
    setFacultySubmitAction('updateFaculty');
});

document.getElementById('deleteFaculty').addEventListener('click', function(event) {
    toggleFacultyInputFields(['ProfessorId']);
    setFacultySubmitAction('deleteFaculty');
});

document.getElementById('submitFaculty').addEventListener('click', function(event) {
    const action = this.getAttribute('data-action');
    let source="faculty";
    switch (action) {
        case 'getallFaculty':
            getAll(source);
            break;
        case 'getFaculty':
            let id = document.getElementById('ProfessorId').value;
            getById(source,id);
            break;
        case 'createFaculty':
            
            let body= JSON.stringify({
                'pid': document.getElementById('ProfessorId').value,
                'name':document.getElementById('Name').value,
                'qualification': document.getElementById('Qualification').value,
                'mail': document.getElementById('Email').value,
                'mob': document.getElementById('Mobile').value
            });
            create(source,body);
            break;
        case 'updateFaculty':
            let updateId = document.getElementById('ProfessorId').value;
            let body1= JSON.stringify({
                'pid': document.getElementById('ProfessorId').value,
                'name':document.getElementById('Name').value,
                'qualification': document.getElementById('Qualification').value,
                'mail': document.getElementById('Email').value,
                'mob': document.getElementById('Mobile').value
            });
            update(source,updateId, body1);
            break;
        case 'deleteFaculty':
            let deleteId = document.getElementById('ProfessorId').value;
            deletefun(source,deleteId);
            break;
    }
});

function toggleFacultyInputFields(fields, showSubmit = true) {
    const allFields = ['ProfessorId', 'Name', 'Qualification', 'Email', 'Mobile'];
    allFields.forEach(field => {
        const element = document.getElementById(field);
        if (fields.includes(field)) {
            element.style.display = 'block';
        } else {
            element.style.display = 'none';
        }
    });

    document.getElementById('faculty-input-fields').style.display = fields.length > 0 || showSubmit ? 'flex' : 'none';
    document.getElementById('submitFaculty').style.display = showSubmit ? 'block' : 'none';
}

function setFacultySubmitAction(action) {
    document.getElementById('submitFaculty').setAttribute('data-action', action);
}

//for subjects

    document.getElementById('getallSubjects').addEventListener('click', function(event) {
        toggleSubjectInputFields([], true);
        setSubjectSubmitAction('getallSubjects');
    });

    document.getElementById('getSubject').addEventListener('click', function(event) {
        toggleSubjectInputFields(['SubjectCode']);
        setSubjectSubmitAction('getSubject');
    });

    document.getElementById('createSubject').addEventListener('click', function(event) {
        toggleSubjectInputFields(['SubjectCode', 'SubName', 'SubType', 'ProfId']);
        setSubjectSubmitAction('createSubject');
    });

    document.getElementById('updateSubject').addEventListener('click', function(event) {
        toggleSubjectInputFields(['SubjectCode', 'SubName', 'SubType', 'ProfId']);
        setSubjectSubmitAction('updateSubject');
    });

    document.getElementById('deleteSubject').addEventListener('click', function(event) {
        toggleSubjectInputFields(['SubjectCode']);
        setSubjectSubmitAction('deleteSubject');
    });

    document.getElementById('submitSubject').addEventListener('click', function(event) {
        const action = this.getAttribute('data-action');
        let source = "subject";
        switch (action) {
            case 'getallSubjects':
                getAll(source);
                break;
            case 'getSubject':
                let id = document.getElementById('SubjectCode').value;
                getById(source, id);
                break;
            case 'createSubject':
                let body = JSON.stringify({
                    'subCode': document.getElementById('SubjectCode').value,
                    'subName': document.getElementById('SubName').value,
                    'subType': document.getElementById('SubType').value,
                    'professorId':{ 'pid':document.getElementById('ProfId').value}
                });
                create(source, body);
                break;
            case 'updateSubject':
                let updateId = document.getElementById('SubjectCode').value;
                let body1 = JSON.stringify({
                    'subName': document.getElementById('SubName').value,
                    'subType': document.getElementById('SubType').value,
                    'professorId': { 'pid':document.getElementById('ProfId').value}
                });
                update(source, updateId, body1);
                break;
            case 'deleteSubject':
                let deleteId = document.getElementById('SubjectCode').value;
                deletefun(source, deleteId);
                break;
        }
    });

    function toggleSubjectInputFields(fields, showSubmit = true) {
        const allFields = ['SubjectCode', 'SubName', 'SubType', 'ProfId'];
        allFields.forEach(field => {
            const element = document.getElementById(field);
            if (fields.includes(field)) {
                element.style.display = 'block';
            } else {
                element.style.display = 'none';
            }
        });

        document.getElementById('subject-input-fields').style.display = fields.length > 0 || showSubmit ? 'flex' : 'none';
        document.getElementById('submitSubject').style.display = showSubmit ? 'block' : 'none';
    }

    function setSubjectSubmitAction(action) {
        document.getElementById('submitSubject').setAttribute('data-action', action);
    }

    // feedback 

    document.getElementById('getAllFeedback').addEventListener('click', function(event) {
        toggleFeedbackInputFields([], true);
        setFeedbackSubmitAction('getAllFeedback');
    });

    document.getElementById('getFeedback').addEventListener('click', function(event) {
        toggleFeedbackInputFields(['feedbackId']);
        setFeedbackSubmitAction('getFeedback');
    });

    document.getElementById('createFeedback').addEventListener('click', function(event) {
        toggleFeedbackInputFields(['professorIdForFeedback', 'courseNameForFeedback', 'Feedback']);
        setFeedbackSubmitAction('createFeedback');
    });

    document.getElementById('updateFeedback').addEventListener('click', function(event) {
        toggleFeedbackInputFields(['feedbackId', 'professorIdForFeedback', 'courseNameForFeedback', 'Feedback']);
        setFeedbackSubmitAction('updateFeedback');
    });

    document.getElementById('deleteFeedback').addEventListener('click', function(event) {
        toggleFeedbackInputFields(['feedbackId']);
        setFeedbackSubmitAction('deleteFeedback');
    });

    document.getElementById('submitFeedback').addEventListener('click', function(event) {
        const action = this.getAttribute('data-action');
        let source = "feedback";
        switch (action) {
            case 'getAllFeedback':
                getAll(source);
                break;
            case 'getFeedback':
                let id = document.getElementById('feedbackId').value;
                getById(source, id);
                break;
            case 'createFeedback':
                let body = JSON.stringify({
                    'proffesor': {"pid":document.getElementById('professorIdForFeedback').value},
                    'coursename':{"courseName" :document.getElementById('courseNameForFeedback').value},
                    'feedback': document.getElementById('Feedback').value
                });
                create(source, body);
                break;
            case 'updateFeedback':
                let updateId = document.getElementById('feedbackId').value;
                let body1 = JSON.stringify({
                    'proffesor': {"pid":document.getElementById('professorIdForFeedback').value},
                    'coursename':{"courseName" :document.getElementById('courseNameForFeedback').value},
                    'feedback': document.getElementById('Feedback').value
                });
                update(source, updateId, body1);
                break;
            case 'deleteFeedback':
                let deleteId = document.getElementById('feedbackId').value;
                deletefun(source, deleteId);
                break;
        }
    });

    function toggleFeedbackInputFields(fields, showSubmit = true) {
        const allFields = ['feedbackId', 'professorIdForFeedback', 'courseNameForFeedback', 'Feedback'];
        allFields.forEach(field => {
            const element = document.getElementById(field);
            if (fields.includes(field)) {
                element.style.display = 'block';
            } else {
                element.style.display = 'none';
            }
        });

        document.getElementById('feedback-input-fields').style.display = fields.length > 0 || showSubmit ? 'flex' : 'none';
        document.getElementById('submitFeedback').style.display = showSubmit ? 'block' : 'none';
    }

    function setFeedbackSubmitAction(action) {
        document.getElementById('submitFeedback').setAttribute('data-action', action);
    }

    // for courses

    document.getElementById('getAllCourses').addEventListener('click', function(event) {
        toggleCourseInputFields([], true);
        setCourseSubmitAction('getAllCourses');
    });
    
    document.getElementById('getCourse').addEventListener('click', function(event) {
        toggleCourseInputFields(['courseName']);
        setCourseSubmitAction('getCourse');
    });
    
    document.getElementById('createCourse').addEventListener('click', function(event) {
        toggleCourseInputFields(['courseName', 'subject1Id', 'subject2Id', 'subject3Id', 'subject4Id']);
        setCourseSubmitAction('createCourse');
    });
    
    document.getElementById('updateCourse').addEventListener('click', function(event) {
        toggleCourseInputFields([ 'courseName', 'subject1Id', 'subject2Id', 'subject3Id', 'subject4Id']);
        setCourseSubmitAction('updateCourse');
    });
    
    document.getElementById('deleteCourse').addEventListener('click', function(event) {
        toggleCourseInputFields(['courseName']);
        setCourseSubmitAction('deleteCourse');
    });
    
    document.getElementById('submitCourse').addEventListener('click', function(event) {
        const action = this.getAttribute('data-action');
        let source = "courses";
        switch (action) {
            case 'getAllCourses':
                getAll(source);
                break;
            case 'getCourse':
                let id = document.getElementById('courseName').value;
                getById(source, id);
                break;
            case 'createCourse':
                let body = JSON.stringify({
                    'courseName': document.getElementById('courseName').value,
                    'subjects': [
                        {'subCode': document.getElementById('subject1Id').value},
                        {'subCode': document.getElementById('subject2Id').value},
                        {'subCode': document.getElementById('subject3Id').value},
                        {'subCode': document.getElementById('subject4Id').value}
                    ]
                });
                create(source, body);
                break;
            case 'updateCourse':
                let updateId = document.getElementById('courseName').value;
                let body1 = JSON.stringify({
                    'subjects': [
                        {'subCode': document.getElementById('subject1Id').value},
                        {'subCode': document.getElementById('subject2Id').value},
                        {'subCode': document.getElementById('subject3Id').value},
                        {'subCode': document.getElementById('subject4Id').value}
                    ]
                });
                update(source, updateId, body1);
                break;
            case 'deleteCourse':
                let deleteId = document.getElementById('courseName').value;
                deletefun(source, deleteId);
                break;
        }
    });
    
    function toggleCourseInputFields(fields, showSubmit = true) {
        const allFields = ['courseName', 'subject1Id', 'subject2Id', 'subject3Id', 'subject4Id'];
        allFields.forEach(field => {
            const element = document.getElementById(field);
            if (fields.includes(field)) {
                element.style.display = 'block';
            } else {
                element.style.display = 'none';
            }
        });
    
        document.getElementById('course-input-fields').style.display = fields.length > 0 || showSubmit ? 'flex' : 'none';
        document.getElementById('submitCourse').style.display = showSubmit ? 'block' : 'none';
    }
    
    function setCourseSubmitAction(action) {
        document.getElementById('submitCourse').setAttribute('data-action', action);
    }

    //for studentAdmission

    document.getElementById('getAllStudents').addEventListener('click', function(event) {
        toggleStudentInputFields([], true);
        setStudentSubmitAction('getAllStudents');
    });
    
    document.getElementById('getStudent').addEventListener('click', function(event) {
        toggleStudentInputFields(['regNo']);
        setStudentSubmitAction('getStudent');
    });
    
    document.getElementById('createStudent').addEventListener('click', function(event) {
        toggleStudentInputFields(['regNo','name', 'mobNo', 'email', 'courseNameForAdmission']);
        setStudentSubmitAction('createStudent');
    });
    
    document.getElementById('updateStudent').addEventListener('click', function(event) {
        toggleStudentInputFields(['regNo', 'name', 'mobNo', 'email', 'courseNameForAdmission']);
        setStudentSubmitAction('updateStudent');
    });
    
    document.getElementById('deleteStudent').addEventListener('click', function(event) {
        toggleStudentInputFields(['regNo']);
        setStudentSubmitAction('deleteStudent');
    });
    
    document.getElementById('submitStudent').addEventListener('click', function(event) {
        const action = this.getAttribute('data-action');
        let source = "students";
        switch (action) {
            case 'getAllStudents':
                getAll(source);
                break;
            case 'getStudent':
                let id = document.getElementById('regNo').value;
                getById(source, id);
                break;
            case 'createStudent':
                let body = JSON.stringify({
                    'rollNo':document.getElementById('regNo').value,
                    'name': document.getElementById('name').value,
                    'mob': document.getElementById('mobNo').value,
                    'mail': document.getElementById('email').value,
                    'course':{ 'courseName':document.getElementById('courseNameForAdmission').value}
                });
                create(source, body);
                break;
            case 'updateStudent':
                let updateId = document.getElementById('regNo').value;
                let body1 = JSON.stringify({
                    'name': document.getElementById('name').value,
                    'mobNo': document.getElementById('mobNo').value,
                    'email': document.getElementById('email').value,
                    'course':{ 'courseName':document.getElementById('courseNameForAdmission').value}
                });
                update(source, updateId, body1);
                break;
            case 'deleteStudent':
                let deleteId = document.getElementById('regNo').value;
                deletefun(source, deleteId);
                break;
        }
    });
    
    function toggleStudentInputFields(fields, showSubmit = true) {
        const allFields = ['regNo', 'name', 'mobNo', 'email', 'courseNameForAdmission'];
        allFields.forEach(field => {
            const element = document.getElementById(field);
            if (fields.includes(field)) {
                element.style.display = 'block';
            } else {
                element.style.display = 'none';
            }
        });
    
        document.getElementById('student-input-fields').style.display = fields.length > 0 || showSubmit ? 'flex' : 'none';
        document.getElementById('submitStudent').style.display = showSubmit ? 'block' : 'none';
    }
    
    function setStudentSubmitAction(action) {
        document.getElementById('submitStudent').setAttribute('data-action', action);
    }

    //results

    document.getElementById('getAllResults').addEventListener('click', function(event) {
        toggleResultInputFields([], true);
        setResultSubmitAction('getAllResults');
    });
    
    document.getElementById('getResult').addEventListener('click', function(event) {
        toggleResultInputFields(['regNoForResult']);
        setResultSubmitAction('getResult');
    });
    
    document.getElementById('createResult').addEventListener('click', function(event) {
        toggleResultInputFields(['regNoForResult', 'marks1', 'marks2', 'marks3', 'marks4']);
        setResultSubmitAction('createResult');
    });
    
    document.getElementById('updateResult').addEventListener('click', function(event) {
        toggleResultInputFields(['regNoForResult', 'marks1', 'marks2', 'marks3', 'marks4']);
        setResultSubmitAction('updateResult');
    });
    
    document.getElementById('deleteResult').addEventListener('click', function(event) {
        toggleResultInputFields(['regNoForResult']);
        setResultSubmitAction('deleteResult');
    });
    
    document.getElementById('submitResult').addEventListener('click', function(event) {
        const action = this.getAttribute('data-action');
        let source = "marks";
        switch (action) {
            case 'getAllResults':
                getAll(source);
                break;
            case 'getResult':
                let id = document.getElementById('regNoForResult').value;
                getById(source, id);
                break;
            case 'createResult':
                let body = JSON.stringify({
                    'regno':{'rollNo': document.getElementById('regNoForResult').value},
                    'marks1': document.getElementById('marks1').value,
                    'marks2': document.getElementById('marks2').value,
                    'marks3': document.getElementById('marks3').value,
                    'marks4': document.getElementById('marks4').value
                });
                create(source, body);
                break;
            case 'updateResult':
                let updateId = document.getElementById('regNoForResult').value;
                let body1 = JSON.stringify({
                    'regno':{'rollNo': document.getElementById('regNoForResult').value},
                    'marks1': document.getElementById('marks1').value,
                    'marks2': document.getElementById('marks2').value,
                    'marks3': document.getElementById('marks3').value,
                    'marks4': document.getElementById('marks4').value
                });
                update(source, updateId, body1);
                break;
            case 'deleteResult':
                let deleteId = document.getElementById('regNoForResult').value;
                deletefun(source, deleteId);
                break;
        }
    });
    
    function toggleResultInputFields(fields, showSubmit = true) {
        const allFields = ['regNoForResult', 'marks1', 'marks2', 'marks3', 'marks4'];
        allFields.forEach(field => {
            const element = document.getElementById(field);
            if (fields.includes(field)) {
                element.style.display = 'block';
            } else {
                element.style.display = 'none';
            }
        });
    
        document.getElementById('result-input-fields').style.display = fields.length > 0 || showSubmit ? 'flex' : 'none';
        document.getElementById('submitResult').style.display = showSubmit ? 'block' : 'none';
    }
    
    function setResultSubmitAction(action) {
        document.getElementById('submitResult').setAttribute('data-action', action);
    }
    

    // togglevisiblity
    document.addEventListener('DOMContentLoaded', () => {
        const role = localStorage.getItem('role');
    
        // Define sections and their fields
        const sections = {
            faculty: {
                fields: {
                    getAllFaculty: ['PROFESSOR', 'ADMIN'],
                    getFaculty: ['PROFESSOR', 'ADMIN'],
                    createFaculty: ['ADMIN'],
                    updateFaculty: ['ADMIN'],
                    deleteFaculty: ['ADMIN']
                }
            },
            courses: {
                fields: {
                    getAllCourses: ['PROFESSOR', 'ADMIN', 'STUDENT'],
                    getCourse: ['PROFESSOR', 'ADMIN', 'STUDENT'],
                    createCourse: ['ADMIN'],
                    updateCourse: ['ADMIN'],
                    deleteCourse: ['ADMIN']
                }
            },
            students: {
                fields: {
                    getAllStudents: ['PROFESSOR', 'ADMIN'],
                    getStudent: ['PROFESSOR', 'ADMIN','STUDENT'],
                    createStudent: ['ADMIN'],
                    updateStudent: ['ADMIN'],
                    deleteStudent: ['ADMIN']
                }
            },
            subjects: {
                fields: {
                    getallSubjects: ['PROFESSOR', 'ADMIN', 'STUDENT'],
                    getSubject: ['PROFESSOR', 'ADMIN', 'STUDENT'],
                    createSubject: ['ADMIN'],
                    updateSubject: ['ADMIN'],
                    deleteSubject: ['ADMIN']
                }
            },
            attendance: {
                fields: {
                    getall: ['PROFESSOR', 'ADMIN'],
                    get: ['PROFESSOR', 'ADMIN'],
                    GetByReg: ['PROFESSOR', 'ADMIN','STUDENT'],
                    create: ['PROFESSOR'],
                    update: ['PROFESSOR'],
                    delete: ['PROFESSOR']
                }
            },
            feedback: {
                fields: {
                    getAllFeedback: ['PROFESSOR', 'ADMIN'],
                    getFeedback: ['STUDENT'],
                    createFeedback: ['STUDENT'],
                    updateFeedback: ['STUDENT'],
                    deleteFeedback: ['STUDENT']
                }
            },
            results: {
                fields: {
                    getAllResults: ['PROFESSOR', 'ADMIN'],
                    getResult: ['PROFESSOR', 'STUDENT'],
                    createResult: ['PROFESSOR'],
                    updateResult: ['PROFESSOR'],
                    deleteResult: ['PROFESSOR']
                }
            }
        };
    
        // Function to show or hide fields based on role
        function updateSectionVisibility() {
            for (const sectionId in sections) {
                const section = sections[sectionId];
                const sectionElement = document.getElementById(sectionId);
                if (sectionElement) {
                    for (const fieldId in section.fields) {
                        const fieldElement = document.getElementById(fieldId);
                        if (fieldElement) {
                            if (section.fields[fieldId].includes(role)) {
                                fieldElement.style.display = 'inline-block';
                            } else {
                                fieldElement.style.display = 'none';
                            }
                        }
                    }
                }
            }
        }
    
        // Call the function on page load
        updateSectionVisibility();
    });
    
    
    