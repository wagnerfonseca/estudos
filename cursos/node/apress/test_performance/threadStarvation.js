
// Mede  o tempo passado usando os comandos console.time e console.timeEnd
console.time('timer');
 
setTimeout(function(){
    console.timeEnd('timer');
},3000)