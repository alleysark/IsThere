Ext.define('IsThere.model.DetailInfo', {
    extend:'Ext.data.Model',
    alias:'model.detailInfo',

    fields:[
        { name:'titleKr' },
        { name:'titleEn' },
        { name:'classificDet' },
        { name:'classificName' },
        { name:'separation' },
        { name:'appNo' },
        { name:'announceNo' },
        { name:'originAppNo' },
        { name:'relAppNo' },
        { name:'priorityAssertionNo' },
        { name:'retroactive'},
        { name:'disposal' },
        { name:'judge' },
        { name:'regState' }
    ],

    proxy:{
        type:'ajax',
        url:'/rest/detailPatentGetter.do',
        method:'POST',
        waitMsg:'Loading detail information..',
        headers:{
            'Accept':'application/json'
        },
        extraParams:{
            reqAppNo:''
        },
        reader:{
            type:'json',
            root:'detInfo',
            totalProperty:'total',
            successProperty:'success'
        }
    }
});