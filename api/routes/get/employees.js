"use strict";

/**
 * Created by Tomasz Czart on 11.11.2016.
 */

const router = require('express').Router();
const sql = require('../../db/db-connection');

module.exports = (function() {
    router.get('/', function(req, res) {
        sql.query`select * from Employee`.then(recordset => {
            res.json(recordset);
        }).catch(err => {
            res.status(404).send();
        });
    });
    return router;
})();