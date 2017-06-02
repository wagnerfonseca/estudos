var fs = require('fs');

// pos fixo nos nomes das funções 'Sync' para funções sincronas
fs.writeFileSync('test.txt', 'Hello my friends!');

// read
console.log(fs.readFileSync('test.txt').toString());