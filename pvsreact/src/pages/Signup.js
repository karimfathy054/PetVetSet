import dog from "../images/Sign-In-1.jpg"
import { FaHome } from "react-icons/fa";
import styles from '../CSS/style.module.css'
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { FaGoogle } from "react-icons/fa";
import { Link } from "react-router-dom";
import { useLocation } from 'react-router-dom';
import { useGoogleLogin } from "@react-oauth/google";
import { useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode"
export default function Signup() {

    const navigate = useNavigate();
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const [confirmedPassword, setConfirmedPassword] = useState("");
    const [email, setEmail] = useState("");
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
    const handleConfirmedPassword = (e) => {
        setConfirmedPassword(e.target.value);
    }
    const handleEmail = (e) => {
        setEmail(e.target.value);
    }
    const handleSubmit = (e) => {
        e.preventDefault();
        // navigate('/SignupController', { replace: true, state: { userName, password, email } });
        fetch('http://localhost:8080/api/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: email,
                password: password,
                userName: userName
            }),
        })
            .then(response => response.json())
            .then(data => {
                setToken(data.token);
                setDecode(jwtDecode(data.token));
                navigate('../Home', { replace: true, state: { token: data.token, decode: jwtDecode(data.token) } });
            })
            .catch(error => { console.error('Error creating user:', error); window.alert("Account Is Already Exist"); });

    }



    //new code
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
        if (temp) {
            fetch('http://localhost:8080/api/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    email: profile.email,
                    password: "null",
                    userName: profile.name
                }),
            })
                .then(response => response.json())
                .then(data => {
                    setToken(data.token);
                    setDecode(jwtDecode(data.token));
                    setTemp(false);
                    navigate('../Home', { replace: true, state: { token: data.token, decode: jwtDecode(data.token) } });
                })
                .catch(error => {
                    console.error('Error creating user:', error);
                    if (temp)
                        window.alert("Account Is Already Exist!")
                    setTemp(false);
                });
        }
    })
    return (
        <>
            <div className={styles.signin}>
                <div className={styles.content}>
                    <form onSubmit={handleSubmit}>
                        <Link to="/Signin" className={styles.home}><FaHome></FaHome> Return</Link>
                        <div className={styles.headup}>Sign up</div>
                        <input type="text" placeholder="Username" value={userName} onChange={handleUserName} required></input>
                        <input type="password" placeholder="Password" value={password} onChange={handlePassword} pattern="(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" required></input>
                        <input type="password" placeholder="Confirm Password" value={confirmedPassword} onChange={handleConfirmedPassword} pattern={password} title="Password and Confirm Password does not match." required></input>
                        <input type="email" placeholder="Email" value={email} onChange={handleEmail}></input>
                        <button type="submit">Sign Up</button>
                        {/* <div onClick={() => navigate('../GoogleOAuthSignupController', { replace: true })} className={styles.googleSign}><FaGoogle></FaGoogle> Google</div> */}
                        <div onClick={() => { login(); }} className={styles.googleSign} ><FaGoogle></FaGoogle> Google</div>
                    </form>
                    <img src={dog}></img>
                </div>
            </div>
        </>
    )
}
