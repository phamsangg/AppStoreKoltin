var express = require('express');
var router = express();
var dateFormat = require('dateformat');
var parseString = require('xml2js').parseString;

var Customer = require('./model/Customer');
var Transfer = require('./model/Transfer');

router.get('/insert/customer', function (req, res) {

    var phone = req.query.phone_number;
    var address = req.query.address;
    var name = req.query.name;
    var cmt = req.query.cmt;
    var date = parseInt(req.query.date);

    Customer.addCustomer(phone, address, name, cmt, date, function (err, count) {
        if (err) {
            res.status(500).json(err);
        } else {
            res.status(200).json(req.body);
        }
    });
});

router.get('/insert/transfer', function (req, res) {

    var date = parseInt(req.query.date_tranfer);
    var money = req.query.money;
    var item = req.query.item;
    var phone = req.query.customer_phone_number

    Transfer.addTranfer(date, money, item, phone, function (err, count) {
        if (err) {
            res.status(500).json(err);
        } else {
            res.status(200).json(req.body);
        }
    });
});

router.get('/get/customer', function (req, res) {
    var limited = parseInt(req.query.limit);
    var offseted = parseInt(req.query.offset);
    var liked = req.query.like;

    Customer.getCustomer(limited, offseted, liked, function (err, row) {
        if (err) {
            res.status(500).send(err);
        } else {
            res.status(200).send(row);
        }
    });
});

router.get('/update/customer', function (req, res) {

    var phone = req.query.phone_number;
    var address = req.query.address;
    var name = req.query.name;
    var cmt = req.query.cmt;
    var date = dateFormat(req.query.date, "yyyy-mm-dd HH:MM:ss");

    Customer.update(phone, address, name, cmt, function (err, count) {
        if (err) {
            res.status(500).json(err);
        } else {
            res.status(200).json(req.body);
        }
    });
});

router.get('/get/customer/phone', function (req, res) {
    var phone = req.query.phone_number;

    Customer.getonlyCustomer(phone, function (err, row) {
        if (err) {
            res.status(500).send(err);
        } else {
            res.status(200).send(row);
        }
    });
});

router.get('/get/customer/listname', function (req, res) {

    var list = req.query.listphone;

    var string = '(';
    var myObject = JSON.parse(list);
    for (var i = 0; i < myObject.contact.length; i++) {
        var counter = myObject.contact[i];
        string += counter.phone;
        if (i != myObject.contact.length - 1)
            string += ',';
    }
    string += ")";
    Customer.getListName(string, function (err, row) {
        if (!err) {
            res.status(200).send(row);
            console.dir(JSON.stringify(row));
        } else {
            res.status(500).send(err);
            console.dir(err);
        }
    });

});

router.get('/get/transfer', function (req, res) {

    var limited = parseInt(req.query.limit);
    var offseted = parseInt(req.query.offset);
    var sdt = req.query.phone_number;

    Transfer.getTransfer(sdt, limited, offseted, function (err, row) {
        if (err) {
            res.status(500).send(err);
        } else {
            res.status(200).send(row);
        }
    });
});

router.get('/get/transfer/sum', function (req, res) {
    var phone = req.query.phone_number;

    Transfer.getSumMoney(phone, function (err, row) {
        if (err) {
            res.status(500).send(err);
        } else {
            res.status(200).send(row);
        }
    });
});

router.get('/get/alltransfer', function (req, res) {

    var limited = parseInt(req.query.limit);
    var offseted = parseInt(req.query.offset);
    var begin = parseInt(req.query.begin);
    var end = parseInt(req.query.end);
    console.log(begin);
    console.log(end);

    Transfer.getListTransfer(begin, end, limited, offseted, function (err, row) {
        if (err) {
            res.status(500).send(err);
        } else {
            res.status(200).send(row);
        }
    });
});

module.exports = router;