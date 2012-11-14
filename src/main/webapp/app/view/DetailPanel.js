Ext.define('IsThere.view.DetailPanel', {
	extend:'Ext.panel.Panel',
	alias:'widget.detailPanel',

	requires:[
		'IsThere.view.LargeImgPanel',
		'IsThere.view.DetailInfoProperty',
		'IsThere.view.MoreDetail'
	],

	layout:'border',

	title:'Detail Information',

	initComponent:function () {
		var me = this;
		me.items = [
			{
				xtype:'largeImgPanel',
				region:'west',
				width:256,
				height:256
			},
			{
				xtype:'detailInfoProperty',
				region:'center',
				flax:1,
				margin:'5 5 5 5'
			},
			{
				xtype:'moreDetail',
				region:'south',
				height:100,
				split:true,
				collapsible:true
			}
		];
		me.callParent(arguments);
	}

});