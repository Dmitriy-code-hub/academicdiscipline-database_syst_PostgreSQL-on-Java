package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Dohod{
    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty region_id = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty vid_id = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty misyac = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty rik = new SimpleIntegerProperty(0);
    private final SimpleDoubleProperty grn = new SimpleDoubleProperty(0);
    private final SimpleStringProperty region_name = new SimpleStringProperty("");
    private final SimpleStringProperty vid_name = new SimpleStringProperty("");

    public Dohod() {
        this(0, 0, 0, 1,1,0.0,"", "");
    }

    public Dohod(Integer id, Integer region_id, Integer vid_id,
                 Integer misyac, Integer rik, Double grn,
                 String region_name, String vid_name) {
        setId(id);
        setRegion_id(region_id);
        setVid_id(vid_id);
        setMisyac(misyac);
        setRik(rik);
        setGrn(grn);
        setRegion_name(region_name);
        setVid_name(vid_name);
    }

    public Integer getId() {
        return id.get();
    }
    public void setId(Integer id1) {
        id.set(id1);
    }

    public Integer getRegion_id() {
        return region_id.get();
    }
    public void setRegion_id(Integer id1) {
        region_id.set(id1);
    }

    public Integer getVid_id() {
        return vid_id.get();
    }
    public void setVid_id(Integer id1) {
        vid_id.set(id1);
    }

    public Integer getMisyac() {
        return misyac.get();
    }
    public void setMisyac(Integer id1) {
        misyac.set(id1);
    }

    public Integer getRik() {
        return rik.get();
    }
    public void setRik(Integer id1) {
        rik.set(id1);
    }

    public Double getGrn() {
        return grn.get();
    }
    public void setGrn(Double kod1) {
        grn.set(kod1);
    }

    public String getRegion_name() {
        return region_name.get();
    }
    public void setRegion_name(String name1) {
        region_name.set(name1);
    }

    public String getVid_name() {
        return vid_name.get();
    }
    public void setVid_name(String name1) {
        vid_name.set(name1);
    }
}