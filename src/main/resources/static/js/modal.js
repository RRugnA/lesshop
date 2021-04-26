function modalStatus(id) {
	document.querySelector(".modal-status").classList.toggle("show");
	document.querySelector("#compra").value = id;
}
function modalStatusClose() {
	document.querySelector(".modal-status").classList.toggle("show");
}