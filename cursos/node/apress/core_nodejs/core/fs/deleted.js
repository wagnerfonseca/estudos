var fs = require('fs');
// deletando o arquivo

// Exemplo Sincrono
// try {
//     fs.unlinkSync('./test.txt');
//     console.log('test.txt successfully deleted');
// } catch(e) {
//     console.log('Error:', e);
// }

// Exemplo assincrono
// na versão assincrona, o obejto erro é passado para a função de callback
fs.unlink('./test.txt', function(err){
    if (err) {
        console.log('Error:', err);
    } else {
        console.log('test.txt has fully deleted!');
    }
});
