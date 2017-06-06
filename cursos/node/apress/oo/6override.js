function Base() {
    this.message = 'Message';
}

Base.prototype.foo = function() {
    return this.message + ' base foo';
}

function Child() {
    Base.call(this);
}

Child.prototype.__proto__ = Base.prototype;

// Sobreescrevendo sobre o comportamento do pai
Child.prototype.foo = function() {
    // chamando a função que esta declara na classe pai
    return Base.prototype.foo.call(this) + ' child foo';
}


// Test
var child = new Child();
console.log(child.foo());