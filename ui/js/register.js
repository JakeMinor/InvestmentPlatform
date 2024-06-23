import { hideNavbar, setFieldInvalid } from "./utilities.js";
import { loadPage } from "./router.js";
import {registerInvestor, registerBroker, loginUser} from "./api.js";

document.getElementById('register-script').onload = function () {
    hideNavbar()
    hideRegisterInvestor()

    document.getElementById('register-user-button').addEventListener("click", register)

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

async function register() {
    const userType = document.getElementById("userType").value;
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    if(userType === "BROKER") {
        const companyName = document.getElementById("company").value;

        const valid = validateBrokerDetails(username, password, companyName)

        if(valid) {
            const response = await registerBroker(username, password, companyName)

            if(response.ok) {
                loadPage("login")
            } else {
                setFieldInvalid("username")
                setFieldInvalid("password")
                setFieldInvalid("company")


                document.getElementById("register-error").innerText = await response.text()
                document.getElementById('sign-in-button').style.paddingTop = "8px"

            }
        }


    } else {
        const firstName = document.getElementById("company").value;
        const lastName = document.getElementById("company").value;

        const valid = validateInvestorDetails(username, password, firstName, lastName)

        if(valid) {
            const response = await registerInvestor(username, password, firstName, lastName)

            if(response.ok) {
                await loginUser(username, password, userType)
                loadPage("login")
            } else {
                setFieldInvalid("username")
                setFieldInvalid("password")
                setFieldInvalid("first-name")
                setFieldInvalid("last-name")

                document.getElementById("register-error").innerText = await response.text()
                document.getElementById('sign-in-button').style.paddingTop = "8px"
            }
        }
    }
}

function validateBrokerDetails(username, password, companyName) {
    if(username === "" || username === null || password === "" || password === null || companyName === "" || companyName === null) {

        if(username === "" || username === null) {
            setFieldInvalid("username", "Username is required.")
        }

        if(password === "" || password === null) {
            setFieldInvalid("password", "Password is required.")
        }

        if(companyName === "" || companyName === null) {
            setFieldInvalid("company", "Company is required.")
        }

        return false;
    }

    return true
}

function validateInvestorDetails(username, password, firstName, lastName) {
    if(username === "" || username === null || password === "" || password === null || firstName === "" || firstName === null || lastName === "" || lastName === null) {

        if(username === "" || username === null) {
            setFieldInvalid('username', "Username is required.")
        }

        if(password === "" || password === null) {
            setFieldInvalid('password', "Password is required.")
        }

        if(firstName === "" || firstName === null) {
            setFieldInvalid('first-name', "First Name is required.")
        }

        if(lastName === "" || lastName === null) {
            setFieldInvalid('last-name', "Last Name is required.")
        }

        return false;
    }

    return true
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