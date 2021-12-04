package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Zvit1 {
    private final SimpleDoubleProperty grn = new SimpleDoubleProperty(0);
    private final SimpleStringProperty code = new SimpleStringProperty("");
    private final SimpleStringProperty name = new SimpleStringProperty("");

    public Zvit1 ()
    {
        setGrn ((double)0);
        setCode("");
        setName ("");
    }

    public Zvit1 (double grn1, String code1, String name1)
    {
        setGrn (grn1);
        setCode(code1);
        setName (name1);
    }

    public Double getGrn() {
        return grn.get();
    }
    public void setGrn(Double grn1) {
        grn.set(grn1);
    }

    public String getCode() {
        return code.get();
    }
    public void setCode(String code1) {
        code.set(code1);
    }

    public String getName() {
        return name.get();
    }
    public void setName(String name1) {
        name.set(name1);
    }

}
