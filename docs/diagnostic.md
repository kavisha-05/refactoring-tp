# Étape 1 : diagnostic

Ce diagnostic décrit le code historique, avant le refactoring. Les listes d'extension ci-dessous
servent de point de comparaison pour la séance 2.

## Rôle et dépendances des classes historiques

| Classe | Rôle | Dépendances principales |
|---|---|---|
| Main | Lance un scénario de démonstration. | Flotte, services, clients, colis et expéditions |
| Client | Représente un client. | Date |
| Colis | Représente une marchandise et son type. | Aucune classe métier |
| Expedition | Représente une expédition et produit sa facture. | Client, Colis, Vehicule, Utils, Date |
| ServiceClient | Crée et valide les clients. | Client, Database, EmailService, Utils |
| ServiceExpedition | Gère les expéditions, les devis et leur cycle de vie. | Database, CalculateurPrix, AffectationVehicule, Flotte indirectement, EmailService, SmsService, Utils et modèle métier |
| CalculateurPrix | Calcule le prix d'une expédition. | Colis |
| AffectationVehicule | Choisit un véhicule compatible. | Flotte, Colis, Vehicule et ses sous-classes |
| Flotte | Initialise et conserve les véhicules. | Camion, CamionFrigorifique, Train, Bateau, Avion |
| IVehicule | Définit les opérations imposées aux véhicules. | Colis |
| Vehicule | Fournit l'état et les opérations communs des véhicules. | IVehicule, Colis |
| Camion | Représente un camion. | Vehicule |
| CamionFrigorifique | Représente un camion réfrigéré. | Camion, Colis |
| Train | Représente un train. | Vehicule |
| Bateau | Représente un bateau. | Vehicule |
| Avion | Représente un avion. | Vehicule |
| Database | Simule la persistance et les requêtes SQL. | Client, Expedition, collections |
| EmailService | Simule les emails et conserve leur journal. | Collections |
| SmsService | Simule les SMS et conserve leur journal. | Collections |
| Utils | Fournit l'arrondi, le formatage, la validation d'email et les distances. | Date et API de dates Java |

## Défauts relevés

| # | Défaut | Emplacement | Principe |
|---|---|---|---|
| 1 | ServiceExpedition fait tout : validation, création, devis, délais, cycle de vie, notifications, id | ServiceExpedition | S |
| 2 | L'entité fabrique sa propre facture | Expedition.genererFacture() | S |
| 3 | creerClient valide, enregistre et envoie un email | ServiceClient.creerClient | S |
| 4 | Classe fourre-tout : dates, prix, email, distances | Utils | S |
| 5 | Stockage, SQL simulé et singleton mélangés | Database | S |
| 6 | if/else sur le mode, le type de client et les suppléments | CalculateurPrix.calculerPrixHT | O |
| 7 | switch sur le mode pour les délais | ServiceExpedition.calculerDelai | O |
| 8 | Règles d'acceptation avec mode.equals("AIR"), "MER"... | ServiceExpedition.creerExpedition | O |
| 9 | if/else sur le mode pour rouler/naviguer/voler | ServiceExpedition.demarrerExpedition | O |
| 10 | Cascade de if (mode) et d'instanceof | AffectationVehicule.trouverVehicule | O |
| 11 | Table des distances en chaîne de if | Utils.getDistance | O |
| 12 | switch sur un int pour le libellé | Colis.getLibelleType | O |
| 13 | CamionFrigorifique.charger lève une exception (précondition renforcée), contournée par un continue | CamionFrigorifique, AffectationVehicule | L |
| 14 | Train.faireLePlein lève UnsupportedOperationException, masquée par un catch vide | Train, ServiceExpedition.demarrerExpedition | L |
| 15 | IVehicule impose rouler, naviguer, voler, activerRefrigeration, faireLePlein à tous (12 occurrences de UnsupportedOperationException) | IVehicule et sous-classes | I |
| 16 | Database.getInstance(), new CalculateurPrix(), new AffectationVehicule(), new SmsService(), EmailService.envoyer() statique | ServiceExpedition, ServiceClient | D |
| 17 | État statique modifiable : Flotte.vehicules, Database.expeditions/clients, EmailService.journal, SmsService.journal, ServiceExpedition.compteur | plusieurs classes | D |

Autres défauts : nombres magiques, types codés en String/int, arrondi répété 4 fois alors que Utils.arrondir existe, validation d'email dupliquée, volume calculé en double, clé d'API en clair dans SmsService, catch vide dans EmailService, Vehicule.charger échoue en silence.

## Liste pour ajouter le mode FLUVIAL (7 endroits)

1. Utils.getDistance : nouveau trajet
2. CalculateurPrix.calculerPrixHT : nouvelle branche de tarif
3. ServiceExpedition.creerExpedition : interdire réfrigéré et urgence
4. ServiceExpedition.calculerDelai : nouveau case
5. ServiceExpedition.demarrerExpedition : nouvelle branche de départ
6. AffectationVehicule.trouverVehicule : nouvelle branche + instanceof Peniche
7. Flotte.initialiser : ajouter la péniche (+ nouvelle classe Peniche)

Si le nouveau mode doit aussi être accessible par l'interface de contrat existante, son enum
et l'adaptateur devraient également être étendus. Les règles de l'atelier interdisent toutefois
de modifier le module `transport-contrat` : la preuve d'extension devra donc tester le nouveau
mode directement dans le module refactorisé, sans changer les 68 tests existants.

## Liste pour le supplément « hors gabarit » (2 endroits)

1. CalculateurPrix.calculerPrixHT : boucle de détection des colis
2. CalculateurPrix.calculerPrixHT : bloc des suppléments

## Choix réalisés en séance 1

`ServiceClient` orchestre le cas d'utilisation de création ; la validation, la persistance et
l'envoi sont confiés à des collaborateurs distincts. `Application` est la racine de composition
commune à `Main` et à l'adaptateur de test : elle construit les services et leurs dépendances.
La fabrique `Flotte.standard()` construit une flotte neuve à chaque appel ; elle ne conserve pas
d'état statique partagé. Les branches par mode et les interfaces des véhicules restent à traiter
pendant la séance 2.
