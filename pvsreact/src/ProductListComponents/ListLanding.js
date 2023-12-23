import land from "../images/landing-image.png"
import styles from "../CSS/List.module.css"
export default function ListLanding() {
    return (
        <div class={styles.landing}>
            <div class={styles.container}>
                <div class={styles.text}>
                    <h1>Welcome, To PVS World</h1>
                    <p>Here We gonna share everything about our products. Pets, foods and other products that you need.</p>
                </div>
                <div class={styles.image}>
                    <img src={land} alt="" />
                </div>
            </div>
        </div>
    )
}