package jpp.gol.rules;

public class IllegalNumberOfNeighborsException extends RuntimeException {

    public IllegalNumberOfNeighborsException(String message) {
        //Erstellt eine IllegalNumberOfNeighborsException mit der Nachricht message.
        super(message);
    }
}
