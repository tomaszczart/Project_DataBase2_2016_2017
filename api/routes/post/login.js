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

        let email = req.body.email;
        let password = req.body.password;

        findUserByEmailAndPhone(email, user => {//check if user exists
            if(user != null){//if not create new user
                bcrypt.compare(password, user.password, function(err, bcryptRes) {
                    if(!err){
                        if(bcryptRes){
                            let token = jwt.encode({email: user.email}, secretKey); // TODO username except email !!! (NO USERNAME FIELD IN DATABASE)
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
            callback(recordset[0]);
        }else{
            callback(null);
        }
    }).catch(err => {
        callback(null);
    });
}