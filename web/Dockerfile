FROM nginx:1.19.4

ADD ./nginx.conf /etc/nginx/conf.d/

ADD ./index.html /app/

ADD ./favicon.ico /app/

ADD ./build/bundle.js /app/build/

EXPOSE 8888
