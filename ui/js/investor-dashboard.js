import { showNavbar, formatKind } from './utilities.js'
import {getInvestorsPortfolios, getInvestorProfile} from "./api.js";

let portfolios;

document.getElementById('investor-dashboard-script').onload = async function () {
    document.getElementById("container").classList.add('container')

    await getInvestor()

    showNavbar()
}

async function getInvestor() {
    const investor = await getInvestorProfile()

    document.getElementById("investor-name").innerText = investor.firstname + " " + investor.lastname
    document.getElementById("portfolio-count").innerText = investor.portfolios.length - 1;

    await callGetAllPortfolios()
}


async function callGetAllPortfolios() {
    portfolios = await getInvestorsPortfolios()

    document.getElementById("portfolio-table").getElementsByTagName('tbody')[0].innerHTML = "";

    portfolios.forEach((asset, index) => {
        let row = document.getElementById("portfolio-table").getElementsByTagName('tbody')[0].insertRow(index)

        row.insertCell(0).innerHTML = asset.companyName;
        row.insertCell(1).innerHTML = asset.creationDate

        const action = row.insertCell(2)
        action.innerHTML = "<a id='" + index + "' class='link mx-auto'>View Asset</a>"
        action.classList.add("col-small")

        document.getElementById(index).addEventListener('click', (event) => {
            openAssetTable(event.target.id)
        });
    })
}

function openAssetTable(index) {
    document.getElementById("view-asset-modal").classList.add("show")

    document.getElementById('modal-close').addEventListener('click', () => {
        document.getElementById("view-asset-modal").classList.remove("show")
    })

    populatePortfolioAssetTable(index)
}

function populatePortfolioAssetTable(index) {
    portfolios[index].assets.forEach((asset, index) => {
        let row = document.getElementById("portfolio-asset-table").getElementsByTagName('tbody')[0].insertRow(index)

        row.insertCell(0).innerHTML = asset.name;
        row.insertCell(1).innerHTML = formatKind(asset.kind)
    })
}