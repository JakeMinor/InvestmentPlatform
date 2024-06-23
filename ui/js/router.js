window.onload = function()
{
    const path = window.location.pathname.split("/");

    switch(path[1])
    {
        case "":
        {
            loadPage("home");
            break;
        }
        case "about":
        {
            loadPage("about");
            break;
        }
        case "pricing":
        {
            loadPage("pricing");
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
            if(path == "home")
            {
                window.history.pushState("", "", "/");
                return;
            }

            window.history.pushState("", "", path);
        });
    });


}

export function loadPage($path)
{
    if($path == "") return;

    if(sessionStorage.getItem("authentication_token") === null) {
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

function loadJS(route, async = true) {

    const id = route + "-script"
    let scriptTags = Array.from(document.getElementsByTagName("script"))

    scriptTags.forEach(function(scriptTag) {
        if(scriptTag.id !== id && scriptTag.id !== 'router') {
            document.getElementsByTagName("body").item(0).removeChild(scriptTag)
        }
    })

    let scriptEle = document.createElement("script");

    scriptEle.id = id
    scriptEle.setAttribute("src",  "/InvestmentPlatform/ui/js/" + route + ".js");
    scriptEle.setAttribute("type", "module");
    scriptEle.setAttribute("async", async);

    document.body.appendChild(scriptEle);
}