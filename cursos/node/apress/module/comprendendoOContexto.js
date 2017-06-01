// Exemplo simples
var foo  = {
    bar: 123,
    bas: function() {
        console.log("dentro do 'bas' this.bar:", this.bar);
    }
}

console.log("O valor de 'foo.bar':", foo.bar);
foo.bas();

/**
 * Contexto this  
 */
var foo1 = {
    bar1: 123
}

function bas1() {
    if (this === global)
        console.log('foi chamado no contexto global');
    if (this === foo1)
        console.log('foi chamado no contexto de foo1');
};

bas1();

foo1.bas = bas1;
foo1.bas();

/**
 * comprendendo o contexto quando usa a palavra new
 */

function foo2() {
    this.foo2 = 99999;
    console.log('Esta no contexto global?', this == global);
}

// sem o operador NEW
foo2();
console.log(global.foo2);

var newFoo = new foo2();
console.log(newFoo.foo2); // 123