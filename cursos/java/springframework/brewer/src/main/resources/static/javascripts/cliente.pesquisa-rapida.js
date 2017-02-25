Brewer = Brewer || {};

Brewer.PesquisaRapidaCliente = (function(){
	
	function PesquisaRapidaCliente() {
		this.pesquisaRapidaClientesModal = $('#pesquisaRapidaClientes');
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
		this.pesquisaRapidaClientesModal.on('shown.bs.modal', onModalShow.bind(this)); 
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
		this.mensagemErro.addClass('hidden');
		
		var html = this.template(resultado); //pega o Html, e joga o objeto JSON para dentro e compila o html renderizado com os valores
		this.containerTabelaPesquisa.html(html);  // Pego o Resultado "compilado" e rederiza
		
		var tabelaPesquisaRapida = new Brewer.TabelaClientePesquisaRapida(this.pesquisaRapidaClientesModal);
		tabelaPesquisaRapida.iniciar();		
		
	}
	
	function onErroPesquisa() {
		this.mensagemErro.removeClass('hidden');
	}
	
	function onModalShow() {
		 this.nomeInput.focus();
	}
	
	return PesquisaRapidaCliente;
	
}());

// Classe resposanvel por capturar os dados do cliente selecionado
Brewer.TabelaClientePesquisaRapida = (function(){
	
	function TabelaClientePesquisaRapida(modal) {
		this.modalCliente = modal;
		this.cliente = $('.js-cliente-pesquisa-rapida');		
	}
	
	TabelaClientePesquisaRapida.prototype.iniciar = function() {
		this.cliente.on('click', onClienteSelecionado.bind(this));
	}
	
	function onClienteSelecionado(evento) {
		this.modalCliente.modal('hide');
		var clienteSelecionado = $(evento.currentTarget);
		//console.log('codigo', clienteSelecionado.data('codigo'));
		$('#nomeCliente').val(clienteSelecionado.data('nome'));
		$('#codigoCliente').val(clienteSelecionado.data('codigo'));
	}
	
	return TabelaClientePesquisaRapida;
	
}());

$(function() {
	var pesquisaRapidaCliente = new Brewer.PesquisaRapidaCliente();
	pesquisaRapidaCliente.iniciar();
});