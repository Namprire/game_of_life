package jpp.gol.ui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FalscheEingabe {
    public static void display(String title, String message){
        Label secondLabel = new Label(message);

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 230, 100);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle(title);
        newWindow.setScene(secondScene);

        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(GameOfLife.primaryStage);

        // Set position of second window, related to primary window.
        newWindow.setX(GameOfLife.primaryStage.getX() + 200);
        newWindow.setY(GameOfLife.primaryStage.getY() + 100);
        newWindow.showAndWait();
        //newWindow.show();
    }
}
