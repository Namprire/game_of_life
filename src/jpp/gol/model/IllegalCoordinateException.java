package jpp.gol.model;

public class IllegalCoordinateException extends RuntimeException {

    public IllegalCoordinateException(String message) {
//public IllegalCoordinateException(String message)
//Erstellt eine IllegalCoordinateException mit der Nachricht message.
// Delegieren Sie an den Konstruktor der Oberklasse RuntimeException.
        super(message);
    }
}
