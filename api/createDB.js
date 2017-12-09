var mysql = require('mysql');

var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "123456"
});


con.connect(function (err) {
    if (err) throw err;
    console.log("Connected!");
    con.query("CREATE DATABASE IF NOT EXISTS accumulat", function (err, result) {
        if (err) throw err;
        console.log("Database created");

    });

    con.query("use accumulat",function (err,result) {
       if(err) throw err;
       console.log("use database accumulat");
    });

    var sqlcustomer = "create table IF NOT EXISTS customer(" +
        " phone_number varchar(15) not null primary key ," +
        "`name` nvarchar(250) not null," +
        "cmt varchar(20) not null," +
        "address nvarchar(250) not null," +
        "date_create bigint" +
        ")";
    con.query(sqlcustomer, function (err, result) {
        if (err) throw err;
        console.log("Table customer created");
    });

    var sqltransfer = "create table IF NOT EXISTS transfer(" +
        "id int not null auto_increment primary key," +
        "date_transfer bigint ," +
        "item nvarchar(250) ," +
        " money int not null," +
        "customer_phone_number varchar(15)," +
        "foreign key(customer_phone_number) references customer(phone_number)" +
        ")";
    con.query(sqltransfer, function (err, result) {
        if (err) throw err;
        console.log("Table transfer created");
    });
});





