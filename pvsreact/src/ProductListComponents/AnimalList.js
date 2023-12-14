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
            fetch(`http://localhost:8080/pet/all`, {
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
        fetch(`http://localhost:8080/pet/name=${search}`, {
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
            fetch(`http://localhost:8080/pet/type=${e.target.value}`, {
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
            fetch(`http://localhost:8080/pet/all`, {
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
                            <div class={styles.image}><img src={require("../images/" + product.imageLink)} alt="" /></div>
                            <div class={styles.text}>
                                <h3>{product.name}</h3>
                                <p>{product.description}</p>
                                <p>{product.breed}</p>
                                <div className={styles.price}>For Adoption</div>
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