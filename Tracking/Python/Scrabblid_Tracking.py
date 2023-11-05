import pandas as pd
import pip
import math
from datetime import datetime
import numpy as np
pip.main(["install", "openpyxl"])

class project:
    def __init__(self, taches,nbPersonne,dateFin):
        self.taches=taches
        self.totalPersonne=nbPersonne
        self.dateFin=dateFin

    def calculTotalInitial(self):
        total=0
        for i in range(len(self.taches)):
            total+=self.taches[i].initLoad
        return total

    def printProject(self):
        for i in range(len(self.taches)):
           print(self.taches[i].printTask()+" | \n%age dans le projet: "+str(math.floor(self.taches[i].initLoad/self.calculTotalInitial()*10000)/100))
    def resteAFaire(self):
        raf=0       
        for i in range(len(self.taches)):
            raf+=self.taches[i].remaining
        return raf
    def jourCalendaire(self):
        now = datetime.now()
        return self.dateFin-now


class rowTrackingHistory:
    def __init__(self, date, who,what,workload,commentary):
        self.date=date
        self.who=who
        self.what=what
        self.workload=workload
        self.comment=commentary

    def printTrackingHistory(self):
        print("Date: "+str(self.date) +" | who: "+str(self.who)+" | workload: "+str(self.workload)+" | comment: "+str(self.comment))

class Tache:
    realized = 0
    progress = 0
    rowList=[]
    def __init__(self, id, description, initialWorkload, actualWorkload):
        self.id = id
        self.desc = description
        self.initLoad = initialWorkload
        self.actualLoad = actualWorkload
        self.remaining = initialWorkload-self.realized 

    def printTask(self):
        if(round(self.remaining,2)==0):
            self.remaining=0.0
            self.progress=100
        return "Id: " + self.id + " | DESC: " + self.desc + " | initWorkload: " + str(
            self.initLoad) + " | ActualWorkload: " + str(
            self.actualLoad) + " | Realized: " + str(round(self.realized,2)) + " | Remaining: " + str(
            round(self.remaining,2)) + " | Progress:" + str(self.progress) + "% | Available Time: "+str(round(self.initLoad-self.actualLoad,2))

    def update(self, workload):
        self.realized += workload
        self.remaining = self.actualLoad - self.realized
        self.progress = math.ceil((self.realized / self.actualLoad) * 100)

    def addRowHistory(self,row):
        self.rowList.append(row)
    
    def printHistory(self):
        for i in range(len(self.rowList)):
            self.rowList[i].printTrackingHistory()

header = 3 # The header is the 2nd row
excel = pd.read_excel("../Scrabblid_Tracking.xlsm",engine="openpyxl", sheet_name="History_Scrabblid",header = 3,usecols="C:G")
pd.set_option('display.max_rows', None)
print("HISTORY SHEET")
print(excel)
print("------------------------------------------------------------------------------------------------------------------------")
listeTache = []
def creationTache():
    listeTache.append(Tache("LRN.ING.CRS","Course « Outils pour Ing. Logicielle »",4,4))
    listeTache.append(Tache("LRN.ING.TD","TD « Outils pour Ing. Logicielle »",6,6))
    listeTache.append(Tache("LRN.TRK.CRS","Course « Tracking »",2,2))
    listeTache.append(Tache("LRN.TRK.TD","TD « Tracking »",4,4))
    listeTache.append(Tache("MGT","Project management activities",0.5,0.5))
    listeTache.append(Tache("MGT.MET","Working meetings",8,8))
    listeTache.append(Tache("MGT.TRK","Project tracking",2.4,2.5))
    listeTache.append(Tache("DOC.TRK","Tracking end report",2.5,2.6))
    listeTache.append(Tache("DOC.USR","User documentation",0.9,0.9))
    listeTache.append(Tache("CONCEPTION","Diagram Conception",1,1))
    listeTache.append(Tache("DEV.SRV","Development sans modules",2.2,2.2))
    listeTache.append(Tache("DEV.PARTIE","Dev partie",4,4.1))
    listeTache.append(Tache("DEV.ANAGRAMMEUR","Dev anagrammeur",4,4.65))
    listeTache.append(Tache("DEV.APPARIEMENT","Dev appariement",4.5,4.35))
    listeTache.append(Tache("DEV.COMMUN","Dev classes communes",5,4.3))
    listeTache.append(Tache("DEV.JOUEUR","Dev joueur",3,2.35))
    listeTache.append(Tache("TEST.UNITAIRE","tests unitaires",2.5,1.8))
    listeTache.append(Tache("TEST.INTEGRATION","tests d'integration de l'app scrabblid",3,3.05))
    listeTache.append(Tache("TEST.CI","Integration continue via travis",0.5,0.5))
    
creationTache()


projet= project(listeTache,5,datetime(2022, 6, 25))
print("PROJET SHEET")
print("id | Description | InitialWorkload | ActualWorkload | Realized | Remaining | Progress | pourcentage dans le projet") 
for i in range(len(excel)):
    for j in range(len(projet.taches)):
        if(projet.taches[j].id==excel["What"].values[i]):
            projet.taches[j].update(excel['Workload'].values[i])
            projet.taches[j].addRowHistory(rowTrackingHistory(excel['Date'].values[i],excel['Who'].values[i],excel['What'].values[i],excel['Workload'].values[i],excel['Comment'].values[i]))

for i in range(len(projet.taches)):
    projet.printProject()


print("------------------------------------------------------------------------------------------------------------------------")
print("Jour calendaire: "+str(projet.jourCalendaire()))
print("RAF / nb de membres: "+str(projet.resteAFaire()/5))







