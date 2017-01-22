"use strict";

/**
 * Created by Tomasz Czart on 26.12.2016.
 */

const router = require('express').Router();
const bcrypt = require('bcrypt-nodejs');
const jwt = require('jwt-simple');
const secretKey = require('../../js/secretKey').secretKey;
const sql = require('../../db/db-connection');

module.exports = (function() {

    router.post('/', function(req, res) {

        let employee = req.body.employee;
        let username = req.body.username;
        let password = req.body.password;

        if(employee == 'true'){
            //LOG IN AS A EMPLOYEE
            findEmployeeByUsername(username, user => {//check if user exists
                if(user != null){//if not create new user
                    bcrypt.compare(password, user.password, function(err, bcryptRes) {
                        if(!err){
                            if(bcryptRes){
                                let token = jwt.encode({username: user.username, employee: true}, secretKey);
                                res.status(200).send(token);
                            }else{
                                res.status(401).send();//wrong password
                            }
                        }else{
                            res.status(500).send();//server error (problem with bcrypt)
                        }
                    });
                }else{
                    res.status(401).send();//no user found == unauthorized
                }
            });
        }else{
            // IF NOT A EMPLOYEE, LOG IN AS A CUSTOMER
            findUserByUsername(username, user => {//check if user exists
                if(user != null){//if not create new user
                    bcrypt.compare(password, user.password, function(err, bcryptRes) {
                        if(!err){
                            if(bcryptRes){
                                let token = jwt.encode({username: user.username, employee: false}, secretKey);
                                res.status(200).send(token);
                            }else{
                                res.status(401).send();//wrong password
                            }
                        }else{
                            res.status(400).send();//bad request
                        }
                    });
                }else{
                    res.status(401).send();//no user found == unauthorized
                }
            });
        }
    });

    return router;
})();

/* Check if given user exists in database */
function findUserByUsername(username, callback){
    sql.query`SELECT username, password FROM Customer WHERE username=${username}`.then(recordset => {
        if(recordset.length == 1){
            callback(recordset[0]);
        }else{
            callback(null);
        }
    }).catch(err => {
        callback(null);
    });
}

/* Check if given employee exists in database */
function findEmployeeByUsername(username, callback){
    sql.query`SELECT username, password FROM Employee WHERE username=${username}`.then(recordset => {
        if(recordset.length == 1){
            callback(recordset[0]);
        }else{
            callback(null);
        }
    }).catch(err => {
        callback(null);
    });
}
