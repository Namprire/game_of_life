package jpp.gol.logic;

import jpp.gol.model.World;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ObservableGameLogicDecorator implements GameLogic {
    GameLogic delegate;
    Set<WorldChangedListener> listeners = new HashSet<>();

    public ObservableGameLogicDecorator(GameLogic delegate) {
//Initialisiert eine neue Instanz, die alle Aufrufe an delegate weiterleitet!
// Hat der Parameter delegate den Wert null, werfen Sie eine NullPointerException.
    if(delegate==null){
    throw new NullPointerException();
    }
    this.delegate=delegate;
    }

    public void addWorldChangedListener(WorldChangedListener listener) {
//Registriert den Listener, sodass er in Zukunft über Änderungen an der Welt
// informiert wird. Jeder Listener darf nur ein Mal registriert werden.
        if(listener==null){
            throw new NullPointerException();
        }
        this.listeners.add(listener);
    }

    public void removeWorldChangedListener(WorldChangedListener listener) {
//Entfernt einen Listener, sodass er in Zukunft nicht mehr über
// Änderungen an der Welt informiert wird.
        if(listener==null){
            throw new NullPointerException();
        }
        if(!listeners.contains(listener)){
            throw new IllegalArgumentException("listener is not registered");
        }
        this.listeners.remove(listener);
    }

//Leiten Sie alle Aufrufe der in der Schnittstelle GameLogic definierten
// Methoden an die im Konstruktor übergebene Instanz von GameLogic weiter.
// Handelt es sich um eine Methode, welche das Spielfeld verändert,
// teilen Sie anschließend allen Beobachtern mit, dass sich das Spielfeld verändert hat.

    @Override
    public void step() {
        delegate.step();
        for (WorldChangedListener listener:listeners) {
            listener.onChange(delegate.getWorld());
        }
    }

    @Override
    public void setWorld(World world) {
        delegate.setWorld(world);
        for (WorldChangedListener listener:listeners) {
            listener.onChange(delegate.getWorld());
        }
    }

    @Override
    public World getWorld() {
        return delegate.getWorld();
    }

    @Override
    public void changeState(int x, int y) {
        delegate.changeState(x,y);
        //listener current world
        for (WorldChangedListener listener:listeners) {
            listener.onChange(delegate.getWorld());
        }
    }
}
