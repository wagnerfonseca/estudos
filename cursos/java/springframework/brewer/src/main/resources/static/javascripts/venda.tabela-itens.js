/** Aula 23.5 - Itens de vendas 
 * Script responsavel pelos itens da venda
 * #CadastroMestreDetalhe */

Brewer.TabelaItens = (function () {
	
	function TabelaItens(autocomplete) {
		this.autocomplete = autocomplete;
	}
	
	TabelaItens.prototype.iniciar =  function() {
		// quero escutar o evento que foi criado em Brewer.Autocomplete
		this.autocomplete.on('item-selecionado', onItemSelecionado.bind(this));		
	}
	
	function onItemSelecionado(evento, item) {
		// "item" é referente aos dados enviados 
		// console.log('item', item);
		var resposta = $.ajax({
			// Declarando dessa forma ele retira o "localhost:8080:/brewer/venda/nova" e substitui por "localhost:8080:/brewer/venda/item"
			// Se colocar "/item" vai chamar localhost:8080:/brewer/item
			url: 'item',
			method: 'POST',
			data: {
				codigoCerveja: item.codigo
			}
		});
		
		resposta.done(function(data){
			
		});
	}
	
	return TabelaItens;
	
}());

$(function(){
	var autocomplete =  new Brewer.Autocomplete();
	autocomplete.iniciar();
	
	var tabelaItens =  new Brewer.TabelaItens(autocomplete);
	tabelaItens.iniciar();
});