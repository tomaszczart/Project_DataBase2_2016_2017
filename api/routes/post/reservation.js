"use strict";

/**
 * Created by Tomasz Czart on 27.12.2016.
 */

const router = require('express').Router();
const bcrypt = require('bcrypt-nodejs');
const jwt = require('jwt-simple');
const secretKey = require('../../js/secretKey').secretKey;
const sql = require('../../db/db-connection');

module.exports = (function() {

    router.post('/', function(req, res) {
        let token = req.headers['x-auth'];
        let itemId = req.body.item_id;
        let date = req.body.date;
        let auth = jwt.decode(token, secretKey); // TODO username except email !!! (NO USERNAME FIELD IN DATABASE)
        verifyUser(auth.email, user => {//TODO USERNAME
            if(user != null){
                let request = new sql.Request();//end save in database
                request.query(`INSERT INTO Reservation (customer_id, item_id, date) VALUES (${user.customer_id}, ${itemId}, '${date}')`).then(recordset => { //DATE MUST BE FORMATED AS 'YYYY-mm-dd HH:ii:ss'
                    res.status(201).send();//if everything goes right, return 201 status code
                }).catch(err => {
                    console.log(err);
                    res.status(500).send();//else return error code
                });
            }else{
                res.status(401).send();
            }
        });
    });

    return router;
})();


/* Check if given user exist and get  */
function verifyUser(email, callback){
    sql.query`SELECT * FROM Customer WHERE email=${email}`.then(recordset => {//TODO USERNAME
        if(recordset.length == 1){
            callback(recordset[0]);
        }else{
            callback(null);
        }
    }).catch(err => {
        callback(null);
    });
}