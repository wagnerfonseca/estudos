Brewer = Brewer || {};

Brewer.DialogoExcluir = (function(){
	
	function DialogoExcluir() {
		this.exclusaoBtn = $('.js-exclusao-btn');
	}
	
	DialogoExcluir.prototype.iniciar = function() {
		this.exclusaoBtn.on('click', onExcluirClicado.bind(this));
		
		// no carregamento da tela - verifica se existe um parametro excluido
		if (window.location.search.indexOf('excluido') > -1) {
			swal('Pronto!', 'Excluído com sucesso!', 'success');
		}
	}
	
	function onExcluirClicado(evento) {
		event.preventDefault();
		
		var botaoClicado = $(evento.currentTarget); // quem disparou o envento
		var url = botaoClicado.data('url');
		var objeto = botaoClicado.data('objeto');
		
		swal({
			title: 'Tem certeza?',
			text: 'Excluir "' + objeto + '"? Você não poderá recuperar depois.',
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Sim, exclua agora!',
			closeOnConfirm: false // mostra a mensagem de erro se existir
		}, onExcluirConfirmado.bind(this, url));
		
	}
	
	function onExcluirConfirmado(url) {
		$.ajax({
			url: url,
			method: 'DELETE',
			success: onExcluidoSucesso.bind(this), // Mensagem de sucesso
			error: onErroExcluir.bind(this)  // Mensagem de erro
		});
	}
	
	function onExcluidoSucesso() {
		// a ideia aqui é forçar um parametro "?excluido"
		
		var urlAtual = window.location.href; // pega o URI atual
		var separador = urlAtual.indexOf('?') > -1 ? '&' : '?'; // se ja existe nesta URI um '?' coloca um '&' senao '?'
		var novaUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separador + 'excluido';
		
		window.location = novaUrl; // substitui para a nova URL
	}
	
	function onErroExcluir(e) {
		console.log('ahahahah', e.responseText);
		swal('Oops!', e.responseText, 'error'); // Texto do erro 
	}
	
	return DialogoExcluir;
	
}());

$(function(){
	
	var dialogo = new Brewer.DialogoExcluir();
	dialogo.iniciar();
	
});