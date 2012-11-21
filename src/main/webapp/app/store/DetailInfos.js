Ext.define('IsThere.store.DetailInfos', {
	extend:'Ext.data.Store',
	alias:'store.detailInfos',

	requires:'IsThere.model.DetailInfo',
	model:'IsThere.model.DetailInfo',

	autoLoad:false
});