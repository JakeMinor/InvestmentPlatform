document.addEventListener("click", (e) => {
    const { target } = e;

    if(!target.matches("nav a")) {
        return;
    }

    e.preventDefault();

    urlRoute();
})

const urlRoutes = {
    404: {
        name: "not-found",
        template: "./InvestmentPlatform/ui/templates/404.html",
        script: ''
    },
    "/": {
        name: "dashboard",
        template: "./InvestmentPlatform/ui/templates/index.html",
        script: ''
    },
    "/portfolio": {
        name: "portfolio",
        template: "./InvestmentPlatform/ui/templates/portfolio.html",
        script: './InvestmentPlatform/ui/js/portfolio.js'
    }
}

const urlRoute = (event) => {
    event = event || window.event;

    event.preventDefault();

    window.history.pushState({}, '', event.target.href)

    urlLocationHandler();
}

const urlLocationHandler = async () => {
    let location = window.location.pathname;

    if(location.length === 0) {
        location = "/"
    }

    const route = urlRoutes[location] || urlRoutes[404];

    document.getElementById('content').innerHTML = await fetch(route.template)
        .then((response) =>  response.text())

    loadJS(route)
}

function loadJS(route, async = true) {

    const id = route.name + "-script"
    let scriptTags = Array.from(document.getElementsByTagName("script"))

    console.log(scriptTags)

    scriptTags.forEach(function(scriptTag) {
        if(scriptTag.id !== id && scriptTag.id !== 'router') {
            document.getElementsByTagName("body").item(0).removeChild(scriptTag)
        }
    })

    let scriptEle = document.createElement("script");

    scriptEle.id = id
    scriptEle.setAttribute("src", route.script);
    scriptEle.setAttribute("type", "text/javascript");
    scriptEle.setAttribute("async", async);

    document.body.appendChild(scriptEle);
}