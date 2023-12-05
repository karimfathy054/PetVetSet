export default function GoogleOAuthSignupController() {
    // const url = `http://localhost:8080/oauthSignUp`;
    // fetch(url, {
    //     method: "GET",
    //     headers: {
    //         "Content-Type": "application/json",
    //     }
    // }).then((r) => r.json()).then((data) => console.log(data));
    // {/*the data is the response of the get request from the backend*/}

    const xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/");
    xhr.onload = () => {
    if (xhr.readyState == 4 && xhr.status == 200) {
        console.log(xhr.responseText);
    } else {
        console.log(`Error: ${xhr.status}`);
    }
    };
    xhr.send();

}
