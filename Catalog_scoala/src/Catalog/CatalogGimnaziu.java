package Catalog;

import Administrare.Audit;
import Administrare.SingletonBD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CatalogGimnaziu extends Catalog {
    Map<MateriePersoana, Integer> absente = new HashMap<>();
    Map<MateriePersoana, Integer> note = new HashMap<>();
    Map<MateriePersoana, Integer> teza = new HashMap<>();
    List<String> Note = new ArrayList<>();

    private SingletonBD serviciuBD;
    private Audit audit;

    CatalogGimnaziu (int val) {
        super(val);
    }

    CatalogGimnaziu() {
        super();
        audit = new Audit();
        serviciuBD = SingletonBD.getInstance();
        serviciuBD.setConnection(connection);
        String createTableQuery = "CREATE TABLE IF NOT EXISTS catalog_gimnaziu (id INT AUTO_INCREMENT PRIMARY KEY, materie VARCHAR(255), persoana VARCHAR(255), teza INT, nota INT, absenta INT)";
        serviciuBD.execUpdate(createTableQuery);

        for (MateriePersoana materiePersoana : CatalogulDeBaza) {
            int nota = 0;
            note.put(materiePersoana, nota);
        }

        SingletonBD bazaDate = SingletonBD.getInstance();

        String insertQuery = "INSERT INTO catalog_gimnaziu (materie, persoana) VALUES (?, ?)";
        try {
            PreparedStatement insertStatement = serviciuBD.getConnection().prepareStatement(insertQuery);
            for (MateriePersoana item : CatalogulDeBaza) {
                insertStatement.setString(1, item.getMaterie());
                insertStatement.setString(2, item.getNumeComplet());
                insertStatement.executeUpdate();
            }
            audit.log("CREATE", "catalog_gimnaziu", "materie, persoana");
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public boolean verificarePersoana(String nume, String prenume, String initialaTatalui) {
        for (Persoana persoana : Persoane) {
            if (persoana.getNume().equals(nume) && persoana.getPrenume().equals(prenume) && persoana.getInitialaTatalui().equals(initialaTatalui)) {
                return true;
            }
        }
        return false;
    }

    public boolean verificareMaterie(String materie) {
        for (String Materie : Materii) {
            if (Materie.equals(materie)) {
                return true;
            }
        }
        return false;
    }

    public void adaugaAbsenta() {
        String numeAbsent;
        String prenumeAbsent;
        String initialaTataluiAbsent;
        String materieAbsent;
        int nrAbsente = 0;

        Scanner input = new Scanner(System.in);
        System.out.print("Numele elevului: ");
        numeAbsent = input.nextLine();
        System.out.print("Prenumele elevului: ");
        prenumeAbsent = input.nextLine();
        System.out.print("Initiala tatalui: ");
        initialaTataluiAbsent = input.nextLine();
        System.out.print("Denumire materie: ");
        materieAbsent = input.nextLine();

        // obtinem nr de absente din baza de date
        SingletonBD BazaDeDate = SingletonBD.getInstance();
        if (BazaDeDate == null) {
            System.out.println("Database connection is null. Make sure the connection is established.");
            return;
        }

        String selectQuery = "SELECT absenta AS num_abs FROM catalog_gimnaziu WHERE materie = ? AND persoana = ?";
        try {
            PreparedStatement selectStatement = BazaDeDate.getConnection().prepareStatement(selectQuery);
            selectStatement.setString(1, materieAbsent);
            selectStatement.setString(2, numeAbsent + " " + initialaTataluiAbsent + " "+ prenumeAbsent);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                nrAbsente = resultSet.getInt("num_abs");
                nrAbsente += 1;
            }

            resultSet.close();
            selectStatement.close();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        try {
            String updateQuery = "UPDATE catalog_gimnaziu SET absenta = ? WHERE materie = ? AND persoana = ?";
            PreparedStatement updateStatement = BazaDeDate.getConnection().prepareStatement(updateQuery);
            updateStatement.setInt(1, nrAbsente);
            updateStatement.setString(2, materieAbsent);
            updateStatement.setString(3, numeAbsent + " " + initialaTataluiAbsent + " "+ prenumeAbsent);
            updateStatement.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public void motiveazaAbsenta() {
        String numeAbsent;
        String prenumeAbsent;
        String initialaTataluiAbsent;
        String materieAbsent;
        int nrAbsente = 0;

        Scanner input = new Scanner(System.in);
        System.out.print("Numele elevului: ");
        numeAbsent = input.nextLine();
        System.out.print("Prenumele elevului: ");
        prenumeAbsent = input.nextLine();
        System.out.print("Initiala tatalui: ");
        initialaTataluiAbsent = input.nextLine();
        System.out.print("Denumire materie: ");
        materieAbsent = input.nextLine();

        // obtinem nr. de absente din baza de date
        SingletonBD bazaDate = SingletonBD.getInstance();
        if (bazaDate == null) {
            System.out.println("Database connection is null. Make sure the connection is established.");
            return;
        }

        String selectQuery = "SELECT absenta as num_abs FROM catalog_gimnaziu WHERE materie = ? AND persoana = ?";
        try {
            PreparedStatement selectStatement = bazaDate.getConnection().prepareStatement(selectQuery);
            selectStatement.setString(1, materieAbsent);
            selectStatement.setString(2, numeAbsent + " " + initialaTataluiAbsent + " "+ prenumeAbsent);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                nrAbsente = resultSet.getInt("num_abs");
                nrAbsente -= 1;
            }

            resultSet.close();
            selectStatement.close();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        try {
            String updateQuery = "UPDATE catalog_gimnaziu SET absenta = ? WHERE materie = ? AND persoana = ?";
            PreparedStatement updateStatement = bazaDate.getConnection().prepareStatement(updateQuery);
            updateStatement.setInt(1, nrAbsente);
            updateStatement.setString(2, materieAbsent);
            updateStatement.setString(3, numeAbsent + " " + initialaTataluiAbsent + " "+ prenumeAbsent);
            updateStatement.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void adaugaNota() {
        String nume;
        String prenume;
        String initialaTatalui;
        String materie;
        int nota;

        Scanner input = new Scanner(System.in);
        System.out.print("Numele elevului: ");
        nume = input.nextLine();
        System.out.print("Prenumele elevului: ");
        prenume = input.nextLine();
        System.out.print("Initiala tatalui: ");
        initialaTatalui = input.nextLine();
        System.out.print("Denumire materie: ");
        materie = input.nextLine();
        System.out.print("Nota obtinuta: ");
        nota = input.nextInt();

        int nr = nota;
        while (!(nr >= 1 && nr <= 10)) {
            System.out.println("INVALID!");
            System.out.print("Reintroduceti nota: ");
            nr = input.nextInt();
            nota = nr;
        }

        for (MateriePersoana item : note.keySet()) {
            if (item.getNumeComplet().equals(nume + " " + initialaTatalui + " " + prenume) && item.getMaterie().equals(materie))
                note.put(item, nota);
        }

        SingletonBD bazaDate = SingletonBD.getInstance();
        String updateQuery = "UPDATE catalog_gimnaziu SET nota = ? WHERE materie = ? AND persoana = ?";
        try {
            PreparedStatement updateStatement = bazaDate.getConnection().prepareStatement(updateQuery);
            updateStatement.setInt(1, nota);
            updateStatement.setString(2, materie);
            updateStatement.setString(3, nume + " " + initialaTatalui + " " + prenume);
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                if (audit == null) {
                    audit = new Audit();
                }
                audit.log("UPDATE", "catalog_gimnaziu", "nota");
                System.out.println("Nota a fost actualizata cu succes.");
            } else {
                System.out.println("Nu s-a gasit o intrare corespunzatoare in baza de date.");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }



    void calculeazaMedia() {
        String nume;
        String prenume;
        String initialaTatalui;
        int medie = 0;
        int contor = 0;

        Scanner input = new Scanner(System.in);
        System.out.print("Numele elevului: ");
        nume = input.nextLine();
        System.out.print("Prenumele elevului: ");
        prenume = input.nextLine();
        System.out.print("Initiala tatalui: ");
        initialaTatalui = input.nextLine();

        SingletonBD bazaDate = SingletonBD.getInstance();
        String selectQuery = "SELECT persoana, nota FROM catalog_gimnaziu WHERE persoana = ?";
        try {
            PreparedStatement selectStatement = bazaDate.getConnection().prepareStatement(selectQuery);
            selectStatement.setString(1, nume + " " + initialaTatalui + " " + prenume);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                int nota = resultSet.getInt("nota");
                medie += nota;
                contor++;
            }

            resultSet.close();
            selectStatement.close();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        if (contor > 0) {
            float medie1 = (float) medie / contor;
            System.out.println("Media aritmetica a elevului " + nume + " " + initialaTatalui + " " + prenume + ": " + medie1);
        }
        else {
            System.out.println("Nu exista date pentru elevul " + nume + " " + initialaTatalui + " " + prenume + ".");
        }
    }

    public void afisareCatalogData() {
        String selectQuery = "SELECT * FROM catalog_gimnaziu";
        if (serviciuBD == null) {
            serviciuBD = serviciuBD.getInstance();
        }

        if (audit == null) {
            audit = new Audit();
        }
        ResultSet resultSet = serviciuBD.execQuery(selectQuery);

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String materie = resultSet.getString("materie");
                String persoana = resultSet.getString("persoana");

                System.out.println("ID: " + id);
                System.out.println("Materie: " + materie);
                System.out.println("Persoana: " + persoana);
                System.out.println("-----------------------");
            }
            audit.log("READ", "catalog_gimnaziu", "materie, persoana");
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void stergereMateriePersoana(int id) {
        SingletonBD bazaDate = SingletonBD.getInstance();
        if (audit == null) {
            audit = new Audit();
        }
        String deleteQuery = "DELETE FROM catalog_gimnaziu WHERE id = ?";
        try {
            PreparedStatement deleteStatement = bazaDate.getConnection().prepareStatement(deleteQuery);
            deleteStatement.setInt(1, id);
            int rowsAffected = deleteStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("MateriePersoana record deleted successfully.");
            }
            else {
                System.out.println("No matching records found to delete.");
            }
            audit.log("DELETE", "catalog_gimnaziu", "materie, persoana");
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CatalogGimnaziu catalogGimnaziu = new CatalogGimnaziu(3);
        catalogGimnaziu.afisareCatalogData();

    }
}
