Ext.define('IsThere.view.SearchPanel', {
	extend:'Ext.panel.Panel',
	alias:'widget.searchPanel',

	requires:[
		'IsThere.view.ResultList'
	],

	layout:'fit',

	border:false,

	title:'Search',

	initComponent:function () {
		var me = this;

		Ext.applyIf(me, {
			dockedItems:[
				{
					xtype:'toolbar',
					dock:'top',
					items:[
						{
							itemId:'conditionCombo',
							xtype:'combo',
							width:180,
							labelWidth:50,
							store:'SearchOptStore',
							fieldLabel:'Option :',
							displayField:'name',
							emptyText:'- 선택 -',
							valueField:'id',
							typeAhead:true,
							editable:false,
							allowBlank:false,
							margin:'0 5 0 0',
							rootVisible:false,
							useArrows:true,
							selectOnFocus:true,
							queryMode:'local'
						},
						{
							xtype:'textfield',
							itemId:'keywordTextfield',
							width:250
						},
						{
							xtype:'button',
							itemId:'searchButton',
							text:'Search',
							iconCls:'search'
						},
						{
							xtype:'button',
							itemId:'uploadButton',
							text:'Upload',
							iconCls:'upload'
						}
					]
				}
			],
			items:[
				{
					xtype:'resultList'
				}
			]
		});

		me.callParent(arguments);
	}

});