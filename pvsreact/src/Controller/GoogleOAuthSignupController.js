export default function GoogleOAuthSignupController() {
    const url = `http://localhost:8080/oauthSignUp`;
    fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        }
    }).then((r) => r.json()).then((data) => console.log(data));
}