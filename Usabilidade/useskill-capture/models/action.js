module.exports = function(sequelize, DataTypes) {
	var Action = sequelize.define('Action', {
			sActionType: DataTypes.STRING,
			sContent: DataTypes.TEXT,
			sPosX: DataTypes.INTEGER,
			sPosY: DataTypes.INTEGER,
			sTag: DataTypes.STRING,
			sTagIndex: DataTypes.STRING,
			sTime: DataTypes.BIGINT(20),
			sUrl: DataTypes.STRING,
			sContentText: DataTypes.TEXT,
			sClass: DataTypes.STRING,
			sId: DataTypes.STRING,
			sName: DataTypes.STRING,
			sXPath: DataTypes.TEXT,
			sUserAgent: DataTypes.TEXT,

			sClient: DataTypes.STRING,
			sVersion: DataTypes.INTEGER,

			sUsername: DataTypes.STRING,
			sRole: DataTypes.STRING,

			sJhm: DataTypes.STRING,
			sActionJhm: DataTypes.STRING,
			sSectionJhm: DataTypes.STRING,
			sStepJhm: DataTypes.STRING,

			sDeleted: {
				type: DataTypes.BOOLEAN,
				defaultValue: false
			}
	  	}, {
	    associate: function(models) {
	   		//Action.hasMany(models.Task)
	    }
	});

  	return Action;
}