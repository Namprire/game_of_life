package jpp.gol.rules;

import jpp.gol.model.CellState;

public class StandardRules implements Rules {

    @Override
    public CellState nextState(int numberOfNeighbors, CellState currentValue) {
        if(numberOfNeighbors<0){
            throw new IllegalNumberOfNeighborsException("Anzahl an Nachbarn kann nicht negativ sein");
        }
        if(numberOfNeighbors>8){
            throw new IllegalNumberOfNeighborsException("Anzahl an Nachbarn kann nicht groesser als 8 sein");
        }
        //1 Eine tote Zelle mit genau drei lebenden Nachbarn wird in der Folgegeneration neu geboren.
        if(currentValue==CellState.DEAD&&numberOfNeighbors==3){
            return currentValue.invert();
        }
        //2 Lebende Zellen mit weniger als zwei lebenden Nachbarn sterben in der Folgegeneration an Einsamkeit.
        if(currentValue==CellState.ALIVE&&numberOfNeighbors<2){
            return currentValue.invert();
        }
        //3 Eine lebende Zelle mit zwei oder drei lebenden Nachbarn bleibt in der Folgegeneration lebend.
        if(currentValue==CellState.ALIVE&&(numberOfNeighbors==3||numberOfNeighbors==2)){
            return currentValue;
        }
        //4 Lebende Zellen mit mehr als drei lebenden Nachbarn sterben in der Folgegeneration an Überbevölkerung.
        if(currentValue==CellState.ALIVE&&numberOfNeighbors>3){
            return currentValue.invert();
        }
        //Sollte numberOfNeighbors unerwartete Werte annehmen, werfen Sie eine IllegalArgumentException mit aussagekräftiger Nachricht.
        return currentValue;
    }
}
