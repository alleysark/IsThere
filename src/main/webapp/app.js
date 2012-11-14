Ext.Loader.setConfig({
	enabled:true
});

Ext.application({

	models:[ 'Results', 'SearchOption', 'DetailInfomation' ],

	stores:[ 'ResultStore', 'SearchOptStore', 'DetailInfoStore', 'CartStore' ],

	views:[ 'AppPanel', 'mainPanel', 'CartPanel', 'SearchPane', 'DetailPanel', 'ResultList', 'MyViewport', 'MyPanel5', 'LargeImgPanel', 'DetailInfoProperty', 'CartList' ],

	controllers:[ 'MainController' ],

	autoCreateViewport:true,

	name:'IsThere'
});
