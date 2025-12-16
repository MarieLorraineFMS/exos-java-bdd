-- //////////////////////////////////////////////////////
-- Reconstruction de la base de données
-- //////////////////////////////////////////////////////
DROP DATABASE IF EXISTS Shop;

CREATE DATABASE Shop;

USE Shop;

-- //////////////////////////////////////////////////////
-- Construction de la tables des articles en vente
-- //////////////////////////////////////////////////////
CREATE TABLE
	T_Articles (
		IdArticle int (4) PRIMARY KEY AUTO_INCREMENT,
		Description varchar(30) NOT NULL,
		Brand varchar(30) NOT NULL,
		UnitaryPrice float (8) NOT NULL DEFAULT 0
	) ENGINE = InnoDB;

INSERT INTO
	T_Articles (Description, Brand, UnitaryPrice)
VALUES
	('Souris', 'Logitoch', 65);

INSERT INTO
	T_Articles (Description, Brand, UnitaryPrice)
VALUES
	('Clavier', 'Microhard', 49.5);

INSERT INTO
	T_Articles (Description, Brand, UnitaryPrice)
VALUES
	(
		'Systeme d''exploitation',
		'Fenetres Vistouille',
		150
	);

INSERT INTO
	T_Articles (Description, Brand, UnitaryPrice)
VALUES
	('Tapis souris', 'Chapeau Bleu', 5);

INSERT INTO
	T_Articles (Description, Brand, UnitaryPrice)
VALUES
	('Cle USB 8 To', 'Syno', 8);

INSERT INTO
	T_Articles (Description, Brand, UnitaryPrice)
VALUES
	('Laptop', 'PH', 1199);

INSERT INTO
	T_Articles (Description, Brand, UnitaryPrice)
VALUES
	('CD x 500', 'CETME', 250);

INSERT INTO
	T_Articles (Description, Brand, UnitaryPrice)
VALUES
	('DVD-R x 100', 'CETME', 99);

INSERT INTO
	T_Articles (Description, Brand, UnitaryPrice)
VALUES
	('DVD+R x 100', 'CETME', 105);

INSERT INTO
	T_Articles (Description, Brand, UnitaryPrice)
VALUES
	('Batterie Laptop', 'PH', 80);

INSERT INTO
	T_Articles (Description, Brand, UnitaryPrice)
VALUES
	('Casque Audio', 'Syno', 105);

INSERT INTO
	T_Articles (Description, Brand)
VALUES
	('WebCam', 'Logitoch');

USE Shop;

INSERT INTO
	T_Articles (Description, Brand, UnitaryPrice)
VALUES
	('Potion de debug', 'Console.log', 13.37),
	(
		'Clavier qui fait "clic clic"',
		'ASMR Corp',
		42.00
	),
	('Souris Schrödinger', 'QuantumMouse', 0.01),
	(
		'Licence Windows "Fenetres XP++"',
		'Microhard',
		199.99
	),
	('SSD 1To (promis juré)', 'TrustMeBro', 9.99),
	('Tapis de souris +10 aim', 'RPGWare', 5.55),
	('Café du développeur', 'StackOverflow', 3.14);

DESCRIBE T_Articles;

SELECT
	*
FROM
	T_Articles;

CREATE TABLE
	T_Categories (
		IdCategory INT (4) PRIMARY KEY AUTO_INCREMENT,
		CatName VARCHAR(30) NOT NULL,
		Description VARCHAR(100) NOT NULL
	);

-- ALTER TABLE t_articles ADD COLUMN IdCategory INT(4);
-- ALTER TABLE T_Articles ADD FOREIGN KEY(IdCategory) REFERENCES T_Categories(IdCategory);
-- select IdArticle,T_Articles.Description,Brand,UnitaryPrice,T_Articles.IdCategory,CatName,T_Categories.Description
-- from t_articles inner join t_categories where t_articles.IdCategory = t_categories.IdCategory and IdArticle=1;
-- SELECT IdArticle,t_articles.Description,brand,UnitaryPrice,CatName FROM t_articles
-- INNER JOIN t_categories WHERE t_articles.IdCategory=t_categories.IdCategory AND IdArticle>10 ORDER BY UnitaryPrice;
CREATE TABLE
	T_Users (
		IdUser int (4) PRIMARY KEY AUTO_INCREMENT,
		Login varchar(20) NOT NULL UNIQUE,
		Password varchar(20) NOT NULL
	) ENGINE = InnoDB;

CREATE TABLE
	T_Customers (
		IdCustomer int (10) PRIMARY KEY AUTO_INCREMENT,
		name varchar(20) NOT NULL,
		firstName varchar(20) NOT NULL,
		email varchar(45) NOT NULL,
		phone varchar(45) NOT NULL,
		address varchar(90) NOT NULL,
		IdUser INT (4) NOT NULL,
		FOREIGN KEY (IdUser) REFERENCES T_Users (IdUser)
	) ENGINE = InnoDB;

CREATE TABLE
	T_Orders (
		IdOrder int (4) PRIMARY KEY AUTO_INCREMENT,
		Amount float (4) NOT NULL DEFAULT 0,
		DateOrder DATE NOT NULL DEFAULT (CURRENT_DATE),
		IdCustomer INT (4) NOT NULL,
		FOREIGN KEY (IdCustomer) REFERENCES T_Customers (IdCustomer)
	) ENGINE = InnoDB;

CREATE TABLE
	T_Order_Items (
		IdOrderItem int (4) PRIMARY KEY AUTO_INCREMENT,
		IdArticle INT (4) NOT NULL,
		FOREIGN KEY (IdArticle) REFERENCES T_Articles (IdArticle),
		Quantity FLOAT (4) NOT NULL DEFAULT 1,
		UnitaryPrice FLOAT (4) NOT NULL DEFAULT 0,
		IdOrder INT (4) NOT NULL,
		FOREIGN KEY (IdOrder) REFERENCES T_Orders (IdOrder)
	) ENGINE = InnoDB;