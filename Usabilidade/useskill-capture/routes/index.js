var db = require('../models')

function getValueTypeFromView(view) {
	switch(view){
		case 'time':
			return 'sTime';
		case 'username':
			return 'sUsername';
		case 'role':
			return 'sRole';
		case 'version':
			return 'sVersion';
		case 'jhm':
			return 'sJhm';
		case 'tag':
			return 'sTag';
		case 'type':
			return 'sActionType';
		default:
			return null;
	}
	return null;
}

function isNumeric(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}

function isArray(obj) {
	return Object.prototype.toString.call( obj ) === '[object Array]';
}

function isObject (obj) {
	return obj !== null && typeof obj === 'object';
}

function isEmptyObject (obj) {
	if(isObject(obj) && Object.keys(obj).length == 0){
		return true;
	}else{
		return false;
	}
}

exports.actions = function(req, res){
	doSearch(req, res, function(actions) {
		res.json({ actions: actions });
	});
}

exports.index = function(req, res){
	doSearch(req, res, function(actions) {
		res.render('index', {
			title: 'Actions',
			actions: actions
		});
	});
}

function doSearch (req, res, callback) {
	var findData = {
		limit: 50,
		order: [['sTime', 'DESC']]
	};

	if(!isEmptyObject(req.query)){
		var 
		orderParam = getValueTypeFromView(req.query['orderParam']),
		orderValue = req.query['orderValue'],
		limit = req.query['limit'],
		paramSearch = req.query['paramSearch'],
		operationSearch = req.query['operationSearch'],
		valueSearch = req.query['valueSearch']
		;

		if(orderParam != null && orderValue != null){
			findData['order'] = [[orderParam, orderValue]];
		}
		if(isNumeric(limit) && limit > 0){
			findData['limit'] = limit;
		}
		if(isArray(paramSearch) && paramSearch.length > 0){
			var where = {};
			for(var i in paramSearch){
				var param = getValueTypeFromView(paramSearch[i]);
				if(param != null){
					var op = operationSearch[i];
					if(op == 'equal'){
						where[param] = valueSearch[i];
					}else{
						var val = {};
						val[op] = op == 'like' ? '%'+valueSearch[i]+'%' : valueSearch[i];
						where[param] = val;
					}
				}
			}
			findData['where'] = where;
		}
		
	}

	console.log(findData)

	db.Action.findAll(findData).success(function(actions) {
		if (callback && typeof callback === 'function'){
			callback.call(this, actions);
		}
	});
}
