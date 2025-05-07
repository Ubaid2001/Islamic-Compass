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
// const Grid = require('gridfs-stream');
// const methodOverride = require('method-override');
// const crypto = require('crypto'); 

// // Middleware
// app.use(bodyParser.json());
// app.use(methodOverride('_method'));
// app.set('view engine', 'ejs');

// // Init gfs
// let gfs;

// app.once('open', () => {
//   gfs = Grid(app.db, mongoose.mongo);
//   gfs.collection('PDFs');
// });

// const mongoURI = `mongodb+srv://${username}:${password}@${cluster}.mongodb.net/?retryWrites=true&w=majority`

// // Create storage engine
// const storage = new GridFsStorage({
//   url: mongoURI,
//   file: (req, file) => {
//     return new Promise((resolve, reject) => { 
//       crypto.randomByetes(16, (err, buf) => {
//         if(err){
//           return reject(err);
//         }
//         const filename = buf.toString('hex') + path.extname(file.originalname); 
//         const fileInfo = {
//           filename: filename,
//           bucketName: 'PDFs'
//         };
//         resolve(fileInfo);
//       });
//     });
//   }
// });

// const upload = multer({ storage });

// app.post('/uploads', upload.single('file'), (req, res) => {
//   res.json({file: req.file}); 
// });

// app.use('/uploads', express.static(__dirname + '/uploads'));

app.use(Router);


app.listen(port, () => {
  console.log(`app listening on port ${port}`)
})