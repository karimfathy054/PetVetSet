import { useLocation } from "react-router-dom"
import { useState, useEffect } from "react";
import ProductListHeader from "../ProductListComponents/ProductListHeader";
import List from "../ProductListComponents/List";
import ListLanding from "../ProductListComponents/ListLanding";
import AnimalList from "../ProductListComponents/AnimalList";
import CartList from "../ProductListComponents/CartList";
import ProductUploadForm from "../pages/RequestForm";
import AnimalUploadForm from "./RquestAnimalForm";
export default function ProductList() {
    const location = useLocation();
    const [user, setUser] = useState({});
    const [temp, setTemp] = useState(true);
    useEffect(() => {
        if (location.state != null) {
            setUser(location.state.user);
            setTemp(false);
        }
    }
    )

    return (
        <>
            <ProductListHeader user={user} />
            <ListLanding></ListLanding>
            <List user={user} />
            <AnimalList user={user} />
            <CartList user={user} />
            <ProductUploadForm user={user} />
            <AnimalUploadForm user={user} />
        </>
    )
}