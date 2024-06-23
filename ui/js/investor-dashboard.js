import { showNavbar } from './utilities.js'

document.getElementById('investor-dashboard-script').onload = function () {
    document.getElementById("container").classList.add('container')
    document.getElementById('home-navlink').setAttribute("value", "investor-dashboard")

    showNavbar()
}