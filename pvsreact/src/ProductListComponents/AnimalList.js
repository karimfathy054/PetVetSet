import { useEffect, useState } from "react"
import styles from "../CSS/List.module.css"
import { FaArrowRight } from "react-icons/fa";
import { FaSearch } from "react-icons/fa";
import { FaStar } from "react-icons/fa";
import { FaBookmark } from "react-icons/fa";
import { FaCartShopping } from "react-icons/fa6";
import { IoClose } from "react-icons/io5";
import { useCookies } from "react-cookie";
export default function AnimalList({ user }) {
    const [products, setProducts] = useState([]);
    const [specialProduct, setSpecialProduct] = useState({});
    const [temp, setTemp] = useState(true);
    const [search, setSearch] = useState('');
    const [rate, setRate] = useState(0);
    const [cookies, setCookie] = useCookies(["cart"]);
    function handleCartCookies(cart) {
        setCookie("cart", cart, { path: "/" });
    }
    useEffect(() => {
        if (temp) {
            fetch(`http://localhost:8080/pet/all`, {
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
    }
    )
    const handleSearch = () => {
        fetch(`http://localhost:8080/pet/name=${search}`, {
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
    const handleFilter = (e) => {
        if (e.target.value != "all") {
            fetch(`http://localhost:8080/pet/type=${e.target.value}`, {
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
        else {
            fetch(`http://localhost:8080/pet/all`, {
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
    }
    const handleReadMe = (e) => {
        setSpecialProduct(products[e.target.id]);
        console.log(products[e.target.id]);
        document.getElementsByClassName(styles.cover)[0].style.display = "block"
    }
    const handleClose = () => {
        document.getElementsByClassName(styles.cover)[0].style.display = "none"
    }
    const handleBookMark = () => {
        //handle the color 
        const color = document.getElementsByClassName(styles.book)[0].style.color;
        if (color === "black") {
            document.getElementsByClassName(styles.book)[0].style.color = "#f22c5c";
            fetch(`http://localhost:8080/bookmarks/add/user=${user.id}&&product=${specialProduct.id}`, {
                method: 'PUT',
                headers: {
                    'Authorization': `Bearer ${user.token}`,
                }
            })
                .then(data => {
                    console.log(data);
                    setTemp(false);
                })
                .catch(error => {
                    console.error('Error creating user:', error);
                    setTemp(false);
                });
        }
        else {
            document.getElementsByClassName(styles.book)[0].style.color = "black";
            fetch(`http://localhost:8080/bookmarks/remove/user=${user.id}&&product=${specialProduct.id}`, {
                method: 'PUT',
                headers: {
                    'Authorization': `Bearer ${user.token}`,
                }
            })
                .then(data => {
                    console.log(data);
                    setTemp(false);
                })
                .catch(error => {
                    console.error('Error creating user:', error);
                    setTemp(false);
                });
        }
        // add to back
    }
    const handleCart = () => {
        let temp = false;
        const hasCookie = cookies.cart !== undefined;
        console.log(hasCookie)
        if (hasCookie !== false) {
            cookies.cart.map((product) => {
                if (product.id === specialProduct.id && product.productName) { temp = true; }
            })
        }
        if (!temp) {
            console.log(temp)
            if (hasCookie === false) {
                handleCartCookies([specialProduct]);
            }
            else {
                handleCartCookies([...cookies.cart, specialProduct]);
            }
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
                    {products.map((product, index) => {
                        return (<div class={styles.box} key={product.id}>
                            {product.imageLink ? (<div class={styles.image}><img src={require("../images/" + product.imageLink)} alt="" /></div>) : (<></>)}
                            <div class={styles.text}>
                                <h3>{product.name}</h3>
                                <p>{product.description}</p>
                                <p>{product.breed}</p>
                                <div className={styles.price}>For Adoption</div>
                            </div>
                            <div class={styles.info}>
                                <a id={index} onClick={handleReadMe}>Read Me</a>
                                <FaArrowRight className={styles.i} />
                            </div>
                        </div>)
                    })}
                </div>
            </div>
            <div className={styles.cover}>
                <div class={styles.specialProduct}>
                    <div className={styles.close} onClick={handleClose}><IoClose /></div>
                    {specialProduct.imageLink ? (<div class={styles.image}><img src={require("../images/" + specialProduct.imageLink)} alt="" /></div>) : <></>}
                    <div class={styles.text}>
                        <h3>{specialProduct.name}</h3>
                        <p>{specialProduct.description}</p>
                        <div className={styles.price}>For Adoption</div>
                        {/* <FaBookmark className={styles.book} onClick={handleBookMark} /> */}
                    </div>
                    <div className={styles.brand}><p>Type</p>{specialProduct.type}</div>
                    <div className={styles.brand}><p>Breed</p>{specialProduct.breed}</div>
                    <div className={styles.category}><p>Birth-Date</p>{specialProduct.birthDate}</div>
                    <div className={styles.category}><p>Age</p>{specialProduct.age} Years Old</div>
                    <div className={styles.cart} onClick={handleCart}><FaCartShopping /> Add To Cart</div>
                </div>
            </div>
        </div>
    )
}