Ext.define('IsThere.view.CartList',{
	extend:'Ext.grid.Panel',
	alias:'widget.cartList',

	requires:['Ext.grid.plugin.DragDrop'],

	store:'CartStore',

	viewConfig:{
		plugins:{
			ptype:'gridviewdragdrop',
			ddGroup:'result-ddgroup',

			enableDrag:false,
			enableDrop:true
		},
		listeners: {
			beforedrop:function(node, data, overModel, dropPosition, dropFunction, eOpts ){
				//카트에 항목이 이미 있다면, 복사하지 않는다.
				if( this.getStore().findRecord('name',data.records[0].get('name')) != null ){
					console.log('there already exist');
				}
			},
	        drop: function(node, data, dropRec, dropPosition) {
	            console.log('drop the ' + data.records[0].get('url') + ' ' + data.records[0].get('name'));
	        }
	    }
	},
	columns:[
		{
			xtype:'gridcolumn',
			renderer:function (value, metaData, record, rowIndex, colIndex, store, view) {
				return '<img src="' + value + '" width="64" height="64">';
			},
			dataIndex:'url',
			width:64,
			text:'Image'
		},
		{
			xtype:'gridcolumn',
			dataIndex:'name',
			flex:1,
			align:'center',
			text:'Name'
		}
	],

	initComponent:function () {
		var me = this;

		//Ext.applyIf(me, {

		//});

		me.callParent(arguments);
	}
});