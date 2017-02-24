Brewer = Brewer || {};

Brewer.PesquisaRapidaCliente = (function(){
	
	function PesquisaRapidaCliente() {
		this.pesquisaRapidaClientesModal= $('#pesquisaRapidaClientes');
		this.nomeInput = $('#nomeClienteModal');
		this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-clientes-btn');
		
		// HANDLEBARS
		// Container
		this.containerTabelaPesquisa = $('#containerTabelaPesquisaRapidaClientes');
		// html
		this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-cliente').html();		
		// Template
		this.template = Handlebars.compile(this.htmlTabelaPesquisa);
				
		this.mensagemErro = $('.js-mensagem-erro');
	}
	
	PesquisaRapidaCliente.prototype.iniciar = function() {
		this.pesquisaRapidaBtn.on('click', onPesquisaRapidaClicado.bind(this));
	}
	
	function onPesquisaRapidaClicado(event) {
		event.preventDefault();
		
		$.ajax({
			url: this.pesquisaRapidaClientesModal.find('form').attr('action'),		
			method: 'GET',
			contentType: 'application/json',			
			data: {
				nome: this.nomeInput.val()
			},
			success: onPesquisaConcluida.bind(this),
			error: onErroPesquisa.bind(this)
		});
		
	}
	
	function onPesquisaConcluida(resultado) {
		var html = this.template(resultado); //pega o Html, e joga o objeto JSON para dentro e compila o html renderizado com os valores
		this.containerTabelaPesquisa.html(html);
		this.mensagemErro.addClass('hidden');
	}
	
	function onErroPesquisa() {
		this.mensagemErro.removeClass('hidden');
	}
	
	return PesquisaRapidaCliente;
	
}());

$(function() {
	var pesquisaRapidaCliente = new Brewer.PesquisaRapidaCliente();
	pesquisaRapidaCliente.iniciar();
});