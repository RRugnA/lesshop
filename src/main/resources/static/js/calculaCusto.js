const inputValorDeCusto = document.querySelector('#valorDeCusto');
const inputValor = document.querySelector('#valor');

inputValorDeCusto.addEventListener("input", function(){
    let valor = parseFloat(inputValorDeCusto.value);
    inputValor.value = (valor + (valor * 0.2)).toFixed(2);
});