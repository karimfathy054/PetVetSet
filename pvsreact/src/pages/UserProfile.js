import styles from "../CSS/profileStyles.module.css"
import Navbar from "../ProfileComponents/Navbar"
import Info from "../ProfileComponents/Info";
import { useLocation } from "react-router-dom";
import { useState, useEffect } from "react";
export default function Profile() {
  const location = useLocation();
  const [user, setUser] = useState({});
  const [temp, setTemp] = useState(true);
  useEffect(() => {
    if (location.state != null) {
      setUser(location.state.user);
      setTemp(false);
    }
  })


  return (
    <>
      <div className={styles.profile}>
        <Navbar user={user}></Navbar>
        <Info></Info>
      </div>
    </>
  )
}