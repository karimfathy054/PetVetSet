import Header from "../Components/Header"
import Landing from "../Components/Landing"
import Products from "../Components/Products"
import Services from "../Components/Services"

export default function Home({ user }) {
    return (
        <>
            <Header user={user}></Header>
            <Landing user={user} ></Landing>
            <Services user={user}></Services>
            <Products user={user}></Products>
        </>
    )
}