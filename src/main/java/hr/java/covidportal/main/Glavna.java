package main.java.hr.java.covidportal.main;

import main.java.hr.java.covidportal.model.*;
import main.java.hr.java.covidportal.sortiranje.CovidSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static main.java.hr.java.covidportal.main.MetodeZaValidaciju.*;

/**
 * Glavna klasa za, unos podataka i ispis podataka.
 */
public class Glavna {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    protected static int BROJ_ZUPANIJA;
    protected static int BROJ_SIMPTOMA;
    protected static int BROJ_BOLESTI;
    protected static int BROJ_OSOBA;

    /**
     * Glavna metoda za izvršavanje programa, u kojoj se radi unos podataka i pozivaju
     * dodatne druge metode.
     *
     * @param args ulazni parametri kod pokretanja glavne metode
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        Set<Zupanija> zupanije = new HashSet<>();
        Set<Simptom> simptomi = new HashSet<>();
        Set<Bolest> bolesti = new HashSet<>();
        List<Osoba> osobe = new ArrayList<>();

        System.out.print("Unesite broj zupanija: ");
        BROJ_ZUPANIJA = IntegerExHandler(scan);
        System.out.println("Unesite podatke o " + BROJ_ZUPANIJA + " zupanija!");
        for (int i = 0; i < BROJ_ZUPANIJA; i++) {
            zupanije.add(MetodeZaUnosPodataka.unosZupanije(scan, i));
        }

        System.out.print("Unesite broj simptoma: ");
        BROJ_SIMPTOMA = IntegerExHandler(scan);
        System.out.println("Unesite podatke o " + BROJ_SIMPTOMA + " simptoma!");
        for (int i = 0; i < BROJ_SIMPTOMA; i++) {
            simptomi.add(MetodeZaUnosPodataka.unosSimptoma(scan, i));
        }

        System.out.print("Unesite broj bolesti: ");
        BROJ_BOLESTI = IntegerExHandler(scan);
        System.out.println("Unesite podatke o " + BROJ_BOLESTI + " bolesti!");
        for (int i = 0; i < BROJ_BOLESTI; i++) {
            hvatanjeNeoznaceneIznimkeBolestIstihSimptoma(scan, simptomi, bolesti, i);
        }

        Map<Bolest, List<Osoba>> bolestiOsobaMap = new HashMap<>();
        System.out.print("Unesite broj osoba: ");
        BROJ_OSOBA = IntegerExHandler(scan);
        System.out.println("Unesite podatke o " + BROJ_OSOBA + " osobe!");
        for (int i = 0; i < BROJ_OSOBA; i++) {
            hvatanjeOznaceneIznikePonovljenaOsobaKodUnosa(scan, zupanije, bolesti, osobe, bolestiOsobaMap, i);
        }

        System.out.println("Popis osoba: ");
        for (Osoba osoba : osobe) {
            System.out.println(osoba.toString());
        }

        for (Bolest bolestVirusOsobe : bolestiOsobaMap.keySet()) {
            if (bolestVirusOsobe instanceof Virus virusOsobe) {
                System.out.println("Od zarazenosti virusa " + virusOsobe.getNaziv().toUpperCase(Locale.ROOT) + " boluju: ");
                int i = 0;
                for (Osoba osobeZarazeneBolescu : bolestiOsobaMap.get(bolestVirusOsobe)) {
                    System.out.println((i += 1) + ". " + osobeZarazeneBolescu.getIme() + " " + osobeZarazeneBolescu.getPrezime());
                }
            } else {
                System.out.println("Od bolesti " + bolestVirusOsobe.getNaziv().toUpperCase(Locale.ROOT) + " boluju: ");
                int i = 0;
                for (Osoba osobeZarazeneBolescu : bolestiOsobaMap.get(bolestVirusOsobe)) {
                    System.out.println((i += 1) + ". " + osobeZarazeneBolescu.getIme() + " " + osobeZarazeneBolescu.getPrezime());
                }
            }
        }

        List<Zupanija> sortiraneZupanije = new ArrayList<>(zupanije);
        sortiraneZupanije.sort(new CovidSorter());
        double min = ((sortiraneZupanije.get(0).getBrojZarazenihOsoba() / sortiraneZupanije.get(0).getBrojStanovnikaZupanije()) * 100);
        double max = ((sortiraneZupanije.get(sortiraneZupanije.size() - 1).getBrojZarazenihOsoba() / sortiraneZupanije.get(sortiraneZupanije.size() - 1).getBrojStanovnikaZupanije()) * 100);
        System.out.print("Najmanji postotak zarazenih ima zupanija: " + sortiraneZupanije.get(0).getNaziv() + " sa postotkom zarazenosti: " + min + " %\n");
        System.out.print("Najveci postotak zarazenih ima zupanija: " + sortiraneZupanije.get(sortiraneZupanije.size() - 1).getNaziv() + " sa postotkom zarazenosti: " + max + " %\n");

        scan.close();
    }


}
