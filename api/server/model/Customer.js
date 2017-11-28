var db = require('../Dbconnection');

var Customer = {
    addCustomer: function (phone, address, name, cmt, date, Callback) {
        return db.query('insert into customer(phone_number,name,cmt,address,date_create) values(?,?,?,?,?)', [phone, name, cmt, address,date], Callback);
    },

    getCustomer: function (limit, offset, liked, Callback) {
        if (liked == null) {
            return db.query('select * from customer order by date_create desc limit ? offset ?', [limit, offset], Callback);
        } else {
            return db.query('select phone_number,name,cmt,address,date_create from customer where name like ? or phone_number like ? order by date_create desc limit ? offset ?', [liked, liked, limit, offset], Callback);
        }

    }
};

module.exports = Customer;