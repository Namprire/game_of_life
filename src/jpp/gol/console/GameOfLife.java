package jpp.gol.console;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import jpp.gol.io.StandardWorldLoader;
import jpp.gol.logic.GameLogic;
import jpp.gol.logic.ObservableGameLogicDecorator;
import jpp.gol.logic.StandardGameLogic;
import jpp.gol.model.CellState;
import jpp.gol.model.World;
import jpp.gol.rules.Rules;
import jpp.gol.rules.StandardRules;

import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class GameOfLife  {
    StandardGameLogic standardGameLogic;
    StandardRules rules;
    ObservableGameLogicDecorator observableGameLogicDecorator;
    public StandardWorldLoader standardWorldLoader= new StandardWorldLoader();
    public World world;
    LeaveRequestState state = LeaveRequestState.ONE;
    int height = 1;
    int width = 1;
    boolean runnning = true;

    public void run(InputStream in, OutputStream out) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            PrintStream ps = new PrintStream(out);

            while (runnning) {
                executestate(this.state, br, ps);
            }
        } catch (Exception e) {
            throw new IllegalConsoleException("irgendwas ist schiefgelaufen");
        }
    }

    public void executestate(LeaveRequestState state, BufferedReader br, PrintStream ps) {

        switch (state) {
            case ONE -> {
                ps.println("Willkommen zu Game of Life.");
                this.state = LeaveRequestState.TWO;
            }
            case TWO -> {
                ps.println("Moechten Sie die Welt aus einer [D]atei laden, oder selbst [k]onfigurieren?");
                try {
                    String line = br.readLine();
                    switch (line) {
                        case "D" -> {
                            this.state = LeaveRequestState.THREE;
                        }
                        case "k" -> {
                            this.state = LeaveRequestState.FOUR;
                        }
                        default -> ps.println("Ungueltige Aktion! Es sind nur die beiden Aktionen k und D zulaessig.");
                    }
                } catch (Exception e) {
                    throw new IllegalConsoleException("");
                }
            }
            case THREE -> {
                try {
                    ps.println("Geben Sie den Pfad zur Datei ein:");
                    String line = br.readLine();
                    try {



//                        Scanner s = new Scanner(inputStream).useDelimiter("\\A"); //nur schauen ob die datei auch gefunden wurde
//                        String input = s.hasNext() ? s.next() : "";
//                        ps.println(input);


                        File world = new File(line);
                        InputStream inputStream = new FileInputStream(world);

                        this.world = standardWorldLoader.load(inputStream);
                        this.rules=new StandardRules();
                        this.standardGameLogic=new StandardGameLogic(this.world,rules);
                        this.state = LeaveRequestState.NINE;


                    } catch (Exception e) {
                        ps.println("Ungueltiger Dateipfad oder der Inhalt der Datei ist inkorrekt.");
                        this.state = LeaveRequestState.TWO;
                    }
                } catch (Exception e) {
                    throw new IllegalConsoleException("");
                }
            }
            case FOUR -> {
                try {
                    int value;
                    String regex = "^[1-9]\\d*$";
                    ps.println("Geben Sie die Hoehe der Welt ein:");
                    String heightinput;
                    heightinput = br.readLine();
                    if(heightinput.matches(regex)){
                        value = Integer.parseInt(heightinput);
                        height = value;
                        this.state = LeaveRequestState.FIVE;
                    }else{
                        ps.println("Ungueltige Hoehe.");
                    }
                } catch (Exception e) {
                    throw new IllegalConsoleException("");
                }
            }
            case FIVE -> {
                try {
                    int value;
                    String regex = "^[1-9]\\d*$";
                    ps.println("Geben Sie die Breite der Welt ein:");
                    String widthinput;
                    widthinput = br.readLine();
                    if(widthinput.matches(regex)){
                        value = Integer.parseInt(widthinput);
                        width = value;
                        this.world = new World(width, height);
                        this.rules=new StandardRules();
                        this.standardGameLogic=new StandardGameLogic(world,rules);
                        this.state = LeaveRequestState.SIX;
                    }else{
                        ps.println("Ungueltige Breite.");
                        this.state = LeaveRequestState.FIVE;
                    }
                } catch (Exception e) {
                    throw new IllegalConsoleException("");
                }
            }
            case SIX -> {

                ps.println('\n' + world.toString() + '\n');
                this.state = LeaveRequestState.SEVEN;
            }
            case SEVEN -> {
                ps.println("Moechten Sie ein Feld veraendern? (Ja/Nein)");
                try {
                    String line = br.readLine();
                    switch (line) {
                        case "Ja" -> {
                            this.state = LeaveRequestState.EIGHT;
                        }
                        case "Nein" -> {
                            this.state = LeaveRequestState.NINE;
                        }
                        default -> ps.println("Ungueltige Eingabe.");
                    }
                } catch (Exception e) {
                    throw new IllegalConsoleException("");
                }
            }
            case EIGHT -> {
                try {
                    int x;
                    int y;
                    //splitte coordinates mit string split limiter bei ','
                    //array mit zwei elementen und pruefe ob jeweils beide parsabel sind
                    String regex = "^\\d(\\d*),\\d(\\d*)$";
                    ps.println("Geben Sie die x- und y-Koordinaten im Format <x>,<y> des zu aendernden Feldes ein:");
                    String coordinate;
                    while ((coordinate = br.readLine()) != null) {

                        try
                        { String[] coordinates = coordinate.split(",", 2);
                            x = Integer.parseInt(coordinates[0]);
                            y = Integer.parseInt(coordinates[1]);
//todo try catch um nummer zu gross als hoehe oder breite
                            //Sind die Koordinaten gültig, wird das Feld aktualisiert, anschließend zurück zu 6.
                            CellState current = this.world.get(x, y);
                            CellState inverted = current.invert();
                            this.world.set(x, y, inverted);

                        } catch (Exception e){
                            ps.println("Ungueltige Koordinaten!");
                        }






//                        if (coordinate.matches(regex)) {
//
//                            String[] coordinates = coordinate.split(",", 2);
//                            x = Integer.parseInt(coordinates[0]);
//                            y = Integer.parseInt(coordinates[1]);
////todo try catch um nummer zu gross als hoehe oder breite
//                            //Sind die Koordinaten gültig, wird das Feld aktualisiert, anschließend zurück zu 6.
//                            CellState current = this.world.get(x, y);
//                            CellState inverted = current.invert();
//                            this.world.set(x, y, inverted);
//                        } else {
//                            ps.println("Ungueltige Koordinaten!");
//                        }
                        this.state = LeaveRequestState.SIX;
                        break;
                    }
                } catch (Exception e) {
                    throw new IllegalConsoleException("");
                }
            }
            case NINE -> {
                ps.println();
                this.state = LeaveRequestState.TEN;
            }
            case TEN -> {
                ps.println("Spiel wird gestartet.");
                this.state = LeaveRequestState.ELEVEN;
            }
            case ELEVEN -> {
                //todo was bedeutet hier "\n<Inhalt von World.toString()>\n"
                ps.println('\n' + world.toString() + '\n');
                this.state = LeaveRequestState.TWELVE;
            }
            case TWELVE -> {
                ps.println("Soll die naechste [I]teration berechnet werden, oder das Spiel [b]eendet werden?");
                try {
                    String line = br.readLine();
                    switch (line) {
                        case "I" -> {
                            this.standardGameLogic.step();
                            this.state = LeaveRequestState.ELEVEN;
                        }
                        case "b" -> {
                            this.state = LeaveRequestState.THIRTEEN;
                        }
                        default -> ps.println("Ungueltige Aktion. Es sind nur die Aktionen I und b zulaessig.");
                    }
                } catch (Exception e) {
                    throw new IllegalConsoleException("");
                }
            }
            case THIRTEEN -> {
                ps.println("Auf Wiedersehen!");
                this.state = LeaveRequestState.FOURTEEN;
            }
            case FOURTEEN -> runnning = false;
        }

    }


    public static void main(String[] args) {
        new GameOfLife().run(System.in, System.out);

    }

}
