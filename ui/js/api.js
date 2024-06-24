const baseUrl = "http://localhost:8080/api"

export async function loginUser(username, password, userType) {
    let body = {
        username: username,
        password: password,
        userType: userType
    }

    return await fetch(baseUrl + "/authentication/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    })
}

export async function registerBroker(username, password, companyName) {
    let body = {
        username: username,
        password: password,
        company: companyName
    }

    return await fetch(baseUrl + "/authentication/register-broker", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    })
}

export async function registerInvestor(username, password, firstName, lastName) {
    let body = {
        username: username,
        password: password,
        firstName: firstName,
        lastName: lastName
    }

    return await fetch(baseUrl + "/authentication/register-investor", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    })
}

export async function getAllAssets() {
    return await fetch(baseUrl + "/broker/assets", {
        method: "GET",
        headers: {
            "Authorization": getAuthorisationToken()
        }
    }).then(async (response) => JSON.parse(await response.text()))
}

export async function deleteAsset(id) {
    return await fetch(baseUrl + "/broker/delete-asset/" + id, {
        method: "DELETE",
        headers: {
            "Authorization": getAuthorisationToken()
        }
    })
}

function getAuthorisationToken() {
    return "Bearer " + sessionStorage.getItem("authentication_token")
}