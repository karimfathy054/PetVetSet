import styles from "../CSS/List.module.css";
import { useCookies } from "react-cookie";
export default function Cart() {
    const [cookies, setCookie] = useCookies(["cart"]);
    function handleCartCookies(cart) {
        setCookie("cart", cart, { path: "/" });
    }
    const handleRemove = (e) => {
        const productToRemove = cookies.cart[e.target.id];
        const newCart = cookies.cart.filter((element) => element !== productToRemove);
        handleCartCookies(newCart)
    }
    const handleOrder = () => {
        // send to back
        handleCartCookies([])
    }
    return (
        <>
            <div class={styles.list}>
                <div class={styles.container}>
                    <div className={styles.approve} onClick={handleOrder}>Approve Order</div>
                    <div class={styles.content}>
                        {cookies.cart ? (cookies.cart.map((product, index) => {
                            return (
                                <div class={styles.box} key={product.id}>
                                    <div class={styles.image} style={{ width: "50%" }}><img src={require("../images/" + product.imageLink)} alt="" /></div>
                                    <div class={styles.text}>
                                        {product.name ? (<h3>{product.name}</h3>) : (<h3>{product.productName}</h3>)}
                                        {product.price ? (<div className={styles.price}>{product.price} $</div>) : (<div className={styles.price}>For Adoption</div>)}
                                    </div>
                                    <div className={styles.remove} onClick={handleRemove} id={index}>Remove</div>
                                </div>
                            )
                        })) : (<></>)}
                    </div>
                </div>
            </div>
        </>
    )
}