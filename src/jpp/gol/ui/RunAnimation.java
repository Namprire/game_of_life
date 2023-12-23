package jpp.gol.ui;

import javafx.animation.AnimationTimer;

import java.util.concurrent.TimeUnit;

public class RunAnimation extends AnimationTimer {
    public MainView mainView;
    private long lastUpdate = 0;
    private long duration = 500;

    public RunAnimation(MainView mainView){
        this.mainView=mainView;
    }

    public void changeduration(String duration) {
        switch ( duration){
            case "0.1"->{this.duration=100;}
            case "0.2"->{this.duration=200;}
            case "0.3"->{this.duration=300;}
            case "0.4"->{this.duration=400;}
            case "0.5"->{this.duration=500;}
            case "0.6"->{this.duration=600;}
            case "0.7"->{this.duration=700;}
            case "0.8"->{this.duration=800;}
            case "0.9"->{this.duration=900;}
            case "1.0"->{this.duration=1000;}
            case "1.1"->{this.duration=1100;}
            case "1.2"->{this.duration=1200;}
            case "1.3"->{this.duration=1300;}
            case "1.4"->{this.duration=1400;}
            case "1.5"->{this.duration=1500;}
            case "1.6"->{this.duration=1600;}
            case "1.7"->{this.duration=1700;}
            case "1.8"->{this.duration=1800;}
            case "1.9"->{this.duration=1900;}
            case "2.0"->{this.duration=2000;}
            default -> {
                System.out.println("irgendwas funk net");;
            }
        }

    }

    @Override
        public void handle(long now) {
            // only update once every second
            if ((now - lastUpdate) >= TimeUnit.MILLISECONDS.toNanos(duration)) {
                mainView.getSimulation().step();
                mainView.draw();
                lastUpdate = now;
            }
        }

}
