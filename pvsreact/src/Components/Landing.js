import styles from "../CSS/HomeStyle.module.css"
export default function Landing() {
    return (
        <div class={styles.landing}>
            <div class={styles.overlay}>
                <div class={styles.text}>
                    <div class={styles.content}>
                        <h2>We Make Your Pets Well Behaved</h2>
                    </div>
                </div>
            </div>
        </div>
    )
}