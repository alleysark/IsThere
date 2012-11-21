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
                layout:'vbox',
                align:'stretchmax',
                pack:'start', //what this property means?

                items:[
                    {
                        xtype:'panel',
                        layout:'hbox',
                        align:'stretch',

                        items:[
                            {
                                xtype:'largeImgPanel',
                                width:266,
                                height:266,
                                padding:'5'
                            },
                            {
                                xtype:'detailInfoProperty',
                                flex:1,
                                margin:'30 0 0 10'
                            }
                        ]
                    },
                    {
                        xtype:'moreDetail',
                        region:'south',
                        flax:1
                    }
                ]
            }
        ];
        me.callParent(arguments);
    }

});