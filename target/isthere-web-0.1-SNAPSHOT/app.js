Ext.Loader.setConfig({
	enabled:true
});

Ext.application({

	models:[ 'Result', 'SearchOption', 'DetailInfo' ],

	stores:[ 'ResultStore', 'SearchOptStore', 'DetailInfos', 'CartStore' ],

	views:[ 'AppPanel', 'mainPanel', 'CartPanel', 'SearchPane', 'DetailPanel', 'ResultList', 'MyViewport', 'MyPanel5', 'LargeImgPanel', 'DetailInfoProperty', 'CartList' ],

	controllers:[ 'MainController' ],

	autoCreateViewport:true,

	name:'IsThere'
});
