import dog from "../images/Sign-In-1.jpg"
import { FaHome } from "react-icons/fa";
import styles from '../CSS/style.module.css'
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { FaGoogle } from "react-icons/fa";
import { Link } from "react-router-dom";
import { useLocation } from 'react-router-dom';
import { GoogleOAuthProvider } from "@react-oauth/google";
import { useGoogleOAuth } from "@react-oauth/google";
import { useGoogleLogin } from "@react-oauth/google";
import { useEffect } from "react";
// import axios from "axios";

export default function Signup() {

    const location = useLocation();
    const navigate = useNavigate();
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const [confirmedPassword, setConfirmedPassword] = useState("");
    const [email, setEmail] = useState("");
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
        fetch('http://localhost:8080/api/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: email,
                password: password,
                user_name: userName
            }),
        })
            .then(response => response.json())
            .then(response => console.log('User :', response))
            .catch(error => console.error('Error creating user:', error));

        //test
        fetch('http://localhost:8080/api/hello',{method:'GET'})
        .then (response => response.json())
        .then(response => console.log("Res:",response))
        // .catch(error => console.log("Error:", error))

    }



    //new code
    const [user, setUser] = useState([]);
    const [profile, setProfile] = useState([]);
    const { oauthTokens, signIn } = useGoogleOAuth();
    //end code
    const handleGoogleOAuth = async (e) => {
        // e.preventDefault();
        try {
            await signIn;
            const accessToken = oauthTokens.access_token;

            // Use the accessToken to make authorized requests
            const response = await fetch('http://localhost:8080/oauthSignUp', {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
            });

            const data = await response.json();
            console.log('Protected Resource Data:', data);
        } catch (error) {
            console.error('Error signing in:', error);
        }
    };


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
                        <div onClick={handleGoogleOAuth} className={styles.googleSign}><FaGoogle></FaGoogle> Google</div>
                    </form>
                    <img src={dog}></img>
                </div>
            </div>
        </>
    )
}
