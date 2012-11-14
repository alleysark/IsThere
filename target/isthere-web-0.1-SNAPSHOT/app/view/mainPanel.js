Ext.define('IsThere.view.mainPanel', {
	extend:'Ext.panel.Panel',
	alias:'widget.mainPanel',

	requires:[
		'IsThere.view.SearchPanel',
		'IsThere.view.DetailPanel'
	],

	autoScroll:false,

	layout:{
		type:'border'
	},

	initComponent:function () {
		var me = this;

		Ext.applyIf(me, {
			items:[
				{
					xtype:'searchPanel',
					region:'center'
				},
				{
					xtype:'detailPanel',
					region:'south',
					height:450,
					collapsible:true,
					split:true,
					collapsed:true
				}
			]
		});

		me.callParent(arguments);
	}

});