db = db.getSiblingDB('bankdb');

db.createCollection('clients');
db.createCollection('admins');
db.createCollection('transactions');

db.admins.insertOne({
  "_id": "2bd4f3a2c278d1b4e916d31c",
  "accessLevel": 3,
  "name": "Luis",
  "lastName": "Rodriguez",
  "username": "bankadmin",
  "email": "luisro@gmail.com",
  "password": {
    "hash": "$2a$10$A6MeNA2t86YzX7UPWhm48OBZWNWmVCqvRa2viYb0tlJ6pYFostzcu",
    "salt": "$2a$10$A6MeNA2t86YzX7UPWhm48O"
  },
  "uuid": "566998bb-915a-3d43-a7f6-de22d975e00c",
  "_class": "com.gerna_v1.banksystem.models.entities.AdminEntity"
});