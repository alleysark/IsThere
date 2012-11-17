Ext.define('IsThere.store.ResultStore', {
	extend:'Ext.data.Store',
	alias:'store.resultStore',

	requires:'IsThere.model.Result',
	model:'IsThere.model.Result',

	storeId:'resStore',
	autoLoad:false
});