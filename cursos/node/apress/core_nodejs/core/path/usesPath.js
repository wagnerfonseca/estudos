var path = require('path');

console.log(path.normalize('foo/bar/..'));
console.log(path.normalize('foo//bas/bar/..'));

console.log(path.join('foo//bas', 'bar', 'dac'));

var completePath = '/foo/bar/bas.html';
// Logs : /foo/bar
console.log(path.dirname(completePath));
// Logs : bas.html
console.log(path.basename(completePath));
// Logs : .html
console.log(path.extname(completePath));

// Documentacao 
// https://nodejs.org/api/path.html