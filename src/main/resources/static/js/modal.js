function modalStatus(id) {
	document.querySelector(".modal-status").classList.toggle("show");
	document.querySelector("#compra").value = id;
}
function modalStatusClose() {
	document.querySelector(".modal-status").classList.toggle("show");
}

function modalStatusTroca(compraId, produtoId) {
	document.querySelector(".modal-status-troca").classList.toggle("show");
	document.querySelector("#compra").value = compraId;
	document.querySelector("#troca").value = produtoId;
}
function modalStatusTrocaClose() {
	document.querySelector(".modal-status-troca").classList.toggle("show");
}

// MODAL CARRINHO ENDEREÇO
function modalCarrinho() {
	document.querySelector(".modalEndereco").classList.toggle("show");
	document.querySelector("body").classList.toggle("of-hidden");
}
function modalCarrinhoClose() {
	document.querySelector(".modalEndereco").classList.toggle("show");
	document.querySelector("body").classList.toggle("of-hidden");
}