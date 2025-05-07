const mongoose = require("mongoose");


const bookSchema = mongoose.Schema(
  {
    Book_Name:{
      type: String,
      required: true 
    },
    Book_Desc:{
      type: String,
      required: true 
    },
    Book_Author:{
      type: String,
      required: true 
    },
    imageId:{
      type: Number, 
      required: true
    },
    imagePath:{
      type: String,
      required: true
    }
  }
)


const Book = mongoose.model("Book", bookSchema); 
module.exports = Book; 
