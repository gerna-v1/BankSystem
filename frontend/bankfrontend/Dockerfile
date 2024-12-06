# Stage 1: Build
FROM node:20-alpine AS build

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npx vite build

# Stage 2: Serve
FROM node:20-alpine

WORKDIR /app

COPY --from=build /app/dist ./dist

RUN ls -la /app/dist

COPY package*.json ./

RUN npm install -g serve

EXPOSE 3000

CMD ["npx", "serve", "-s", "dist", "-l", "3000"]