# Tracker JavaFX

Projet JavaFX de Tracker, utilisant Maven pour la gestion des dépendances.

## Prérequis
- Java 17 ou supérieur
- Maven

## Lancer le projet
Pour compiler et démarrer l'application depuis le terminal (ou utiliser la fonction Run de votre IDE) :
```bash
mvn clean javafx:run
```

## Structure
- Les vues `.fxml` et le CSS se trouvent dans `src/main/resources`.
- Le code source Java dans `src/main/java`.
- La configuration de la base de données se trouve dans le dossier `prisma` à la racine.
