import { useEffect, useState } from "react";
import styles from "../CSS/HomeStyle.module.css"
import logo from "../images/logo.png"
import { FaUser } from "react-icons/fa";
import axios from "axios";
export default function Header({ token, decode }) {
    const [name, setName] = useState("");
    useEffect(() => {
        fetch(`http://localhost:8080/api/getUserByEmail`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                "Accept": 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: decode.sub,
            }),
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                setName(data.user_name);
            })
            .catch(error => {
                console.error('Error creating user:', error);
            });
    })
    return (
        <div className={styles.header}>
            <div className={styles.welcome}>
                <p className={styles.semititle}>PVS</p>
                <img src={logo} className={styles.logo}></img>
                <div className={styles.user} ><FaUser /> {name}</div>
            </div>
            <div className={styles.title}>PetVetSet</div>
            <ul>
                <li><a href="#home">Home</a></li>
                <li><a href="#services">Services</a></li>
                <li><a href="#products">Products</a></li>
                <li><a href="#about">About US</a></li>
                <li><a href="#contact">Contact US</a></li>
            </ul>
        </div>
    )
}