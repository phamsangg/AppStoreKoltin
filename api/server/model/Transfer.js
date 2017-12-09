var db = require('../Dbconnection');
var customer = require('./Customer');

var Transfer = {
    getTransfer: function (sdt, limit, offset, Callback) {
        return db.query('select * from transfer where customer_phone_number = ? order by date_transfer desc limit ? offset ?', [sdt, limit, offset], Callback);
    },

    addTranfer: function (date, money, item, phone, Callback) {
        return db.query('insert into transfer(date_transfer,item,money,customer_phone_number) values(?,?,?,?)', [date, item, money, phone], Callback);
    },

    getSumMoney: function (phone, Callback) {
        return db.query('select sum(money) as sum from transfer where customer_phone_number = ?', [phone], Callback);
    },

    getListTransfer: function (begin, end, limit, offset, Callback) {
        return db.query('select * from transfer where date_transfer < ? and date_transfer > ? order by date_transfer desc limit ? offset ?', [end, begin, limit, offset], Callback);
    }

};

module.exports = Transfer;