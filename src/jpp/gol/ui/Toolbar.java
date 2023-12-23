package jpp.gol.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;

import javafx.scene.control.Button;
import javafx.scene.transform.Affine;
import javafx.stage.FileChooser;
import jpp.gol.logic.StandardGameLogic;
import jpp.gol.model.World;
import jpp.gol.rules.StandardRules;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Toolbar extends ToolBar {
    public static String duration;
    private MainView mainView;
    public static RunAnimation runAnimation;
    TextField input;

    public Toolbar(MainView mainView){
        this.duration="0.5";
        this.mainView = mainView;
        this.runAnimation=new RunAnimation(this.mainView);
        this.input = new TextField("<width>,<height>");
        Button enter= new Button("Enter");
        enter.setOnAction(this::handleEnter);
        Button load = new Button("Load");
        load.setOnAction(this::handleLoad);
        Button start = new Button("Start");
        start.setOnAction(this::handleStart);
        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);
        Button step = new Button("Step");
        step.setOnAction(this::handleStep);
        Button schneller = new Button("schneller");
        schneller.setOnAction(this::handleSchneller);
        Button langsamer = new Button("langsamer");
        langsamer.setOnAction(this::handleLangsamer);
        Button exit = new Button("Exit");
        exit.setOnAction(event -> handleExit());

        this.getItems().addAll(input,enter,load,start,stop,step,schneller,langsamer,exit);
    }

    private void handleLangsamer(ActionEvent actionEvent) {
        if(mainView.applicationState==1){
            FalscheEingabe.display("Fehler","Game should be paused");
        }else {
            switch (duration) {
                case "0.1" -> {
                    this.duration = "0.2";

                }
                case "0.2" -> {
                    this.duration = "0.3";
                }
                case "0.3" -> {
                    this.duration = "0.4";
                }
                case "0.4" -> {
                    this.duration = "0.5";
                }
                case "0.5" -> {
                    this.duration = "0.6";
                }
                case "0.6" -> {
                    this.duration = "0.7";
                }
                case "0.7" -> {
                    this.duration = "0.8";
                }
                case "0.8" -> {
                    this.duration = "0.9";
                }
                case "0.9" -> {
                    this.duration = "1.0";
                }
                case "1.0" -> {
                    this.duration = "1.1";
                }
                case "1.1" -> {
                    this.duration = "1.2";
                }
                case "1.2" -> {
                    this.duration = "1.3";
                }
                case "1.3" -> {
                    this.duration = "1.4";
                }
                case "1.4" -> {
                    this.duration = "1.5";
                }
                case "1.5" -> {
                    this.duration = "1.6";
                }
                case "1.6" -> {
                    this.duration = "1.7";
                }
                case "1.7" -> {
                    this.duration = "1.8";
                }
                case "1.8" -> {
                    this.duration = "1.9";
                }
                case "1.9" -> {
                    this.duration = "2.0";
                }
                default -> {
                    FalscheEingabe.display("Fehler", "Geht nicht langsamer");
                }
            }
        }
        mainView.infobox.setvelocity(duration);
        runAnimation.changeduration(duration);
    }

    private void handleSchneller(ActionEvent actionEvent) {
        if(mainView.applicationState==1){
            FalscheEingabe.display("Fehler","Game should be paused");
        }else{
            switch (duration) {
                case "0.2" -> {
                    this.duration = "0.1";
                }
                case "0.3" -> {
                    this.duration = "0.2";
                }
                case "0.4" -> {
                    this.duration = "0.3";
                }
                case "0.5" -> {
                    this.duration = "0.4";
                }
                case "0.6" -> {
                    this.duration = "0.5";
                }
                case "0.7" -> {
                    this.duration = "0.6";
                }
                case "0.8" -> {
                    this.duration = "0.7";
                }
                case "0.9" -> {
                    this.duration = "0.8";
                }
                case "1.0" -> {
                    this.duration = "0.9";
                }
                case "1.1" -> {
                    this.duration = "1.0";
                }
                case "1.2" -> {
                    this.duration = "1.1";
                }
                case "1.3" -> {
                    this.duration = "1.2";
                }
                case "1.4" -> {
                    this.duration = "1.3";
                }
                case "1.5" -> {
                    this.duration = "1.4";
                }
                case "1.6" -> {
                    this.duration = "1.5";
                }
                case "1.7" -> {
                    this.duration = "1.6";
                }
                case "1.8" -> {
                    this.duration = "1.7";
                }
                case "1.9" -> {
                    this.duration = "1.8";
                }
                case "2.0" -> {
                    this.duration = "1.9";
                }
                default -> {
                    FalscheEingabe.display("Fehler", "Geht nicht schneller");
                }
            }
        }
        mainView.infobox.setvelocity(duration);
        runAnimation.changeduration(duration);
    }

    private void handleLoad(ActionEvent actionEvent) {
        if(mainView.applicationState==1){
            return;
        }
        FileChooser chooser = new FileChooser();
        File f = chooser.showOpenDialog(null);
        try {
            InputStream inputStream = new FileInputStream(f);

            World test =MainView.standardWorldLoader.load(inputStream);
            MainView.width=test.getWidth();
            MainView.height=test.getHeight();
            MainView.world= test;
            MainView.rules = new StandardRules();
            MainView.standardGameLogic = new StandardGameLogic(MainView.world,MainView.rules);
            MainView.affine=new Affine();
            mainView.canvasnewscale(MainView.width,MainView.height);
            mainView.draw();
        } catch (Exception e) {
            FalscheEingabe.display("Fehler","Datei hat falsches Format.");
        }
    }

    private void handleEnter(ActionEvent actionEvent) {
        if(mainView.applicationState==1){
            return;
        }
        String regex = "^\\d(\\d*),\\d(\\d*)$";
        String dimensions = this.input.getText();
        try
        {

            //funktionert nicht mit regex

            if(!dimensions.matches(regex)){
                System.out.println(dimensions);
                throw new Exception();
            }
            String[] coordinates = dimensions.split(",", 2);
            int weite = Integer.parseInt(coordinates[0]);
            int breite = Integer.parseInt(coordinates[1]);
            MainView.width =weite; //15
            MainView.height =breite; //14
            MainView.world=new World(weite,breite);
            MainView.rules = new StandardRules();
            MainView.standardGameLogic = new StandardGameLogic(MainView.world,MainView.rules);
            MainView.affine=new Affine();
            mainView.canvasnewscale(weite,breite);
            mainView.draw();
        } catch (Exception e){
            FalscheEingabe.display("Fehler","Eingabe hat falsches Format.");
        }

    }

    public void handleExit() {
        runAnimation.stop();
        GameOfLife.primaryStage.close();
    }


    private void handleStop(ActionEvent actionEvent) {
        mainView.applicationState=0;
        mainView.infobox.setState("0");
        runAnimation.stop();
    }

    private void handleStart(ActionEvent actionEvent) {
        mainView.applicationState=1;
        mainView.infobox.setState("1");
        runAnimation.start();
        this.mainView.draw();
    }

    private void handleStep(ActionEvent actionEvent) {
        if(mainView.applicationState==1){
            FalscheEingabe.display("Fehler","Game should be paused");
        }else{
            System.out.println("p");
            this.mainView.getSimulation().step();
            this.mainView.draw();
        }

    }
}
