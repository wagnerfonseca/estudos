// Errors apenas são lançados para funções sincronas

// Maneira incorreta de implentar erros com setTimeout
try {
    setTimeout( 
        function() {
            console.log('About to throw an error');
            throw new Error('Error thrown');
        }
        , 3000);
} catch(e) {
    console.log('I will never execute');
}


console.log('I am outside the try block'); 