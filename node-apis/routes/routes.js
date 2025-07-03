// import other routes
const userRoutes = require('./users');
const productsRoutes = require('./products');
const productsPriceRoutes = require('./productsprice');

const appRouter = (app, fs) => {

    // default route
    app.get('/', (req, res) => {
        res.send('welcome to the development api-server');
    });

    // // other routes
    userRoutes(app, fs);
    productsRoutes(app, fs);
    productsPriceRoutes(app, fs);


};

module.exports = appRouter;