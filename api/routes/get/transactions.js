"use strict";

/**
 * Created by VAIO on 27.12.2016.
 */

const router = require('express').Router();
const sql = require('../../db/db-connection');
const jwt = require('jwt-simple');
const secretKey = require('../../js/secretKey').secretKey;

module.exports = (function() {

    router.get('/', function(req, res) {
        let token = req.headers['x-auth'];
        let auth = jwt.decode(token, secretKey); // TODO username except email !!! (NO USERNAME FIELD IN DATABASE)
        console.log(auth);
        verifyUser(auth.email, isEmployee => {//TODO USERNAME
            if(isEmployee){
                sql.query`SELECT * FROM Transactions`.then(recordset => {
                    res.json(recordset);
                }).catch(err => {
                    res.status(404).send();
                });
            }else{
                res.status(401).send();
            }
        });
    });

    return router;
})();

/* Check if given user got permission to access this data */
function verifyUser(email, callback){
    sql.query`SELECT role FROM Customer WHERE email=${email}`.then(recordset => {//TODO USERNAME
        if(recordset.length == 1 && recordset[0].role == 1){
            callback(true);
        }else{
            callback(false);
        }
    }).catch(err => {
        callback(false);
    });
}