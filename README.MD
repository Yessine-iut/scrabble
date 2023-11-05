# Scrabblid
### _Scrabble sans humains avec des web services (spring), intégration continue, tests unitaires et d’intégration_

[![Build Status](https://app.travis-ci.com/Master1-MIAGE-UCA/scrabble-td2-scrabblid.svg?token=5RaPNQzRod2wJ5QRvqrq&branch=develop-refactored)](https://app.travis-ci.com/Master1-MIAGE-UCA/scrabble-td2-scrabblid.svg?token=5RaPNQzRod2wJ5QRvqrq&branch=develop-refactored)

Ce projet a été réalisé durant le M1 MIAGE lors du module **"Outils pour l'ingénieurie logicielle"**.

## Contributeurs

- Hugo NORTIER
- Saad el din AHMED
- Yessine BEN EL BEY
- Wajdi GAIECH
- Elias TAZI

## Livraisons

> Livraison 01 (13/05) : mise en place du projet, intégration continue
Il n’y a de contrainte sur le code, mais penser qu’il faudra ensuite séparer en plusieurs modules

> Livraison 02 (27/05) : introduction de Spring, introduction de docker

> Livraison 03 (10/06) : des tests unitaires spring, un test d’intégration, docker-compose

> Livraison 04 (24/06 – 27/06) : livraison finale
Les « réponses » aux questions (données ultérieurement)
Une vidéo suivant un fil conducteur de présentation

La vidéo se situe à cet emplacement [youtube]()

## Suivi de projet
[Fichier excel](Tracking/Scrabblid_Tracking.xlsm)  
[Fichier python](Tracking/Python/Scrabblid_Tracking.py)

pour lancer le python:  
```sh
cd Tracking/Python
pip install pandas
pip install openpyxl
python3 Scrabblid_Tracking.py
```

## Commandes

```sh
mvn clean install -DskipTests
docker-compose build --no-cache
docker-compose run
```
