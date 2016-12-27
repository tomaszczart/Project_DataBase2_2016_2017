"use strict";

/**
 * Created by Tomasz Czart on 26.12.2016.
 */

const router = require('express').Router();
const sql = require('../../db/db-connection');
const jwt = require('jwt-simple');
const bcrypt = require('bcrypt-nodejs');
const secretKey = require('../../js/secretKey').secretKey;

module.exports = (function() {

    router.post('/', function(req, res) {

        let email = req.body.email;
        let password = req.body.password;

        findUserByEmailAndPhone(email, (isUser, user) => {//check if user exists
            if(isUser){//if not create new user
                console.log(secretKey);
                bcrypt.compare(password, user.password, function(err, bcryptRes) {
                    if(!err){
                        if(bcryptRes){
                            console.log(user);
                            let token = jwt.encode({username: user.email}, secretKey); // TODO username except email !!! (NO USERNAME FIELD IN DATABASE)
                            res.json(token);
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
    });

    return router;
})();

/* Check if given user exists in database */
function findUserByEmailAndPhone(email, callback){
    sql.query`SELECT email, password FROM Customer WHERE email=${email}`.then(recordset => {
        if(recordset.length == 1){
            callback(true, recordset[0]);
        }else{
            callback(false, "");
        }
    }).catch(err => {
        console.log(err);
        callback(false, "");
    });
}