"use strict";

/**
 * Created by Tomasz Czart on 14.01.2017.
 */

const router = require('express').Router();
const sql = require('../../db/db-connection');

module.exports = (function() {

    router.get('/:categoryId', function(req, res) {

        let categoryId = req.params.categoryId;

        sql.query`select model_id, name, description, price_per_hour, category_id FROM Model WHERE category_id=${categoryId}`.then(recordset => {
            res.json(recordset);
        }).catch(err => {
            res.status(404).send();
        });
    });

    return router;
})();