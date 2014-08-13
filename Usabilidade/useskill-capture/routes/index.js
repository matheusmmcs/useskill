var db = require('../models')

exports.index = function(req, res){

	console.log(req.body)
	//Object.keys({a:1})
		//var acoes = JSON.parse(req.body.acoes);

	db.Action.findAll({
		limit: 12,
		order: [['sTime', 'ASC']]
	}).success(function(actions) {
		res.render('index', {
			title: 'Actions',
			actions: actions
		});
	});
}
