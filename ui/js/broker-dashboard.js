import { removeInvalidState, setFieldInvalid, showNavbar, formatKind } from './utilities.js'
import {getAllAssets, deleteAsset, addAsset} from './api.js'

document.getElementById('broker-dashboard-script').onload = async function () {
    document.getElementById("container").classList.add('container')
    document.getElementById('add-asset-button').addEventListener('click', openAddAssetModal)

    showNavbar()

    await callGetAllAssets()

}


async function callDeleteAsset(id) {

    const response = await deleteAsset(id)

    if(response.ok) {
        await callGetAllAssets()
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

function openAddAssetModal() {
    document.getElementById("add-asset-modal").classList.add("show")

    document.getElementById("modal-close").addEventListener('click', closeModal)

    document.getElementById("modal-save").addEventListener('click', saveAsset)
}

function closeModal() {
    document.getElementById("add-asset-modal").classList.remove("show")

    document.getElementById("asset-name").value = ""
    document.getElementById("asset-type").value = "SHARE"
}

async function saveAsset() {
    const assetName = document.getElementById("asset-name").value
    const assetType = document.getElementById("asset-type").value

    const valid = validateAsset(assetName)

    if(valid) {
        const response = await addAsset(assetName, assetType)

        if(response.ok) {
            closeModal()

            await callGetAllAssets()
        } else {
            document.getElementById("asset-error").innerText = await response.text()
            setFieldInvalid("asset-name")
            setFieldInvalid("asset-type")
        }
    }
}

function validateAsset(assetName) {
    removeInvalidState("asset-name")

    if(assetName === "" || assetName === null) {

        if(assetName === "" || assetName === null) {
            setFieldInvalid("asset-name", "Asset Name is required.")
        }

        return false;
    }

    return true;
}