import { hideNavbar } from "./utilities.js";
import { loadPage } from "./router.js";

document.getElementById('register-script').onload = function () {
    hideNavbar()
    hideRegisterInvestor()

    document.getElementById('submit').addEventListener("click", register)

    document.getElementById("sign-in-button").addEventListener("click", redirectToSignIn)

    document.getElementById("userType").addEventListener('change', () => {
        const value = document.getElementById("userType").value;

        if(value === "BROKER") {
            hideRegisterInvestor()
        } else {
            hideRegisterBroker()
        }
    })
}

function register() {

}

function redirectToSignIn() {
    loadPage("login")
}

function hideRegisterBroker() {
    document.getElementById("register-investor").classList.remove("hide")
    document.getElementById("register-broker").classList.add("hide")
}

function hideRegisterInvestor() {
    document.getElementById("register-broker").classList.remove("hide")
    document.getElementById("register-investor").classList.add("hide")
}