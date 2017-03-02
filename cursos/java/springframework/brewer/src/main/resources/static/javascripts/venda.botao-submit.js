Brewer = Brewer || {};

Brewer.BotaoSubmit = (function(){
	
	function BotaoSubmit() {
		this.submitBtn = $('.js-submit-btn');
		this.formulario = $('.js-formulario-principal');
	}
	
	BotaoSubmit.prototype.iniciar = function() {
		this.submitBtn.on('click', onSubmit.bind(this));
	}
	
	function onSubmit(event) {
		// Cancelo a ação padrão do botão submit
		// Neste caso, a chamada do metodo salvar a venda no controller não acontece mais
		event.preventDefault();
		
		// Capturo qual a acao vai ser executada
		var botaoClicado = $(event.target);
		var acao = botaoClicado.data('acao');
		
		// Por isso é criado um input com a acao que foi selecionada
		var acaoInput = $('<input>');
		acaoInput.attr('name', acao);
		
		// atribuo esse input para o formulario
		this.formulario.append(acaoInput);
		
		// executo a acao do input
		this.formulario.submit();
	}
	
	return BotaoSubmit;
	
}());

$(function(){
	
	var botaoSubmit = new Brewer.BotaoSubmit();
	botaoSubmit.iniciar();
	
});