var express = require('express');

var app = express();

var PORT = 3000;
app.listen(PORT, () => {
    console.log('Este App esta ouvindo a porta %s!', PORT);
});