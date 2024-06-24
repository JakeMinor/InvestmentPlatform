import { showNavbar } from './utilities.js'
import { getAllAssets, deleteAsset } from './api.js'

document.getElementById('broker-dashboard-script').onload = async function () {
    document.getElementById("container").classList.add('container')
    document.getElementById('home-navlink').setAttribute("value", "broker-dashboard")

    showNavbar()

    await callGetAllAssets()

}

function formatKind(kind) {
    return kind.charAt(0).toUpperCase() + kind.slice(1).toLowerCase();
}

async function callDeleteAsset(id) {

    const response = await deleteAsset(id)

    if(response.ok) {
        await getAllAssets()
    } else {
        document.getElementById("toast-body").innerText = await response.text()
        document.getElementById("toast").classList.remove('hide')

        setTimeout(() => {
            document.getElementById("toast").classList.add("hide")
        }, 5000)
    }


}

async function callGetAllAssets() {
    const assets = await getAllAssets()

    document.getElementById("asset-table").getElementsByTagName('tbody')[0].innerHTML = "";

    assets.forEach((asset, index) => {
        let row = document.getElementById("asset-table").getElementsByTagName('tbody')[0].insertRow(index)

        row.insertCell(0).innerHTML = asset.name;
        row.insertCell(1).innerHTML = formatKind(asset.kind)

        const action = row.insertCell(2)
        action.innerHTML = "<a id='" + asset.asset_id + "' class='link mx-auto'>Delete Asset</a>"
        action.classList.add("col-small")

        document.getElementById(asset.asset_id).addEventListener('click', (event) => {
            callDeleteAsset(event.target.id)
        });
    })
}