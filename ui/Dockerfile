FROM node:lts as builder

WORKDIR /home/node/frontend

COPY . .

RUN npm ci && npm run build

# ------
FROM nginx
WORKDIR /usr/share/nginx/html
COPY --from=builder /home/node/frontend/dist .
EXPOSE 80
EXPOSE 3000