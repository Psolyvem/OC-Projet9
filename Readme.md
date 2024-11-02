
# Medilabo Solutions

<p align="center">
<img src="https://img.shields.io/badge/Language-Java-green">
<img src="https://img.shields.io/badge/Framework-Spring-green">
<img src="https://img.shields.io/badge/PM-Maven-green">
<img src="https://img.shields.io/badge/Docker-blue">
</p>

Application de de gestion de dossiers médicaux créée dans le cadre d'un projet de formation OpenClassrooms.


## Usage
Cette application en microservices est constituée de 6 modules : 
- `discovery-service` : Serveur Eureka destiné a détécter les autres microservices 
- `gateway` : Service de routage des requêtes qui gère également l'authentification avec Spring Security
- `patient-manager` : API REST gérant les données des dossiers des patients stockés dans une BDD MySQL
- `note-manager` : API REST gérant les notes des médecins liées au dossier d'un patient via une BDD NoSQL MongoDB
- `risk-analyzer` : API REST calculant la catégorie de risque diabétique d'un patient en fonction de son dossier et des notes 
- `web-client` : Client web offrant une interface permettant de consulter les données des autres services

Le module `web-client` est accessible a l'adresse `localhost:9100/web-client/`. Une authentification sera demandée, les identifiants sont :
- Username : `user`
- Password : `password`

#### Architecture
Il s'agit d'une application en microservice qui communique via des requêtes HTTP. Chacun des microservice suit une architecture MVC (Modèle-Vue-Contrôleur). L'application est dockerisée, chaque module possède un dockerfile permettant de construire son image indépendamment, et le projet comporte également un fichier `docker-compose.yml` permettant de construire et configurer tout les modules ensemble dans une version fonctionnelle de l'application.\
En plus des modules du projet le fichier docker-compose instancie deux containers de base de données. Un container MySQL pour le service patient-manager et un container MongoDB pour le service note-manager.\
Chaque microservice est mappé sur un port différent :

- `gateway` : `9100`
- `patient-manager` : `9101`
- `note-manager` : `9102`
- `risk-analyzer` : `9103`
- `web-client` : `9200`
- `discovery-service` : `9500`
- `mysql` : `3306`
- `mongodb` : `27017`

## Installation

#### Avec Docker

Si vous souhaitez installer ce projet il vous faudra au préalable installer Docker :
- Docker : https://www.docker.com/

Une fois Docker téléchargé et installé, téléchargez le projet.\
Ouvrez un terminal et placez vous dans le dossier du projet et lancez le avec docker :
```bash
docker-compose up -d
```
Pour arrêter les conteneurs et stopper le projet :
```bash
docker-compose down
```
>[!IMPORTANT]
>Si vous détruisez les conteneurs avec `docker-compose down` les données des bases de données seront également supprimées.

Une fois le projet lancé il est prêt a l'emploi, mais si vous souhaitez pouvoir consulter les données de test il faut lancer la configuration de la base de données NoSQL. Pour cela rendez-vous seulement sur l'endpoint http://localhost:9100/note-manager/setup . Si vous n'obtenez pas d'erreur, la base de donnée à été correctement configurée.

> [!NOTE]  
> Il faudra parfois attendre un peu que le serveur discovery et la gateway découvrent et mappent tout les services. Si vous obtenez une `Erreur 503 : Service Unavailable`, cela signifie que les services n'ont pas encore démarré, attendez une minute et réessayez.

Pour accéder aux services vous pouvez passer par la gateway dont l'addresse est `localhost:9100` suivie du nom du microservice, exemple : `localhost:9100/web-client/`.\
Si les autres microservices sont accessibles il n'est néanmoins pas nécessaire de les consulter directement, toutes les informations ainsi que la création et la gestion des patients, de leurs dossiers, des notes et des estimations de risque les concernant sont affichées dans le client web.

#### Sans Docker

Si vous souhaitez tester ce projet en dehors des conteneurs docker, il vous faudra installer :
- MySQL : https://www.mysql.com/downloads/
- MongoDB : https://www.mongodb.com/try/download/community

Une fois ces prérequis installés vous pouvez télécharger le projet. Il faudra en premier lieu configurer la base de données MySQL. Pour cela, ouvrez un terminal, rendez vous dans le dossier du projet sous `patient-manager/db` et executez le fichier `database.sql` à l'aide de la commande :
```bash
mysql -u root -p
...
[votremotdepasseroot]
...
source database.sql
```

Vous allez maintenant pouvoir exécuter les modules un par un à l'aide de la commande :

```bash
java -jar ./[nom-du-module]/target/[nom-du-module]-0.0.1-SNAPSHOT.jar
```
Vous pouvez également recompiler les modules séparément. Pour cela vous devez avoir installé Maven (https://maven.apache.org/) \
Placez vous dans le dossier du module a recompiler et faites :

```bash
mvn install -DskipTests
```

> [!TIP]
> De la même façon qu'avec Docker, si vous souhaitez visualiser les données de test, un endpoint du service note-manager permet de configurer la base de donnée MongoDB avec des notes de test. Si vous avez lancé le module note-manager rendez-vous sur `localhost:9102/setup` , si vous y accédez par la gateway rendez vous sur `localhost:9100/note-manager/setup`
