package jpp.gol.logic;

import jpp.gol.model.CellState;
import jpp.gol.model.World;
import jpp.gol.rules.Rules;

public class StandardGameLogic implements GameLogic {
public World world;
public Rules rules;
    public StandardGameLogic(World world, Rules rules) {
//Initialisiert eine neue Instanz mit einer Welt und den zu
// verwendenden Regeln. Hat einer der beiden Parameter world
// und rules den Wert null, werfen Sie eine NullPointerException.
        if(world==null||rules==null){
            throw new NullPointerException();
        }
        this.world=world;
        this.rules=rules;
    }

    @Override
    public void step() {
//Berechnet die nächste Iteration des Spielfeldes.
// Dazu werden die im Konstruktor gesetzten Regeln verwendet.

        //erstelle eine kopie der welt
        //betrachte zelle isoliert um den naechsten step zu berechnen und zurueckzugeben
        World copy= new World(world);
        int width= world.getWidth();
        int height= world.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                CellState currentcell=copy.get(i,j);
                int neighbours=copy.countNeighbors(i,j);
                world.set(i,j,rules.nextState(neighbours,currentcell));
            }
        }
    }

    @Override
    public void setWorld(World world) {
        this.world=world;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void changeState(int x, int y) {
        CellState currentcell=world.get(x,y);
        world.set(x,y,currentcell.invert());
    }
}
