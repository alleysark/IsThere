Ext.define('IsThere.store.CartStore', {
	extend:'Ext.data.Store',
	alias:'store.cartStore',

	requires:'IsThere.model.Result',
	model:'IsThere.model.Result'
});