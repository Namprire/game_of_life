package jpp.gol.console;

public class IllegalConsoleException extends RuntimeException {
    public IllegalConsoleException(String message) {
//Erstellt eine IllegalWorldSizeException mit der Nachricht message.
// Delegieren Sie an den Konstruktor der Oberklasse RuntimeException.
        super(message);
    }
}
