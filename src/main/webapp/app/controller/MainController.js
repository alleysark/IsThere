Ext.define('IsThere.controller.MainController', {
	extend:'Ext.app.Controller',
	alias:'controller.mainController',

	models:[ 'Results' ],

	stores:[ 'ResultStore' ],

	views:[ 'SearchPanel', 'CartPanel'],

	refs:[
		//searchPanel Toolbar items
		{
			ref:'conditionCombo',
			selector:'searchPanel #conditionCombo'
		},
		{
			ref:'keywordTextfield',
			selector:'searchPanel #keywordTextfield'
		},

		//resultGrid
		{
			ref:'resultGrid',
			selector:'resultList'
		},

		//cartGrid
		{
			ref:'cartList',
			selector:'cartList'
		},

		//detailInfoProperty
		{
			ref:'nameTextfield',
			selector:'detailInfoProperty #nameTextfield'
		},
		{
			ref:'largeImgPanel',
			selector:'largeImgPanel'
		}
	],

	init:function (application) {
		this.control({
			'resultList':{
				itemclick:this.onShowDetailInfo
			},
			'cartList':{
				itemclick:this.onShowDetailInfo
			},
			'searchPanel #uploadButton':{
				click:this.onUploadClick
			},
			'searchPanel #searchButton':{
				click:this.onSearchClick
			}
		});
	},

	onShowDetailInfo:function (tablepanel, record, item, index, e, options) {
		console.log(record.get('url') + " " + record.get('name'));

		//set image

		//set detail informations
		this.getNameTextfield().setValue(record.get('name'));
	},

	onSearchClick:function () {
		var keywordText = this.getKeywordTextfield().getValue();
		var condition = this.getConditionCombo().getValue();

		this.getResultGrid().getStore().load({
			params:{
				keyword:keywordText,
				condition:condition
			}
		});
	},

	onUploadClick:function () {
		console.log('onUploadClick');
	}
});