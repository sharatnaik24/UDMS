export async function getAll(){
    let token=localStorage.getItem('token');
    console.log(token);
    if(token){
    try{
        const response= await fetch(`http://localhost:8080/attendance/getAll`, {
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
return data;
}
catch(error){
throw new Error("there is an error");
}
}
}