"use strict";

/**
 * Created by Tomasz Czart on 26.12.2016.
 */

const router = require('express').Router();
const sql = require('../../db/db-connection');
const bcrypt = require('bcrypt-nodejs');

module.exports = (function() {

    router.post('/', function(req, res) {

        let employee = req.body.employee;

        let name = req.body.name;
        let address = req.body.address;
        let password = req.body.password;
        let username = req.body.username;

        if(employee == 'true'){
            //REGISTER AS EMPLOYEE
            findEmployeeByUsername(username, noEmployee => {//check if user exists
                if (noEmployee) {//if not create new user
                    bcrypt.hash(password, null, null, (err, hash) => {//encrypt password
                        let request = new sql.Request();//end save in database
                        request.query(`INSERT INTO Employee (name, address, password, username) VALUES ('${name}', '${address}', '${hash}', '${username}')`).then(recordset => {
                            res.status(201).send();//if everything goes right, return 201 status code
                        }).catch(err => {
                            res.status(500).send();//else return error code
                        });
                    });
                } else {
                    res.status(409).send();//Conflict code == user exists
                }
            });
        }else {
            //REGISTER AS CUSTOMER

            let email = req.body.email;
            let phone = req.body.phone;

            findUserByUsername(username, noUser => {//check if user exists
                if (noUser) {//if not create new user
                    bcrypt.hash(password, null, null, (err, hash) => {//encrypt password
                        let request = new sql.Request();//end save in database
                        request.query(`INSERT INTO Customer (name, reservation_counter, address, email, phone, password, username) VALUES ('${name}', 0, '${address}', '${email}', '${phone}', '${hash}', '${username}')`).then(recordset => {
                            res.status(201).send();//if everything goes right, return 201 status code
                        }).catch(err => {
                            console.log(err);
                            res.status(400).send();//else return error code
                        });
                    });
                } else {
                    res.status(409).send();//Conflict code == user exists
                }
            });
        }
    });
    return router;
})();

/* Check if given user exists in database */
function findUserByUsername(username, callback){
        sql.query`SELECT * FROM Customer WHERE username=${username}`.then(recordset => {
            if(recordset.length > 0){
                callback(false);
            }else{
                callback(true);
            }
        }).catch(err => {
            callback(false);
        });
    }

/* Check if given user exists in database */
function findEmployeeByUsername(username, callback){
    sql.query`SELECT * FROM Employee WHERE username=${username}`.then(recordset => {
        if(recordset.length > 0){
            callback(false);
        }else{
            callback(true);
        }
    }).catch(err => {
        callback(false);
    });
}
