import { useLocation } from "react-router-dom";
export default function SignupController() {
    const location = useLocation();
    const userName = location.state.userName;
    const password = location.state.password;
    const email = location.state.email;
    const encodedUserName = encodeURIComponent(userName);
    const encodedPassword = encodeURIComponent(password);
    const encodedEmail = encodeURIComponent(email);
    console.log(encodedEmail);

    const url = `http://localhost:8080/signup?user_name=${encodedUserName}&password=${encodedPassword}&email=${encodedEmail}`;
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ userName, password, email }),
    }).then((r) => r.json()).then((data) => console.log(data));

}
