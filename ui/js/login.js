import { loginUser } from "./api.js";
import { loadPage } from "./router.js";
import {hideNavbar, removeInvalidState, setFieldInvalid} from "./utilities.js";

document.getElementById('login-script').onload = function () {
    hideNavbar()

    document.getElementById('submit').addEventListener("click", login)

    document.getElementById("register-button").addEventListener("click", redirectToRegister)
}

async function login() {
    document.getElementById("user-details-error").innerText = "";

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const userType = document.getElementById("userType").value;

    const valid = validateLoginForm(username, password)

    if(valid) {
        const response = await loginUser(username, password, userType)

        if(response.ok) {
            const token = await response.text()

            sessionStorage.setItem("authentication_token", token)

            const subject = decodeJwt(token).sub;
            const username = subject.split(":")[0]
            const role = subject.split(":")[1]

            sessionStorage.setItem("username", username)
            sessionStorage.setItem("role", role)

            if(role === "BROKER") {
                loadPage("broker-dashboard")
            } else {
                loadPage("investor-dashboard")
            }

        } else {
            document.getElementById("user-details-error").innerText = await response.text()
            setFieldInvalid("username")
            setFieldInvalid("password")
        }
    }
}

async function redirectToRegister() {
    loadPage("register")
}

function validateLoginForm(username, password) {
    removeInvalidState("username")
    removeInvalidState("password")

    if(username === "" || username === null || password === "" || password === null) {

        if(username === "" || username === null) {
            setFieldInvalid("username", "Username is required.")
        }

        if(password === "" || password === null) {
            setFieldInvalid("password", "Password is required.")
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