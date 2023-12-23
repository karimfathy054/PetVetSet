import { Link } from "react-router-dom";
import styles from "../CSS/profileStyles.module.css"
import { MdDashboard } from "react-icons/md";
export default function Navbar({ user }) {
    return (
        <>
            <div className={styles.navbar}>
                <div className={styles.dashboard}><MdDashboard /> Dashboard</div>
                <ul>
                    <li><Link to='/'>Home</Link></li>
                    <li><Link to='/'>Setting</Link></li>
                    <li><Link to='/'>My Products</Link></li>
                    <li><Link to='/'>Book Mark</Link></li>
                    {user.isAdmin ? (
                        <li><Link to='/'>Upload Requests</Link></li>
                    ) : (<></>)}
                </ul>
            </div>
        </>
    )
}