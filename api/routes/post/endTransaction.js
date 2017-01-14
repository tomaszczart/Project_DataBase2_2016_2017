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
        let auth;

        try{
            auth = jwt.decode(token, secretKey);
            if(auth.employee){
                let transactionId = req.body.transaction_id;
                let dataEnd = req.body.data_end ;
                let price = req.body.price;

                let request = new sql.Request();//end save in database
                request.query(`UPDATE Transactions SET data_end='${dataEnd}', price=${price} WHERE transaction_id = ${transactionId}`).then(recordset => {
                    res.status(201).send();//if everything goes right, return 201 status code
                }).catch(err => {
                    console.log(err)
                    res.status(400).send();//else bad request code
                });
            }else{
                res.status(401).send();
            }
        }catch(err){
            res.status(401).send();
        }
    });
    return router;
})();