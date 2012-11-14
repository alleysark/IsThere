Ext.define('IsThere.store.SearchOptStore', {
	extend:'Ext.data.Store',
	alias:'store.searchOptStore',

	requires:[
		'IsThere.model.SearchOption'
	],

	constructor:function (cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
			model:'IsThere.model.SearchOption',
			data:[
				{
					name:'Default',
					id:1
				},
				{
					name:'Color Based',
					id:2
				},
				{
					name:'Object Based',
					id:3
				}
			]
		}, cfg)]);
	}
});