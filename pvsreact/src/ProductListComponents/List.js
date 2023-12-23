import { useEffect, useState } from "react"
import styles from "../CSS/List.module.css"
import { FaArrowRight } from "react-icons/fa";
import { FaSearch } from "react-icons/fa";
import { FaStar } from "react-icons/fa";
export default function List({ user }) {
    const [products, setProducts] = useState([]);
    const [temp, setTemp] = useState(true);
    const [search, setSearch] = useState('');
    const [rate, setRate] = useState(0);
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
    }
    )
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
    const handleRating = (e) => {
        e.preventDefault();
        console.log(e.target.id)
        console.log(e.target[0].value)
        fetch(`http://localhost:8080/products/rate=${e.target[0].value}&&id=${e.target.id}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${user.token}`,
            }
        })
            .then(response => response.json())
            .then(data => {
                setTemp(false);
            })
            .catch(error => {
                console.error('Error creating user:', error);
                setTemp(false);
            });
        const contact = document.getElementById("rate" + e.target.id);
        contact.style.display = "none";
        fetch('http://localhost:8080/products/all', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${user.token}`,
            }
        })
            .then(response => response.json())
            .then(data => {
                setProducts(data);
                setTemp(false);
            })
            .catch(error => {
                console.error('Error creating user:', error);
                setTemp(false);
            });
    }
    const handleRate = (e) => {
        const contact = document.getElementById("rate" + e.target.id);
        contact.style.display = "block";
    }
    return (
        <div class={styles.list}>
            <div class={styles.container}>
                <h2 class={styles.heading}>Products</h2>
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
                                    <div className={styles.add}>
                                        <div onClick={handleRate} className={styles.addRating} id={product.id}>Add Rating</div>
                                        <div className={styles.type} id={"rate" + product.id}>
                                            <form id={product.id} onSubmit={handleRating}>
                                                <input type="number" min="0" max="10" step="0.01" value={rate} onChange={(e) => setRate(e.target.value)} ></input>
                                            </form>
                                        </div>
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
    )
}
