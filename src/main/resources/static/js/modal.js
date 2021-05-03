function modalStatus(id) {
	document.querySelector(".modal-status").classList.toggle("show");
	document.querySelector("#compra").value = id;
}
function modalStatusClose() {
	document.querySelector(".modal-status").classList.toggle("show");
}

function modalStatusTroca(id) {
	document.querySelector(".modal-status-troca").classList.toggle("show");
	document.querySelector("#troca").value = id;
}
function modalStatusTrocaClose() {
	document.querySelector(".modal-status-troca").classList.toggle("show");
}