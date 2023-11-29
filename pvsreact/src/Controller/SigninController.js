import { useLocation } from "react-router-dom";
export default function SigninController() {
    const location = useLocation();
    const userName = location.state.userName;
    const password = location.state.password;
    const encodedUserName = encodeURIComponent(userName);
    const encodedPassword = encodeURIComponent(password);

    const url = `http://localhost:8080/login?user_name=${encodedUserName}&password=${encodedPassword}`;
    fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ userName, password }),
    }).then((r) => r.json()).then((data) => console.log(data));
}
