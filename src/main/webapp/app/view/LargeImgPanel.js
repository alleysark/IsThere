Ext.define('IsThere.view.LargeImgPanel', {
	extend:'Ext.panel.Panel',
	alias:'widget.largeImgPanel',

	layout:'anchor',
	border:false,

	initComponent:function () {
		var me = this;

		me.items = [
			{
				xtype:'imagecomponent',
				id:'largeImg',
				region:'center',
                width:256,
                height:256
			}
		];

		me.callParent(arguments);
	}

});