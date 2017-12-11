var db = require('../Dbconnection');

var Customer = {
    addCustomer: function (phone, address, name, cmt, date, Callback) {
        if (address == 'undefined' && name == 'undefined' && date == 'undefined' && cmt == 'undefined')
            return db.query('insert into customer(phone_number) values(?)', [phone], Callback);
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
                '(select date_transfer from transfer where customer_phone_number = customer.phone_number order by date_transfer desc limit 1) as lateDateItem' +
                ' from customer where name like ? or phone_number like ? order by date_create desc limit ? offset ?', [liked, liked, limit, offset], Callback);
        }

    },

    getonlyCustomer: function (phone, Callback) {
        return db.query('select customer.phone_number,customer.name,customer.cmt,customer.address,customer.date_create as date,' +
            '(select date_transfer from transfer where customer_phone_number = customer.phone_number order by date_transfer desc limit 1) as lateDateItem' +
            ' from customer ' +
            'where phone_number = ?', [phone], Callback);
    },

    getListName: function (array, Callback) {
        return db.query('select customer.name as name,customer.phone_number as phone from customer where phone_number in ' + array, Callback);
    },

    update: function (phone, address, name, cmt, Callback) {
        if (address == 'undefined')
            return db.query('update customer set name  = ?, cmt = ? where phone_number = ?', [name, cmt, phone], Callback);
        if (address == 'undefined' && name == 'undefined')
            return db.query('update customer set cmt = ? where phone_number = ?', [cmt, phone], Callback);
        if (address == 'undefined' && cmt == 'undefined')
            return db.query('update customer set name  = ? where phone_number = ?', [name, phone], Callback);
        if (name == 'undefined')
            return db.query('update customer set cmt = ? , address = ? where phone_number = ?', [cmt, address, phone], Callback);
        if (name == 'undefined' && cmt == 'undefined')
            return db.query('update customer set address = ? where phone_number = ?', [address, phone], Callback);
        if (cmt == 'undefined')
            return db.query('update customer set name  = ?, address = ? where phone_number = ?', [name, address, phone], Callback);
        return db.query('update customer set name  = ?, cmt = ? , address = ? where phone_number = ?', [name, cmt, address, phone], Callback);
    },

    getlistCustomerAutoComplate: function (limit, liked, Callback) {

        return db.query('select customer.phone_number as phoneNumber,customer.name' +
            ' from customer where name like ? or phone_number like ? order by date_create desc limit ?', [liked, liked, limit], Callback);
    },
};

module.exports = Customer;