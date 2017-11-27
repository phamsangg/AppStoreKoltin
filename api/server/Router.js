var express = require('express');
var router = express();

var Customer = require('./model/Customer');
var Transfer = require('./model/Transfer');

router.get('/insert/customer', function (req, res) {

    Customer.addCustomer(req.body, function (err, count) {
        if (err) {
            res.json(err);
        } else {
            res.json(req.body);
        }
    });
});

router.get('/insert/transfer', function (req, res) {

    Transfer.addTranfer(req.boby, function (err, count) {
        if (err) {
            res.json(err);
        } else {
            res.json(req.body);
        }
    });
});

router.get('/get/customer', function (req, res) {
    var limited = parseInt(req.query.limit);
    var offseted = parseInt(req.query.offset);

    Customer.getCustomer(limited, offseted, function (err,row) {
        if (err) {
            res.status(404).send(err);
        } else {
            res.status(200).send(row);
        }
    });
});

router.get('/get/transfer', function (req, res) {

    var limited = parseInt(req.query.limit);
    var offseted = parseInt(req.query.offset);
    var sdt = parseInt(req.query.phone_number);

    Transfer.getTransfer(sdt, limited, offseted, function (err, row) {
        if (err) {
            res.json(err);
        } else {
            res.json(row);
        }
    });
});

module.exports = router;