var emails = [];

emails.push('joao@email.com');
emails.push('jose@email.com');
emails.push('maria@email.com');
emails.push('marta@email.com');



console.log(emails.push('pedro@email.com'));
console.log(emails.join(' - '));


console.log(emails.pop());

emails.unshift('wagner@email.com');

emails.forEach(function(e) {
	console.log('email', '->', e);
});


console.log(emails.toString());

console.log(emails.shift());


var myObjectLiteral = {
 
    variableKey: variableValue,
 
    functionKey: function () {
      // ...
    }
};