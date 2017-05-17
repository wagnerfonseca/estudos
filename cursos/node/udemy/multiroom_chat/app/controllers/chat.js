module.exports.init = function(app, req, res) {

    var dadosForm = req.body;

    // não existe necessidade de tratar o nome da propriedade
    req.assert('apelido', 'Nome ou apelido é obrigatório').notEmpty();
    req.assert('apelido', 'Nome ou apelido deve conter entre 3 a 15 caracteres').len(3,15);

    var erros = req.validationErrors();

    if (erros) {
        res.render('index', {validacao: erros});
        return;
    }

    // get para recuperar o valor da variavel 'io'
    app.get('io').emit('msgParaCliente', {apelido: dadosForm.apelido, mensagem: ' acabou de entrar no chat!'});

    res.render('chat', {apelido: dadosForm.apelido});
}