function Animal(name) {
    this.name = name;
}

Animal.prototype.walk = function(destination) {
    console.log(this.name, 'esta andando para', destination);
}

var elefante = new Animal('elefante');
elefante.walk('casa');

function Bird(name) {
    // Chama o contrutor de Animal
    Animal.call(this, name);    
}
// Forma mais simples e rápida para herdar o protype de Animal
Bird.prototype.__proto__ = Animal.prototype

Bird.prototype.fly = function(destination) {
    console.log(this.name,'esta voando para', destination);
}

var passaro = new Bird('Passaro');
passaro.walk('Casa');
passaro.fly('Ninho');