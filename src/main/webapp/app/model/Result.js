Ext.define('IsThere.model.Result', {
	extend:'Ext.data.Model',
	alias:'model.result',

	fields:[
		{
			name:'name'
		},
		{
			name:'appNo'
		},
		{
			name:'url'
		}
	],

	proxy:{
		type:'ajax',
		url:'/app/data/test.json',
		headers:{
			'Accept':'application/json'
		},
		extraParams:{
			keyword:'',
			condition:''
		},
		reader:{
			type:'json',
			root:'images',
			totalProperty:'total'
		}
	}
});
