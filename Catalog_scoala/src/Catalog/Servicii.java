package Catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Servicii {
    CatalogPrimara catalogPrimara = new CatalogPrimara(2);
    CatalogGimnaziu catalogGimnaziu = new CatalogGimnaziu(2);
    Catalog catalogSimplu = new Catalog(3);
    List<Catalog> Cataloage = new ArrayList<>();

    // catalog primara
    void catalogulPrimara() {
        CatalogPrimara catalog = new CatalogPrimara();
        Cataloage.add(catalog);
    }
    void calificativPrimara() {
        catalogPrimara.adaugaCalificativ();
    }
    void adaugareAbsentePrimara() {
        catalogPrimara.adaugaAbsenta();
    }
    void motivareAbsentePrimara() {
        catalogPrimara.motiveazaAbsenta();
    }
    void afisarePrimara() {
        catalogPrimara.afisareCatalogData();
    }

    // catalog gimnaziu
    void catalogulGimnaziu() {
        CatalogGimnaziu catalog = new CatalogGimnaziu();
        Cataloage.add(catalog);
    }
    void notaGimnaziu() {
        catalogGimnaziu.adaugaNota();
    }
    void adaugareAbsenteGimnaziu() {
        catalogGimnaziu.adaugaAbsenta();
    }
    void motivareAbsenteGimnaziu() {
        catalogGimnaziu.motiveazaAbsenta();
    }
    void calculareMedieGimnaziu() {
        catalogGimnaziu.calculeazaMedia();
    }
    void afisareGimnaziu() {
        catalogGimnaziu.afisareCatalogData();
    }

    // afisarea tuturor datelor
    void afisareToateDatele() {
        catalogSimplu.afisareCatalogData();
    }

    // sortare cataloage in functie de nr de elevi din fiecare catalog
    void sortareCataloage() {
        Collections.sort(Cataloage, new Comparator<Catalog>() {
            @Override
            public int compare(Catalog catalog1, Catalog catalog2) {
                return Integer.compare(catalog1.getNrElevi(), catalog2.getNrElevi());
            }
        });
    }
}
