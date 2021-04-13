document.querySelector("#tipo");

if(tipo.value == "FISICA") {
	selectDocFisica();
} else if(tipo.value == "JURIDICA"){
	selectDocJuridica();
}

function selectDocFisica() {
	  
	const selectBox = document.querySelector(".selectDoc");
	removeOption();
	
	const opt0 = document.createElement('option');
	opt0.value = "0";
	opt0.text = "";
	selectBox.add(opt0, selectBox.options[0]);
	
	const opt1 = document.createElement('option');
	opt1.value = "1";
	opt1.text = "RG";
	selectBox.add(opt1, selectBox.options[1]);
	
	const opt2 = document.createElement('option');
	opt2.value = "2";
	opt2.text = "CPF";
	selectBox.add(opt2, selectBox.options[2]);
}

function selectDocJuridica() {
  
	const selectBox = document.querySelector(".selectDoc");
	removeOption();
	
	const opt0 = document.createElement('option');
	opt0.value = "0";
	opt0.text = "";
	selectBox.add(opt0, selectBox.options[0]);
	
	const opt1 = document.createElement('option');
	opt1.value = "1";
	opt1.text = "CNPJ";
	selectBox.add(opt1, selectBox.options[1]);
}

function removeOption() {
  const selectBox = document.querySelector(".selectDoc");
  while (selectBox.length) {
    selectBox.remove(0);
  }
}