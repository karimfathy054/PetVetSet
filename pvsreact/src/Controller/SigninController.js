// import { useEffect } from "react";
// import { useState } from "react";
// import { useLocation } from "react-router-dom";
// export default function SigninController() {
//     const location = useLocation();
//     const email = location.state.userName;
//     const password = location.state.password;
//     const encodedUserName = encodeURIComponent(email);
//     const encodedPassword = encodeURIComponent(password);
//   // async function postData(url = '', data = {}) { 
//   //   // Default options are marked with * 
//   //   const response = await fetch(url, { 
//   //     method: 'POST', // *GET, POST, PUT, DELETE, etc. 
//   //     headers: { 
//   //       'Content-Type': 'application/json', 
//   //       // You can add other headers as needed 
//   //     }, 
//   //     body: JSON.stringify(data), // body data type must match "Content-Type" header 
//   //   }); 
   
//   //   // Check if the request was successful (status in the range 200-299) 
//   //   if (!response.ok) { 
//   //     throw new Error(`HTTP error! Status: ${response.status}`); 
//   //   } 
   
//   //   // Parse the response as JSON and return 
//   //   return response.json(); 
//   // } 
//   //  var ress
//   // // Example usage 
//   // (async () => { 
//   //   try { 
//   //     const data = { email: email, password: password}; 
//   //     const url = 'http://localhost:8080/api/login'; 
//   //     console.log("Sending",data)
//   //     const result = await postData(url, data); 
//   //     console.log('Response:', result); 
//   //     ress = result
//   //   } catch (error) { 
//   //     console.error('Error:', error); 
//   //   } 
//   // })();

//   var result=""
//   const[tes, setTes]=useState("")
//   const[sent, setSent]=useState(false)
//   useEffect(() =>{
//       const xhr = new XMLHttpRequest();
//       xhr.open("POST", "http://localhost:8080/api/login");
//       xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
//       const body = JSON.stringify({
//           email: email,
//           password: password
//       });
//       if(sent===false){
//           setSent(true)
//           console.log("here")
//           xhr.send(body);
//       }
//       xhr.onload = () => {
//       if (xhr.readyState == 4 && xhr.status == 200) {
//           result = xhr.responseText
//           console.log(xhr.responseText);
//           setTes(xhr.responseText)
//       } else {
//           console.log(`Error: ${xhr.status}`);
//       }
//       };
// })
// return (
//         <>
//           <div>
//             <p>
//               Request Successfull
//             </p>
//             <p>
//               {tes}
//             </p>
//           </div>
//         </>
// )


// }

