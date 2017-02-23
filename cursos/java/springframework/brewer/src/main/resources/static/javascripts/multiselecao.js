Brewer = Brewer || {};

Brewer.MultiSelecao = (function(){
	
	function MultiSelecao() {
		this.statusBtn = $('.js-status-btn');
		this.selecaoCheckbox = $('.js-selecao');
	}
	
	MultiSelecao.prototype.iniciar = function() {
		this.statusBtn.on('click', onStatusBtnClicado.bind(this));
	}	
	
	function onStatusBtnClicado(event) {
		var botaoClicado = $(event.currentTarget);
		var status = botaoClicado.data('status');
		// console.log(botaoClicado.data('status'));
		
		var checkBoxSelecionados = this.selecaoCheckbox.filter(':checked');
		// montar um array de codigo selecionados
		var codigos = $.map(checkBoxSelecionados, function(c) {
			return $(c).data('codigo');
		});
		
		//console.log(codigos);
		
		if (codigos.length > 0) {
			$.ajax({			
				url: '/brewer/usuarios/status',
				method: 'PUT',
				data: {
					codigos: codigos,
					status: status
				},
				success: function() {
					//Atualizar pagina. refazer  a requisicao de pagina
					window.location.reload();
				}
			});
		}
		
	}
	
	return MultiSelecao;
	
}());

$(function(){
	var multiSelecao = new Brewer.MultiSelecao();
	multiSelecao.iniciar();
});