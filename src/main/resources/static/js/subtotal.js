const valorProdutos = document.getElementsByClassName("valorProduto");
let total = 0;

for(let i = 0; i < valorProdutos.length; i++){
  total += parseFloat(valorProdutos[i].innerText);
}

const valorFinal = total.toFixed(2);

document.querySelector("#subtotal").textContent = "R$ " + valorFinal;