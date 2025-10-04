const chai = require('chai');
const supertest = require('supertest');
const app = require('../app');
const pool = require('../connexion');

const { expect } = chai;
const request = supertest(app);

describe('API Transport - Tests complets', function () {
  let adminToken;
  let testToken;

  const adminUser = {
    nom: 'AdminT',
    prenom: 'User',
    email: 'admin.transport@example.com',
    mdp: 'adminPass123',
    role: 'admin'
  };

  const testUser = {
    nom: 'test',
    prenom: 'User',
    email: 'testpourtransport@example.com',
    mdp: 'testPass123',
    role: 'user'
  };

  const testTransport = {
    nomT: 'TestBus',
    constante: 1.23
  };

  before(async () => {
    await pool.query("DELETE FROM compte WHERE email = ?", [adminUser.email]);
    await pool.query("DELETE FROM compte WHERE email = ?", [testUser.email]);
    await pool.query("DELETE FROM transport WHERE nomT = ?", [testTransport.nomT]);

    const dateCourante = new Date().toISOString().slice(0, 19).replace('T', ' ');
    await pool.query(
      `INSERT INTO compte (nom, prenom, mdp, email, date, role) 
       VALUES (?, ?, ?, ?, ?, ?)`,
      [adminUser.nom, adminUser.prenom, adminUser.mdp, adminUser.email, dateCourante, adminUser.role]
    );

    await pool.query(
      `INSERT INTO compte (nom, prenom, mdp, email, date, role) 
       VALUES (?, ?, ?, ?, ?, ?)`,
      [testUser.nom, testUser.prenom, testUser.mdp, testUser.email, dateCourante, testUser.role]
    );

    const res = await request
      .post('/gC/connexion')
      .send({ email: adminUser.email, mdp: adminUser.mdp });

    adminToken = res.body.token;
    expect(adminToken).to.be.a('string');

    const resTest = await request
      .post('/gC/connexion')
      .send({ email: testUser.email, mdp: testUser.mdp });

    testToken = resTest.body.token;
    expect(testToken).to.be.a('string');
  });

  describe('Ajout de transport', () => {
    it('devrait ajouter un nouveau transport si user connecté est admin', async () => {
      const res = await request
        .post('/gT/ajouterT')
        .set('auth_token', adminToken)
        .send(testTransport)
        .expect(200);

      expect(res.body).to.include(testTransport);
    });
  ;

  it('devrait refuser d\'ajouter un nouveau transport si user connecté n\'est pas admin', async () => {
    const res = await request
      .post('/gT/ajouterT')
      .set('auth_token', testToken)
      .send(testTransport)
      .expect(403);

      expect(res.body).to.have.property('message', "Vous n'êtes pas autorisé à accéder à cette ressource.");
    });
});

  describe('Modification et récupération', () => {
    it('devrait modifier la constante du transport si user connecté admin', async () => {
      const res = await request
        .put('/gT/modifierT')
        .set('auth_token', adminToken)
        .send({ nomT: testTransport.nomT, constante: 4.56 })
        .expect(200);

      expect(res.body).to.include({ nomT: testTransport.nomT, constante: 4.56 });
    });

    it('ne devrait pas modifier la constante du transport si user connecté non admin', async () => {
      const res = await request
        .put('/gT/modifierT')
        .set('auth_token', testToken)
        .send({ nomT: testTransport.nomT, constante: 24.56 })
        .expect(403);

        expect(res.body).to.have.property('message', "Vous n'êtes pas autorisé à accéder à cette ressource.");
      });

    it('devrait retourner la constante mise à jour', async () => {
      const res = await request
        .post('/gT/getConstante')
        .set('auth_token', adminToken)
        .send({ nomT: testTransport.nomT })
        .expect(200);

        expect(res.body.constante[0].constante).to.equal(4.56);
    });
  });

  describe('Liste des transports', () => {
    it('devrait retourner tous les transports', async () => {
      const res = await request
        .get('/gT/getAllTransports')
        .set('auth_token', adminToken)
        .expect(200);

      expect(res.body.transports).to.be.an('array');
      const exists = res.body.transports.some(t => t.nomt === testTransport.nomT);
      expect(exists).to.be.true;
    });
  ;
});

  describe('Suppression', () => {
    it('devrait supprimer un transport si user connecté est admin', async () => {
      const res = await request
        .delete('/gT/supprimerT')
        .set('auth_token', adminToken)
        .send({ nomT: testTransport.nomT })
        .expect(200);

      expect(res.body).to.have.property('nomT', testTransport.nomT);
    });

    it('ne devrait pas supprimer un transport si user connecté non admin', async () => {
      const res = await request
        .delete('/gT/supprimerT')
        .set('auth_token', testToken)
        .send({ nomT: testTransport.nomT })
        .expect(403);

        expect(res.body).to.have.property('message', "Vous n'êtes pas autorisé à accéder à cette ressource.");
    });
  });

  describe('Suppression', () => {
    it('devrait renvoyer 200 si on supprime un transport non existant', async () => {
      const transportInex = {nomT: 'transportinexistant', constante: 0}
      const res = await request
        .delete('/gT/supprimerT')
        .set('auth_token', adminToken)
        .send({ nomT: transportInex.nomT })
        .expect(200);

      expect(res.body).to.have.property('nomT', transportInex.nomT);
    });
  });

  describe('Accès sécurisé', () => {
    it('refuse sans token', async () => {
      await request
        .get('/gT/getAllTransports')
        .expect(401);
    });

    it('refuse avec token invalide', async () => {
      await request
        .get('/gT/getAllTransports')
        .set('auth_token', 'invalidToken')
        .expect(401);
    });
  });
});
