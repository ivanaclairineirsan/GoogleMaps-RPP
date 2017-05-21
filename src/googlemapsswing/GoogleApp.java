/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlemapsswing;

/**
 *
 * @author Ivana Clairine
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;

public class GoogleApp extends Application {


    private Scene scene;
    MyBrowser myBrowser;
    double lat;
    double lon;
    private Stage stage;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;


        myBrowser = new MyBrowser();
        Scene scene = new Scene(myBrowser);

        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }

    //stuff and stuff

    class MyBrowser extends Pane {


        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();


        public MyBrowser() {


            final URL urlGoogleMaps = getClass().getResource("demo.html");
            webEngine.load(urlGoogleMaps.toExternalForm());
            webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
                @Override
                public void handle(WebEvent<String> e) {
                    System.out.println(e.toString());
                }
            });

            getChildren().add(webView);

            final TextField latitude = new TextField("" + -6.883384);
            final TextField longitude = new TextField("" + 107.619109);
            Button update = new Button("Update");
            update.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    lat = Double.parseDouble(latitude.getText());
                    lon = Double.parseDouble(longitude.getText());

                    System.out.printf("%.2f %.2f%n", lat, lon);

                    webEngine.executeScript("" +
                        "window.lat = " + lat + ";" +
                        "window.lon = " + lon + ";" +
                        "document.goToLocation(window.lat, window.lon);"
                    );
                }
            });

            HBox toolbar  = new HBox();
            toolbar.getChildren().addAll(latitude, longitude, update);

            getChildren().addAll(toolbar);

        }
    }
    
    public void readFile() {
        
    }

}
