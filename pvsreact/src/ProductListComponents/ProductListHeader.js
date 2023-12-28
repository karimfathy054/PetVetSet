import { useEffect, useState } from "react";
import styles from "../CSS/List.module.css"
import logo from "../images/logo.png"
import { FaUser } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { User } from "../User";

export default function ProductListHeader({ user }) {
    const [name, setName] = useState("");
    const navigate = useNavigate();
    const [mainUser, setMainUser] = useState({});
    const handleHome = () => {
        navigate('/', { replace: true });
    }
    const handleAnimals = () => {
        navigate('../Animals', { replace: true, state: { user } });
    }
    const handleProducts = () => {
        navigate('../ProductList', { replace: true, state: { user } });
    }
    const handleUser = () => {
        navigate('/Profile', { replace: true, state: { user } });
    }
    const handleAddProduct = () => {
        console.log("ERERE")
        navigate('/RequestForm', { replace: true, state: { user } });
    }
    const handleAddAnimal = () => {
        navigate('/RequestAnimalForm', { replace: true, state: { user } });
    }
    const handleCart = () => {
        navigate('/Cart', { replace: true, state: { user } });
    }
    return (
        <div className={styles.header}>
            <div className={styles.welcome}>
                <p className={styles.semititle}>PVS</p>
                <div className={styles.user} onClick={handleUser}><FaUser /> {user.userName}</div>
            </div>
            <ul>
                <li><a onClick={handleHome}>Home</a></li>
                <li><a onClick={handleAnimals} id={styles.animals}>Animals</a></li>
                <li><a onClick={handleProducts} id={styles.products}>Products</a></li>
                <li><a onClick={handleAddProduct} id={styles.addProduct}>Add Product</a></li>
                <li><a onClick={handleAddAnimal} >Add Animal</a></li>
                <li><a onClick={handleCart} id={styles.cart}>Cart</a></li>
            </ul>
        </div>
    )
}