"use strict";

/**
 * Created by Tomasz Czart on 14.01.2017.
 */

const router = require('express').Router();
const sql = require('../../db/db-connection');

module.exports = (function() {

    router.get('/:modelId', function(req, res) {

        let modelId = req.params.modelId;

        sql.query`select * FROM Item WHERE model_id=${modelId}`.then(recordset => {
            res.json(recordset);
        }).catch(err => {
            res.status(404).send();
        });
    });

    return router;
})();