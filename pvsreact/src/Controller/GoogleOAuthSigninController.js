export default function GoogleOAuthSigninController() {
    const url = `http://localhost:8080/oauthLogin`;
    fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        }
    }).then((r) => r.json()).then((data) => console.log(data));
    {/*the data is the response of the get request from the backend*/}
}
