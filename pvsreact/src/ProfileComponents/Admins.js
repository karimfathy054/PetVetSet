import styles from "../CSS/profileStyles.module.css"
import { useEffect, useState } from "react"
import { FaSearch } from "react-icons/fa";
export default function Admins({ user }) {
    const [search, setSearch] = useState('');
    const [admins, setAdmins] = useState([]);
    const handleSearch = () => {
        // Replace By Search For Users 

        // fetch(`http://localhost:8080/pet/name=${search}`, {
        //     method: 'GET',
        //     headers: {
        //         'Authorization': `Bearer ${user.token}`,
        //     }
        // })
        //     .then(response => response.json())
        //     .then(data => {
        //         setProducts(data);
        //         console.log(data);
        //     })
        //     .catch(error => {
        //         console.error('Error creating user:', error);
        //     });
    }
    return (
        <>
            <div id="admins1" className={styles.admins}>
                <div className={styles.action}>
                    <form onSubmit={(e) => { e.preventDefault(); handleSearch() }}>
                        <input type="text" placeholder="Search by email" value={search} onChange={(e) => setSearch(e.target.value)}></input>
                        <FaSearch className={styles.searchIcon} onClick={handleSearch} />
                    </form>
                </div>
            </div>
        </>
    )
}