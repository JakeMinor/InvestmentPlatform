import { showNavbar, formatKind } from './utilities.js'
import {
    getInvestorsPortfolios,
    getInvestorProfile,
    sellPortfolio,
    getAllBroker,
    createPortfolio,
    getAllAssetsByBrokerId, addAssetToPortfolio
} from "./api.js";

let portfolios;

document.getElementById('investor-dashboard-script').onload = async function () {
    document.getElementById("container").classList.add('container')
    document.getElementById("create-portfolio-button").addEventListener('click', openPortfolioModal)

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

    portfolios.forEach((portfolio, index) => {
        let row = document.getElementById("portfolio-table").getElementsByTagName('tbody')[0].insertRow(index)

        row.insertCell(0).innerHTML = portfolio.companyName;
        row.insertCell(1).innerHTML = portfolio.creationDate

        const action = row.insertCell(2)
        action.innerHTML = "<a id='view-assets-" + portfolio.id + "' class='link mx-auto'>View Assets</a><br/><br/><a id='add-asset-" + portfolio.id + "' class='link mx-auto'>Add Asset</a><br/><br/><a id='sell-portfolio-" + portfolio.id + "' class='link mx-auto'>Sell</a>"
        action.classList.add("col-small")

        document.getElementById("view-assets-" + portfolio.id).addEventListener('click', async (event) => {
            await openAssetTable(event.target.id.split("-")[2])
        });

        document.getElementById("sell-portfolio-" + portfolio.id).addEventListener('click', (event) => {
            callSellPortfolio(event.target.id.split("-")[2])
        });

        document.getElementById("add-asset-" + portfolio.id).addEventListener('click', (event) => {
            openAddAssetToPortfolioModal(event.target.id.split("-")[2])
        });
    })
}

async function openAssetTable(index) {
    await getInvestor()

    document.getElementById("view-asset-modal").classList.add("show")

    document.getElementById('view-asset-modal-close').addEventListener('click', () => {
        document.getElementById("view-asset-modal").classList.remove("show")
    })

    populatePortfolioAssetTable(index)
}

async function openAddAssetToPortfolioModal(id) {
    document.getElementById("add-asset-to-portfolio-modal").classList.add("show")

    document.getElementById('add-asset-to-portfolio-modal-close').addEventListener('click', () => {
        document.getElementById("add-asset-to-portfolio-modal").classList.remove("show")
    })

    document.getElementById("asset").innerHTML = "";

    const portfolio = portfolios.filter(portfolio => portfolio.id.toString() === id.toString())[0]

    let assets = await getAllAssetsByBrokerId(portfolio.brokerId)

    assets = removeMatchingElementsById(assets, portfolio.assets)

    document.getElementById("portfolio-asset-table").getElementsByTagName('tbody')[0].innerHTML = "";

    assets.forEach(asset => {
        const el = document.createElement("option")

        el.value = asset.asset_id;
        el.innerHTML = asset.name + " | " + formatKind(asset.kind)

        document.getElementById('asset').appendChild(el)
    })

    document.getElementById('add-asset-to-portfolio-modal-save').addEventListener('click', async () => {
        await callAddAssetToPortfolio(portfolio.id)
    })
}

function removeMatchingElementsById(list1, list2) {
    const idsInList2 = new Set(list2.map(item => item.asset_id));

    const filteredList1 = list1.filter(item => !idsInList2.has(item.asset_id));

    const idsInList1 = new Set(list1.map(item => item.asset_id));
    const filteredList2 = list2.filter(item => !idsInList1.has(item.asset_id));

    return [...filteredList1, ...filteredList2];
}

async function openPortfolioModal() {
    document.getElementById("broker").innerHTML = "";

    document.getElementById("create-portfolio-modal").classList.add("show")

    document.getElementById('create-portfolio-modal-close').addEventListener('click', () => {
        document.getElementById("create-portfolio-modal").classList.remove("show")
    })


    const brokers = await getAllBroker()

    brokers.forEach(broker => {
        const el = document.createElement("option")

        el.value = broker.id;
        el.innerHTML = broker.username

        document.getElementById('broker').appendChild(el)
    })

    document.getElementById('create-portfolio-modal-save').addEventListener('click', async () => {

        await callCreatePortfolio()
    })

}

function populatePortfolioAssetTable(id) {

    document.getElementById("portfolio-asset-table").getElementsByTagName('tbody')[0].innerHTML = "";

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

async function callAddAssetToPortfolio(portfolioId) {
    document.getElementById("add-asset-to-portfolio-modal").classList.remove("show")

    const assetId = document.getElementById("asset").value;

    await addAssetToPortfolio(assetId, portfolioId)
}

async function callCreatePortfolio() {
    const broker = document.getElementById("broker").value

    await createPortfolio(broker)

    document.getElementById("create-portfolio-modal").classList.remove("show")

    await getInvestor()
}