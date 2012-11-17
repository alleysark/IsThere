Ext.define('IsThere.store.DetailInfos', {
	extend:'Ext.data.Store',
	alias:'store.detailInfos',

	requires:'IsThere.model.DetailInfo',
	model:'IsThere.model.DetailInfo',

	autoLoad:false,
	listeners:{
		load:function( thisStore, records, successful, eOpts ){
			//this.add(records[0]);
		}
	}
});