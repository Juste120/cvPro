# 🚀 CVPro Backend - API REST pour Générateur de CV Professionnel

## 📋 Table des Matières
- [Vue d'ensemble](#-vue-densemble)
- [Fonctionnalités](#-fonctionnalités)
- [Architecture](#️-architecture)
- [Stack Technique](#️-stack-technique)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Configuration](#️-configuration)
- [Lancement](#-lancement)
- [API Endpoints](#-api-endpoints)
- [Tests](#-tests)
- [Déploiement](#-déploiement)
- [Contribution](#-contribution)

## 🎯 Vue d'ensemble
CVPro est une API REST backend complète pour la création et la gestion de CV professionnels. Elle offre un système d'authentification sécurisé avec JWT, une gestion complète des CV avec personnalisation visuelle, et un export PDF de haute qualité avec internationalisation.

### Pourquoi CVPro ?
✅ **Sécurité robuste** : Authentification JWT, mots de passe hashés BCrypt
✅ **Personnalisation complète** : Thèmes Light/Dark, couleurs personnalisables
✅ **Export PDF professionnel** : Génération PDF haute qualité avec i18n
✅ **Architecture propre** : Respect des principes SOLID et Clean Architecture
✅ **MongoDB moderne** : NoSQL pour flexibilité et performance
✅ **Docker Ready** : Déploiement simplifié avec Docker Compose

## ✨ Fonctionnalités

### 🔐 Authentification & Sécurité
- Inscription et connexion avec JWT
- Tokens sécurisés avec expiration configurable
- Mots de passe hashés avec BCrypt (salt automatique)
- Gestion des rôles (USER, ADMIN)
- Protection CSRF et CORS configurables

### 👤 Gestion des Utilisateurs
- CRUD utilisateur complet
- Profil utilisateur avec préférences
- Modification des préférences (langue, thème, couleur par défaut)
- Changement de mot de passe sécurisé
- Administration des utilisateurs (rôle ADMIN)

### 📄 Gestion des CV
- Création illimitée de CV par utilisateur
- Sections complètes :
  - Informations personnelles
  - Résumé professionnel
  - Expériences professionnelles (avec réalisations)
  - Formation académique
  - Compétences techniques (par catégories et niveaux)
  - Langues parlées (4 niveaux)
  - Activités bénévoles
  - Centres d'intérêt
- Personnalisation visuelle :
  - Thèmes Light/Dark
  - Couleur primaire personnalisable
  - Couleur d'accent personnalisable
- Opérations :
  - Modification complète ou partielle (styling uniquement)
  - Suppression avec vérification propriétaire
  - Duplication de CV

### 📥 Export PDF
- Génération PDF haute qualité avec iText
- Respect du thème (Light/Dark)
- Application des couleurs personnalisées
- Internationalisation (FR/EN) via Accept-Language
- Nom de fichier automatique : CV_[Date].pdf
- Headers HTTP appropriés pour téléchargement

### 🌍 Internationalisation
- Support français et anglais
- Fichiers de ressources séparés
- Détection automatique via header Accept-Language
- Traductions des labels dans le PDF

## 🏗️ Architecture
Le projet suit une architecture en couches avec séparation claire des responsabilités :

```
┌─────────────────────────────────────────┐
│         Controllers (REST API)           │
│  - Validation des entrées (DTOs)        │
│  - Gestion des réponses HTTP            │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│       Services (Business Logic)         │
│  - Logique métier                       │
│  - Transactions                          │
│  - Vérifications d'autorisation         │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│        Repositories (Data Access)        │
│  - Interactions MongoDB                  │
│  - Requêtes personnalisées              │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│           MongoDB Database               │
│  - Collections: users, cvs               │
│  - Indexes pour performance             │
└─────────────────────────────────────────┘
```

### Composants Transversaux
- **Security** : JWT, filtres d'authentification
- **Mappers** : Conversion Document ↔ DTO
- **Exceptions** : Gestion globale des erreurs
- **Configuration** : MongoDB, Security, I18n

## 🛠️ Stack Technique

### Backend
- **Java 17** - Langage de programmation
- **Spring Boot 3.2.0** - Framework principal
- **Spring Data MongoDB** - Accès aux données
- **Spring Security** - Sécurité et authentification
- **JWT (jjwt 0.12.3)** - Tokens d'authentification
- **MapStruct** - Mapping automatique
- **Lombok** - Réduction du boilerplate
- **iText 5.5.13** - Génération PDF

### Base de Données
- **MongoDB 7.0** - Base NoSQL
- **Mongo Express** - Interface web d'administration

### DevOps & Tools
- **Docker & Docker Compose** - Conteneurisation
- **Maven** - Gestion des dépendances
- **JUnit 5** - Tests unitaires
- **Mockito** - Mocking pour tests
- **Testcontainers** - Tests d'intégration

### Documentation
- **SpringDoc OpenAPI 3** - Documentation API interactive (Swagger)

## 📦 Prérequis
Avant de commencer, assurez-vous d'avoir installé :
- Java 17 ou supérieur
- Maven 3.8+
- Docker et Docker Compose
- Git
- Un éditeur de code (IntelliJ IDEA, VS Code, Eclipse)

### Vérification des versions
```bash
java -version    # Java 17+
mvn -version     # Maven 3.8+
docker --version # Docker 20.10+
docker-compose --version
```

## 🚀 Installation

### 1. Cloner le projet
```bash
git clone https://github.com/votre-username/cvpro-backend.git
cd cvpro-backend
```

### 2. Créer le fichier .env
Copiez le contenu suivant dans un fichier `.env` à la racine du projet :
```dotenv
# MongoDB Root Credentials
MONGO_ROOT_USERNAME=admin
MONGO_ROOT_PASSWORD=SecurePassword123!
MONGO_PORT=27017

# Mongo Express (Interface web)
MONGO_EXPRESS_PORT=8081
MONGO_EXPRESS_USERNAME=admin
MONGO_EXPRESS_PASSWORD=admin123

# Application MongoDB Connection
MONGO_DB_NAME=cvpro
MONGO_APP_USERNAME=cvpro_user
MONGO_APP_PASSWORD=CvPro2025!

# JWT Configuration
JWT_SECRET=VotreSuperSecretJWTKeyQuiDoitEtreTresLongueEtSecurisee2025CvPro!
JWT_EXPIRATION=86400000

# Spring Profile
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8080
```
⚠️ **IMPORTANT** : Ne jamais committer le fichier `.env` ! Il est déjà dans `.gitignore`.

### 3. Créer le dossier d'initialisation MongoDB
```bash
mkdir -p mongo-init
```
Créez le fichier `mongo-init/01-init-db.js` avec le contenu du script d'initialisation fourni.

## ⚙️ Configuration

### Profils Spring
Le projet utilise 3 profils :
- `dev` (par défaut) : Développement local
- `test` : Tests unitaires et intégration
- `prod` : Production

### Variables d'environnement
Toutes les configurations sensibles sont externalisées dans `.env` :

| Variable              | Description          | Valeur par défaut |
|-----------------------|----------------------|-------------------|
| MONGO_ROOT_USERNAME   | Admin MongoDB        | `admin`           |
| MONGO_ROOT_PASSWORD   | Mot de passe admin   | `SecurePassword123!` |
| MONGO_APP_USERNAME    | User applicatif      | `cvpro_user`      |
| MONGO_APP_PASSWORD    | Password applicatif  | `CvPro2025!`      |
| JWT_SECRET            | Secret pour JWT      | (longue clé)      |
| JWT_EXPIRATION        | Expiration token (ms)| `86400000` (24h)  |
| SERVER_PORT           | Port Spring Boot     | `8080`            |

## 🎬 Lancement

### Option 1 : Avec Docker Compose (Recommandé)

#### 1. Démarrer MongoDB
```bash
docker-compose up -d
```
Cela démarre :
- MongoDB sur le port `27017`
- Mongo Express sur le port `8081`

#### 2. Vérifier que MongoDB est prêt
```bash
docker-compose ps
docker-compose logs mongodb
```
Vous devriez voir : ✅ `Database "cvpro" initialized successfully`

#### 3. Accéder à Mongo Express
Ouvrez votre navigateur : http://localhost:8081
- **Username** : `admin`
- **Password** : `admin123`

#### 4. Compiler et lancer l'application
```bash
mvn clean install
mvn spring-boot:run
```

#### 5. Vérifier que l'application fonctionne
```bash
curl http://localhost:8080/actuator/health
```
Réponse attendue : `{"status":"UP"}`

### Option 2 : Sans Docker (MongoDB local)
Si vous avez MongoDB installé localement :
```bash
# 1. Démarrer MongoDB
mongod --dbpath /chemin/vers/data

# 2. Créer la base de données
mongosh
> use cvpro
> db.createUser({user: "cvpro_user", pwd: "CvPro2025!", roles: [{role: "readWrite", db: "cvpro"}]})

# 3. Lancer l'application
mvn spring-boot:run
```
## 📡 API Endpoints

### Swagger UI
Documentation interactive disponible sur :
**URL** : http://localhost:8080/swagger-ui.html

![Swagger UI](./public/cvPro_swagger.png)

### Authentification (Public)

| Méthode | Endpoint             | Description |
|---------|----------------------|-------------|
| POST    | `/api/auth/register` | Inscription |
| POST    | `/api/auth/login`    | Connexion   |

**Exemple - Inscription** :
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "password": "password123"
  }'
```
Réponse :
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "USER"
}
```

### Utilisateurs (Authentifié)

| Méthode | Endpoint                      | Description           | Rôle  |
|---------|-------------------------------|-----------------------|-------|
| GET     | `/api/users/me`               | Profil utilisateur    | USER  |
| PUT     | `/api/users/me`               | Modifier profil       | USER  |
| PATCH   | `/api/users/me/preferences`   | Modifier préférences  | USER  |
| PATCH   | `/api/users/me/password`      | Changer mot de passe  | USER  |
| DELETE  | `/api/users/me`               | Supprimer compte      | USER  |
| GET     | `/api/users`                  | Liste utilisateurs    | ADMIN |
| GET     | `/api/users/{id}`             | Détails utilisateur   | ADMIN |
| DELETE  | `/api/users/{id}`             | Supprimer utilisateur | ADMIN |

**Exemple - Récupérer profil** :
```bash
curl -X GET http://localhost:8080/api/users/me \
  -H "Authorization: Bearer <votre_token>"
```

### CV (Authentifié)

| Méthode | Endpoint                  | Description         |
|---------|---------------------------|---------------------|
| GET     | `/api/cvs`                | Liste mes CV        |
| POST    | `/api/cvs`                | Créer CV            |
| GET     | `/api/cvs/{id}`           | Détails CV          |
| PUT     | `/api/cvs/{id}`           | Modifier CV complet |
| PATCH   | `/api/cvs/{id}/styling`   | Modifier styling    |
| DELETE  | `/api/cvs/{id}`           | Supprimer CV        |
| POST    | `/api/cvs/{id}/duplicate` | Dupliquer CV        |

**Exemple - Créer un CV** :
```bash
curl -X POST http://localhost:8080/api/cvs \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "CV Développeur Full Stack",
    "personalInfo": {
      "fullName": "John Doe",
      "jobTitle": "Développeur Full Stack",
      "email": "john.doe@example.com",
      "phone": "+33 6 12 34 56 78"
    },
    "summary": "Développeur passionné avec 5 ans d'expérience",
    "styling": {
      "theme": "LIGHT",
      "primaryColor": "#3B82F6",
      "accentColor": "#10B981"
    }
  }'
```

### Export (Authentifié)

| Méthode | Endpoint                      | Description     |
|---------|-------------------------------|-----------------|
| GET     | `/api/export/pdf/{cvId}?lang=fr` | Télécharger PDF |

**Exemple - Export PDF** :
```bash
curl -X GET "http://localhost:8080/api/export/pdf/cv123?lang=fr" \
  -H "Authorization: Bearer <token>" \
  -H "Accept-Language: fr" \
  --output CV_John_Doe.pdf
```

## 🧪 Tests

### Exécuter tous les tests
```bash
mvn test
```

### Tests unitaires uniquement
```bash
mvn test -Dtest=*Test
```

### Tests d'intégration
```bash
mvn test -Dtest=*IT
```

### Couverture de code
```bash
mvn test jacoco:report
```
Le rapport est généré dans `target/site/jacoco/index.html`.

## 🚢 Déploiement

### Production avec Docker

**Build de l'application** :
```bash
mvn clean package -DskipTests
```

**Créer l'image Docker** :
```Dockerfile
# Dockerfile
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/cvpro-backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```
```bash
docker build -t cvpro-backend:1.0.0 .
```

**Lancer avec docker-compose** :
Ajoutez le service dans `docker-compose.yml` :
```yaml
  backend:
    build: .
    container_name: cvpro-backend
    ports:
      - "8080:8080"
    environment:
      SPRING_PROFILES_ACTIVE: prod
      MONGODB_URI: mongodb://cvpro_user:CvPro2025!@mongodb:27017/cvpro
    depends_on:
      - mongodb
    networks:
      - cvpro-network
```
```bash
docker-compose up -d
```

### Variables d'environnement Production
Créez un fichier `.env.production` :
```dotenv
MONGO_ROOT_PASSWORD=<strong_password>
MONGO_APP_PASSWORD=<strong_password>
JWT_SECRET=<very_long_random_secret>
SPRING_PROFILES_ACTIVE=prod
```

## 📖 Documentation Supplémentaire

### Structure du Projet
```
cvpro-backend/
├── src/main/java/com/cvpro/
│   ├── config/                 # Configurations
│   ├── controller/             # REST Controllers
│   ├── document/               # MongoDB Documents
│   │   └── embedded/           # Classes embarquées
│   ├── dto/                    # Data Transfer Objects
│   │   ├── request/
│   │   └── response/
│   ├── enums/                  # Énumérations
│   ├── exception/              # Gestion des erreurs
│   ├── mapper/                 # Mappers
│   ├── repository/             # Repositories MongoDB
│   ├── security/               # Configuration sécurité JWT
│   ├── service/                # Services métier
│   │   └── impl/
│   └── CvProApplication.java
├── src/main/resources/
│   ├── application.yml
│   ├── application-dev.yml
│   ├── application-test.yml
│   ├── application-prod.yml
│   └── i18n/
│       ├── messages_fr.properties
│       └── messages_en.properties
├── src/test/java/              # Tests
├── docker-compose.yml
├── .env
├── .gitignore
├── pom.xml
└── README.md
```

### Bonnes Pratiques Utilisées
✅ **Séparation des couches** : Controller → Service → Repository
✅ **DTOs immutables** : Records Java 17
✅ **Validation double** : DTOs + Services
✅ **Gestion d'erreurs centralisée** : GlobalExceptionHandler
✅ **Logging structuré** : SLF4J avec niveaux appropriés
✅ **Transactions** : `@Transactional` sur méthodes de modification
✅ **Sécurité** : JWT, BCrypt, vérification propriétaire
✅ **Tests** : Unitaires + Intégration

## 🤝 Contribution
Les contributions sont les bienvenues ! Voici comment contribuer :
1. Fork le projet
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit vos changements (`git commit -m 'Add AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

### Règles de Contribution
- Code formaté selon les conventions Java
- Tests unitaires pour les nouvelles fonctionnalités
- Documentation mise à jour
- Messages de commit clairs et descriptifs

## 📄 Licence
Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 👨‍💻 Auteur
**CVPro Team**
- **Email**: contact@cvpro.com
- **GitHub**: @cvpro

## 🙏 Remerciements
- Spring Boot Team
- MongoDB Team
- iText Team
- Communauté Open Source

## 📞 Support
Pour toute question ou problème :
- 📧 **Email**: support@cvpro.com
- 💬 **Discord**: CVPro Community
- 🐛 **Issues**: GitHub Issues

Développé avec ❤️ par l'équipe CVPro
