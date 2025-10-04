
const chai = require('chai');
const supertest = require('supertest');
const jwt = require('jsonwebtoken');
const app = require('../app');
const pool = require('../connexion');

const { expect } = chai;
const request = supertest(app);

describe('API Compte - Tests complets', () => {
  const testUser = {
    nom: 'Test',
    prenom: 'User',
    email: 'test.user@example.com',
    mdp: 'securePassword123'
  };

  const adminUser = {
    nom: 'Admin',
    prenom: 'User',
    email: 'admin.user@example.com',
    mdp: 'adminPassword123',
    role: 'admin'
  };

  let adminAuthToken;

  before(async () => {
    await pool.query('DELETE FROM compte WHERE email LIKE \'test.%\' OR email LIKE \'admin.%\'');

    const dateCourante = new Date().toISOString().slice(0, 19).replace('T', ' ');
    await pool.query(
      `INSERT INTO compte (nom, prenom, mdp, email, date, role) 
       VALUES (?, ?, ?, ?, ?, ?)`,
      [adminUser.nom, adminUser.prenom, adminUser.mdp, adminUser.email, dateCourante, adminUser.role]
    );

    const res = await request
      .post('/gC/connexion')
      .send({ email: adminUser.email, mdp: adminUser.mdp });

    adminAuthToken = res.body.token;
    expect(adminAuthToken, 'Token admin est invalide ou non reçu').to.be.a('string').and.to.have.length.above(10);
  });

  describe('Création de compte', () => {
    it('devrait créer un nouveau compte', async () => {
      const res = await request
        .post('/gC/creation')
        .send(testUser)
        .expect(200);

      expect(res.body).to.include({
        nom: testUser.nom,
        prenom: testUser.prenom,
        email: testUser.email
      });
    });

    it('devrait refuser un email déjà utilisé', async () => {
      const res = await request
        .post('/gC/creation')
        .send(testUser)
        .expect(200);

      expect(res.text).to.equal('user deja existant');
    });
  });

  describe('Connexion', () => {
    it('devrait permettre la connexion avec un utilisateur valide', async () => {
      const res = await request
        .post('/gC/connexion')
        .send({ email: testUser.email, mdp: testUser.mdp })
        .expect(200);

      expect(res.body).to.have.property('token');
    });

    it('devrait refuser la connexion avec un mauvais mot de passe', async () => {
      const res = await request
        .post('/gC/connexion')
        .send({ email: testUser.email, mdp: 'wrongPassword' })
        .expect(401);

      expect(res.body).to.have.property('message', 'Nom d\'utilisateur ou mot de passe invalide.');
    });

    it('devrait refuser la connexion avec un email inexistant', async () => {
      const res = await request
        .post('/gC/connexion')
        .send({ email: 'nonexistent.user@example.com', mdp: testUser.mdp })
        .expect(401);

      expect(res.body).to.have.property('message', 'Nom d\'utilisateur ou mot de passe invalide.');
    });
  });

  describe('Récupération du nombre d\'utilisateurs', () => {
    it('devrait retourner le nombre d\'utilisateurs si user connecté admin', async () => {
      const res = await request
        .get('/gC/nbrUser')
        .set('auth_token', adminAuthToken)
        .expect(200);

      expect(res.body).to.have.property('users').that.is.an('array');
      expect(res.body.users[0]).to.have.property('count(*)').that.is.a('number');
      expect(res.body.users[0]['count(*)']).to.be.at.least(1);
    });

    it('devrait retourner non authorized pour un user non admin qui veut recuperer le nombre d\'utilisateurs', async () => {
      const testUser = {
        nom: 'Test',
        prenom: 'User',
        email: 'test.user@example.com',
        mdp: 'securePassword123'
      };
      const userToken = (await (request
        .post('/gC/connexion')
        .send({ email: testUser.email, mdp: testUser.mdp }))).body.token;

      const res = await request
        .get('/gC/nbrUser')
        .set('auth_token', userToken)
        .expect(403);

      expect(res.body).to.have.property('message', 'Vous n\'êtes pas autorisé à accéder à cette ressource.');
    });
  });

  describe('Suppression de compte', () => {
    it('devrait supprimer le compte du user connecté', async () => {
      const userToDelete = {
        nom: 'Delete',
        prenom: 'User',
        email: 'delete.user@example.com',
        mdp: 'securePassword123'
      };

      await request
        .post('/gC/creation')
        .send(userToDelete)
        .expect(200);

      const resConnexion = await request
        .post('/gC/connexion')
        .send({ email: userToDelete.email, mdp: userToDelete.mdp });

      const userToDeleteToken = resConnexion.body.token;

      const res = await request
        .delete('/gC/supprimerU')
        .send({ nom: userToDelete.nom, prenom: userToDelete.prenom })
        .set('auth_token', userToDeleteToken)
        .expect(200);

      expect(res.body).to.include({
        nom: userToDelete.nom,
        prenom: userToDelete.prenom
      });
    });

    it('devrait supprimer un utilisateur si l utilisateur connecté est admin', async () => {
      const user = {
        nom: 'User',
        prenom: 'AdminDeletable',
        email: 'user.admin@example.com',
        mdp: 'securePassword123'
      };

      await request
        .post('/gC/creation')
        .send(user)
        .expect(200);

      const res = await request
        .delete('/gC/supprimerU')
        .send({ nom: user.nom, prenom: user.prenom })
        .set('auth_token', adminAuthToken)
        .expect(200);

      expect(res.body).to.include({
        nom: user.nom,
        prenom: user.prenom
      });
    });

    it('devrait renvoyer une erreur 404 si l’utilisateur à supprimer n’existe pas', async () => {
      const res = await request
        .post('/gC/supprimerU')
        .send({ nom: 'NonExistent', prenom: 'User' })
        .set('auth_token', adminAuthToken)
        .expect(404);
    });
  });

  describe('Routes protégées par token', () => {
    it('devrait permettre l\'accès aux routes protégées avec un token valide', async () => {
      const res = await request
        .get('/gC/nbrUser')
        .set('auth_token', adminAuthToken)
        .expect(200);

      expect(res.body).to.have.property('users').that.is.an('array');
      expect(res.body.users[0]).to.have.property('count(*)').that.is.a('number');
      expect(res.body.users[0]['count(*)']).to.be.at.least(1);
    });

    it('devrait refuser l\'accès aux routes protégées sans token', async () => {
      const res = await request
        .get('/gC/nbrUser')
        .expect(401);

      expect(res.body).to.have.property('message');
    });

    it('devrait refuser l\'accès aux routes protégées avec un token invalide', async () => {
      const res = await request
        .get('/gC/nbrUser')
        .set('auth_token', 'invalidToken')
        .expect(401);

      expect(res.body).to.have.property('message');
    });
  });

  describe('Récupération de la liste de tous les utilisateurs', () => {
    it('devrait retourner tous les utilisateurs inscrits si user connecté est admin', async () => {
      const res = await request
        .get('/gC/Users')
        .set('auth_token', adminAuthToken)
        .expect(200);

      expect(res.body).to.have.property('users').that.is.an('array');
      if (res.body.users.length > 0) {
        const user = res.body.users[0];
        expect(user).to.have.all.keys('nom', 'prenom', 'email', 'date', 'role');
        expect(user.nom).to.be.a('string');
        expect(user.prenom).to.be.a('string');
        expect(user.email).to.be.a('string');
        expect(user.date).to.be.a('string');
        expect(user.role).to.be.oneOf(['user', 'admin']);
  }
    });

    it('ne devrait pas retourner tous les utilisateurs inscrits si user connecté non admin', async () => {
       const userToken = (await (request
        .post('/gC/connexion')
        .send({ email: testUser.email, mdp: testUser.mdp }))).body.token;
      const res = await request
        .get('/gC/Users')
        .set('auth_token', userToken)
        .expect(403);
    });

    it('devrait retourner non authorized pour un user non admin qui veut recuperer le nombre d\'utilisateurs', async () => {
      const testUser = {
        nom: 'Test',
        prenom: 'User',
        email: 'test.user@example.com',
        mdp: 'securePassword123'
      };
      const userToken = (await (request
        .post('/gC/connexion')
        .send({ email: testUser.email, mdp: testUser.mdp }))).body.token;

      const res = await request
        .get('/gC/nbrUser')
        .set('auth_token', userToken)
        .expect(403);

      expect(res.body).to.have.property('message', 'Vous n\'êtes pas autorisé à accéder à cette ressource.');
    });
  });
});


