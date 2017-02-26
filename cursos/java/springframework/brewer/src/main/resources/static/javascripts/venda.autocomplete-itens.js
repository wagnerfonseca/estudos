Brewer = Brewer || {};

Brewer.Autocomplete = (function(){
	
	function Autocomplete() {
		this.skuOuNomeInput = $('.js-sku-nome-cerveja-input');
		// pagina HTML que vai ser renderizada		
		var htmlTemplateAutocomplete = $('#template-autocomplete-cerveja').html();
		// Pagina HTML compilada		
		this.template = Handlebars.compile(htmlTemplateAutocomplete);
		
		// #CadastroMestreDetalhe
		// A tabela de itens de venda precisa ouvir/saber o momento que uma cerveja pesquisada for selecionada
		// cria um emissor de eventos para que outros "scrits" possam ouvir este envento quando uma cerveja for selecionada
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
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
				method: template.bind(this) // retorno
			},
			
			// #CadastroMestreDetalhe			
			// Esta propriedade do plugin "EasyAutocomplete" dispara no momento que um item é selecionado
			list: {
				onChooseEvent: onItemSelecionado.bind(this)
			}
		}
		
		function onItemSelecionado() {
			// getSelectedItemData <- propriedade do "EasyAutoComplete" 
			// console.log('item selecionado:', this.skuOuNomeInput.getSelectedItemData());
			
			// "item-selecionado" <- nome do envento
			// this.skuOuNomeInput.getSelectedItemData() <- os dados que vão ser enviados
			this.emitter.trigger('item-selecionado', this.skuOuNomeInput.getSelectedItemData() );
		}
		
		function template(nome, cerveja) {			
			// Nome é referente a propridade "getValue"
			// e Cerveja é o objeto de retorno do metodo "pesquisar" que esta implementado dentro do controller "CervejasController"
			// console.log(arguments); "arguments" tem os todos os valores de retorno
			
			// nova propriedade para o objeto "cerveja.valorFormatado"
			cerveja.valorFormatado = Brewer.formatarMoeda(cerveja.valor);
			
			// pagina HTML que vai receber o objeto "cerveja"
			return this.template(cerveja);	
		}
		
		this.skuOuNomeInput.easyAutocomplete(options);
	}
	
	return Autocomplete;
	
}()); 