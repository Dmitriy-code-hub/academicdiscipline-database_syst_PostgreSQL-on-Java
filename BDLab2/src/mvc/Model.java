package mvc;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Model {
    Connection connection = null;
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/Laboratornaya1";
    static final String USER = "postgres";
    static final String PASS = "1979Dmitriy";

    public void connect() {
        System.out.println("Testing connection to PostgreSQL JDBC");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");
        connection = null;

        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
            return;
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List selectCountry() {
        //ResultSet rs = null;
        List patternList = new ArrayList<String>();
        try (
                PreparedStatement pst = connection.prepareStatement("SELECT * FROM public.countries");
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String sourcePattern = rs.getString(1) + ":" + rs.getString(2);
                patternList.add(sourcePattern);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ;
        return patternList;

    }

    public String insertCountry(String code, String name) {
        String result = "";
        //java.sql.Timestamp purchase_time = new  java.sql.Timestamp(789456);
        String query = "INSERT INTO public.countries(" + " country_code, country_name) " + " VALUES (?, ?);";//????????update

        try (PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setString(1, code);
            pst.setString(2, name);
            pst.executeUpdate(); //no  insert
            result = "dates insert";
        } catch (SQLException ex) {
            result = "Failed to INSERT";
        }
        return result;
    }

    public String updateCountry(String code, String name) {
        String result = "";
        //java.sql.Timestamp purchase_time = new  java.sql.Timestamp(789456);
        String query = "UPDATE public.countries set Country_name=? where Country_code = ?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, name);
            pst.setString(2, code);
            pst.executeUpdate(); //no  insert
            result = "dannie obnobleny";
        } catch (SQLException ex) {
            result = "Failed to UPDATE";
        }
        return result;

    }

    public String deleteCountry(String code) {
        String result = "";
        //java.sql.Timestamp purchase_time = new  java.sql.Timestamp(789456);
        String query = " DELETE FROM public.countries " + " WHERE Country_code = ? ";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, code);
            pst.executeUpdate(); //no  insert
            result = "dates delete";
        } catch (SQLException ex) {
            result = "Failed to DELETE";
        }
        return result;
    }


    public Object eventsRandom(int n) {
        String result = "";
        //java.sql.Timestamp purchase_time = new  java.sql.Timestamp(789456);
        String query = "insert into events (title, starts, ends,venue_id)\n" +
                "    select 'test'|| cast (cast (random() *1000 as int) as varchar) as title,\n" +
                "    timestamp '2014-01-10 20:00:59' +\n" +
                "    random() * (timestamp '2014-01-20 20:00:00' -\n" +
                "    timestamp '2014-01-10 10:00:00') as starts,\n" +
                "    timestamp '2014-03-10 20:00:00' +\n" +
                "    random() * (timestamp '2014-03-20 20:00:00' -\n" +
                "    timestamp '2014-03-10 10:00:00') as ends,\n" +
                "    cast (random() * 3 + 1 as int) as venue_id\n" +
                "    FROM generate_series(1,?)";
        //events table


        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, n);
            pst.executeUpdate(); //no  insert
            result = "dannie obnobleny";
        } catch (SQLException ex) {
            result = "Failed to UPDATE";
        }
        return result;
    }

    public List selectQuery1(String country_code, String sity_name, String starts1, String starts2) {

           List patternList = new ArrayList<String>();
        System.out.println("          query with timastamp ");
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.uuuu");
       LocalDate d1 = LocalDate.parse(starts1,formatters);
        LocalDate d2 = LocalDate.parse(starts2,formatters);


        try (
                PreparedStatement pst = connection.prepareStatement("SELECT b.*, c.postal_code, c.name, v.name,\n" +
                        "    v.street_address, e.title, e.starts\n" +
                        "    FROM cities c, countries b, events e, venues v\n" +
                        "    WHERE b.country_code = c.country_code\n" +
                        "    AND c.postal_code = v.postal_code\n" +
                        "    AND e.venue_id = v.venue_id\n" +
                        "    AND b.country_code = ?\n" +
                        "    AND c.name LIKE ?\n" +
                        "    AND e.starts BETWEEN ?\n" +
                        "    AND ?"
                ))

        {
                pst.setString (1, country_code);
                pst.setString (2, sity_name);
                pst.setObject (3, d1);
                pst.setObject (4, d2);

                ResultSet rs = pst.executeQuery();


            while (rs.next()) {

     //           System.out.println("194");

                String sourcePattern = rs.getString(1);
                for (int i = 2; i <= 8; i++) {
                   sourcePattern += " : " + rs.getString(i);
                }
                System.out.println(sourcePattern);
                patternList.add(sourcePattern);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return patternList;

    }


}


