"use strict";

/**
 * Created by Tomasz Czart on 11.11.2016.
 */

const express = require('express');
const bodyParser = require('body-parser');

//routes
const getEmployees = require('./routes/get/employees');
const getItems = require('./routes/get/items');
const getModels = require('./routes/get/models');
const getTransaction = require('./routes/get/transactions');
const postRegister = require('./routes/post/register');
const postLogin = require('./routes/post/login');

const PORT = 3000;

const app = express();
app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json());

app.use('/get/employees', getEmployees);
app.use('/get/items', getItems);
app.use('/get/models', getModels);
app.use('/get/transactions', getTransaction);
app.use('/post/register', postRegister);
app.use('/post/login', postLogin);

app.listen(PORT, () => {
    console.log(`Banch of Tools API is now running on port ${PORT}.`);
});