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

var joao = criarPessoa();
var maria = criarPessoa();

joao.setNome('Joao Ninguem');
joao.setIdade(67);

console.log(joao.getNome());
console.log(joao.getIdade());
console.log(joao.calculaIdade());


maria.setNome('Maria vai com as outras');
maria.setIdade(16);

console.log(maria.getNome());
console.log(maria.getIdade());
console.log(maria.calculaIdade());

