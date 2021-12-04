package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {
    @FXML private TextField misyacField1;
    @FXML private TextField rikField1;

    @FXML private TextField misyacField2;
    @FXML private TextField rikField2;

    @FXML private ObservableList<Zvit1> data;
    @FXML private ObservableList<Region> region_data;
    @FXML private ObservableList<Vid> vid_data;
    @FXML private ComboBox regionCombo;
    @FXML private ComboBox vidCombo;

    @FXML private TableView<Zvit1> tableView;



    public void handleAboutAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message Here...");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
              //  System.out.println("Pressed OK.");
            }
        });
    }

    public void handleKeyInput(KeyEvent keyEvent) {
    }

    public MenuItem menuExit;
    public void handleExitAction(ActionEvent actionEvent) {
        //
        Platform.exit();
    }

    public void handleRegionAction(ActionEvent actionEvent) {
        //Довідник - Регіон
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("region.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Регіони");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void handleVidAction(ActionEvent actionEvent) {
        //Довідник - Види надходжень
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("vid.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Види надходжень");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void handleDohodAction(ActionEvent actionEvent) {
        //Довідник - Доход
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dohod.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Доходи");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void sumVyd (ActionEvent actionEvent) {
        //function - vyvid nadchodzen po vydach nadchodzen
        Zvit1 zvitSum = new Zvit1 (0.0, "Razom", "");
        double s = 0.0;
        data = FXCollections.observableArrayList();
        BDConnector connector = new BDConnector(); //static connection  - 1 для всех
        Region r1cb = (Region)regionCombo.getValue();
        int mr1=Integer.parseInt("0"+rikField1.getText())*100+Integer.parseInt("0"+misyacField1.getText());
        int mr2=Integer.parseInt("0"+rikField2.getText())*100+Integer.parseInt("0"+misyacField2.getText());
        if (mr2==0) {
            mr2=9999*100+12;
        }
        String query = "SELECT distinct v.kod, v.name, sum (d.grn) AS grn\n" +
                "FROM public.dohod d, public.vid v\n" +
                "WHERE d.vid_id=v.id \n" +
                "AND d.rik*100+d.misyac BETWEEN ? AND ? \n" +
                "AND d.region_id=?\n" +
                "GROUP BY v.kod, v.name";

        try ( PreparedStatement pst = connector.connection.prepareStatement(query)) {
            pst.setInt(1, mr1);
            pst.setInt(2, mr2);
            pst.setInt(3, r1cb.getId());



                ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Zvit1 reg = new Zvit1(
                        rs.getDouble(3),
                        rs.getString(1),
                        rs.getString(2)
                        );
                data.add(reg);
                s+=reg.getGrn();
            }
            zvitSum.setGrn(s);
            data.add(zvitSum);
            tableView.setItems(data);
        } catch (SQLException ex) {
            System.out.println("Failed to SELECT data");
            return;
        }

    }

    public void sumRegion (ActionEvent actionEvent) {
        //function - vyvid nadchodzen po regionach
        Zvit1 zvitSum = new Zvit1 (0.0, "Razom", "");
        double s = 0.0;
        data = FXCollections.observableArrayList();
        BDConnector connector = new BDConnector(); //static connection  - 1 для всех
        Vid r1cb = (Vid)vidCombo.getValue();
        int mr1=Integer.parseInt("0"+rikField1.getText())*100+Integer.parseInt("0"+misyacField1.getText());
        int mr2=Integer.parseInt("0"+rikField2.getText())*100+Integer.parseInt("0"+misyacField2.getText());
        if (mr2==0) {
            mr2=9999*100+12;
        }
        String query = "SELECT distinct r.kod, r.name, sum (d.grn) AS grn\n" +
                "FROM public.dohod d, public.region r\n" +
                "WHERE d.region_id=r.id \n" +
                "AND d.rik*100+d.misyac BETWEEN ? AND ? \n" +
                "AND d.vid_id=?\n" +
                "GROUP BY r.kod, r.name";

        try ( PreparedStatement pst = connector.connection.prepareStatement(query)) {
            pst.setInt(1, mr1);
            pst.setInt(2, mr2);
            pst.setInt(3, r1cb.getId());


            //  (double grn1, String code1, String name1)

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Zvit1 reg = new Zvit1(
                        rs.getDouble(3),
                        rs.getString(1),
                        rs.getString(2)
                );
                s+=reg.getGrn();
                data.add(reg);
            }
            zvitSum.setGrn(s);
            data.add(zvitSum);
            tableView.setItems(data);
        } catch (SQLException ex) {
            System.out.println("Failed to SELECT data");
            return;
        }

    }



    @FXML
    public void initialize() {
        Region reg = null;
        Vid vid = null;
        BDConnector connector = new BDConnector(); //static connection  - 1 для всех

        //region
        region_data = FXCollections.observableArrayList();
        try (
                PreparedStatement pst = connector.connection.prepareStatement(
                        "SELECT * FROM public.region order by id");
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                reg = new Region(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3));
                region_data.add(reg);
            }
            regionCombo.setItems(region_data);

            regionCombo.setValue(reg);
            //ustanavl po umolchaniyu
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
                vid = new Vid(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3));
                vid_data.add(vid);
            }
            vidCombo.setItems(vid_data);
            vidCombo.setValue(vid);
                 } catch (SQLException ex) {
            System.out.println("Failed to SELECT data");
            return;
        }
    }
}
