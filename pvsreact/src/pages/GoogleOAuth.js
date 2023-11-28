import { useState } from "react";
import { useEffect } from "react";
import { useGoogleLogin } from "@react-oauth/google";
import { googleLogout } from "@react-oauth/google";
import styles from "../CSS/style.module.css"
import axios from 'axios';
import { FaGoogle } from "react-icons/fa";
import { useNavigate } from "react-router-dom";

export default function GoogleOAuth() {
    const [user, setUser] = useState([]);
    const [profile, setProfile] = useState([]);
    const navigate = useNavigate();

    const login = useGoogleLogin({
        onSuccess: (codeResponse) => setUser(codeResponse),
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
                    .catch((err) => console.log(err));
            }
        },
        [user]
    );

    // // log out function to log the user out of google and set the profile array to null
    // const logOut = () => {
    //     googleLogout();
    //     setProfile(null);
    // };
    return (
        <div onClick={() => { login(); console.log(profile); navigate('GoogleOAuthController', { replace: true }); }} className={styles.googleSign}><FaGoogle></FaGoogle> Google</div>
    )
}

