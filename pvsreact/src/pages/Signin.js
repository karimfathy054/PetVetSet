import { Link } from "react-router-dom"
import styles from "../CSS/style.module.css"
import cat from "../images/Screenshot 2023-11-20 201730.png"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import GoogleOAuth from "./GoogleOAuth"
import { FaGoogle } from "react-icons/fa";

export default function Signin() {
    const navigate = useNavigate();
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const handleUserName = (e) => {
        setUserName(e.target.value);
    }
    const handlePassword = (e) => {
        setPassword(e.target.value);
    }
    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(userName);
        console.log(password);
        navigate('/SigninController', { replace: true, state: { userName, password } });
    }
    return (
        <>
            <div className={styles.signin}>
                <div className={styles.content}>
                    <form onSubmit={handleSubmit}>
                        <div className={styles.head}>Sign in</div>
                        <input type="text" placeholder="Username" value={userName} onChange={handleUserName} required></input>
                        <input type="password" placeholder="Password" value={password} onChange={handlePassword} pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Invalid Password" required></input>
                        <button type="submit">Sign in</button>
                        <Link to="/Signup">Create acount</Link>
                        <div className={styles.line}>or sign in with</div>
                        <div onClick={() => navigate('GoogleOAuthSigninController', { replace: true })} className={styles.googleSign}><FaGoogle></FaGoogle> Google</div> {/*Need to implement in google OAuth*/}
                    </form>
                    <img src={cat}></img>
                </div>
            </div>
        </>
    )
}