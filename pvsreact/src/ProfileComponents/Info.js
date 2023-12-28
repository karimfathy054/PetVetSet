import styles from "../CSS/profileStyles.module.css"
import { FaUserCircle } from "react-icons/fa";
import { useEffect, useState } from "react";

export default function Info({ user }) {
    const [image, setImage] = useState();
    const [joinDate, setJoinDate] = useState();
    const [temp, setTemp] = useState(true);

    useEffect(() => {
        if (temp) {
            fetch(`http://localhost:8080/api/getJoinDate/${user.id}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${user.token}`,
                }
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data)
                    setJoinDate(data);
                    setTemp(false);
                })
                .catch(error => {
                    console.log("44444444")
                    console.error('Error creating user:', error);
                    setTemp(false);
                });
        }
    }
    )

    const handleChange = (e) => {
        console.log(e.target.files);
        setImage(URL.createObjectURL(e.target.files[0]));
        handleUpload(e.target.files[0].name);
    }

    const handleUpload = (image) => {
        fetch(`http://localhost:8080/api/addImage/user=${user.id}&&image=${image}`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${user.token}`,
            }
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
            })
            .catch(error => {
                console.error('Error creating user:', error);
            });
    }


    return (
        <>
            <div className={styles.info} id="info">
                <div className={styles.general}>
                    <h2>General Information</h2>
                    <div className={styles.userName}><p>User Name</p> {user.userName}</div>
                    <div className={styles.userName}><p>Email</p> {user.email}</div>
                    <div className={styles.userName}><p>Join Date</p> {joinDate}</div>

                </div>
                <div className={styles.private}>
                    <div className={styles.privateInfo}>
                        <div className={styles.photo}>{user.image ? (<img src={require("../images/" + user.image)}></img>) : <FaUserCircle />}</div>
                        <div className={styles.role}>{user.isAdmin ? (<h3>admin</h3>) : (<h3>user</h3>)}</div>
                        <div className={styles.email}>{user.email}</div>
                    </div>
                    <div className={styles.privateSelect}>
                        <h2>Select profile photo</h2>
                        <input type="file" onChange={handleChange} />
                    </div>
                </div>
            </div>
        </>
    )
}