var db = require('../Dbconnection');
var customer = require('./Customer');

var Transfer = {
    getTransfer: function (sdt,limit,offset, Callback) {
        return db.query('select * from transfer where customer_phone_number = ? limit ? offset ?', [sdt, limit, offset], Callback);
    },

    addTranfer: function (transfer, Callback) {
        return db.query('insert into transfer(date_tranfer,item,money,customer_phone_number) values(?,?,?,?)', [transfer.date, transfer.item, transfer.money, transfer.sdt], Callback);
    }
};

module.exports = Transfer;