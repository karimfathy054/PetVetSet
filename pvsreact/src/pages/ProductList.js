import { useLocation } from "react-router-dom"
export default function ProductList() {
    const location = useLocation();
    return (
        <div>hello {location.state.token}</div>
    )
}