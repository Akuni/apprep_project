# Projet d'Application Répartie
---------------

## Sommaire

* Rappel du sujet
* Configuration
* Explication de la solution
* Problemes rencontrés


---------------

## Rappel du sujet

Un portail "générique" de partage de données ET de services, avec RMI (et un peu de JMS)

&nbsp;&nbsp;&nbsp;&nbsp;Le but de cette application répartie, écrite en RMI a pour but de généraliser ce à quoi sert un Registry RMI. En offrant plus de flexibilité, ce à plusieurs titres :
Stocker des proxy/stubs de services RMI, alore même que le registre (le portail) ne s'exécute pas forcément sur la même machine où ces services s'exécutent
Stocker non seulement des proxy/stubs RMI, mais aussi n'importe quel objet dès lors qu'il est Serializable, ce qui permet ainsi de partager directement des données.
&nbsp;&nbsp;&nbsp;&nbsp;Ce registre "universel" utilisera donc le principe de téléchargement dynamique de bytecode Java (de classe). Il se restreint de fait au support d'applications écrites en Java... Vous réutiliserez le programme de mise à disposition de fichier de classes, utilisé dans le TP2 (classserver.ClassFileServer). Dans ce projet, on fera abstraction des problèmes de sécurité, en supposant que tous les serveurs et les clients qui utilisent ce portail sont de confiance.
&nbsp;&nbsp;&nbsp;&nbsp;Dans ce projet, vous lancez ce registre universel (cette application RMI) et devez donc l'enregistrer dans un RMI Registry standard, qui s'exécute sur la même machine que cette application. Ce RMI Registry est accessible lui même à distance, par exemple sur machineDuRegistre:4000. Donc, ceux qui l'utiliseront ne seront pas forcément lancés sur machineDuRegistre et peuvent accéder depuis n'importe où de l'internet à machineDuRegistre:4000 pour y chercher un proxy/stub vers le registre universel. Bien sûr, pour des questions pratiques évidentes, votre projet utilisera toujours localhost plutôt que n'importe quelle autre adresse IP.

&nbsp;&nbsp;&nbsp;&nbsp;Le registre n'est rien d'autre qu'une application RMI qui offre une API ressemblant à celle de RMIRegistry, ou à n'importe quel service d'annuaire type JNDI (bind, rebind, get, list, ...). En interne, ce registre utilise une HashTable (ou une HashMap, à voir si vous avez besoin de vous soucier d'aspects d'accès concurrents). L'idée est donc d'offir aux utilisateurs de ce service de mise à disposition d'information, la possibilité d'enregistrer sous une clé (une String, choisie par ses utilisateurs) un objet sérializable. Ensuite, d'autres utilisateurs viendront interroger le registre pour récupérer de tels objets à travers leur clé d'enregistrement. En plus des spécifications d'un service de nommage standard, votre service de mise à disposition peut aussi proposer à ses clients de connaitre :

quelles sont les X dernières informations qui ont été enregistrées (ex, X=10);
ou quelles sont toutes les (X) dernières clés utilisées pour enregistrer des informations ;
quelles sont les informations (les clés) les plus demandées, les plus populaires depuis un intervalle de temps Y
etc, libre cours à votre imagination !
Pour commencer supportez un service de nommage "flat", et si vous avez le temps, mettez en place un service de nommage hiérarchique (ex: donnez moi les loueurs de véhicules, mais seulement ceux qui ont déclaré offrir un service de location de véhicules uniquement électriques: loue_vehicules/electrique). Le registre étant générique, cela signifie qu'il ne connait aucune des classes des objets qu'il va enregistrer. Mais, il les téléchargera automatiquement comme vu dans le TP2 (codebase adéquat !). De ce fait, il peut même être utilisé pour faire la mise en relations d'usagers dans plusieurs domaines d'application à la fois.
Par contre, les clients sauront plus ou moins quel type d'information ils cherchent dans ce registre (souvenez-vous, côté client, il faut au moins connaitre les classes pour que ce code puisse compiler, à moins que vous ne fassiez appel à l'introspection, mais c'est hors sujet): par exemple, soit

