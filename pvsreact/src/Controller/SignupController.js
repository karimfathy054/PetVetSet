import { useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
export default function SignupController() {
    const location = useLocation();
    const userName = location.state.userName;
    const password = location.state.password;
    const email = location.state.email;
    const encodedUserName = encodeURIComponent(userName);
    const encodedPassword = encodeURIComponent(password);
    const encodedEmail = encodeURIComponent(email);
    console.log("email:",encodedEmail);

    // const url = `http://localhost:8080/signup?user_name=${encodedUserName}&password=${encodedPassword}&email=${encodedEmail}`;
    // fetch(url, {
    //     method: "POST",
    //     headers: {
    //         "Content-Type": "application/json",
    //     },
    //     body: JSON.stringify({ userName, password, email }),
    // }).then((r) => r.json()).then((data) => console.log(data));
    var result=""
    const[tes, setTes]=useState("")
    const[sent, setSent]=useState(false)
    useEffect(() =>{
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/api/signup");
        xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
        const body = JSON.stringify({
            email: email,
            password: password,
            user_name: userName
        });
        if(sent===false){
            setSent(true)
            console.log("here")
            xhr.send(body);
        }
        xhr.onload = () => {
        if (xhr.readyState == 4 && xhr.status == 200) {
            result = xhr.responseText
            console.log(xhr.responseText);
            setTes(xhr.responseText)
        } else {
            console.log(`Error: ${xhr.status}`);
        }
        };
})
return (
  <>
    <div>
      <p>
        Request Successfull
      </p>
      <p>
        {tes}
      </p>
    </div>
  </>
)

//     async function postData(url = '', data = {}) { 
//   // Default options are marked with * 
//   const response = await fetch(url, { 
//     method: 'POST', // *GET, POST, PUT, DELETE, etc. 
//     headers: { 
//       'Content-Type': 'application/json', 
//       // You can add other headers as needed 
//     }, 
//     body: JSON.stringify(data), // body data type must match "Content-Type" header 
//   }); 
 
//   // Check if the request was successful (status in the range 200-299) 
//   if (!response.ok) { 
//     throw new Error(`HTTP error! Status: ${response.status}`); 
//   } 
 
//   // Parse the response as JSON and return 
//   return (response); 
// } 
 
// Example usage 
// (async () => { 
//   try { 
//     const data = { email: email, password: password , user_name: userName}; 
//     const url = 'http://localhost:8080/api/signup'; 
//     console.log("Sending",data)
//     const result = await postData(url, data); 
//     console.log('Response:', result); 
//   } catch (error) { 
//     console.error('Error:', error); 
//   } 
// })();
}
