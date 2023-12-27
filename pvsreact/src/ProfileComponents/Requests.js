import styles from "../CSS/profileStyles.module.css";
import { useState, useEffect } from "react";
import { FaArrowRight } from "react-icons/fa";
import { FaSearch } from "react-icons/fa";
import { FaStar } from "react-icons/fa";
export default function Requests({ user }) {
    const [products, setProducts] = useState([]);
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
                                        {(<div class={styles.image}><img src={require("../images/" + product.photo)} alt="" /></div>)}
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
                            else {
                                return (
                                    (<div class={styles.box}>
                                        {(<div class={styles.image}><img src={require("../images/" + product.photo)} alt="" /></div>)}
                                        <div class={styles.text}>
                                            <h3>{product.productName}</h3>
                                            <p>{product.description}</p>
                                            <div className={styles.price}>{product.price} $</div>
                                        </div>
                                        <div className={styles.brand}><p>Brand</p>{product.brandName}</div>
                                        <div className={styles.category}><p>Category</p>{product.categoryName}</div>
                                    </div>)
                                )
                            }
                        })}
                    </div>
                </div>
            </div>
        </>
    )
}