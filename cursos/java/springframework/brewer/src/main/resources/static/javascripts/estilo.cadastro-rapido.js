$(function() {
	
	var modal = $("#modalCadastroRapidoEstilo");
	var botaoSalvar = modal.find(".js-modal-cadastro-estilo-salvar-btn");
	var form = modal.find("form");
	var inputNomeEstilo = $("#nomeEstilo");
	var containerMensagemErro = $(".js-mensagem-cadastro-rapido-estilo");
	
	// remover a ação de submit do form
	form.on('submit', function(e) { e. preventDefault(); });
	
	// capturar a url de action
	var url = form.attr('action');
	
	// Colocar foco no input 
	// o shown.bs.modal, acontece depois que o modal é totalmente carregado
	modal.on('shown.bs.modal', onModalShow);
	// hide.bs.modal envendo no momento que fecha o modal 
	modal.on('hide.bs.modal', onModalClose);
	botaoSalvar.on('click', onBotaoSalvarClick);

	
	function onModalShow() {
		inputNomeEstilo.focus();
	}
	
	function onModalClose() {
		inputNomeEstilo.val('');
		containerMensagemErro.addClass('hidden');
		form.find('.form-group').removeClass('has-error');
	}
	
	function onBotaoSalvarClick() {
		var nomeEstilo = inputNomeEstilo.val().trim();
		
		$.ajax({
			url: url,
			method: 'POST',
			// padrao de dados de envio
			contentType: 'application/json',
			// Dados, valores que serão enviados para o metodo
			data: JSON.stringify({ nome: nomeEstilo }),
			// Erros
			error: onErroSalvandoEstilo,
			success: onEstiloSalvo
		});
	}
	
	
	function onErroSalvandoEstilo(object) {
		//Mostra na tela o objeto retornado em caso de erro ->  console.log(arguments);
		var mensagemErro = object.responseText;
		
		containerMensagemErro.removeClass('hidden');
		containerMensagemErro.html("<span>" +mensagemErro+ "</span>");
		form.find('.form-group').addClass('has-error');
		
	}
	
	function onEstiloSalvo(estilo) { // pode ser qualquer nome		
		// console.log(arguments); // Capturar o retorno
		
		var comboEstilo = $("#estilo");
		comboEstilo.append("<option value=" + estilo.codigo + ">" + estilo.nome + "</option>");
		comboEstilo.val(estilo.codigo);
		
		// Esconder o Modal
		modal.modal('hide');
		
	}
	
});