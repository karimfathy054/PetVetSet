import styles from "../CSS/profileStyles.module.css";
import { useState, useEffect } from "react";
import { FaArrowRight } from "react-icons/fa";
import { FaSearch } from "react-icons/fa";
import { FaStar } from "react-icons/fa";
export default function MyProducts({ user }) {
    const [products, setProducts] = useState([]);
    const [temp, setTemp] = useState(true);
    const [search, setSearch] = useState('');
    useEffect(() => {
        if (temp) {
            fetch('http://localhost:8080/products/all', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${user.token}`,
                }
            })
                .then(response => response.json())
                .then(data => {
                    setProducts(data);
                    // console.log(data);
                    setTemp(false);
                })
                .catch(error => {
                    console.error('Error creating user:', error);
                    setTemp(false);
                });
        }
    })
    const handleSearch = () => {
        fetch(`http://localhost:8080/products/name=${search}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${user.token}`,
            }
        })
            .then(response => response.json())
            .then(data => {
                setProducts(data);
                // console.log(data);
                setTemp(false);
            })
            .catch(error => {
                console.error('Error creating user:', error);
                setTemp(false);
            });
    }
    const handleFilter = (e) => {
        if (e.target.value != "all") {
            fetch(`http://localhost:8080/products/category=${e.target.value}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${user.token}`,
                }
            })
                .then(response => response.json())
                .then(data => {
                    setProducts(data);
                    // console.log(data);
                    setTemp(false);
                })
                .catch(error => {
                    console.error('Error creating user:', error);
                    setTemp(false);
                });
        }
        else {
            fetch(`http://localhost:8080/products/all`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${user.token}`,
                }
            })
                .then(response => response.json())
                .then(data => {
                    setProducts(data);
                    // console.log(data);
                    setTemp(false);
                })
                .catch(error => {
                    console.error('Error creating user:', error);
                    setTemp(false);
                });
        }
    }
    return (
        <>
            <div id="myproducts1" className={styles.myproducts}>
                <div class={styles.container}>
                    <div className={styles.action}>
                        <form onSubmit={(e) => { e.preventDefault(); handleSearch() }}>
                            <input type="text" placeholder="Search" value={search} onChange={(e) => setSearch(e.target.value)}></input>
                            <FaSearch className={styles.searchIcon} onClick={handleSearch} />
                        </form>
                        <select onChange={handleFilter}>
                            <option value="all">All Products</option>
                            <option value="food">Dry Food</option>
                            <option value="accessories">Accessories</option>
                            <option value="ariika">Ariika</option>
                            <option value="frontline">Frontline</option>
                            <option value="canin">Royal Canin</option>
                            <option value="toys">Toys</option>
                            <option value="medicine">Medicine</option>
                        </select>
                    </div>
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
                                    <div class={styles.info}>
                                        <a>Read Me</a>
                                        <FaArrowRight className={styles.i} />
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