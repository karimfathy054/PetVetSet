import styles from "../CSS/HomeStyle.module.css"
import p1 from "../images/product-1.jpg"
import p2 from "../images/product-2.jpg"
import p3 from "../images/product-3.jpg"
import p4 from "../images/product-4.png"
import p5 from "../images/product-5.png"
import { useNavigate } from "react-router-dom"
export default function Products({ user }) {
    const navigate = useNavigate();
    const handleShopping = () => {
        navigate('/ProductList', { replace: true, state: { user } });
    }
    return (
        <div className={styles.products} id="products">
            <div className={styles.container}>
                <h2 className={styles.heading}>Products</h2>
                <div className={styles.content}>
                    <div className={styles.box}>
                        <img src={p1}></img>
                        <h3>product 1</h3>
                        <p>We train your pet with the foundation tricks and commands to make them well behaved and mannered.</p>
                    </div>
                    <div className={styles.box}>
                        <img src={p2}></img>
                        <h3>product 1</h3>
                        <p>We train your pet with the foundation tricks and commands to make them well behaved and mannered.</p>
                    </div>
                    <div className={styles.box}>
                        <img src={p3}></img>
                        <h3>product 1</h3>
                        <p>We train your pet with the foundation tricks and commands to make them well behaved and mannered.</p>
                    </div>
                    <div className={styles.box}>
                        <img src={p4}></img>
                        <h3>product 1</h3>
                        <p>We train your pet with the foundation tricks and commands to make them well behaved and mannered.</p>
                    </div>
                    <div className={styles.box}>
                        <img src={p5}></img>
                        <h3>product 1</h3>
                        <p>We train your pet with the foundation tricks and commands to make them well behaved and mannered.</p>
                    </div>
                </div>
                <div className={styles.list} onClick={handleShopping}>Shopping</div>
            </div>
        </div>
    )
}