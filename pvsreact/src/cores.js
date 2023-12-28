const express = require('express');
const cors = require('cors');

const app = express();

// Use CORS middleware
app.use(cors());

// ... rest of your Express app setup

// Start the server
app.listen(8080, () => {
  console.log('Server is running on port 8080');
});
