Brewer = Brewer || {};

Brewer.Autocomplete = (function(){
	
	function Autocomplete() {
		this.skuOuNomeInput = $('.js-sku-nome-cerveja-input');
		// pagina HTML que vai ser renderizada		
		var htmlTemplateAutocomplete = $('#template-autocomplete-cerveja').html();
		// Pagina HTML compilada		
		this.template = Handlebars.compile(htmlTemplateAutocomplete);
	}
	
	Autocomplete.prototype.iniciar = function() {
		var options = {
			url: function(skuOuNome) {
				return '/brewer/cervejas?skuOuNome=' + skuOuNome;
			},
			getValue: 'nome', // valores que serão mostrados durante a pesquisa no autocomplete 
			minCharNumber: 3, // Minimo de caracter para começar a pesquisar
			requestDelay: 500,
			ajaxSettings: {
				contentType: 'application/json'
			},
			// O retorno de dados vai ser customizado
			// Este componente (easycomplete) permite eu mesmo criar o layout(HTML) de retorno
			template: {
				type: 'custom',
				method: function(nome, cerveja) { // retorno
					// Nome é referente a propridade "getValue"
					// e Cerveja é o objeto de retorno do metodo "pesquisar" que esta implementado dentro do controller "CervejasController"
					// console.log(arguments); "arguments" tem os dois retorno
					
					// nova propriedade para o objeto "cerveja.valorFormatado"
					cerveja.valorFormatado = Brewer.formatarMoeda(cerveja.valor);
					
					// pagina HTML que vai receber o objeto "cerveja"
					return this.template(cerveja);					
				}.bind(this)
			}
			
		}
		
		this.skuOuNomeInput.easyAutocomplete(options);
	}
	
	return Autocomplete;
	
}()); 


$(function(){
	var autocomplete =  new Brewer.Autocomplete();
	autocomplete.iniciar();
});