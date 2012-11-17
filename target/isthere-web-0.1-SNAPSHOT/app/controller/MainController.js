Ext.define('IsThere.controller.MainController', {
    extend:'Ext.app.Controller',
    alias:'controller.mainController',

    models:[ 'Result', 'DetailInfo' ],

    stores:[ 'ResultStore', 'CartStore', 'DetailInfos' ],

    views:[ 'SearchPanel', 'CartPanel'],

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

        //resultGrid
        {
            ref:'resultGrid',
            selector:'resultList'
        },

        //cartGrid
        {
            ref:'cartList',
            selector:'cartList'
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
            'resultList':{
                itemdblclick:this.onShowDetailInfo
            },
            'cartList':{
                itemdblclick:this.onShowDetailInfo
            },
            'searchPanel #uploadButton':{
                click:this.onUploadClick
            },
            'searchPanel #searchButton':{
                click:this.onSearchClick
            }
        });
    },

    onShowDetailInfo:function (tablepanel, record, item, index, e, options) {
        var detailAppNo = record.get('appNo');

        var detInfoStore = this.getDetailInfosStore();
        //var idx = detInfoStore.find('appNo', detailAppNo);
        //if( -1 != idx )...
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
                    alert('No detail data..');
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
                }
            }
        });
    },

    onSearchClick:function () {
        var keywordText = this.getKeywordTextfield().getValue();
        var condition = this.getConditionCombo().getValue();

        this.getResultGrid().getStore().load({
            params:{
                keyword:keywordText,
                condition:condition
            }
        });
    },

    onUploadClick:function () {
        console.log('onUploadClick');
    }
});