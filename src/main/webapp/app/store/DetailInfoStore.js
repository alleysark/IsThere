Ext.define('IsThere.store.DetailInfoStore', {
	extend:'Ext.data.Store',
	alias:'store.detailInfoStore',

	requires:[
		'IsThere.model.DetailInfomation'
	],

	constructor:function (cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
			model:'IsThere.model.DetailInfomation'
		}, cfg)]);
	}
});