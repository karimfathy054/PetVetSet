import { Link } from "react-router-dom";
import styles from "../CSS/profileStyles.module.css"
import { MdDashboard } from "react-icons/md";
import { FaHome } from "react-icons/fa";
import { IoIosSettings } from "react-icons/io";
import { FaProductHunt } from "react-icons/fa";
import { FaBookmark } from "react-icons/fa";
import { FaCodePullRequest } from "react-icons/fa6";
import { MdOutlineAdminPanelSettings } from "react-icons/md";
export default function Navbar({ user }) {
    const handleSetting = () => {
        document.getElementById("info").style.display = "flex";
        // document.getElementById("myproducts1").style.display = "none";
        document.getElementById("bookmark1").style.display = "none";
        document.getElementById(styles.setting).style.color = "#f22c5c";
        // document.getElementById(styles.myProducts).style.color = "white";
        document.getElementById(styles.bookmark).style.color = "white";
        if (user.isAdmin) {
            document.getElementById("admins1").style.display = "none";
            document.getElementById(styles.admins).style.color = "white";
            document.getElementById(styles.requests).style.color = "white";
            document.getElementById("requests1").style.display = "none";
        }
    }
    const handleMyProducts = (e) => {
        // document.getElementById("myproducts1").style.display = "flex";
        document.getElementById("info").style.display = "none";
        document.getElementById("bookmark1").style.display = "none";
        document.getElementById(styles.setting).style.color = "white";
        // document.getElementById(styles.myProducts).style.color = "#f22c5c";
        document.getElementById(styles.bookmark).style.color = "white";
        if (user.isAdmin) {
            document.getElementById(styles.requests).style.color = "white";
            document.getElementById(styles.admins).style.color = "white";
            document.getElementById("admins1").style.display = "none";
            document.getElementById("requests1").style.display = "none";
        }
    }
    const handleBookMark = () => {
        document.getElementById("bookmark1").style.display = "flex";
        document.getElementById("info").style.display = "none";
        // document.getElementById("myproducts1").style.display = "none";
        document.getElementById(styles.setting).style.color = "white";
        // document.getElementById(styles.myProducts).style.color = "white";
        document.getElementById(styles.bookmark).style.color = "#f22c5c";
        if (user.isAdmin) {
            document.getElementById(styles.requests).style.color = "white";
            document.getElementById(styles.admins).style.color = "white";
            document.getElementById("admins1").style.display = "none";
            document.getElementById("requests1").style.display = "none";
        }
    }

    const handleRequests = () => {
        document.getElementById("requests1").style.display = "flex";
        document.getElementById("bookmark1").style.display = "none";
        document.getElementById("info").style.display = "none";
        // document.getElementById("myproducts1").style.display = "none";
        document.getElementById(styles.setting).style.color = "white";
        // document.getElementById(styles.myProducts).style.color = "white";
        document.getElementById(styles.bookmark).style.color = "white";
        document.getElementById(styles.requests).style.color = "#f22c5c";
        document.getElementById("admins1").style.display = "none";
        document.getElementById(styles.admins).style.color = "white";
    }

    const handleAdmins = () => {
        document.getElementById("admins1").style.display = "block";
        document.getElementById("requests1").style.display = "none";
        document.getElementById("bookmark1").style.display = "none";
        document.getElementById("info").style.display = "none";
        // document.getElementById("myproducts1").style.display = "none";
        document.getElementById(styles.setting).style.color = "white";
        // document.getElementById(styles.myProducts).style.color = "white";
        document.getElementById(styles.bookmark).style.color = "white";
        document.getElementById(styles.requests).style.color = "white";
        document.getElementById(styles.admins).style.color = "#f22c5c";
    }

    return (
        <>
            <div className={styles.navbar}>
                <div className={styles.dashboard}><MdDashboard /> Dashboard</div>
                <ul>
                    <li><Link to='/' className={styles.li}><FaHome />Home</Link></li>
                    <li><div id={styles.setting} className={styles.li} onClick={handleSetting}><IoIosSettings />Setting</div></li>
                    {/* <li><div id={styles.myProducts} className={styles.li} onClick={handleMyProducts}><FaProductHunt />My Products</div></li> */}
                    <li><div id={styles.bookmark} className={styles.li} onClick={handleBookMark}><FaBookmark />Book Mark</div></li>
                    {user.isAdmin ? (<>
                        <li><div id={styles.requests} className={styles.li} onClick={handleRequests}><FaCodePullRequest />Requests</div></li>
                        <li><div id={styles.admins} className={styles.li} onClick={handleAdmins}><MdOutlineAdminPanelSettings />Generate Admins</div></li>
                    </>
                    ) : (<></>)}
                </ul>
            </div>
        </>
    )
}