import { useEffect, useState } from "react"
import styles from "../CSS/List.module.css"
import { FaArrowRight } from "react-icons/fa";
import { FaSearch } from "react-icons/fa";
import { FaStar } from "react-icons/fa";
export default function AnimalList({ token }) {
    const [products, setProducts] = useState([]);
    const [temp, setTemp] = useState(true);
    const [search, setSearch] = useState('');
    const [rate, setRate] = useState(0);
    useEffect(() => {
        if (temp) {
            fetch(`http://localhost:8080/products/category=${"pet"}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
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
    }
    )
    const handleSearch = () => {
        fetch(`http://localhost:8080/products/name=${search}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
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
    const handleFilter = (e) => {
        if (e.target.value != "all") {
            fetch(`http://localhost:8080/products/Target_Animal=${e.target.value}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
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
        else {
            fetch(`http://localhost:8080/products/category=${"pet"}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
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
    }
    const handleRating = (e) => {
        e.preventDefault();
        console.log(e.target.id)
        console.log(e.target[0].value)
        fetch(`http://localhost:8080/products/rate=${e.target[0].value}&&id=${e.target.id}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
            }
        })
            // .then(response => response.json())
            .then(data => {
                console.log(data);
                setTemp(false);
            })
            .catch(error => {
                console.error('Error creating user:', error);
                setTemp(false);
            });
        const contact = document.getElementById("rate" + e.target.id);
        contact.style.display = "none";
    }
    const handleRate = (e) => {
        const contact = document.getElementById("rate" + e.target.id);
        contact.style.display = "block";
    }
    return (
        <div class={styles.list}>
            <div class={styles.container}>
                <h2 class={styles.heading}>Animals</h2>
                <div className={styles.action}>
                    <form onSubmit={(e) => { e.preventDefault(); handleSearch() }}>
                        <input type="text" placeholder="Search" value={search} onChange={(e) => setSearch(e.target.value)}></input>
                        <FaSearch className={styles.searchIcon} onClick={handleSearch} />
                    </form>
                    <select onChange={handleFilter}>
                        <option value="all">All Animals</option>
                        <option value="cat">Cats</option>
                        <option value="dog">Dogs</option>
                        <option value="bird">Birds</option>
                    </select>
                </div>
                <div class={styles.content}>
                    {products.map((product) => {
                        return (<div class={styles.box} key={product.id}>
                            <div class={styles.image}><img src={require("../" + product.imageLink)} alt="" /></div>
                            <div class={styles.text}>
                                <h3>{product.productName}</h3>
                                <p>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Reprehenderit</p>
                                <div className={styles.price}>{product.price} $</div>
                            </div>
                            <div className={styles.rateAndAdd}>
                                <div className={styles.rate}>
                                    <FaStar className={styles.i} />
                                    <div className={styles.rating}>{product.rating} /10</div>
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
                        </div>)
                    })}
                </div>
            </div>
        </div>
    )
}