Ext.define('IsThere.view.CartPanel', {
	extend:'Ext.panel.Panel',
	alias:'widget.cartPanel',

	requires:[
		'IsThere.view.CartList'
	],

	layout:'fit',
	width:128,
	collapsed:false,
	collapsible:true,
	title:'Cart',

	initComponent:function () {
		var me = this;

		Ext.applyIf(me, {
            id:'cart-list',
			items:[
				{
					xtype:'cartList'
				}
			]
		});

		me.callParent(arguments);
	}

});