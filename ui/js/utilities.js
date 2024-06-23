export function showNavbar() {
    document.getElementById("navbar").classList.remove("hide")
    document.getElementById("navbar").classList.add("show")
}

export function hideNavbar() {
    document.getElementById("navbar").classList.remove("show")
    document.getElementById("navbar").classList.add("hide")
}