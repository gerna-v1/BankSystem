# Stage 1: Build
FROM node:20-alpine AS build

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

# Stage 2: Serve
FROM node:20-alpine

WORKDIR /app

COPY --from=build /app/dist ./dist

RUN npm install -g serve

# Expose port 3000
EXPOSE 3000

CMD ["serve", "-s", "dist", "-l", "3000"]