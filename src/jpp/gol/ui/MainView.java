package jpp.gol.ui;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import jpp.gol.io.StandardWorldLoader;
import jpp.gol.logic.ObservableGameLogicDecorator;
import jpp.gol.logic.StandardGameLogic;
import jpp.gol.model.CellState;
import jpp.gol.model.World;
import jpp.gol.rules.StandardRules;

public class MainView extends VBox {
public static double canvasheight;
    public static double canvaswidht;
    public static Infobox infobox;

    public static int height;
    public static int width;
    public static final int PAUSED = 0;
    public static final int RUNNING = 1;
    public int applicationState;
    //public static Canvas canvas;
    public static Canvas canvas;
    public static Pane pane;

    public static World world;
    public static StandardGameLogic standardGameLogic;
    public static StandardRules rules;
    public static StandardWorldLoader standardWorldLoader= new StandardWorldLoader();
    public static  Affine affine;
    public static Toolbar toolbar;

    public MainView(){

        this.width=10;
        this.height=10;
        this.applicationState=PAUSED;
        this.world = new World(width,height);
        this.rules=new StandardRules();
        this.standardGameLogic=new StandardGameLogic(world,rules);
        this.canvaswidht = 650;
        this.canvasheight = 650;
        this.canvas = new Canvas(canvaswidht,canvasheight);
        toolbar = new Toolbar(this);
        this.infobox=new Infobox();
        infobox.setState("0");
        infobox.setvelocity("0.5");

//Platzfueller zwischen canvas und infobox
        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        VBox.setVgrow(spacer,Priority.ALWAYS);

        this.canvas.setOnMousePressed(this::handleDraw);
        this.pane = new Pane();
        pane.getChildren().add(canvas);

/**versuch das layout mit borderpane and this zu binden damit alles gefuellt wird, funktioniert allerdings nicht **/
//        BorderPane borderPane = new BorderPane();
//        borderPane.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
//        borderPane.setPadding(new Insets(5));
//        toolbar.setPrefHeight(50);
//        borderPane.setTop(toolbar);
//
//        pane.setMinWidth(600);
//        pane.setPrefSize(650,650);
//        pane.setMaxSize(1000,1000);
//        BorderPane.setAlignment(pane, Pos.TOP_CENTER);
//        borderPane.setCenter(pane);
//
//        borderPane.setBottom(infobox);
//
//
//
//
//
//        this.getChildren().addAll(borderPane);


        this.getChildren().addAll(toolbar,pane,spacer,infobox);

        this.affine = new Affine();
        canvasnewscale(width,height);

    }
    public void canvasnewscale(int width,int height){
        this.affine.appendScale(canvaswidht/width,canvasheight/height);
    }


    private void handleDraw(MouseEvent event) {

        if(this.applicationState==RUNNING){
            return;
        }

        double mouseX = event.getX();
        double mouseY = event.getY();


        try{
            Point2D simcord = this.affine.inverseTransform(mouseX,mouseY);
            int simX = (int) simcord.getX();
            int simY = (int) simcord.getY();


            this.standardGameLogic.changeState(simX,simY);
            draw();
        } catch (Exception e){
            System.out.println("");
        }

    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        draw();
    }

    public void draw(){
// nur zum testen
//         this.world.set(1,2,CellState.ALIVE);
//         this.world.set(1,3,CellState.ALIVE);
//         this.world.set(1,4,CellState.ALIVE);
        GraphicsContext g =this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);
        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, 450, 450);
        g.setFill(Color.BLACK);
         for (int x = 0; x < this.standardGameLogic.getWorld().getWidth(); x++) {
             for (int y = 0; y < this.standardGameLogic.getWorld().getHeight(); y++) {
                 if(this.world.get(x,y)== CellState.ALIVE){
                     this.standardGameLogic.getWorld().set(x,y,CellState.ALIVE);
                     g.fillRect(x,y,1,1);
                 }

             }
         }
         g.setStroke(Color.GRAY);
         g.setLineWidth(0.05);

         for (int x = 0; x <= this.standardGameLogic.getWorld().getWidth() ; x++) { //senkrecht
             g.strokeLine(x,0,x,this.standardGameLogic.getWorld().getHeight());
         }


         for (int y = 0; y <= this.standardGameLogic.getWorld().getHeight(); y++) { //waagerecht
             g.strokeLine(0,y,this.standardGameLogic.getWorld().getWidth(),y);
         }

         
     }

    public StandardGameLogic getSimulation() {
        return this.standardGameLogic;
    }

}
