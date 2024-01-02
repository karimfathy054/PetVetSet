import styles from "../CSS/profileStyles.module.css";
import { useState, useEffect } from "react";
import { FaArrowRight } from "react-icons/fa";
import { FaSearch } from "react-icons/fa";
import { FaStar } from "react-icons/fa";
import { FaBookmark } from "react-icons/fa";
import done from "../images/done.png"
export default function BookMark({ user }) {
    const [products, setProducts] = useState([]);
    const [temp, setTemp] = useState(true);
    const [search, setSearch] = useState('');
    const [specialProduct, setSpecialProduct] = useState({});
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

    const handleRemove = (product) => {
        fetch(`http://localhost:8080/bookmarks/remove/user=${user.id}&&product=${product.id}`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${user.token}`,
            }
        })
            .then(data => {
                console.log(data);
                const done = document.getElementById("serviceDone");
                done.style.display = "block";
                setTemp(true)
            })
            .catch(error => {
                console.error('Error creating user:', error);
                setTemp(true)
            });
    }
    const handleRemoveBookMark = (e) => {
        handleRemove(products[e.target.id])
    }
    const handleCloseDone = () => {
        const done = document.getElementById("serviceDone");
        done.style.display = "none";
    }
    return (
        <>
            <div id="bookmark1" className={styles.bookmark}>
                <div className={styles.done} id="serviceDone">
                    <div className={styles.cover}>
                        <div className={styles.close} onClick={handleCloseDone}>x</div>
                        <p>Product Removed Successfully</p>
                        <img src={done}></img>
                        <button onClick={handleCloseDone}>Continue</button>
                    </div>
                </div>
                <div class={styles.container}>
                    <div class={styles.content}>
                        {products.map((product, index) => {
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
                                    <div className={styles.book} onClick={handleRemoveBookMark} id={index} >Remove</div>
                                </div>
                            )
                        })}
                    </div>
                </div>
            </div>
        </>
    )
}