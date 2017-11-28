var db = require('../Dbconnection');

var Customer = {
    addCustomer: function (phone, address, name, cmt, date, Callback) {
        return db.query('insert into customer(phone_number,name,cmt,address,date_create) values(?,?,?,?,?)', [phone, name, cmt, address, date], Callback);
    },

    getCustomer: function (limit, offset, liked, Callback) {
        if (liked == null) {
            return db.query('select customer.phone_number,customer.name,customer.cmt,customer.address,customer.date_create as date,' +
                '(select date_transfer from transfer where customer_phone_number = customer.phone_number order by date_transfer desc limit 1) as lateDateItem' +
                ' from customer ' +
                'order by date_create desc limit ? offset ?', [limit, offset], Callback);
        } else {
            return db.query('select customer.phone_number,customer.name,customer.cmt,customer.address,customer.date_create as date,' +
                '(select date_transfer from transfer where customer_phone_number = customer.phone_number order by date_transfer desc limit 1) as lastdate' +
                ' from customer where name like ? or phone_number like ? order by date_create desc limit ? offset ?', [liked, liked, limit, offset], Callback);
        }

    }
};

module.exports = Customer;