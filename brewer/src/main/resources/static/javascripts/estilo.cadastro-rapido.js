var Brewer = Brewer || {};

Brewer.EstiloCadastroRapido = (function(){
	
	function EstiloCadastroRapido() {
		// Colocar aqui todas as inicializações
		// Todas as variaveis tornaram propriedades, atributos
		this.modal = $("#modalCadastroRapidoEstilo");
		this.botaoSalvar = this.modal.find(".js-modal-cadastro-estilo-salvar-btn");
		this.form = this.modal.find("form");		
		this.inputNomeEstilo = $("#nomeEstilo");
		this.containerMensagemErro = $(".js-mensagem-cadastro-rapido-estilo");
		// capturar a url de action
		this.url = this.form.attr('action');
	}
	
	EstiloCadastroRapido.prototype.iniciar = function() { // pode ser o nome da sua preferência		
		// remover a ação de submit do form
		this.form.on('submit', function(e) { e.preventDefault(); });	
		// Colocar foco no input 
		// o shown.bs.modal, acontece depois que o modal é totalmente carregado
		this.modal.on('shown.bs.modal', onModalShow.bind(this)); // bind é para vincular com o contexto do objeto
		// hide.bs.modal envendo no momento que fecha o modal 
		this.modal.on('hide.bs.modal', onModalClose.bind(this));
		this.botaoSalvar.on('click', onBotaoSalvarClick.bind(this));
	}
	
	function onModalShow() {
		this.inputNomeEstilo.focus();
	}
	
	function onModalClose() {
		this.inputNomeEstilo.val('');
		this.containerMensagemErro.addClass('hidden');
		this.form.find('.form-group').removeClass('has-error');
	}
	
	function onBotaoSalvarClick() {
		var nomeEstilo = this.inputNomeEstilo.val().trim();		
		$.ajax({
			url: this.url,
			method: 'POST',
			// padrao de dados de envio
			contentType: 'application/json',
			// Dados, valores que serão enviados para o metodo
			data: JSON.stringify({ nome: nomeEstilo }),
			// Erros
			error: onErroSalvandoEstilo.bind(this),
			success: onEstiloSalvo.bind(this)
		});
	}
		
	function onErroSalvandoEstilo(object) {
		//Mostra na tela o objeto retornado em caso de erro ->  console.log(arguments);
		var mensagemErro = object.responseText;		
		this.containerMensagemErro.removeClass('hidden');
		this.containerMensagemErro.html("<span>" +mensagemErro+ "</span>");
		this.form.find('.form-group').addClass('has-error');		
	}
	
	function onEstiloSalvo(estilo) { // pode ser qualquer nome		
		// console.log(arguments); // Capturar o retorno		
		var comboEstilo = $("#estilo");
		comboEstilo.append("<option value=" + estilo.codigo + ">" + estilo.nome + "</option>");
		comboEstilo.val(estilo.codigo);		
		// Esconder o Modal
		this.modal.modal('hide');		
	}	
	
	return EstiloCadastroRapido;
	
}());


$(function() {
	
	var estiloCadastroRapido = new Brewer.EstiloCadastroRapido();
	estiloCadastroRapido.iniciar();			
	
});