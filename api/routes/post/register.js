"use strict";

/**
 * Created by Tomasz Czart on 26.12.2016.
 */

const router = require('express').Router();
const sql = require('../../db/db-connection');
const bcrypt = require('bcrypt-nodejs');

module.exports = (function() {

    router.post('/', function(req, res) {

        let name = req.body.name;
        let address = req.body.address;
        let email = req.body.email;
        let phone = req.body.phone;
        let password = req.body.password;

        findUserByEmailAndPhone(email, phone, noUser => {//check if user exists
            if(noUser){//if not create new user
                bcrypt.hash(password, null, null, (err, hash) => {//encrypt password
                    let request = new sql.Request();//end save in database
                    request.query(`INSERT INTO Customer (name, address, email, phone, password) VALUES ('${name}', '${address}', '${email}', '${phone}', '${hash}')`).then(recordset => {
                        res.status(201).send();//if everything goes right, return 201 status code
                    }).catch(err => {
                        console.log(err);
                        res.status(500).send();//else return error code
                    });
            });
        }else{
                res.status(409).send();//Conflict code == user exists
            }
        });
    });
    return router;
})();

/* Check if given user exists in database */
function findUserByEmailAndPhone(email, phone, callback){
        sql.query`SELECT * FROM Customer WHERE email=${email} AND phone=${phone}`.then(recordset => {
            if(recordset.length > 0){
                callback(false);
            }else{
                callback(true);
            }
        }).catch(err => {
            callback(false);
        });
    }