var _ = require('underscore');

var arr = [1,10,50,200,900,90,40];

var results = _.filter(arr, function(item) {
    return item > 100
});

console.log(results);