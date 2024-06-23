
export async function loginUser(username, password, userType) {
    let body = {
        username: username,
        password: password,
        userType: userType
    }

    return await fetch("http://localhost:8080/api/authentication/login", {
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

    return await fetch("http://localhost:8080/api/authentication/register-broker", {
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

    return await fetch("http://localhost:8080/api/authentication/register-investor", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    })
}