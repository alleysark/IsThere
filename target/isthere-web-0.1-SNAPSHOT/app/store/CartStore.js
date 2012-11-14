Ext.define('IsThere.store.CartStore', {
	extend:'Ext.data.Store',
	alias:'store.cartStore',

	requires:'IsThere.model.Results',
	model:'IsThere.model.Results'
});