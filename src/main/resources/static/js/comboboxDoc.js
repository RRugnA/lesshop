document.getElementById("tipoCliente1").onclick = function() {
  
  const selectBox = document.querySelector(".selectDoc");
  removeOption();

  const opt0 = document.createElement('option');
  opt0.value = "0";
  opt0.text = "";
  selectBox.add(opt0, selectBox.options[0]);

  const opt1 = document.createElement('option');
  opt1.value = "CPF";
  opt1.text = "CPF";
  selectBox.add(opt1, selectBox.options[1]);
}

document.getElementById("tipoCliente2").onclick = function() {
  
  const selectBox = document.querySelector(".selectDoc");
  removeOption();

  const opt0 = document.createElement('option');
  opt0.value = "0";
  opt0.text = "";
  selectBox.add(opt0, selectBox.options[0]);

  const opt1 = document.createElement('option');
  opt1.value = "CNPJ";
  opt1.text = "CNPJ";
  selectBox.add(opt1, selectBox.options[1]);
}

function removeOption() {

  const selectBox = document.querySelector(".selectDoc");
  while (selectBox.length) {
    selectBox.remove(0);
  }
}