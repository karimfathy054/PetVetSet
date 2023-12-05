import { useEffect, useState } from "react";
// import {axios} from "axios";
export default function GoogleOAuthSigninController() {
    // const xhr = new XMLHttpRequest();
    // xhr.open("GET", "http://localhost:8080/oauthLogin");
    
    // xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
    // xhr.onload = () => {
    // if (xhr.readyState == 4 && xhr.status == 200) {
    //     console.log(xhr.responseText);
    // } else {
    //     console.log(`Error: ${xhr.status}`);
    // }
    // };
    // xhr.send();
//     var result=""
//     const[tes, setTes]=useState("")
//     const[sent, setSent]=useState(false)
//     useEffect(() =>{
//         const xhr = new XMLHttpRequest();
//         xhr.open("GET", "http://localhost:8080/");
//         xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
//         if(sent===false){
//             setSent(true)
//             console.log("here")
//             xhr.send();
//         }
//         xhr.onload = () => {
//         if (xhr.readyState == 4 && xhr.status == 200) {
//             result = xhr.responseText
//             console.log(xhr.responseText);
//             setTes(xhr.responseText)
//         } else {
//             console.log(`Error: ${xhr.status}`);
//         }
//         };
//   })
//   return (
//           <>
//             <div>
//               <p>
//                 Request Successfull
//               </p>
//               <p>
//                 {tes}
//               </p>
//             </div>
//           </>
//   )

window.open("http://localhost:8080/")

// axios.request({
//   url: "/oauth/token",
//   method: "post",
//   baseURL: "http://sample.oauth.server.com/",
//   auth: {
//     username: "myUsername", // This is the client_id
//     password: "myPassword" // This is the client_secret
//   },
//   data: {
//     "grant_type": "client_credentials",
//     "scope": "public"    
//   }
// }).then(respose => {
//   console.log(respose);  
// });
}
