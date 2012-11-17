Ext.define('IsThere.view.ResultList', {
	extend:'Ext.grid.Panel',
	alias:'widget.resultList',

	requires:[
		'IsThere.view.CartList',
		'Ext.grid.plugin.DragDrop'
	],

	store:'ResultStore',

	viewConfig:{
		plugins:{
			ptype:'gridviewdragdrop',
			ddGroup:'result-ddgroup',

			enableDrag:true,
			enableDrop:false
		},
		copy:true
	},
	columns:[
		{
			xtype:'gridcolumn',
			renderer:function (value, metaData, record, rowIndex, colIndex, store, view) {
				return '<img src="' + value + '" width="100" height="100">';
			},
			dataIndex:'url',
			width:100,
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