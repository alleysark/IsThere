Ext.define('IsThere.view.DetailInfoProperty', {
    extend:'Ext.form.FieldContainer',
    alias:'widget.detailInfoProperty',

    layout:'anchor',
    border:false,

    fieldDefaults:{
        labelAlign:'left'
    },
    width:900,
    items:[
        {
            xtype:'textfield',
            id:'nameTextfield',
            fieldLabel:'명칭',
            labelWidth:100,
            width:610,
            readOnly:true
        },

        {
            xtype:'fieldcontainer',
            combineErrors:true,
            layout:'hbox',
            defaults:{
                readOnly:true
            },
            items:[
                {
                    xtype:'textfield',
                    id:'classificTextfield',
                    fieldLabel:'상품분류',
                    width:300
                },
                {
                    xtype:'textfield',
                    id:'separationTextfield',
                    fieldLabel:'구분',
                    labelWidth:130,
                    width:300,
                    padding:'0 0 0 10'
                }
            ]
        },
        {
            xtype:'fieldcontainer',
            combineErrors:true,
            layout:'hbox',
            defaults:{
                readOnly:true
            },
            items:[
                {
                    xtype:'textfield',
                    id:'appNoTextfield',
                    fieldLabel:'출원번호(일자)',
                    width:300
                },
                {
                    xtype:'textfield',
                    id:'regNoTextfield',
                    fieldLabel:'등록번호(일자)',
                    labelWidth:130,
                    width:300,
                    padding:'0 0 0 10'
                }
            ]
        },
        {
            xtype:'fieldcontainer',
            combineErrors:true,
            layout:'hbox',
            defaults:{
                readOnly:true
            },
            items:[
                {
                    xtype:'textfield',
                    id:'announceNoTextfield',
                    fieldLabel:'공고번호(일자)',
                    width:300
                },
                {
                    xtype:'textfield',
                    id:'originAppNoTextfield', //origin이 맞는지는 모름
                    fieldLabel:'원출원번호(일자)',
                    labelWidth:130,
                    width:300,
                    padding:'0 0 0 10'
                }
            ]
        },
        {
            xtype:'fieldcontainer',
            combineErrors:true,
            layout:'hbox',
            defaults:{
                readOnly:true
            },
            items:[
                {
                    xtype:'textfield',
                    id:'relAppNoTextfield',
                    fieldLabel:'관련출원번호',
                    width:300
                },
                {
                    xtype:'textfield',
                    id:'priorityAssertionNoTextfield',
                    fieldLabel:'우선권주장번호(일자)',
                    labelWidth:130,
                    width:300,
                    padding:'0 0 0 10'
                }
            ]
        },
        {
            xtype:'fieldcontainer',
            combineErrors:true,
            layout:'hbox',
            defaults:{
                readOnly:true
            },
            items:[
                {
                    xtype:'textfield',
                    id:'retroactiveTextfield',
                    fieldLabel:'소급구분(일자)',
                    width:300
                },
                {
                    xtype:'textfield',
                    id:'disposalTextfield',
                    fieldLabel:'최종처분(일자)',
                    labelWidth:130,
                    width:300,
                    padding:'0 0 0 10'
                }
            ]
        },
        {
            xtype:'fieldcontainer',
            combineErrors:true,
            layout:'hbox',
            defaults:{
                readOnly:true

            },
            items:[
                {
                    xtype:'textfield',
                    id:'judgeTextfield',
                    fieldLabel:'심판사항',
                    width:300
                },
                {
                    xtype:'textfield',
                    id:'regStateTextfield',
                    fieldLabel:'등록상태',
                    labelWidth:130,
                    width:300,
                    padding:'0 0 0 10'
                }
            ]
        }
    ]
});