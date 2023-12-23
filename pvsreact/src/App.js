import Signin from "./pages/Signin";
import Home from "./pages/Home";
import { CookiesProvider, useCookies } from "react-cookie";
function App() {
  const [cookies, setCookie] = useCookies(["user"]);

  function handleLogin(user) {
    setCookie("user", user, { path: "/" });
  }

  return (
    <CookiesProvider>
      <div>
        {cookies.user ? (
          <Home user={cookies.user} />
        ) : (
          <div className="App">
            <Signin onLogin={handleLogin}></Signin>
          </div>
        )}
      </div>
    </CookiesProvider>
  );
}

export default App;