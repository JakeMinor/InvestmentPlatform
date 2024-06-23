import { loginUser } from "./api.js";
import { loadPage } from "./router.js";
import { hideNavbar } from "./utilities.js";

document.getElementById('login-script').onload = function () {
    hideNavbar()

    document.getElementById('submit').addEventListener("click", login)
}

async function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const userType = document.getElementById("userType").value;

    const valid = validateLoginForm(username, password)

    if(valid) {
        const response = await loginUser(username, password, userType)

        sessionStorage.setItem("authentication_token", response)

        const subject = decodeJwt(response).sub;

        sessionStorage.setItem("username", subject.split(":")[0])
        sessionStorage.setItem("role", subject.split(":")[1])

        loadPage("home")
    }
}

function validateLoginForm(username, password) {

    document.getElementById("username").classList.remove('invalid-input')
    document.getElementById('username-error').innerText = ""
    document.getElementById('username-error').classList.remove('error-message')

    document.getElementById("password").classList.remove('invalid-input')
    document.getElementById('password-error').innerText = ""
    document.getElementById('password-error').classList.remove('error-message')

    if(username === "" || username === null || password === "" || password === null) {

        if(username === "" || username === null) {
            document.getElementById("username").classList.add('invalid-input')
            document.getElementById('username-error').innerText = "Username is required."
            document.getElementById('username-error').classList.add('error-message')
        }

        if(password === "" || password === null) {
            document.getElementById("password").classList.add('invalid-input')
            document.getElementById('password-error').innerText = "Password is required."
            document.getElementById('password-error').classList.add('error-message')
        }

        return false;
    }

    return true;
}

function decodeJwt(token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}