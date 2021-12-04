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

public class RegionController {
    @FXML
    private Button btn;

    @FXML
    private void click(ActionEvent event) {
        btn.setText("click");
    }

    @FXML
    private Button btn1;

    @FXML
    private void click1(ActionEvent event) {
        btn.setText("click1");
    }

    @FXML
    private Button btn2;

    @FXML
    private void click2(ActionEvent event) {
        btn.setText("click2");
    }


    @FXML
    private MenuBar menuBar;

    @FXML
    private void handleKeyInput(ActionEvent event) {

    }

    @FXML
    private MenuItem menuAbout;

    @FXML private TableView<Region> tableView;
    @FXML private TextField idField;
    @FXML private TextField kodField;
    @FXML private TextField nameField;
    @FXML private ObservableList<Region> data;

    @FXML
    protected void addRow(ActionEvent event) {
        //Додати
        BDConnector connector = new BDConnector(); //static connection  - 1 для всех
        Region r1 = new Region(Integer.parseInt("0"+idField.getText()),
                kodField.getText(),
                nameField.getText());
        String query = "INSERT into public.region (kod, name) VALUES (?,?)";

        try ( PreparedStatement pst = connector.connection.prepareStatement(query)) {
            pst.setString(1, r1.getKod());
            pst.setString(2, r1.getName());
            pst.executeUpdate(); //no  insert
        } catch (SQLException ex) {
            System.out.println("Failed to INSERT data");
            return;
        }
        idField.setText("");
        kodField.setText("");
        nameField.setText("");
        initialize();
    }

    public void handleExitAction(ActionEvent actionEvent) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        //System.out.println("region initialize()" );
        data = FXCollections.observableArrayList();
        BDConnector connector = new BDConnector(); //static connection  - 1 для всех
        try (
                PreparedStatement pst = connector.connection.prepareStatement(
                        "SELECT * FROM public.region order by id");
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Region reg = new Region(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3));
                data.add(reg);
            }
            tableView.setItems(data);
        } catch (SQLException ex) {
            System.out.println("Failed to SELECT data");
            return;
        }
    }

    public void editRow(ActionEvent actionEvent) {
        //Редагувати = скопіювати дані з таблиці в поля під нею
        Region reg = tableView.getSelectionModel().getSelectedItem();
        if (reg==null){
            info("Помилка","Не обрано рядок","Спочатку оберіть рядок в таблиці.");
            return;
        }
        idField.setText(reg.getId().toString());
        kodField.setText(reg.getKod());
        nameField.setText(reg.getName());
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
        Region r1 = new Region(Integer.parseInt(idField.getText()),
                kodField.getText(),
                nameField.getText());
        String query = "UPDATE public.region SET kod=?, name=? WHERE id= ?";

        try ( PreparedStatement pst = connector.connection.prepareStatement(query)) {
            pst.setString(1, r1.getKod());
            pst.setString(2, r1.getName());
            pst.setInt(3, r1.getId());
            pst.executeUpdate(); //no  insert
        } catch (SQLException ex) {
            System.out.println("Failed to UPDATE data");
            return;
        }
        idField.setText("");
        kodField.setText("");
        nameField.setText("");
        initialize();
    }

    public void deleteRow(ActionEvent actionEvent) {
        //Видалити
        Region reg = tableView.getSelectionModel().getSelectedItem();
        if (reg==null){
            info("Помилка","Не обрано рядок","Спочатку оберіть рядок в таблиці.");
            return;
        }
        idField.setText(reg.getId().toString());

        BDConnector connector = new BDConnector(); //static connection  - 1 для всех
        Region r1 = new Region(Integer.parseInt(idField.getText()),
                kodField.getText(),
                nameField.getText());
        String query = "DELETE FROM  public.region WHERE id= ?";

        try ( PreparedStatement pst = connector.connection.prepareStatement(query)) {
            pst.setInt(1, r1.getId());
            pst.executeUpdate(); //no  insert
        } catch (SQLException ex) {
            System.out.println("Failed to DELETE data");
            return;
        }
        initialize();
    }
}
