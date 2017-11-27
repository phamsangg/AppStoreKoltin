var db = require('../Dbconnection');

var Customer = {
    addCustomer: function (customer, Callback) {
        return db.query('insert into customer(phone_number,name,cmt,address) values(?,?,?,?)', [customer.sdt, customer.name, customer.cmt, customer.address], Callback);
    },

    getCustomer: function (limit,offset, Callback) {

        return db.query('select * from customer limit ? offset ?', [limit, offset], Callback);
    }
};

module.exports = Customer;