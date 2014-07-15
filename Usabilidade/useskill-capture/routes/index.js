var db = require('../models')

exports.index = function(req, res){
  db.Action.findAll().success(function(actions) {
  	console.log(actions)
    res.render('index', {
      	title: 'Actions',
      	actions: actions
    })
  })
}
