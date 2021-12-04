package sample;
//https://javadevblog.com/kak-dobavit-biblioteku-jar-fajl-v-proekt-intellij-idea.html
// як додати jar

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    BDConnector connector;
    @Override
    public void start(Stage primaryStage) throws Exception{
        connector = new BDConnector();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Державне казначейство України");
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
    }
    @Override
    public void stop() {
        if (connector != null) connector.DisConnect();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
