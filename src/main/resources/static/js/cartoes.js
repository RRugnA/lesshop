let valor1 = document.querySelector("#card1");
let valor2 = document.querySelector("#card2");
const optionParc1 = document.querySelector("#valorParcela1").options;

for(let i = 0; i < optionParc1.length; i++){
    let valorParc1 = (valor1.value/(i+1)).toFixed(2);
    optionParc1[i].innerHTML = (i+1) + "x de R$ " + valorParc1;
    optionParc1[i].value =  valorParc1 + ":" + (i+1);
};

if(document.querySelector("#valorParcela2")){
	const optionParc2 = document.querySelector("#valorParcela2").options;
	for(let i = 0; i < optionParc2.length; i++){
	    let valorParc2 = (valor2.value/(i+1)).toFixed(2);
	    optionParc2[i].innerHTML = (i+1) + "x de R$ " + valorParc2;
	    optionParc2[i].value = valorParc2 + ":" + (i+1);
	};
	
	valor2.addEventListener('blur', (event) => {
	    document.querySelector("#card1").value = (document.querySelector("#valorFinal").innerText - valor2.value).toFixed(2);
	    for(let i = 0; i < optionParc2.length; i++){
	        let valorParc2 = (valor2.value/(i+1)).toFixed(2);
	        optionParc2[i].innerHTML = (i+1) + "x de R$ " + valorParc2;
	        optionParc2[i].value = valorParc2 + ":" + (i+1);
	    };
	    for(let i = 0; i < optionParc1.length; i++){
	        let valorParc1 = (valor1.value/(i+1)).toFixed(2);
	        optionParc1[i].innerHTML = (i+1) + "x de R$ " + valorParc1;
	        optionParc1[i].value = valorParc1 + ":" + (i+1);
	    };
	});
}

valor1.addEventListener('blur', (event) => {
    
    for(let i = 0; i < optionParc1.length; i++){
        let valorParc1 = (valor1.value/(i+1)).toFixed(2);
        optionParc1[i].innerHTML = (i+1) + "x de R$ " + valorParc1;
        optionParc1[i].value = valorParc1 + ":" + (i+1);
    };
    
    if(document.querySelector("#valorParcela2")){    	
    	const optionParc2 = document.querySelector("#valorParcela2").options;
    	document.querySelector("#card2").value = (document.querySelector("#valorFinal").innerText - valor1.value).toFixed(2);
    	 for(let i = 0; i < optionParc2.length; i++){
    	        let valorParc2 = (valor2.value/(i+1)).toFixed(2);
    	        optionParc2[i].innerHTML = (i+1) + "x de R$ " + valorParc2;
    	        optionParc2[i].value = valorParc2 + ":" + (i+1);
    	    };
    }
   
});