ils cherchent une Donnee (classe implantant une méthode toString adéquate) ou une sous-classe de Donnee;
soit ils cherchent un Service donnant accès à des données interessantes au travers par exemple d'une méthode String getInfo() pour récupérer quelques premières informations sur le service, telles le propriétaire du service, la nature du service rendue ; puis une méthode ReponseService accesService() qui permettra au client d'effectivement utiliser le service (là, ce sera forcément au travers d'un appel RMI). En bonus il est fortement suggéré d'utiliser le pattern d'un Smart Proxy puisque certaines informations (les premières informations) sont a priori immuables, alors que d'autres non puisque elles sont obtenues seulement si on utilise le service.
&nbsp;&nbsp;&nbsp;&nbsp;Pour supporter ce "plus ou moins", dans le code du client il suffit de capturer une ClassCastException: si ce qu'on a récupéré du registre est effectivement castable en Donnee, c'est bon, on a obtenu des informations qu'un autre usager a bien voulu partager ("gratuitement") au travers du registre ; mais si ce n'est pas de ce type là, (cad pas de type Donnee), une ClassCastException est levée et peut être attrapée, et on essaye alors de la caster en un stub vers une interface Remote nommée Service. Pour simplifier, Service n'est pas sous classée, et offre toujours les méthodes Remote prévues, et pas de méthodes en plus. En bonus, vous pourrez imaginer qu'une sous classe de Service puisse permettre de demander à un client appelant ce service d'être rappelé ultérieurement en passant en paramètre son proxy/stub implémentant une interface Remote, proposant par exemple une méthode boolean etreRappelé(AccesClient) ; le service confirmera au client par le boolean qu'il a bien pris en compte la demande de rappel qui sera effectuée ultérieurement (donc, le code du client offre aussi une méthode Remote d'une interface AccesRemote que le service utilisera pour rappeler le client). Votre service devra obligatoirement aussi offrir une méthode s'abonner aux dernières informations. Le client qui l'invoque reçoit en réponse si c'est possible, une référence vers un topic JMS (cette référence est donc un nom de topic JMS auquel le client devra se brancher). En tant qu'abonné à ce topic, le client recevrac ainsi automatiquement et au fur et à mesure les informations qui seront publiées par le service.
Ainsi votre client est un client JMS, en plus d'être client d'un service RMI (le registre universel, et le serveur implantant le service). En bonus, le client est aussi un serveur RMI.

&nbsp;&nbsp;&nbsp;&nbsp;Pour illustrer l'aspect générique de votre registre, vous coderez plusieurs serveurs et plusieurs clients (des méthodes main possibles) pour un domaine d'application de votre choix (après tout, votre innovation est de vendre un registre ayant des propriétés utiles pour n'importe quelle problématique de mise en relation d'usagers) : par exemple, mettant en relation des vendeurs/acheteurs de véhicules, ou de passionés de botanique qui répertorient des plantes rares (et donc, des fournisseurs de services qui permettent d'interroger ou répertorier telle ou telle espèce dans des bases de données adéquates), ou des services de petites annonces de type co-voiturage, entre particuliers ou utilisant des services payants qu'il faut donc contacter... Donc, selon les applications, ce ne seront pas forcément des classes nommées Donnee ou Service, mais tout autre nom. Encore une fois, l'aspect générique de votre registre permet de télécharger n'importe quelle classe dont il aura besoin et dont il n'a pas connaissance.

----------------------------------

## Configuration
Afin de bien faire communiquer le client au RMIRegistry ainsi qu'aux différents serveurs, ces derniers nécessitent certaines configurations de leur JVM.

##### Configuration du ClassServer
Le programme necessite comme argument :
###### 1 - Aucun argument
###### 2 - un seul argument representant le port
###### 3 - deux argument representant le port est le lien vers les .class de l'application
#
#

##### Configuration du ClientRMI
La JVM du client nécessite la configuration suivante :
`-Djava.security.policy=java.policy ` , `-Djava.rmi.server.codebase=http://xxx.xxx.xxx.xxx:1234` qui permet de configurer la gestion des connexions aux serveurs. De base tout le monde est accepté puisque le fichier java.policy contient ` grant { permission java.security.AllPermission; } ;`.

##### Configuration du Serveur
La JVM du serveur nécessite la configuration suivante:
`-Djava.rmi.server.hostname="xxx.xxx.xxx.xxx"` (en general `localhost`) qui permet de configurer l'adresse IP à laquelle le serveur sera affecté.

##### Configuration du RMIRegistry
Le rmiregistry doit être lancé en se plaçant dans le repertoire parent du package "app" avec la commande suivante permettant de le lancer sur le port 2000 :
```shell
> rmiregistry 2000
```
----------------------------------

## Explication de la solution

Comme expliqué précédemment il y a donc quatre éléments distincts :
 * Un serveur de class permettant le telechargement dynamic de classes 
 * Un UniversalRegistry qui contient une hashmap de services et de données
 * Un serveur qui peut déposer des services et des données
 * Un client qui peut utiliser ces services et ces données
 

Dans un premier temps, concernant le serveur de classe, il s'agit ici d'un programme fournit qui assure le bon fonctionnement du téléchargement dynamique de classe.
Nous n'aurons qu'à l'utiliser ainsi qu'à le paramétrer afin qu'il pointe vers un dossier contenant nos `.class`.

#### Le serveur de classe 
Le serveur de classe permet aux différents clients de télécharger les classes manquantes pour l'exécution du code.

#### Le servor
Le servor permet de stocker les services ainsi que les données dans des Map. A chaque appel d'un client pour obtenir un service ou une donnée, il suffira alors d'incrémenter le bon enregistrement pour les garder trier, avant de lui envoyer la référence du service ou la copie de la donnée. 

#### Le Serveur
Le serveur implémente l'interface `ICommunication` et peut déposer des objets dans le Servor en utilisant la méthode `rebind` fournie par l'interface.  

#### Le Client
Le client utilise lui aussi l'interface


