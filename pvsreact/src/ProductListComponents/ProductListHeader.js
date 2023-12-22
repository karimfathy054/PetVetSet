import { useEffect, useState } from "react";
import styles from "../CSS/List.module.css"
import logo from "../images/logo.png"
import { FaUser } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { User } from "../User";

export default function ProductListHeader({ token, decode }) {
    const [name, setName] = useState("");
    const navigate = useNavigate();
    const [mainUser, setMainUser] = useState({});
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
                setName(data.user_name);
                setMainUser(data);
            })
            .catch(error => {
                console.error('Error creating user:', error);
            });
    })
    const handleHome = () => {
        navigate('../Home', { replace: true, state: { token: token, decode: decode } });
    }
    const handleAnimals = () => {
        navigate('../Animals', { replace: true, state: { token: token, decode: decode } });
    }
    const handleProducts = () => {
        navigate('../ProductList', { replace: true, state: { token: token, decode: decode } });
    }
    const handleUser = () => {
        navigate('/Profile', { replace: true, state: { token: token, decode: decode } });
    }
    const handleAddProduct = () => {
        console.log("ERERE")
        navigate('/RequestForm', { replace: true, state: { mainUser: mainUser, token: token, decode: decode } });
    }
    return (
        <div className={styles.header}>
            <div className={styles.welcome}>
                <p className={styles.semititle}>PVS</p>
                <div className={styles.user} onClick={handleUser}><FaUser /> {name}</div>
            </div>
            <ul>
                <li><a onClick={handleHome}>Home</a></li>
                <li><a onClick={handleAnimals}>Animals</a></li>
                <li><a onClick={handleProducts}>Products</a></li>
                <li><a onClick={handleAddProduct}>Add Product</a></li>
                <li><a>Cart</a></li>
            </ul>
        </div>
    )
}