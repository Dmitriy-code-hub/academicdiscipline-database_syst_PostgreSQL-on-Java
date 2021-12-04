package mvc;
import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Controller {
    Model model;
    View view;
    public Controller (Model m,View v) {
        this.model=m;
        this.view=v;
        m.connect();
    }
    public void showCountries (){
        List rs = model.selectCountry();
        view.showCountry(rs);
    }

    public void insertCountry (String code,String name) {
        String s = model.insertCountry(code, name);
        view.showString(s);
    }

    public void updateCountry (String code,String name) {
        String s = model.updateCountry(code, name);
        view.showString(s);
    }

    public void deleteCountry (String code) {
        String s = model.deleteCountry(code);
        view.showString(s);
    }

       public void eventsRandom (int n) {
        String s = (String) model.eventsRandom(n);
        view.showString(s);
    }
   public void selectQuery1(String country_code, String sity_name, String starts1, String starts2) {
       List rs = model.selectQuery1(country_code, sity_name, starts1, starts2);
       view.showCountry(rs);
   }


}
