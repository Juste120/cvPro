db = db.getSiblingDB('cvpro');

db.createUser({
  user: 'cvpro_user',
  pwd: 'cvpro123',
  roles: [{ role: 'readWrite', db: 'cvpro' }]
});

db.users.createIndex({ "email": 1 }, { unique: true });
db.cvs.createIndex({ "userId": 1 });

print('Database cvpro ready!');