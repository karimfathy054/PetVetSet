import { useLocation } from "react-router-dom"
import { useState, useEffect } from "react";
import ProductListHeader from "../ProductListComponents/ProductListHeader";
import CartList from "../ProductListComponents/CartList"
export default function Cart() {
    const location = useLocation();
    const [user, setUser] = useState({});
    useEffect(() => {
        if (location.state != null) {
            setUser(location.state.user)
        }
    }
    )
    return (
        <>
            <ProductListHeader user={user} />
            <CartList user={user} />
        </>
    )
}