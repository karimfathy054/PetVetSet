import styles from "../CSS/profileStyles.module.css"
import Navbar from "../ProfileComponents/Navbar"
import Info from "../ProfileComponents/Info";
import { useLocation } from "react-router-dom";
import { useState, useEffect } from "react";
import MyProducts from "../ProfileComponents/MyProducts";
import BookMark from "../ProfileComponents/BookMark";
import Requests from "../ProfileComponents/Requests";
import Admins from "../ProfileComponents/Admins";
export default function Profile() {
  const location = useLocation();
  const [user, setUser] = useState({});
  useEffect(() => {
    if (location.state != null) {
      setUser(location.state.user);
    }
  })


  return (
    <>
      <div className={styles.profile}>
        <Navbar user={user}></Navbar>
        <Info user={user}></Info>
        <BookMark user={user}></BookMark>
        <MyProducts user={user}></MyProducts>
        <Requests user={user}></Requests>
        <Admins user={user}></Admins>
      </div>
    </>
  )
}