import { useLocation } from "react-router-dom"
import { useState, useEffect } from "react";
import ProductListHeader from "../ProductListComponents/ProductListHeader";
import List from "../ProductListComponents/List";
import ListLanding from "../ProductListComponents/ListLanding";
export default function ProductList() {
    const location = useLocation();
    const [token, setToken] = useState('');
    const [decode, setDecode] = useState({});
    const [temp, setTemp] = useState(true);
    useEffect(() => {
        if (location.state != null) {
            setToken(location.state.token);
            setDecode(location.state.decode);
            setTemp(false);
        }
    }
    )

    return (
        <>
            <ProductListHeader token={token} decode={decode} />
            <ListLanding></ListLanding>
            <List token={token} />
        </>
    )
}