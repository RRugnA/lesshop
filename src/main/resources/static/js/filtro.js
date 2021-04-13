const campoFiltro = document.querySelector("#filtrar-tabela");

campoFiltro.addEventListener("input", function(){
    console.log(this.value);
    const pacientes = document.querySelectorAll(".cliente");

    if(this.value.length > 0){
        pacientes.forEach(function(paciente){
            const tdNome = paciente.querySelector(".info-nome");
            const nome = tdNome.textContent;
            const expressao = new RegExp(campoFiltro.value, "i");
            if(!expressao.test(nome)){
                paciente.classList.add("invisivel");
            }else{
                paciente.classList.remove("invisivel");
            }
        });
    }else{
        for (let i = 0; i < pacientes.length; i++) {
            const paciente = pacientes[i];
            paciente.classList.remove("invisivel");
            
        }
    }
});