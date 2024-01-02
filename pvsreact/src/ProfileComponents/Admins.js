import styles from "../CSS/profileStyles.module.css"
import { useEffect, useState } from "react"
import { FaSearch } from "react-icons/fa";
import done from "../images/done.png"
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
                // window.alert(newAdmin.userName + " becomes admin");
                const done = document.getElementById("serviceDone4");
                done.style.display = "block";
                setTemp(true)
            })
            .catch(error => {
                console.error('Error creating user:', error);
            });
    }

    const handleCreateAdmin = (e) => {
        handleCreateAdminRequest(admins[e.target.id]);
    }
    const handleCloseDone = () => {
        const done = document.getElementById("serviceDone4");
        done.style.display = "none";
    }
    return (
        <>
            <div id="admins1" className={styles.admins}>
                <div className={styles.done} id="serviceDone4">
                    <div className={styles.cover}>
                        <div className={styles.close} onClick={handleCloseDone}>x</div>
                        <p>User Becomes Admin</p>
                        <img src={done}></img>
                        <button onClick={handleCloseDone}>Continue</button>
                    </div>
                </div>
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