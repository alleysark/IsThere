Ext.define('IsThere.store.ResultStore', {
	extend:'Ext.data.Store',
	alias:'store.resultStore',

	requires:'IsThere.model.Results',
	model:'IsThere.model.Results',

	autoLoad:false
});