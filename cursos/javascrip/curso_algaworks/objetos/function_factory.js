// Preciso de "FABRICAR" objetos

var criarObjeto =  function(nome, sexo, idade) {

	return {
		nome: nome,
		sexo: sexo,
		idade: idade
	}

}

var novoObjeto = criarObjeto('Maria', 'F', '30');

console.log(novoObjeto)