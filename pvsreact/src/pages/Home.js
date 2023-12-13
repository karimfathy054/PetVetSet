import { useEffect, useState } from "react"
import Header from "../Components/Header"
import Landing from "../Components/Landing"
import Products from "../Components/Products"
import Services from "../Components/Services"
import { useLocation } from "react-router-dom"

export default function Home() {
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
            <Header token={token} decode={decode}></Header>
            <Landing token={token} decode={decode} ></Landing>
            <Products token={token} decode={decode}></Products>
            <Services token={token} decode={decode}></Services>
        </>
    )
}