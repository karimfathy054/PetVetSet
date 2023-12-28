import styles from "../CSS/profileStyles.module.css";
import { useState, useEffect } from "react";
import { FaArrowRight } from "react-icons/fa";
import { FaSearch } from "react-icons/fa";
import { FaStar } from "react-icons/fa";
export default function Requests({ user }) {
    const [products, setProducts] = useState([]);
    const [pets, setPets] = useState([]);
    const [temp, setTemp] = useState(true);
    const [search, setSearch] = useState('');
    useEffect(() => {
        if (temp) {
            fetch('http://localhost:8080/api/getAllRequestProducts', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${user.token}`,
                }
            })
                .then(response => response.json())
                .then(data => {
                    setProducts(data);
                    console.log(data);
                    setTemp(false);
                })
                .catch(error => {
                    console.error('Error creating user:', error);
                    setTemp(false);
                });
        }
    })
    useEffect(() => {
        if (temp) {
            fetch('http://localhost:8080/api/getAllRequestPets', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${user.token}`,
                }
            })
                .then(response => response.json())
                .then(data => {
                    setPets(data);
                    console.log(data);
                    setTemp(false);
                })
                .catch(error => {
                    console.error('Error creating user:', error);
                    setTemp(false);
                });
        }
    })
    const handleAccept = (e) => {
        fetch(`http://localhost:8080/api/acceptRequestProduct`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${user.token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: products[e.target.id].productId
            })
        })
            .then(data => {
                console.log(data);
                window.alert("Product Accepted");
            })
            .catch(error => {
                console.error('Error creating user:', error);
            });
    }
    const handleRefuse = (e) => {
        fetch(`http://localhost:8080/api/deleteRequestProduct`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${user.token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: products[e.target.id].productId
            })
        })
            .then(data => {
                console.log(data);
                window.alert("Product Refuced");
            })
            .catch(error => {
                console.error('Error creating user:', error);
            });
    }
    const handleAcceptPet = (e) => {
        fetch(`http://localhost:8080/api/acceptRequestPet`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${user.token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: pets[e.target.id].productId
            })
        })
            .then(data => {
                console.log(data);
                window.alert("Pet Accepted");
            })
            .catch(error => {
                console.error('Error creating user:', error);
            });
    }
    const handleRefusePet = (e) => {
        fetch(`http://localhost:8080/api/refuseRequestPet`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${user.token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: pets[e.target.id].productId
            })
        })
            .then(data => {
                console.log(data);
                window.alert("Pet Refused");
            })
            .catch(error => {
                console.error('Error creating user:', error);
            });
    }
    return (
        <>
            <div id="requests1" className={styles.requests}>
                <div class={styles.container}>
                    <div class={styles.content}>
                        {products.map((product, index) => {
                            if (product.categoryName) {
                                return (
                                    (<div class={styles.box}>
                                        {product.photo ? (<div class={styles.image}><img src={require("../images/" + product.photo)} alt="" /></div>) : (<></>)}
                                        <div class={styles.text}>
                                            <h3>{product.productName}</h3>
                                            <p>{product.description}</p>
                                            <div className={styles.price}>{product.price} $</div>
                                        </div>
                                        <div className={styles.brand}><p>Brand</p>{product.brandName}</div>
                                        <div className={styles.category}><p>Category</p>{product.categoryName}</div>
                                        <div className={styles.actionProduct}>
                                            <div className={styles.accept} onClick={handleAccept} id={index}>Accept</div>
                                            <div className={styles.refuse} onClick={handleRefuse} id={index}>Refuse</div>
                                        </div>
                                    </div>
                                    )
                                )
                            }
                        })}
                        {pets.map((product, index) => {
                            return (
                                (<div class={styles.box}>
                                    {product.imageLink ? (<div class={styles.image}><img src={require("../images/" + product.imageLink)} alt="" /></div>) : (<></>)}
                                    <div class={styles.text}>
                                        <h3>{product.productName}</h3>
                                        <p>{product.description}</p>
                                    </div>
                                    <div className={styles.brand}><p>Type</p>{product.type}</div>
                                    <div className={styles.brand}><p>Birth Date</p>{product.birthDate}</div>
                                    <div className={styles.category}><p>Breed</p>{product.breed}</div>
                                    <div className={styles.actionProduct}>
                                        <div className={styles.accept} onClick={handleAcceptPet} id={index}>Accept</div>
                                        <div className={styles.refuse} onClick={handleRefusePet} id={index}>Refuse</div>
                                    </div>
                                </div>
                                )
                            )
                        })}
                    </div>
                </div>
            </div>
        </>
    )
}