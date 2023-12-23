package jpp.gol.model;

public enum CellState {
    DEAD{
        @Override
        public CellState invert() {
            return ALIVE;
        }
    },
    ALIVE{
        @Override
        public CellState invert() {
            return DEAD;
        }
    };

    public static CellState fromBoolean(boolean b) {
        if(b){
            return ALIVE;
        }else{
            return DEAD;
        }
    }

    public CellState invert() {
        //Gibt den jeweils anderen CellState zurück.
        // Geben Sie DEAD beim Aufruf von CellState.ALIVE.invert() zurück und ALIVE bei CellState.DEAD.invert().
        if(this==ALIVE){
            return DEAD;
        } else {
            return ALIVE;
        }
    }

    @Override
    public String toString() {
        if (this == ALIVE)
            return "1";
        else
            return "0";
    }
}
