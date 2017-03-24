var criarPessoa = function() {
  var idade;
  var nome;

  function calculaIdade() {
    return 100 - idade ;
  }
  
  return {
    setNome: function(novoNome) {
      nome = novoNome;
    },
    
    getNome: function() {
      return nome;
    },
    
    getIdade: function() {
      return idade;
    },
    
    setIdade: function(newIdade) {
      idade = newIdade;
    }, 
    calculaIdade
  }
}

var pessoa = criarPessoa();


pessoa.setNome('Joao Ninguem');
pessoa.setIdade(67);


console.log(pessoa.getNome());
console.log(pessoa.getIdade());
console.log(pessoa.calculaIdade());

