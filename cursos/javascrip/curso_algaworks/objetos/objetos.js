var pessoa = {
	nome: 'Jose',
	idade: 67,
	temFoto: true,
	endereco: {
		logradouro: 'Rua das cassias',
		numero: 154,
		cidade: 'Curitiba',
		estado: 'PR'
	}
}

console.log(pessoa);

delete pessoa.temFoto;
delete pessoa.endereco.numero;

console.log(pessoa);

var txt = '';
for (x in pessoa) {
    txt += pessoa[x];
}

console.log(txt);