const path = require('path');
module.exports = {
    entry: {
        app:['./main.jsx']
    },
    output: {
        path: path.resolve(__dirname, './build'),
        filename: 'bundle.js',
        publicPath:'/build/',
    },
    module: {
        rules:[
            {
                test: /\.jsx?$/,
                loader: 'babel-loader',
                exclude: /node_modules/,
                query: {
                    presets: ['@babel/react', '@babel/env'],
                }
            },
        ]
    },
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                pathRewrite: {
                    '^/api' : ''
                }
            }
        },
        port: 7777,
    }
};