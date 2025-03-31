package Catalog;

import java.util.Scanner;

public class Main {
    static Servicii serviciu = new Servicii();

    public static void main(String[] args) {
        boolean bool = true;
        while (bool == true) {
            Scanner input = new Scanner(System.in);
            int varianta = 0;
            System.out.println("!ALEGETI NUMARUL ATRIBUIT FIECAREI ACTIUNI!");
            System.out.println("---PRIMARA (CLASELE 1-4)---");
            System.out.println("1\tCreare catalog");
            System.out.println("2\tAdaugare calificativ");
            System.out.println("3\tAdaugare absenta");
            System.out.println("4\tMotivare absenta");
            System.out.println("5\tAfisare catalog");
            System.out.println("---GIMNAZIALA (CLASELE 5-8)---");
            System.out.println("6\tCreare catalog");
            System.out.println("7\tAdaugare nota");
            System.out.println("8\tAdaugare absenta");
            System.out.println("9\tMotivare absenta");
            System.out.println("10\tCalculare medie generale");
            System.out.println("11\tAfisare catalog");
            System.out.println("---ALTELE---");
            System.out.println("12\tSortarea tuturor cataloagelor in functie de numarul de elevi");
            System.out.println("13\tAfisarea tuturor elevilor");
            System.out.println("14\tIesire");
            System.out.print("\nALEGERE: ");
            varianta = input.nextInt();
            switch (varianta) {
                case 1:
                    serviciu.catalogulPrimara();
                    break;
                case 2:
                    serviciu.calificativPrimara();
                    break;
                case 3:
                    serviciu.adaugareAbsentePrimara();
                    break;
                case 4:
                    serviciu.motivareAbsentePrimara();
                    break;
                case 5:
                    serviciu.afisarePrimara();
                    break;
                case 6:
                    serviciu.catalogulGimnaziu();
                    break;
                case 7:
                    serviciu.notaGimnaziu();
                    break;
                case 8:
                    serviciu.adaugareAbsenteGimnaziu();
                    break;
                case 9:
                    serviciu.motivareAbsenteGimnaziu();
                    break;
                case 10:
                    serviciu.calculareMedieGimnaziu();
                    break;
                case 11:
                    serviciu.afisareGimnaziu();
                    break;
                case 12:
                    serviciu.sortareCataloage();
                    break;
                case 13:
                    serviciu.afisareToateDatele();
                    break;
                case 14:
                    bool = false;
                    break;
                default:
                    System.out.println("INVALID! REINCERCATI:");
                    break;

            }
        }
    }
}