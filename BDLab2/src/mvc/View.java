package mvc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class View {
    public void showCountry ( List rs) {
        if (rs == null) {
            System.out.println("Tabl Pusta");
            return;
        }
        for (int i = 0; i < rs.size(); i++) {
            System.out.println(rs.get(i));
        }
    }
    public void showString (String s){
        System.out.println(s);
    }

}
