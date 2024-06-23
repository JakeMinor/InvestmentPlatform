import { showNavbar } from './utilities.js'
import { getAllAssets } from './api.js'

document.getElementById('broker-dashboard-script').onload = async function () {
    document.getElementById("container").classList.add('container')
    document.getElementById('home-navlink').setAttribute("value", "broker-dashboard")

    showNavbar()

    const assets = await getAllAssets()

    assets.forEach((asset, index) => {
        let row = document.getElementById("asset-table").getElementsByTagName('tbody')[0].insertRow(index)

        row.insertCell(0).innerHTML = asset.name;
        row.insertCell(1).innerHTML = formatKind(asset.kind)

        const action = row.insertCell(2)
        action.innerHTML = "<a id='" + asset.name + "-delete-button' class='link mx-auto'>Delete Asset</a>"
        action.classList.add("col-small")

        document.getElementById(asset.name + "-delete-button").addEventListener('click', (event) => {
            deleteAsset(event.target.id)
        });
    })
}

function formatKind(kind) {
    return kind.charAt(0).toUpperCase() + kind.slice(1).toLowerCase();
}

function deleteAsset(id) {
    console.log(id)
}