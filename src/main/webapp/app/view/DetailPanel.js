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
                layout:{
                    type:'table',
                    column:2
                },
                items:[
                    {
                        xtype:'largeImgPanel',
                        region:'west',
                        width:256,
                        height:256
                    },
                    {
                        xtype:'detailInfoProperty',
                        region:'center',
                        width:800,
                        padding:'0 0 0 10'
                    }
                ]
            },
            {
                xtype:'moreDetail',
                region:'south',
                flax:1
            }
        ];
        me.callParent(arguments);
    }

});