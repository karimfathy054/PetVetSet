import { Link } from "react-router-dom"
import styles from "../CSS/style.module.css"
import cat from "../images/Screenshot 2023-11-20 201730.png"
import { useContext, useState } from "react"
import { useNavigate } from "react-router-dom"
import { FaGoogle } from "react-icons/fa";
import { useGoogleLogin } from "@react-oauth/google";
import { useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode"
import Token from "../Token"
import { User } from "../User.js"
export default function Signin() {
    const navigate = useNavigate();
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const [token, setToken] = useState("");
    const [user, setUser] = useState([]);
    const [profile, setProfile] = useState([]);
    const [temp, setTemp] = useState(false);
    const [decode, setDecode] = useState({});

    const handleUserName = (e) => {
        setUserName(e.target.value);
    }

    const handlePassword = (e) => {
        setPassword(e.target.value);
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        // navigate('/SigninController', { replace: true, state: { userName, password } });
        fetch('http://localhost:8080/api/auth/authenticate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: userName,
                password: password
            }),
        })
            .then(response => response.json())
            .then(data => {
                createUser(data.token);
                setToken(data.token);
                setDecode(jwtDecode(data.token));
                navigate('../Home', { replace: true, state: { token: data.token, decode: jwtDecode(data.token) } });
            })
            .catch(error => { console.error('Error creating user:', error); window.alert("Account Not Found Login") });
    }

    const login = useGoogleLogin({
        onSuccess: (codeResponse) => { setUser(codeResponse); },
        onError: (error) => console.log('Login Failed:', error)
    });

    useEffect(
        () => {
            if (user) {
                axios
                    .get(`https://www.googleapis.com/oauth2/v1/userinfo?access_token=${user.access_token}`, {
                        headers: {
                            Authorization: `Bearer ${user.access_token}`,
                            Accept: 'application/json'
                        }
                    })
                    .then((res) => {
                        setProfile(res.data);
                    })
                    .then(() => setTemp(true))
                    .catch((err) => console.log("oooo" + err));
            }
        },
        [user]
    );

    useEffect(() => {
        // login();
        console.log(profile.email)
        if (temp) {
            fetch('http://localhost:8080/api/auth/authenticate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    email: profile.email,
                    password: "null"
                }),
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data)
                    setToken(data.token);
                    setDecode(jwtDecode(data.token));
                    setTemp(false);
                    createUser(data.token, profile.email);
                    // console.log(jwtDecode(data.token))
                    console.log(data.token)
                })
                .catch(error => {
                    console.error('Error creating user:', error);
                    if (temp)
                        window.alert("Account Not Found")
                    setTemp(false);
                });
        }
    })
    function createUser(token, email) {
        console.log("Here")
        console.log(email)
        let body = {
            email: email
        }
        console.log(body)
        fetch('http://localhost:8080/api/getUserByEmail', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: "Bearer " + token
            },
            body: JSON.stringify(body),
        })
            .then(response => response.json())
            .then(data => {
                console.log(data)
                let user = User.getUser()
                user.set_id(data.id)
                user.set_user_name(data.user_name)
                user.set_email(data.email)
                user.set_is_admin(data.is_admin)
                user.set_token(token)
                navigate('../Home', { replace: true, state: { token: token, decode: jwtDecode(token) } });
            })
    }
    return (
        <div className={styles.signin}>
            <div className={styles.content}>
                <form onSubmit={handleSubmit}>
                    <div className={styles.head}>Sign in</div>
                    <input type="email" placeholder="Email" value={userName} onChange={handleUserName} required></input>
                    <input type="password" placeholder="Password" value={password} onChange={handlePassword} pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Invalid Password" required></input>
                    <button type="submit">Sign in</button>
                    <Link to="/Signup">Create acount</Link>
                    <div className={styles.line}>or sign in with</div>
                    {/* <div onClick={() => { navigate('../GoogleOAuthSigninController', { replace: true, state: { token: token } }) }} className={styles.googleSign}><FaGoogle></FaGoogle> Google</div> Need to implement in google OAuth */}
                    <div onClick={() => login()} className={styles.googleSign}><FaGoogle></FaGoogle> Google</div>
                </form>
                <img src={cat}></img>
            </div>
        </div>
    )
}
