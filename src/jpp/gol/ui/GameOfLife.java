package jpp.gol.ui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class GameOfLife extends Application {
    public static Scene scene;
    public MainView mainView;
    public static Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.mainView = new MainView();
        this.scene = new Scene(mainView, 700, 730);
        this.primaryStage=primaryStage;

        //Eigentlich resizen des canvases mit listener, allerdings ist der neue canvas in klasse mainview weshalb dieser nicht
        // im jetzigen scene neue geladen werden kann, da mainview von der klasse Scene gekapselt wird.
        //eigentlich muesste man dann eine neue Scene erstellen und sie dann primarystage geben.

//        scene.widthProperty().addListener(observable -> redraw());
//        scene.heightProperty().addListener(observable -> redraw());

        primaryStage.setOnCloseRequest(e ->mainView.toolbar.handleExit());
        //oben, beendet auch beim fenster schliessen alle java prozesse.
        primaryStage.setScene(scene);

        primaryStage.show();
        mainView.draw();
    }

    private void redraw() {
        MainView.canvasheight= scene.getHeight()-50;
        MainView.canvaswidht= scene.getWidth()-50;
        //test
        System.out.println(MainView.canvasheight);

        //MainView.canvas=new Canvas(MainView.canvaswidht,MainView.canvasheight);
        MainView.canvas=new Canvas(MainView.canvaswidht,MainView.canvasheight);

        MainView.affine.appendScale(MainView.canvaswidht/MainView.width,MainView.canvasheight/MainView.height);
       // scene = new Scene(mainView,scene.getWidth(),scene.getHeight());
        this.mainView.draw();

    }


}


