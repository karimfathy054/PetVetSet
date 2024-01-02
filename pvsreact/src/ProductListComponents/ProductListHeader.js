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
        // navigate('../Animals', { replace: true, state: { user } });
        document.getElementById("product").style.display = "none";
        document.getElementById("cart").style.display = "none";
        document.getElementById("pet").style.display = "block";
        document.getElementById("uploadProduct").style.display = "none";
        document.getElementById("uploadPet").style.display = "none";
        document.getElementById(styles.products).style.color = "black";
        document.getElementById(styles.products).style.fontWeight = "400";
        document.getElementById(styles.addProduct).style.color = "black";
        document.getElementById(styles.addProduct).style.fontWeight = "400";
        document.getElementById(styles.animals).style.color = "#f22c5c";
        document.getElementById(styles.animals).style.fontWeight = "bold";
        document.getElementById(styles.addPet).style.color = "black";
        document.getElementById(styles.addPet).style.fontWeight = "400";
        document.getElementById(styles.carts).style.color = "black";
        document.getElementById(styles.carts).style.fontWeight = "400";
    }
    const handleProducts = () => {
        // navigate('../ProductList', { replace: true, state: { user } });
        document.getElementById("product").style.display = "block";
        document.getElementById("cart").style.display = "none";
        document.getElementById("pet").style.display = "none";
        document.getElementById("uploadProduct").style.display = "none";
        document.getElementById("uploadPet").style.display = "none";
        document.getElementById(styles.products).style.color = "#f22c5c";
        document.getElementById(styles.products).style.fontWeight = "bold";
        document.getElementById(styles.addProduct).style.color = "black";
        document.getElementById(styles.addProduct).style.fontWeight = "400";
        document.getElementById(styles.animals).style.color = "black";
        document.getElementById(styles.animals).style.fontWeight = "400";
        document.getElementById(styles.addPet).style.color = "black";
        document.getElementById(styles.addPet).style.fontWeight = "400";
        document.getElementById(styles.carts).style.color = "black";
        document.getElementById(styles.carts).style.fontWeight = "400";
    }
    const handleUser = () => {
        navigate('/Profile', { replace: true, state: { user } });
    }
    const handleAddProduct = () => {
        // console.log("ERERE")
        // navigate('/RequestForm', { replace: true, state: { user } });
        document.getElementById("product").style.display = "none";
        document.getElementById("cart").style.display = "none";
        document.getElementById("pet").style.display = "none";
        document.getElementById("uploadProduct").style.display = "block";
        document.getElementById("uploadPet").style.display = "none";
        document.getElementById(styles.products).style.color = "black";
        document.getElementById(styles.products).style.fontWeight = "400";
        document.getElementById(styles.addProduct).style.color = "#f22c5c";
        document.getElementById(styles.addProduct).style.fontWeight = "bold";
        document.getElementById(styles.animals).style.color = "black";
        document.getElementById(styles.animals).style.fontWeight = "400";
        document.getElementById(styles.addPet).style.color = "black";
        document.getElementById(styles.addPet).style.fontWeight = "400";
        document.getElementById(styles.carts).style.color = "black";
        document.getElementById(styles.carts).style.fontWeight = "400";
    }
    const handleAddAnimal = () => {
        // navigate('/RequestAnimalForm', { replace: true, state: { user } });
        document.getElementById("product").style.display = "none";
        document.getElementById("cart").style.display = "none";
        document.getElementById("pet").style.display = "none";
        document.getElementById("uploadProduct").style.display = "none";
        document.getElementById("uploadPet").style.display = "block";
        document.getElementById(styles.products).style.color = "black";
        document.getElementById(styles.products).style.fontWeight = "400";
        document.getElementById(styles.addProduct).style.color = "black";
        document.getElementById(styles.addProduct).style.fontWeight = "400";
        document.getElementById(styles.animals).style.color = "black";
        document.getElementById(styles.animals).style.fontWeight = "400";
        document.getElementById(styles.addPet).style.color = "#f22c5c";
        document.getElementById(styles.addPet).style.fontWeight = "bold";
        document.getElementById(styles.carts).style.color = "black";
        document.getElementById(styles.carts).style.fontWeight = "400";
    }
    const handleCart = () => {
        // navigate('/Cart', { replace: true, state: { user } });
        document.getElementById("product").style.display = "none";
        document.getElementById("cart").style.display = "block";
        document.getElementById("pet").style.display = "none";
        document.getElementById("uploadProduct").style.display = "none";
        document.getElementById("uploadPet").style.display = "none";
        document.getElementById(styles.products).style.color = "black";
        document.getElementById(styles.products).style.fontWeight = "400";
        document.getElementById(styles.addProduct).style.color = "black";
        document.getElementById(styles.addProduct).style.fontWeight = "400";
        document.getElementById(styles.animals).style.color = "black";
        document.getElementById(styles.animals).style.fontWeight = "400";
        document.getElementById(styles.addPet).style.color = "black";
        document.getElementById(styles.addPet).style.fontWeight = "400";
        document.getElementById(styles.carts).style.color = "#f22c5c";
        document.getElementById(styles.carts).style.fontWeight = "bold";
    }
    return (
        <div className={styles.header}>
            <div className={styles.welcome}>
                <p className={styles.semititle}>PVS</p>
                <div className={styles.user} onClick={handleUser}><FaUser /> {user.userName}</div>
            </div>
            <ul>
                <li><a onClick={handleHome}>Home</a></li>
                <li><a onClick={handleProducts} id={styles.products} >Products</a></li>
                <li><a onClick={handleAddProduct} id={styles.addProduct} >Add Product</a></li>
                <li><a onClick={handleAnimals} id={styles.animals}>Pets</a></li>
                <li><a onClick={handleAddAnimal} id={styles.addPet} >Add Pet</a></li>
                <li><a onClick={handleCart} id={styles.carts}>Cart</a></li>
            </ul>
        </div>
    )
}