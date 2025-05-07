const express = require('express');
require('dotenv').config(); 
const mongoose = require("mongoose");
const Router = require("./routes");
const app = express();
const bodyParser = require('body-parser');
const port = process.env.SERVER_PORT;

app.use(express.json());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

const username = process.env.MONGODB_USERNAME;
const password = process.env.MONGODB_PASSWORD;
const cluster = process.env.MONGODB_CLUSTER;

mongoose.connect(
  `mongodb+srv://${username}:${password}@${cluster}.mongodb.net/?retryWrites=true&w=majority`
);

app.use(Router);
app.listen(port, () => {
  console.log(`app listening on port ${port}`)
})