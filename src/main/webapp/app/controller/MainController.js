Ext.define('IsThere.controller.MainController', {
    extend:'Ext.app.Controller',
    alias:'controller.mainController',

    models:[ 'Result', 'DetailInfo' ],

    stores:[ 'ResultStore', 'CartStore', 'DetailInfos' ],

    views:[ 'SearchPanel', 'CartPanel', 'DetailInfoProperty', 'LargeImgPanel'],

    refs:[
        //searchPanel Toolbar items
        {
            ref:'conditionCombo',
            selector:'searchPanel #conditionCombo'
        },
        {
            ref:'keywordTextfield',
            selector:'searchPanel #keywordTextfield'
        },

        /*        //resultGrid
         {
         ref:'resultGrid',
         selector:'resultList'
         },*/    //grid to view
        {
            ref:'resultView',
            selector:'resultView'
        },

        //cartGrid
        {
            ref:'cartList',
            selector:'cartList'
        },


        //detail info large image
        {
            ref:'largeImg',
            selector:'largeImgPanel #largeImg'
        },
        //detailInfoProperty
        {
            ref:'nameTextfield',
            selector:'detailInfoProperty #nameTextfield'
        },
        {
            ref:'classificTextfield',
            selector:'detailInfoProperty #classificTextfield'
        },
        {
            ref:'separationTextfield',
            selector:'detailInfoProperty #separationTextfield'
        },
        {
            ref:'appNoTextfield',
            selector:'detailInfoProperty #appNoTextfield'
        },
        {
            ref:'regNoTextfield',
            selector:'detailInfoProperty #regNoTextfield'
        },
        {
            ref:'announceNoTextfield',
            selector:'detailInfoProperty #announceNoTextfield'
        },
        {
            ref:'originAppNoTextfield',
            selector:'detailInfoProperty #originAppNoTextfield'
        },
        {
            ref:'relAppNoTextfield',
            selector:'detailInfoProperty #relAppNoTextfield'
        },
        {
            ref:'priorityAssertionNoTextfield',
            selector:'detailInfoProperty #priorityAssertionNoTextfield'
        },
        {
            ref:'retroactiveTextfield',
            selector:'detailInfoProperty #retroactiveTextfield'
        },
        {
            ref:'disposalTextfield',
            selector:'detailInfoProperty #disposalTextfield'
        },
        {
            ref:'judgeTextfield',
            selector:'detailInfoProperty #judgeTextfield'
        },
        {
            ref:'regStateTextfield',
            selector:'detailInfoProperty #regStateTextfield'
        },

        {
            ref:'largeImgPanel',
            selector:'largeImgPanel'
        }
    ],

    init:function (application) {
        this.control({
            'resultView':{
                itemdblclick:this.onShowDetailInfo
            },
            'resultList':{
                itemdblclick:this.onShowDetailInfo
            },
            'cartList':{
                itemdblclick:this.onShowDetailInfo
            },
            'searchPanel #uploadButton':{
                change:this.onUpload
            },
            'searchPanel #searchButton':{
                click:this.onSearchClick
            }
        });
    },

    onShowDetailInfo:function (tablepanel, record, item, index, e, options) {
        var detailAppNo = record.get('appNo');

        var detInfoStore = this.getDetailInfosStore();
        Ext.Ajax.timeout = 10000;   //10 sec

        detInfoStore.load({
            params:{
                reqAppNo:detailAppNo
            },
            scope:this,
            callback:function (records) {
                if (null == records) {
                    this.getNameTextfield().setValue('');
                    this.getClassificTextfield().setValue('');
                    this.getSeparationTextfield().setValue('');
                    this.getAppNoTextfield().setValue('');
                    this.getAnnounceNoTextfield().setValue('');
                    this.getOriginAppNoTextfield().setValue('');
                    this.getRelAppNoTextfield().setValue('');
                    this.getPriorityAssertionNoTextfield().setValue('');
                    this.getRetroactiveTextfield().setValue('');
                    this.getDisposalTextfield().setValue('');
                    this.getJudgeTextfield().setValue('');
                    this.getRegStateTextfield().setValue('');
                    Ext.MessageBox.alert('실패', '조회에 실패했습니다.');
                } else {
                    var reco = records[0];
                    this.getNameTextfield().setValue(reco.get('titleKr') + ' ' + reco.get('titleEn'));
                    this.getClassificTextfield().setValue(reco.get('classificName'));
                    this.getSeparationTextfield().setValue(reco.get('separation'));
                    this.getAppNoTextfield().setValue(reco.get('appNo'));
                    this.getAnnounceNoTextfield().setValue(reco.get('announceNo'));
                    this.getOriginAppNoTextfield().setValue(reco.get('originAppNo'));
                    this.getRelAppNoTextfield().setValue(reco.get('relAppNo'));
                    this.getPriorityAssertionNoTextfield().setValue(reco.get('priorityAssertionNo'));
                    this.getRetroactiveTextfield().setValue(reco.get('retroactive'));
                    this.getDisposalTextfield().setValue(reco.get('disposal'));
                    this.getJudgeTextfield().setValue(reco.get('judge'));
                    this.getRegStateTextfield().setValue(reco.get('regState'));

                    //set image
                    this.getLargeImg().setSrc(record.get('url'));
                }
            }
        });
    },

    onSearchClick:function (bt, e, eOpts) {
        var keywordText = this.getKeywordTextfield().getValue();
        var condition = this.getConditionCombo().getValue();


        /*this.getResultGrid().getStore().load({*/ //grid to view
        this.getResultView().getStore().load({
            params:{
                keyword:keywordText,
                condition:condition
            }
        });

    },

    onUpload:function (fb, path) {
        path = path.replace("C:\\fakepath\\", "");
        this.getKeywordTextfield().setValue("File:" + path);

        var form = fb.up('form').getForm();

        if (form.isValid()) {
            form.submit({
                url:'/rest/upload.do',
                waitMsg:'검색중입니다...',
                headers:{'Accept':'application/json'},
                isUpload:true,
                params:{
                    file:path
                },
                timeout:999999999999,
                success:function (form, action) {
                    Ext.MessageBox.alert('성공', '업로드를 완료했습니다.');
                    this.getResultStoreStore().loadData(action.response);
                },
                failure:function (form, action) {
                    Ext.MessageBox.alert('실패', '파일을 업로드할 수 없습니다.');
                }
            });
        }
    }
});