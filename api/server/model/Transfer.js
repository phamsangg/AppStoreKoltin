var db = require('../Dbconnection');
var customer = require('./Customer');

var Transfer = {
    getTransfer: function (sdt,limit,offset, Callback) {
        return db.query('select * from transfer where customer_phone_number = ? order by date_transfer desc limit ? offset ?', [sdt, limit, offset], Callback);
    },

    addTranfer: function (date,money,item,phone, Callback) {
        return db.query('insert into transfer(date_transfer,item,money,customer_phone_number) values(?,?,?,?)', [date, item, money, phone], Callback);
    }
};

module.exports = Transfer;