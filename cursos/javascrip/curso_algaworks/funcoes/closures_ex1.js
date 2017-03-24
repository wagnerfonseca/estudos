///================================================
var pet = function(nome) {          // A função externa define uma variável "nome"
      var getNome = function() {
        return nome;                // A função interna tem acesso à variável "nome"  da função externa
      }

      return getNome;               // Retorna a função interna, expondo-a assim para escopos externos
    },
    myPet = pet("javaescrito");

    
                            
console.log(myPet()); 				// Retorna "Vivie"

var myObject = {
    firstName:"John",
    lastName: "Doe",
    fullName: function() {
        return this;
    }
}

console.log(myObject.fullName());