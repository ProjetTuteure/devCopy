CREATE TABLE SITE(
	idSite int IDENTITY(1,1) PRIMARY KEY ,
	nomSite varchar(20) NOT NULL,
	cheminImageSite varchar(500))

CREATE TABLE FABRICANT(
	idFabricant int IDENTITY(1,1) PRIMARY KEY ,
	nomFabricant varchar(20) NOT NULL,
	telFabricant varchar(20),
	mobileFabricant  varchar(20),
	faxFabricant varchar(20),
	emailFabricant varchar(50),
	adresseFabricant varchar(100))

CREATE TABLE COMPOSANT(
	idComposant int IDENTITY(1,1) PRIMARY KEY ,
	nomComposant varchar(20) NOT NULL,
	caracteristiqueComposant varchar(200),
	idFabricant int FOREIGN KEY REFERENCES FABRICANT(idFabricant))

CREATE TABLE TYPE(
	idType int IDENTITY(1,1) PRIMARY KEY,
	nomType varchar(20) NOT NULL,
	cheminImageType varchar(500))


CREATE TABLE REVENDEUR(
	idRevendeur int IDENTITY(1,1) PRIMARY KEY ,
	nomRevendeur varchar(20) NOT NULL,
	telRevendeur varchar(20),
	mobileRevendeur  varchar(20),
	faxRevendeur varchar(20),
	emailRevendeur varchar(50),
	adresseRevendeur varchar(100))

CREATE TABLE FACTURE(
	idFacture int IDENTITY(1,1) PRIMARY KEY ,
	numFacture varchar(30) NOT NULL,
	dateFacture DATE NOT NULL,
	montantFacture numeric(10,2),
	idRevendeur int FOREIGN KEY REFERENCES REVENDEUR(idRevendeur))

CREATE TABLE LOGICIEL(
	idLogiciel int IDENTITY(1,1) PRIMARY KEY ,
	nomLogiciel varchar(40) NOT NULL,
	versionLogiciel varchar(20),
	dateExpirationLogiciel DATE,
	idFacture int FOREIGN KEY REFERENCES FACTURE(idFacture))


CREATE TABLE MATERIEL(
	idMateriel int IDENTITY(1,1) PRIMARY KEY ,
	numImmobMateriel varchar(20),
	numeroSerieMateriel varchar(50),
	systemeExploitationMateriel text,
	nomMateriel varchar(20) NOT NULL,
	dateExpirationGarantieMateriel DATE,
	repertoireDrivers varchar(500),
	modeleMateriel varchar(20),
	etat varchar(20),
	dateModifierEtat DATETIME DEFAULT GETDATE(),
	idFacture int FOREIGN KEY REFERENCES FACTURE(idFacture),
	idFabricant int FOREIGN KEY REFERENCES FABRICANT(idFabricant),
	idSite int FOREIGN KEY REFERENCES SITE(idSite),
	idType int FOREIGN KEY REFERENCES TYPE(idType))

CREATE TABLE COMPOSE(
	idComposant int FOREIGN KEY REFERENCES COMPOSANT(idComposant),
	idMateriel int FOREIGN KEY REFERENCES MATERIEL(idMateriel),
	PRIMARY KEY(idComposant,idMateriel))

CREATE TABLE ESTINSTALLE(
	idMateriel int FOREIGN KEY REFERENCES MATERIEL(idMateriel),
	idLogiciel int FOREIGN KEY REFERENCES LOGICIEL(idLogiciel),
	PRIMARY KEY(idMateriel,idLogiciel))

CREATE TABLE PRESTATAIRE(
	idPrestataire int IDENTITY(1,1) PRIMARY KEY,
	nomPrestataire varchar(20) NOT NULL,
	prenomPrestataire varchar(20),
	telPrestataire varchar(20),
	mobilePrestataire varchar(20),
	faxPrestataire varchar(20),
	emailPrestataire varchar(50),
	societePrestataire varchar(50)
	)
	
CREATE TABLE MAINTENANCE(
	idMaintenance int IDENTITY(1,1) PRIMARY KEY ,
	dateMaintenance DATE NOT NULL,
	objetMaintenance varchar(30),
	descriptionMaintenance varchar(400),
	coutMaintenance float)

CREATE TABLE ESTINTERVENU(
	idFacture int FOREIGN KEY REFERENCES FACTURE(idFacture),
	idPrestataire int FOREIGN KEY REFERENCES PRESTATAIRE(idPrestataire),
	idMaintenance int FOREIGN KEY REFERENCES MAINTENANCE(idMaintenance),
	PRIMARY KEY(idFacture,idPrestataire,idMaintenance))

CREATE TABLE ESTMAINTENU(
	idMaintenance int FOREIGN KEY REFERENCES MAINTENANCE(idMaintenance),
	idMateriel int FOREIGN KEY REFERENCES MATERIEL(idMateriel),
	PRIMARY KEY(idMaintenance,idMateriel))

