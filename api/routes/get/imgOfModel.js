"use strict";

/**
 * Created by Tomasz Czart on 14.01.2017.
 */

const router = require('express').Router();
const sql = require('../../db/db-connection');

module.exports = (function() {

    router.get('/:modelId', function(req, res) {

        let modelId = req.params.modelId;

        sql.query`select picture FROM Model WHERE model_id=${modelId}`.then(recordset => {
            var originalBase64ImageStr = new Buffer(recordset[0].picture);
            res.writeHead(200, {'Content-Type': 'image/jpeg'});
            res.end(originalBase64ImageStr, 'binary');
        }).catch(err => {
            res.status(404).send();
        });
    });

    return router;
})();
