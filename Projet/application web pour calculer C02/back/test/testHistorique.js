const chai = require('chai');
const supertest = require('supertest');
const app = require('../app');
const pool = require('../connexion');

const { expect } = chai;
const request = supertest(app);

describe('API Historique - Tests complets', function () {
  let userToken;
  let userToken1;

  const currentDate = new Date();
  const currentMonth = currentDate.getMonth() + 1; // Les mois sont 0-indexed
  const currentYear = currentDate.getFullYear();

  const testUser = {
    nom: 'TestHist',
    prenom: 'User',
    email: 'user.historique@example.com',
    mdp: 'userPass123',
    role: 'user'
  };

  const testUser1 = {
    nom: 'TestHist1',
    prenom: 'User1',
    email: 'user.historique1@example.com',
    mdp: 'userPass123',
    role: 'user'
  };

  const testTransport = {
    nomT: 'TestBusHist',
    constante: 1.23
  };

  const testHistoryEntry = {
    email: "user.historique@example.com",
    duree: '02:30:00',
    co2: 15.5,
    distance: 120,
    villeD: 'Paris',
    villeA: 'Lyon',
    transports: []
  };

  before(async () => {
    await pool.query("DELETE FROM compte WHERE email = ?", [testUser.email]);
    await pool.query("DELETE FROM compte WHERE email = ?", [testUser1.email]);
    await pool.query("DELETE FROM transport WHERE nomT = ?", [testTransport.nomT]);
    await pool.query("DELETE FROM hmt WHERE email = ?", [testUser.email]);
    await pool.query("DELETE FROM historique WHERE email = ?", [testUser.email]);

    const dateCourante = new Date().toISOString().slice(0, 19).replace('T', ' ');
    await pool.query(
      `INSERT INTO compte (nom, prenom, mdp, email, date, role) 
       VALUES (?, ?, ?, ?, ?, ?)`,
      [testUser.nom, testUser.prenom, testUser.mdp, testUser.email, dateCourante, testUser.role]
    );

    await pool.query(
      `INSERT INTO compte (nom, prenom, mdp, email, date, role) 
       VALUES (?, ?, ?, ?, ?, ?)`,
      [testUser1.nom, testUser1.prenom, testUser1.mdp, testUser1.email, dateCourante, testUser1.role]
    );

    await pool.query(
      `INSERT INTO transport (nomT, constante) VALUES (?, ?)`,
      [testTransport.nomT, testTransport.constante]
    );

    const [transport] = await pool.query(
      "SELECT idT FROM transport WHERE nomT = ?",
      [testTransport.nomT]
    );
    testHistoryEntry.transports = [transport[0].idT];

    const res = await request
      .post('/gC/connexion')
      .send({ email: testUser.email, mdp: testUser.mdp });

    userToken = res.body.token;
    expect(userToken).to.be.a('string');

    const res1 = await request
      .post('/gC/connexion')
      .send({ email: testUser1.email, mdp: testUser1.mdp });

    userToken1 = res1.body.token;
    expect(userToken1).to.be.a('string');
  });

  describe('Ajout historique', () => {
    it('devrait ajouter une nouvelle entrée historique', async () => {
      const res = await request
        .post('/gH/ajouterHistorique')
        .set('auth_token', userToken)
        .send(testHistoryEntry)
        .expect(200);

      expect(res.body).to.include({
        email: testHistoryEntry.email,
        duree: testHistoryEntry.duree,
        co2: testHistoryEntry.co2,
        distance: testHistoryEntry.distance,
        villeD: testHistoryEntry.villeD,
        villeA: testHistoryEntry.villeA
      });
    });
  });

  describe('Quantité CO2', () => {
    it('devrait retourner la quantité CO2 pour le mois courant', async () => {
      const res = await request
        .post('/gH/qauntiteCo2Mois')
        .set('auth_token', userToken)
        .send({ 
          email: testUser.email,
          mois: currentMonth,
          annee: currentYear
        })
        .expect(200);

      expect(res.body.qte[0]).to.have.property('sum(co2)').that.equals(testHistoryEntry.co2);
    });

    it('devrait retourner la quantité CO2 pour l\'année courante', async () => {
      const res = await request
        .post('/gH/qauntiteCo2Annee')
        .set('auth_token', userToken)
        .send({ 
          email: testUser.email,
          annee: currentYear
        })
        .expect(200);

      expect(res.body.qte[0]).to.have.property('sum(co2)').that.equals(testHistoryEntry.co2);
    });

    it('devrait retourner 0 ou null pour un mois sans données', async () => {
      const res = await request
        .post('/gH/qauntiteCo2Mois')
        .set('auth_token', userToken)
        .send({ 
          email: testUser.email,
          mois: currentMonth - 1 || 12, // Mois précédent
          annee: currentMonth === 1 ? currentYear - 1 : currentYear
        })
        .expect(200);

      expect(res.body.qte[0]).to.have.property('sum(co2)').that.equals(0 || null);
    });
  });

  describe('Récupération historique', () => {
    it('devrait retourner l\'historique du mois sans transports', async () => {
      const res = await request
        .post('/gH/historiqueUserMois')
        .set('auth_token', userToken)
        .send({ 
          email: testUser.email,
          mois: currentMonth,
          annee: currentYear
        })
        .expect(200);

      expect(res.body.historique).to.be.an('array');
      expect(res.body.historique[0]).to.include({
        duree: testHistoryEntry.duree,
        co2: testHistoryEntry.co2,
        distance: testHistoryEntry.distance,
        villeD: testHistoryEntry.villeD,
        villeA: testHistoryEntry.villeA
      });
    });

    it('devrait retourner l\'historique du mois avec transports', async () => {
      const res = await request
        .post('/gH/historiqueUserMoisT')
        .set('auth_token', userToken)
        .send({ 
          email: testUser.email,
          mois: currentMonth,
          annee: currentYear
        })
        .expect(200);

      expect(res.body.historique).to.be.an('array');
      expect(res.body.historique[0]).to.include({
        duree: testHistoryEntry.duree,
        co2: testHistoryEntry.co2,
        distance: testHistoryEntry.distance,
        villeD: testHistoryEntry.villeD,
        villeA: testHistoryEntry.villeA
      });
      expect(res.body.historique[0]).to.have.property('transports').that.includes(testTransport.nomT);
    });

    it('devrait retourner un tableau vide pour un mois sans historique', async () => {
      const res = await request
        .post('/gH/historiqueUserMois')
        .set('auth_token', userToken)
        .send({ 
          email: testUser.email,
          mois: currentMonth - 1 || 12, // Mois précédent
          annee: currentMonth === 1 ? currentYear - 1 : currentYear
        })
        .expect(200);

      expect(res.body.historique).to.be.an('array').that.is.empty;
    });
  });

  describe('Accès sécurisé', () => {
    it('refuse sans token', async () => {
      await request
        .post('/gH/ajouterHistorique')
        .send(testHistoryEntry)
        .expect(401);
    });

    it('refuse avec token invalide', async () => {
      await request
        .post('/gH/ajouterHistorique')
        .set('auth_token', 'invalidToken')
        .send(testHistoryEntry)
        .expect(401);
    });
  });

  after(async () => {
    // Nettoyage après les tests
    await pool.query("DELETE FROM historique WHERE email = ?", [testUser.email]);
    await pool.query("DELETE FROM hmt WHERE email = ?", [testUser.email]);
    await pool.query("DELETE FROM transport WHERE nomT = ?", [testTransport.nomT]);
    await pool.query("DELETE FROM compte WHERE email = ?", [testUser.email]);
  });
});