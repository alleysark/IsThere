Ext.define('IsThere.view.LargeImgPanel', {
	extend:'Ext.panel.Panel',
	alias:'widget.largeImgPanel',

	layout:'anchor',
	border:true,

	initComponent:function () {
		var me = this;

		me.items = [
			{
				xtype:'imagecomponent',
				itemId:'largeImg',
				region:'center',
				margin:'5 5 5 5',
				src:'img/01.jpg'
			}
		];

		me.callParent(arguments);
	}

});