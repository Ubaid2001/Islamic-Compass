const express = require("express");
const bookModel = require("./models/Book");
const path = require('path');
const app = express();

app.get('/books', async (req, res) => {

  const books = await bookModel.find({});
  console.log(books);
  
  try {
    res.send(books); 
  } catch (error) {
    res.status(500).send(error);
  }
  
 })

 app.get('/bookImage/:imagePath', (req, res) => {
  const imagePath = req.params.imagePath; 
  const image = path.join(__dirname, `/Images/${imagePath}.png`);
  res.sendFile(image); 
 });

 app.get('/books/:Book_Name', (req, res) => {
  const bookName = req.params.Book_Name
    const filepath = path.join(__dirname, `/uploads/${bookName}.pdf`)
    res.sendFile(filepath); 

    console.log("filepath: " + filepath);
 })
  
  module.exports = app;