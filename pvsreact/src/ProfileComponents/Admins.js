import styles from "../CSS/profileStyles.module.css"
import { useEffect, useState } from "react"
import { FaSearch } from "react-icons/fa";
export default function Admins({ user }) {
    const [search, setSearch] = useState('');
    const [admins, setAdmins] = useState([]);
    const [userToAdmin, setUserToAdmin] = useState({});
    const handleSearch = () => {
        // Replace By Search For Users 

        fetch(`http://localhost:8080/api/searchUsers/${search}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${user.token}`,
            }
        })
            .then(response => response.json())
            .then(data => {
                setAdmins(data);
                console.log(data);
            })
            .catch(error => {
                console.error('Error creating user:', error);
            });
    }

    const handleCreateAdminRequest = (newAdmin) => {
        console.log(newAdmin.id)
        console.log(user.token)
        fetch(`http://localhost:8080/api/setAdmin`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${user.token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                admin: user.id,
                user: newAdmin.id
            })
        })
            .then(data => {
                console.log(data);
                window.alert(newAdmin.userName + " becomes admin");
            })
            .catch(error => {
                console.error('Error creating user:', error);
            });
    }

    const handleCreateAdmin = (e) => {
        handleCreateAdminRequest(admins[e.target.id]);
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
                <div className={styles.users}>
                    {admins.map((user, index) => {
                        return (
                            <div className={styles.user}>
                                <div className={styles.userInfo}>
                                    <div className={styles.userName}>{user.userName}</div>
                                    <div className={styles.userEmail}>Email: {user.email}</div>
                                </div>
                                <div className={styles.createAdmin} id={index} onClick={handleCreateAdmin} >Create Admin</div>
                            </div>
                        )
                    })}
                </div>
            </div>
        </>
    )
}