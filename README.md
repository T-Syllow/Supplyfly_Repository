# aktuelle TODO's:
1. TOMMY - Lagerbestände sollen überwacht werden & bei Veränderung des Lagerbestands soll überprüft werden, ob eine Bestandsmenge unterschritten ist. Daraufhin soll autonom eine Bestellung, beim Lieferanten mit den besten Bedingungen für das betroffene Produkt, erstellt werden.
2. Lagermitarbeiter soll i.d. Lage sein eine Eilbestellung mit finanziell begrenztem Wert auslösen zu können.
3. Anhand von Daten aus Vertrieb (evtl. auch andere Bereiche) soll die Erstellung des Produktportfolios unterstützt werden! Es sollen optimale Mengen im Produktportfolio errechnet werden. (FORMAT: Tabelle) -> Für Preisverhandlung Vorschläge der besten Lieferanten für eine Preisverhandlung.
4. Abteilungsleiter soll festlegen können, wie hoch die Kosten und die Menge für ein Produkt einer Eilbestellung sind.
5. Neue Lieferanten, Produkte erstellen und einander zuweisen können.
6. TOMMY - Bestellung kann von Beschaffungsmitarbeitern, unter Berücksichtigung des Bestellstatus, editiert werden. Das heißt: Produkte hinzufügen, Produkte stornieren, Mengen korrigieren.
7. TOMMY - Jederzeit können Beschaffungsmitarbeiter eine Bestellung aufgeben.
8. TOMMY - Nach Bestellungsanlegung wird durch Drücken auf „BESTÄTIGEN“ direkt eine XML oder E-Mail an den Lieferanten versendet.
9. Beim Produktportfolio muss Produktnummer, Produktname, Produktpreis und Standardlieferant fürs Produkt angezeigt werden.
10. Beim Produkte Hinzufügen & Produkte verwalten muss der Preis editierbar sein.
-----------------------------
11. Programm soll nicht abstürzen -> sehr gutes Exception Handling.
12. Keine falschen Vorschläge vom Programm -> sehr gute Algorithmen zur Vorschlagsberechnung.
13. Log-Dateien sollen erstellt werden, um beim Absturz entsprechend Hilfe leisten zu können. 
14. Selbst extrem unsichere Anwender sollen überblicken können, was sie geändert haben.
-----------------------------
15. Im Lager befinden sich zu wenig Produkte bzw. Mindestmenge unterschritten = Standardbestellung (nur) anlegen mit Berücksichtigung des schnellsten & günstigsten Lieferanten. Einkäufer prüft angelegte Bestellungen und bestätigt diese ggf. Dann werden die Lieferanten benachrichtigt (XML bzw. E-Mail)
16. Produktmenge ist 0 und Monteur braucht betreffendes Produkt = Lagermitarbeiter kann Eilbestellung aufgeben. (muss aber automatisch Auflagen des Abteilungsleiters der Beschaffung berücksichtigen PREIS und MENGE). Er soll sich die Lieferanten mit der kürzesten Wartezeit anzeigen lassen können und dann einen wählen können. (GUI Abb1 u. Abb2)
17. Einkäufer möchte eine Bestellung stornieren. = mit Berücksichtigung auf Bestellstatus. Der Lieferant wird benachrichtigt (E-Mail)
18. Lieferantenkonditionen und Produktinformationen sollen jederzeit anpassbar sein. (Zugriffsrechte Abteilungsleiter Beschaffung)

# Dokumente:
1. GUI-Entwurf => [SupplyFly(1).pdf](https://github.com/T-Syllow/Supplyfly_Repository/files/8663549/SupplyFly.1.pdf)
2. ER-Modell => [Datenmodell_Bestellung&Portfolio.pdf](https://github.com/T-Syllow/Supplyfly_Repository/files/8663577/Datenmodell_Bestellung.Portfolio.pdf)
3. Pflichtenheft => [SupplyFly Pflichtenheft .pdf](https://github.com/T-Syllow/Supplyfly_Repository/files/8663583/SupplyFly.Pflichtenheft.pdf)
4. Soll Prozess => https://academic.signavio.com/p/explorer#/directory/784d9262c8464fb9864bd9f17ec0af15

# Regeln:
1. Immer ein **lauffähiges Programm** hochladen! 
2. Zu jeder **Änderung (Commit)** im Supplyfly_Repository folgendes Schema einhalten:
  - Klassenname nennen + Änderung in ein paar Worten beschreiben.
  - wichtige Informationen / Gedanken online unter dem Commit _kommentieren_.
3. **Vor dem Programmieren** immer das aktuelle Projekt **pullen**!

