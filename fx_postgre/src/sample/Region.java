package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Region {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private final SimpleStringProperty kod = new SimpleStringProperty("");
    private final SimpleStringProperty name = new SimpleStringProperty("");

    public Region() {
        this(0, "", "");
    }

    public Region(Integer id, String kod, String name) {
        setId(id);
        setKod(kod);
        setName(name);
    }

    public Integer getId() {
        return id.get();
    }
    public void setId(Integer id1) {
        id.set(id1);
    }

    public String getKod() {
        return kod.get();
    }
    public void setKod(String kod1) {
        kod.set(kod1);
    }

    public String getName() {
        return name.get();
    }
    public void setName(String name1) {
        name.set(name1);
    }

    @Override
    public String toString() {
        return kod.get()+" "+name.get();
    }

    @Override
    public int hashCode() {
        int hash = id.get();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        int other_id = 0 ;
        if (object instanceof Region) {
            other_id = ((Region)object).getId();
        } else {
            return false;
        }

        if ((this.getId() == null && other_id != 0) ||
                (this.getId() != null && !this.getId().equals(other_id))) {
            return false;
        }
        return true;
    }

}