window.onload = function()
{
    const path = window.location.pathname.split("/");

    switch(path[1])
    {
        case "":
        {
            if(sessionStorage.getItem("role") === "BROKER") {
                loadPage("broker-dashboard");
            } else {
                loadPage("investor-dashboard")
            }

            break;
        }
        default:
        {
            loadPage("404");
            break;
        }
    }

    document.querySelectorAll(".menu__item").forEach((item) =>
    {
        item.addEventListener("click", function()
        {
            const path = item.getAttribute("value");
            loadPage(path);
            if(path === "broker-dashboard" || path === "investor-dashboard")
            {
                window.history.pushState("", "", "/");
                return;
            }

            window.history.pushState("", "", path);
        });
    });

    document.getElementById('sign-out-navlink').addEventListener('click', signOut)
}

export function loadPage($path)
{
    if($path == "") return;

    if(sessionStorage.getItem("authentication_token") === null && $path !== "register" && $path !== "login") {
        $path = "login"
    }

    const container = document.getElementById("container");

    const request = new XMLHttpRequest();
    request.open("GET", "/InvestmentPlatform/ui/templates/" + $path + ".html");
    request.send();
    request.onload = function()
    {
        if(request.status == 200)
        {
            container.innerHTML = request.responseText;
            document.title = "Investment Platform | " + $path;
            loadJS($path)
        }
    }
}

function loadJS(route) {

    const id = route + "-script"
    let scriptTags = Array.from(document.getElementsByTagName("script"))

    scriptTags.forEach(function(scriptTag) {
        if(scriptTag.id !== id && scriptTag.id !== 'router') {
            document.getElementsByTagName("body").item(0).removeChild(scriptTag)
        }
    })

    let scriptEle = document.createElement("script");

    scriptEle.id = id
    scriptEle.setAttribute("src",  "./js/" + route + ".js?" + Math.random());
    scriptEle.setAttribute("type", "module");
    scriptEle.async = true

    document.body.insertBefore(scriptEle, document.body.lastChild);
}


function signOut() {
    sessionStorage.clear()

    loadPage("login")
}