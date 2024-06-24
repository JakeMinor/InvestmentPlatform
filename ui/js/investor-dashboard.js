import { showNavbar, formatKind } from './utilities.js'
import {getInvestorsPortfolios, getInvestorProfile, deleteAsset, sellPortfolio} from "./api.js";

let portfolios;

document.getElementById('investor-dashboard-script').onload = async function () {
    document.getElementById("container").classList.add('container')

    await getInvestor()

    showNavbar()
}

async function getInvestor() {
    const investor = await getInvestorProfile()

    document.getElementById("investor-name").innerText = investor.firstname + " " + investor.lastname
    document.getElementById("portfolio-count").innerText = investor.portfolios.length;

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
        action.innerHTML = "<a id='view-assets-" + asset.id + "' class='link mx-auto'>View Assets</a><br/><br/><a id='sell-portfolio-" + asset.id + "' class='link mx-auto'>Sell</a>"
        action.classList.add("col-small")

        document.getElementById("view-assets-" + asset.id).addEventListener('click', (event) => {
            openAssetTable(event.target.id.split("-")[2])
        });

        document.getElementById("sell-portfolio-" + asset.id).addEventListener('click', (event) => {
            callSellPortfolio(event.target.id.split("-")[2])
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

function populatePortfolioAssetTable(id) {
    
    portfolios.filter(portfolio => portfolio.id.toString() === id.toString())[0].assets.forEach((asset, index) => {
        let row = document.getElementById("portfolio-asset-table").getElementsByTagName('tbody')[0].insertRow(index)

        row.insertCell(0).innerHTML = asset.name;
        row.insertCell(1).innerHTML = formatKind(asset.kind)
    })
}

async function callSellPortfolio(id) {

    const response = await sellPortfolio(id)

    if(response.ok) {
        await getInvestor()
    } else {
        document.getElementById("toast-body").innerText = await response.text()
        document.getElementById("toast").classList.remove('hide')

        setTimeout(() => {
            document.getElementById("toast").classList.add("hide")
        }, 5000)
    }
}