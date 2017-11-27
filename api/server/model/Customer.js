var db = require('../Dbconnection');

var Customer = {
    addCustomer: function (phone, address, name, cmt, Callback) {
        return db.query('insert into customer(phone_number,name,cmt,address) values(?,?,?,?)', [phone, name, cmt, address], Callback);
    },

    getCustomer: function (limit, offset, liked, Callback) {
        if (liked == null) {
            return db.query('select * from customer limit ? offset ?', [limit, offset], Callback);
        } else {
            return db.query('select * from customer where name like ? or phone_number like ? limit ? offset ?', [liked, liked, limit, offset], Callback);
        }

    }
};

module.exports = Customer;