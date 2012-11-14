Ext.define('IsThere.view.Viewport', {
	extend:'Ext.container.Viewport',
	layout:'fit',
	requires:[
		'IsThere.view.mainPanel',
		'IsThere.view.CartPanel'
	],

	border:false,

	initComponent:function () {
		this.items = {
			layout:'border',
			border:false,
			items:[
				{
					xtype:'mainPanel',
					flex:1,
					region:'center'
				},
				{
					xtype:'cartPanel',
					region:'west'
				}
			]
		};
		this.callParent(arguments);
	}
});