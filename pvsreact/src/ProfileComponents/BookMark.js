import styles from "../CSS/profileStyles.module.css";
import { useState, useEffect } from "react";
import { FaArrowRight } from "react-icons/fa";
import { FaSearch } from "react-icons/fa";
import { FaStar } from "react-icons/fa";
export default function BookMark({ user }) {
    const [products, setProducts] = useState([]);
    const [temp, setTemp] = useState(true);
    const [search, setSearch] = useState('');
    useEffect(() => {
        if (temp) {
            fetch(`http://localhost:8080/bookmarks/${user.id}`, {
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
                    console.log("77777777777777777777")
                    console.error('Error creating user:', error);
                    setTemp(false);
                });
        }
    })

    return (
        <>
            <div id="bookmark1" className={styles.bookmark}>
                <div class={styles.container}>
                    <div class={styles.content}>
                        {products.map((product) => {
                            return (
                                <div class={styles.box}>
                                    <div class={styles.image}><img src={require("../images/" + product.imageLink)} alt="" /></div>
                                    <div class={styles.text}>
                                        <h3>{product.productName}</h3>
                                        <p>{product.description}</p>
                                        <div className={styles.price}>{product.price} $</div>
                                    </div>
                                    <div className={styles.rateAndAdd}>
                                        <div className={styles.rate}>
                                            <FaStar className={styles.i} />
                                            <div className={styles.rating}>{parseFloat(product.rating).toFixed(2)} /10</div>
                                        </div>
                                    </div>
                                </div>
                            )
                        })}
                    </div>
                </div>
            </div>
        </>
    )
}