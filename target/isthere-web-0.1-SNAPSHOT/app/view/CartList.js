Ext.define('IsThere.view.CartList',{
	extend:'Ext.grid.Panel',
	alias:'widget.cartList',

	requires:['Ext.grid.plugin.DragDrop', 'Ext.selection.CheckboxModel'],

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
					return false;
				}
			},
	        drop: function(node, data, dropRec, dropPosition) {
	            console.log(
		            'drop the ' + data.records[0].get('url') + ' ' + data.records[0].get('name')
		            + ' ' + data.records[0].get('appNo')
	            );
	        }
	    }
	},
    selModel: Ext.create('Ext.selection.CheckboxModel'),
	columns:[
		{
			xtype:'gridcolumn',
			renderer:function (value, metaData, record, rowIndex, colIndex, store, view) {
				return '<img src="' + value + '" width="64" height="64">';
			},
			dataIndex:'url',
			width:80,
			text:'견본 이미지'
		},
		{
			xtype:'gridcolumn',
			dataIndex:'appNo',
			flex:1,
			align:'center',
			text:'출원번호'
		}
	],

	initComponent:function () {
		var me = this;

		//Ext.applyIf(me, {

		//});

		me.callParent(arguments);
	}
});