# Gilded Rose starting position in Java

## Run the TextTest Fixture from Command-Line

```
./gradlew -q text
```

### Specify Number of Days

For e.g. 10 days:

```
./gradlew -q text --args 10
```

You should make sure the gradle commands shown above work when you execute them in a terminal before trying to use TextTest (see below).


## Run the TextTest approval test that comes with this project

There are instructions in the [TextTest Readme](../texttests/README.md) for setting up TextTest. What's unusual for the Java version is there are two executables listed in [config.gr](../texttests/config.gr) for Java. The first uses Gradle wrapped in a python script. Uncomment these lines to use it:

    executable:${TEXTTEST_HOME}/Java/texttest_rig.py
    interpreter:python

The other relies on your CLASSPATH being set correctly in [environment.gr](../texttests/environment.gr). Uncomment these lines to use it instead:

    executable:com.gildedrose.TexttestFixture
    interpreter:java



1. Testfokus-Identifikation
Basierend auf der Code-Analyse werden folgende kritische Bereiche getestet:

**Spezialitems:** Aged Brie, Backstage passes, Sulfuras

**Qualitätsänderungen:**

* Normaler Qualitätsverfall
* Qualitätssteigerung (z.B. bei Aged Brie)
* Qualitätssprünge bei Backstage passes (5/10 Tage vor Verfall)

**Verfallsdatum-Logik:**

* Verhalten bei sellIn < 0

**Grenzwerte:**

* Qualität nie über 50 (außer Sulfuras)
* Qualität nie unter 0

**TDD-Prinzipien:** Tests wurden vor dem Refactoring geschrieben, um das Verhalten zu spezifizieren.

**Vollständigkeit:**

* 100% Abdeckung aller Item-Typen
* Grenzwerttests für Qualität (0/50)
* Zeitabhängige Logik (sellIn-Verhalten)

**Klare Namensgebung:** Selbstbeschreibende Testmethoden (z.B. backstagePass_QualitaetSteigtUm3Bei5Tagen).

### Herausforderungen

**Verschachtelte Logik:**
Die komplexen if-Bedingungen im Originalcode erforderten präzise Testdaten, um alle Pfade zu erreichen.
-> Lösung: Explizite Tests für sellIn-Schwellwerte (0, 5, 6, 10, 11).

**Seiteneffekte:**
Die Methode updateQuality() ändert Zustände – Tests müssen vor/nach Zustand prüfen.
-> Lösung: Klare Arrange-Act-Assert-Struktur in jedem Test.


2. Refactoring 
Die monolithische updateQuality()-Logik wurde modularisiert, ohne externes Verhalten zu ändern.


**Neue Projektstruktur**
src/main/java/com/gildedrose/
├── Item.java                   # Datenmodell (unverändert)
├── ItemUpdater.java            # Strategie-Interface
├── AgedBrieUpdater.java        # Handhabt "Aged Brie"
├── BackstagePassUpdater.java   # Handhabt "Backstage passes..."
├── SulfurasUpdater.java        # Handhabt "Sulfuras..." (no-op)
├── NormalItemUpdater.java      # Handhabt alle anderen Items
├── ItemUpdaterFactory.java     # Factory zur Auswahl des Updaters
└── GildedRose.java             # Delegiert Update-Aufrufe via Factory

src/test/java/com/gildedrose/
└── GildedRoseTest.java         # Originaltests (unverändert)

**Refactoring-Schritte**

Strategy-Pattern einführen
Extraktion des ItemUpdater-Interfaces, das die update(Item)-Methode definiert.

Dedizierte Updater-Klassen erstellen
Für Aged Brie, Backstage passes, Sulfuras und normale Items.

Factory implementieren
ItemUpdaterFactory ordnet item.name einem Updater zu.

GildedRose vereinfachen
Schleife über Items + ItemUpdaterFactory.getUpdater(item).update(item).

Verhalten validieren
Sicherstellen, dass alle Qualitäts- und sellIn-Regeln (0–50, doppelte Effekte) übereinstimmen.

### Herausforderungen

**Verschachtelte Logik:**
Die vielen if-Bedingungen erforderten genaue Nachbildung im Refactoring.
-> Lösung: Schrittweise Extraktion und sofortige Testläufe.
