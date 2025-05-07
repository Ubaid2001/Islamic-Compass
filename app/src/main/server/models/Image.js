const mongoose = require("mongoose");

const imageSchema = mongoose.Schema(
  {
      image:{
          type: String,
          required: true
      }
  }
);

const Image = mongoose.model("Images",imageSchema);
module.exports = Image; 