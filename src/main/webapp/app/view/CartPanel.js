Ext.define('IsThere.view.CartPanel', {
	extend:'Ext.panel.Panel',
	alias:'widget.cartPanel',

	requires:[
		'IsThere.view.CartList'
	],

	layout:'fit',
	width:220,
	collapsed:false,
	collapsible:true,
	title:'Cart',

	initComponent:function () {
		var me = this;

		Ext.applyIf(me, {
			items:[
				{
					xtype:'cartList'
				}
			]
		});

		me.callParent(arguments);
	}

});