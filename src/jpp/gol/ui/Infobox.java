package jpp.gol.ui;

import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class Infobox extends HBox {

    private static String ge = "Spielgeschwindigkeit: %s";
    private static String rors = "State: %s";

    public static Label state;
    public static Label velocity;

    public Infobox(){
        this.state = new Label();
        this.velocity = new Label();

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(this.state,spacer,this.velocity);
    }

    public void setvelocity (String geschwindigkeit){
        this.velocity.setText(String.format(ge,geschwindigkeit));
    }

    public void setState(String mode){
        this.state.setText(String.format(rors,mode));
    }
}
