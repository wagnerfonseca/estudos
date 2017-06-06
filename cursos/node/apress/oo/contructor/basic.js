function Foo() {}

console.log(Foo.prototype.constructor == Foo);
console.log(Foo.prototype.constructor);
console.log(Foo.prototype.constructor.name);

// Podemos acessar o construtor via função da instancia
var foo = new Foo();
console.log(foo.constructor == Foo);
console.log(foo.constructor);
console.log(foo.constructor.name);
