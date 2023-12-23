import { useLocation } from "react-router-dom"
import { useState, useEffect } from "react";
import ProductListHeader from "../ProductListComponents/ProductListHeader";
import ListLanding from "../ProductListComponents/ListLanding";
import AnimalList from "../ProductListComponents/AnimalList";
export default function Animals() {
    const location = useLocation();
    const [user, setUser] = useState({});
    const [temp, setTemp] = useState(true);
    useEffect(() => {
        if (location.state != null) {
            setUser(location.state.user)
            setTemp(false);
        }
    }
    )
    return (
        <>
            <ProductListHeader user={user} />
            <ListLanding></ListLanding>
            <AnimalList user={user} />
        </>
    )
}