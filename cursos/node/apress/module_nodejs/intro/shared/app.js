var foo =  require('./foo');
console.log('initial something:', foo.something);

// Now modify something
foo.something = 456;

// now load bar
var bar = require('./bar');
