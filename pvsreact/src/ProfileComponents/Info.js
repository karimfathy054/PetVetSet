import styles from "../CSS/profileStyles.module.css"
import { FaUserCircle } from "react-icons/fa";
import { useState } from "react";

export default function Info({ user }) {
    const [image, setImage] = useState();

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