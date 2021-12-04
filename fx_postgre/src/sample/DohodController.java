package sample;
//https://stackoverflow.com/questions/18941093/how-to-fill-up-a-tableview-with-database-data
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DohodController {

    @FXML
    private MenuBar menuBar;

    @FXML
    private void handleKeyInput(ActionEvent event) {

    }

    @FXML private TableView<Dohod> tableView;
    @FXML private ComboBox regionCombo;
    @FXML private ComboBox vidCombo;
    @FXML private TextField idField;
    @FXML private TextField misyacField;
    @FXML private TextField rikField;
    @FXML private TextField grnField;
    @FXML private ObservableList<Dohod> data;
    @FXML private ObservableList<Region> region_data;
    @FXML private ObservableList<Vid> vid_data;

    @FXML
    protected void addRow(ActionEvent event) {
        //Додати
        BDConnector connector = new BDConnector(); //static connection  - 1 для всех
        Region r1cb = (Region)regionCombo.getValue();
        Vid v1cb = (Vid)vidCombo.getValue();

        Dohod r1 = new Dohod(
                Integer.parseInt("0"+idField.getText()),
                r1cb.getId(),
                v1cb.getId(),
                Integer.parseInt("0"+misyacField.getText()),
                Integer.parseInt("0"+rikField.getText()),
                Double.parseDouble("0"+grnField.getText()),
                "no",
                "no");
        String query = "INSERT INTO public.dohod( " +
                " region_id, vid_id, misyac, rik, grn) " +
                " VALUES (?, ?, ?, ?, ?);";

        try ( PreparedStatement pst = connector.connection.prepareStatement(query)) {
            pst.setInt(1, r1.getRegion_id());
            pst.setInt(2, r1.getVid_id());
            pst.setInt(3, r1.getMisyac());
            pst.setInt(4, r1.getRik());
            pst.setDouble(5, r1.getGrn());
            pst.executeUpdate(); //no  insert
        } catch (SQLException ex) {
            System.out.println("Failed to INSERT data");
            return;
        }
        idField.setText("");
        misyacField.setText("");
        rikField.setText("");
        grnField.setText("");
        initialize();
    }

    public void handleExitAction(ActionEvent actionEvent) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        //System.out.println("dohod initialize()" );
        data = FXCollections.observableArrayList();
        BDConnector connector = new BDConnector(); //static connection  - 1 для всех
        try (
                PreparedStatement pst = connector.connection.prepareStatement(
                        "SELECT d.id, d.region_id, d.vid_id," +
                                " d.misyac, d.rik, d.grn, " +
                                " r.name as region_name, v.name as vid_name " +
                                " FROM public.dohod d, public.region r, public.vid v" +
                                " WHERE d.region_id=r.id and d.vid_id = v.id" +
                                " order by d.id");
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Dohod reg = new Dohod(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getDouble(6),
                        rs.getString(7),
                        rs.getString(8));
                data.add(reg);
            }
            tableView.setItems(data);
        } catch (SQLException ex) {
            System.out.println("Failed to SELECT data");
            return;
        }
        //region
        region_data = FXCollections.observableArrayList();
        try (
                PreparedStatement pst = connector.connection.prepareStatement(
                        "SELECT * FROM public.region order by id");
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Region reg = new Region(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3));
                region_data.add(reg);
            }
            regionCombo.setItems(region_data);
        } catch (SQLException ex) {
            System.out.println("Failed to SELECT data");
            return;
        }
        //vid
        vid_data = FXCollections.observableArrayList();
        try (
                PreparedStatement pst = connector.connection.prepareStatement(
                        "SELECT * FROM public.vid order by id");
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Vid reg = new Vid(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3));
                vid_data.add(reg);
            }
            vidCombo.setItems(vid_data);
        } catch (SQLException ex) {
            System.out.println("Failed to SELECT data");
            return;
        }
    }

    public void editRow(ActionEvent actionEvent) {
        //Редагувати = скопіювати дані з таблиці в поля під нею
        Dohod reg = tableView.getSelectionModel().getSelectedItem();
        if (reg==null){
            info("Помилка","Не обрано рядок","Спочатку оберіть рядок в таблиці.");
            return;
        }
        //
        for (Region x : region_data)
        {
            if (x.getId().equals(reg.getRegion_id()))
            {
                regionCombo.setValue(x);
                break;
            }
        }
        //
        for (Vid x : vid_data)
        {
            if (x.getId().equals(reg.getVid_id()))
            {
                vidCombo.setValue(x);
                break;
            }
        }
        //
        idField.setText(reg.getId().toString());
        misyacField.setText(reg.getMisyac().toString());
        rikField.setText(reg.getRik().toString());
        grnField.setText(reg.getGrn().toString());
    }
    private void info(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void updateRow(ActionEvent actionEvent) {
        //Замінити
        BDConnector connector = new BDConnector(); //static connection  - 1 для всех
        Region r1cb = (Region)regionCombo.getValue();
        Vid v1cb = (Vid)vidCombo.getValue();

        Dohod r1 = new Dohod(
                Integer.parseInt("0"+idField.getText()),
                r1cb.getId(),
                v1cb.getId(),
                Integer.parseInt("0"+misyacField.getText()),
                Integer.parseInt("0"+rikField.getText()),
                Double.parseDouble("0"+grnField.getText()),
                "no",
                "no");

        String query = "UPDATE public.dohod " +
                " SET region_id=?, vid_id=?, misyac=?, rik=?, grn=? " +
                "  WHERE id= ?";

        try ( PreparedStatement pst = connector.connection.prepareStatement(query)) {
            pst.setInt(1, r1.getRegion_id());
            pst.setInt(2, r1.getVid_id());
            pst.setInt(3, r1.getMisyac());
            pst.setInt(4, r1.getRik());
            pst.setDouble(5, r1.getGrn());
            pst.setInt(6, r1.getId());
            pst.executeUpdate(); //no  insert
        } catch (SQLException ex) {
            System.out.println("Failed to UPDATE data");
            return;
        }
        idField.setText("");
        misyacField.setText("");
        rikField.setText("");
        grnField.setText("");
        initialize();
    }

    public void deleteRow(ActionEvent actionEvent) {
        //Видалити
        Dohod reg = tableView.getSelectionModel().getSelectedItem();
        if (reg==null){
            info("Помилка","Не обрано рядок","Спочатку оберіть рядок в таблиці.");
            return;
        }

        BDConnector connector = new BDConnector(); //static connection  - 1 для всех

        String query = "DELETE FROM  public.dohod WHERE id= ?";

        try ( PreparedStatement pst = connector.connection.prepareStatement(query)) {
            pst.setInt(1, reg.getId());
            pst.executeUpdate(); //no  insert
        } catch (SQLException ex) {
            System.out.println("Failed to DELETE data");
            return;
        }
        initialize();
    }
}
