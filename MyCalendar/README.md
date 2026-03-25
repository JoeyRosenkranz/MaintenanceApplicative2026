# 🗓️ TP Noté – Maintenance Applicative & TDD en Java
Gestionnaire d'Événements & Calendrier


## 🎯 Objectifs du TP :
Mettre en pratique la démarche Test-Driven Development (TDD).  
Appliquer des principes propres à la maintenance applicative en faisant évoluer une base de code existante.  

Pour pimenter un peu les choses, il y aura quelques petites contraintes:
- Utiliser exclusivement des Value Objects  
// interdiction d'utiliser des primitives nues  
- Implémenter une solution reposant sur le polymorphisme  
// interdiction d'utiliser des conditionnels (if, switch, opérateur ternaire).


## 📌 Contexte du TP :
Votre supérieur, fan de *COBOL* et "autodicacte" a dévellopé un projet nommé **« *CalendarManager* »** (oui, une application avec un nom anglais, c'est plus classe), une application minimaliste de gestion d’événements et rendez-vous. Il n'a pas du tout confiance en l'utilisation de code externe (il a déjà fallu batailler pour passer aux fax...), donc il n'est même pas la peine de lui proposer une autre solution, même éprouvée. 

L’application souffre d’une mauvaise conception initiale (utilisation excessive de primitives, couplage fort, répétitions de conditionnels, etc.).

Votre rôle est d'appliquer une stratégie de refactoring progressif afin de transformer ce code fragile en un système robuste, facilement maintenable et évolutif.


## 🚧 Contraintes techniques imposées :

### Baby-steps
On ne casses pas tout d'un coup, on essaie de faire d'abord un nettoyage progressif pour réduire la quantité de code, puis on restructure.

### Développement des nouvelles fonctionnalités guidé par les tests (TDD) :
Chaque fonctionnalité doit être écrite en suivant strictement la démarche TDD :
- Écrire d’abord un test qui échoue (rouge).
- Implémenter le code nécessaire pour passer le test (vert).
- Refactorer sans casser les tests précédents.

### Interdiction des primitives nues :
Toutes les données métier (dates, heures, durées, titres, lieux) doivent être représentées par des Value Objects immuables.
Exemples obligatoires de Value Objects : 
- DateEvenement,
- HeureDebut,
- DureeEvenement,
- TitreEvenement,
- etc.

Aucune primitive brute (int, double, String, Date, etc.) n’est autorisée dans le domaine métier directement.

### Polymorphisme et interdiction des conditionnels :
Aucun bloc conditionnel explicite (if, switch, opérateur ternaire) n'est autorisé dans le domaine métier.
Tout comportement variant doit être géré par le polymorphisme via des interfaces ou classes abstraites.


## 📦 Domaine Métier (Calendrier d'événements) :

Le calendrier permet d'ajouter différents types d'événements, notamment :

- Rendez-vous personnels : simples, avec une date, une heure de début, une durée, un titre.  
- Réunions : avec des participants et un lieu.  
- Événements périodiques : répétitifs à fréquence fixe (hebdomadaire, mensuelle, annuelle).  
- Chaque événement peut générer une description textuelle spécifique à son type, sans utiliser de conditions explicites.


## ⚙️ Exigences fonctionnelles à implémenter :
- [x] 1) Ajouter un nouveau type d'événement au calendrier.
- [x] 2) Obtenir la liste des événements pour une période donnée.
- [x] 3) Détecter automatiquement les conflits entre événements (chevauchement horaire).
- [x] 4) Générer une description spécifique à chaque type d'événement.
- [x] 5) Pouvoir supprimer un événement par son identifiant métier (ajouter un EventId).


## ✅ Livrables attendus :
Projet Maven ou Gradle comprenant obligatoirement des tests unitaires.  
Commit par étape démontrant la démarche TDD (rouge → vert → refactor).  
Respect strict des contraintes techniques :
- Aucune primitive nue
- Aucun conditionnel explicite
- Utilisation obligatoire des Value Objects
- Démonstration claire du polymorphisme


## 🧑‍💻 Évaluation (à titre indicatif):
- Qualité et fréquence des commits - **2 points**
- Qualité de la démarche TDD - **4 points**
- Implémentation des fonctionnalités - **2 points**
- Respect des contraintes techniques - **4 points**
- Qualité du design orienté objet & maintenabilité - **4 points**
- Qualité/Lisibilité du code et des tests - **4 points**


## 🚀 Suggestions d’évolution ultérieure possible (non obligatoire mais bonus) :
- Sérialisation/Désérialisation vers JSON. **+1** 
- Ajout d'une interface utilisateur minimale. **+3**
- Une idée, ben go, fait des beaux commits et je verrais bien ce que ça vaut. **+X**

**Rappel: Un bonus est un bonus, le projet, hors bonus, est noté sur 20. Les bonus permettent juste de récuppérer des points potentiellement perdus.**