var Brewer = Brewer || {};

Brewer.UploadFoto = (function() {
	
	function UploadFoto() {		
		this.inputNomeFoto = $('input[name=foto]');
		this.inputContentType = $('input[name=contentType]');
		this.novaFoto = $('input[name=novaFoto]');
		
		this.htmlFotoCervejaTemplate = $('#foto-cerveja').html();
		this.template = Handlebars.compile(this.htmlFotoCervejaTemplate);		
		this.containerFotoCerveja = $('.js-container-foto-cerveja');		
		this.uploadDrop = $('#upload-drop');		
		this.urlFotos = this.containerFotoCerveja.data('url-fotos');	
		
	}
	
	UploadFoto.prototype.iniciar = function() {
		var settings = {
				type: 'json',
				filelimit: 1,
				allow: '*.(jpg|jpeg|png)',
				// acessar o atributo que contem a url da action para ser executada
				action: this.urlFotos,
				// 'this' eu passo como parametro para para fazer um vinculo com o contexto do objeto que esta em execução.
				// e assim também ter acesso a todas as propriedades deste objeto
				complete: onUploadCompleto.bind(this),
				
				//Ante de enviar a requisicao - enviar os dados de token od CSRF
				beforeSend: addCsrfToken
		}
		
		UIkit.uploadSelect($('#upload-select'), settings);
		UIkit.uploadDrop(this.uploadDrop, settings);
		
		// a função call obriga this a ser executado dentro do contexto
		if (this.inputNomeFoto.val()) { // se ja existe o nome da foto ele vai renderizar a foto
			renderizarFoto.call(this, { nome:  this.inputNomeFoto.val(), contentType: this.inputContentType.val()});
		}
	}
	
	function onUploadCompleto(resposta) {
		this.novaFoto.val('true');
		renderizarFoto.call(this, resposta);
	}	
	
	function renderizarFoto(resposta) {
		this.inputNomeFoto.val(resposta.nome);
		this.inputContentType.val(resposta.contentType);
		
		this.uploadDrop.addClass('hidden');
		
		var foto = '';
		if (this.novaFoto.val() == 'true') {
			foto = 'temp/';
		}
		foto += resposta.nome;
		
		var htmlFotoCerveja = this.template({foto: foto});
		this.containerFotoCerveja.append(htmlFotoCerveja);
		
		// realizo o seletor nesta parte do codigo por este so ira estar acessivel a partir do carregamento do template	
		$('.js-remove-foto').on('click', onRemoverFoto.bind(this));
	}
	
	function onRemoverFoto(nome) {
		$.ajax({
	        url: this.urlFotos + '/temp/' + nome,
	        method: 'DELETE'
	    });
		
		$('.js-foto-cerveja').remove();
		this.uploadDrop.removeClass('hidden');
		this.inputNomeFoto.val('');
		this.inputContentType.val('');
		
		this.novaFoto.val('false');
	}	
	
	function addCsrfToken(xhr) {
		var token = $('input[name=_csrf]').val();
		var header = $('input[name=_csrf_header]').val();
		
		xhr.setRequestHeader(header, token);
	}
	
	return UploadFoto;
	
})();

$(function() {
	
	var uploadFoto = new Brewer.UploadFoto();
	uploadFoto.iniciar();	
		
}); 