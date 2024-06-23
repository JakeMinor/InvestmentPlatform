
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