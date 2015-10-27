
var forever = require('forever-monitor');

var _HOME_DIR_ = '/home/useskill/useskill_capture/';

var child = new(forever.Monitor)('app.js', {
    max: 4,
    silent: true,
    uid: 'useskill-capture-nodejs',
    	
    minUptime: 2000,
    spinSleepTime: 1000,
    
    command: 'nodejs',
    args: [],
    sourceDir: _HOME_DIR_,
    
    logFile: _HOME_DIR_+'logs/log',
    outFile: _HOME_DIR_+'logs/stdout',
    errFile: _HOME_DIR_+'logs/stderr'
});

child.on('watch:restart', function(info) {
    console.error('Restaring script because ' + info.file + ' changed');
});

child.on('restart', function() {
    console.error('Forever restarting script for ' + child.times + ' time');
});

child.on('exit:code', function(code) {
    console.error('Forever detected script exited with code: ' + code);
});

child.start();