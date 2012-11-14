Ext.define('IsThere.model.Results', {
	extend:'Ext.data.Model',
	alias:'model.results',

	fields:[
		{
			name:'name'
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