CREATE TABLE UTILISATEUR(
	idUtilisateur int IDENTITY(1,1) PRIMARY KEY ,
	nomUtilisateur varchar(20) NOT NULL,
	prenomUtilisateur varchar(20) NOT NULL,
	telUtilisateur varchar(20))

CREATE TABLE UTILISE(
	dateUtilise date,
	idMateriel int FOREIGN KEY REFERENCES MATERIEL(idMateriel),
	idUtilisateur int FOREIGN KEY REFERENCES UTILISATEUR(idUtilisateur),
	PRIMARY KEY(dateUtilise,idMateriel,idUtilisateur))
	

INSERT INTO FABRICANT VALUES ( 'DELL','05.55.66.77.88' ,'06.37.56.89.63','05.55.66.77.89','dell@dell.com','2 route perdu 87000'),( 'HP','05.55.66.77.88','06.37.63.53.52','05.55.56.56.56','hp@hp.com', '2 route troeuve 87000');

INSERT INTO PRESTATAIRE VALUES ( 'Caillou', 'Pierre', '05.55.69.87.23','06.06.06.06.06','05.05.05.05.05','cailloux@pierre.comme','Caillou et Co.'),( 'Noel', 'Papa', '00.36.65.65.65','06.06.06.06.06','05.05.05.05.05','noel@papa.com','Pole Nord');
INSERT INTO TYPE VALUES ('PC','sources/images/pc.jpg'),('Routeur','sources/images/routeur.png'),('Switch','sources/images/switch.jpg'),('Clef 3G','sources/images/cle3G.PNG');
INSERT INTO SITE VALUES ('Agen', 'sources/images/logo-ville-agen0.png'),('Bordeaux', 'sources/images/bordeaux.jpg'),('Chateauroux', 'sources/images/chateauroux.jpg'),('Gueret', 'sources/images/Gueret.jpg'),('Limoges', 'sources/images/limoges.jpg'),('Montlucon', 'sources/images/montlucon.jpg'),('Saint-Agnant', 'sources/images/saintAgnan.png'),('Saint-Junien', 'sources/images/saintJunien.jpg');
INSERT INTO REVENDEUR VALUES ('Darty','05.55.21.36.54','06.06.06.06.06','05.55.55.55.55','darty@darty.com','4 rue nimporte ou'),('Fnac','05.55.68.57.41','06.06.06.06.06','05.55.55.55.55','fnac@fnac.com','5 rue je sais pas ou ');
INSERT INTO FACTURE VALUES (1,'2011-11-11', 123.5,1),(2,'2012-12-12', 99 ,2);

INSERT INTO MATERIEL (numImmobMateriel,numeroSerieMateriel,systemeExploitationMateriel,nomMateriel,dateExpirationGarantieMateriel,
repertoireDrivers,modeleMateriel,etat,idFacture,idFabricant,idSite,idType)
VALUES ('1IMMO','numeroSerie1','windowsXP','pc-martine','2012-11-11','/driver/pc-martine','XXX1','EN_MARCHE',1,1,1,1),
('2IMMO','numeroSerie2','windowsXP','pc-gertrude','2012-11-11','/driver/pc-gertrude','XXX2','EN_MARCHE',1,2,1,1),
('3IMMO','numeroSerie3','windowsXP','PC1','2014-12-31','/driver/PC1','XXX3','EN_MARCHE',1,1,1,1),
('4IMMO','numeroSerie4','windowsXP','PC2','2015-12-31','/driver/PC2','XXX4','EN_MARCHE',1,1,2,1),
('5IMMO','numeroSerie5','windowsXP','PC3','2014-02-05','/driver/PC3','XXX5','HS',2,1,3,1),
('6IMMO','numeroSerie6','windowsXP','Routeur1','2017-02-14','/driver/Routeur1','XXX6','EN_MARCHE',2,1,4,1),
('7IMMO','numeroSerie7','windowsXP','Routeur2','2012-03-14','/driver/Routeur2','XXX7','EN_PANNE',2,1,4,1)

INSERT INTO LOGICIEL VALUES ('Microsoft Office 2012','1.0','2014-10-01',1)

INSERT INTO MAINTENANCE VALUES
('2014-10-01','erreur','une erreur inconnue est survenue',10),
('2012-02-27','orage','tout a grille',5000),
('2012-02-27','neige','il a neige dans la salle des serveurs',999),
('2012-02-27','utilisateur','j''ai besoin d''une grosse description pour voir ce que ca donne alors je cherche des trucs a ecrire mais comme je trouve pas grand chose je continue a dire n''importe quoi voila la je pense que ca suffira',1)

INSERT INTO UTILISATEUR VALUES
('Bon','Jean','055212354'),
('Leroy','Arthur','055684515'),
('Fury','Johanna','0556519819'),
('Garves','Eddy','058749841')

INSERT INTO UTILISE VALUES
('2012-11-11',1,2),
('2011-11-11',1,2),
('2010-11-25',2,2),
('2007-11-08',3,3),
('2012-11-11',5,4),
('2012-11-11',4,4)

INSERT INTO ESTMAINTENU VALUES
(1,1),
(2,1),
(4,2),
(3,3),
(3,4),
(1,4)