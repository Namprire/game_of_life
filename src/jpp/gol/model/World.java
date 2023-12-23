package jpp.gol.model;

import java.util.Arrays;
import java.util.Objects;

public class World {
    CellState[][]welt;
    int height;
    int width;
    public World() {
        this.welt=new CellState[10][10];
        this.height=10;
        this.width=10;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                welt[i][j]=CellState.DEAD;
            }
        }
        //Erstellt eine 10x10-Welt, die nur tote Felder enthält.
    }

    public World(int width, int height) {
        //Erstellt eine Welt mit gegebener Breite und Höhe, die nur tote Felder enthält.
        // Ist ein Parameter <= 0, wird eine IllegalWorldSizeException mit einer aussagekräftigen Fehlermeldung geworfen.
        if (width <= 0)
            throw new IllegalWorldSizeException("width cannot be <= 0");
        if (height <= 0)
            throw new IllegalWorldSizeException("height cannot be <= 0");

        this.width=width;
        this.height=height;
        this.welt=new CellState[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                welt[i][j]=CellState.DEAD;
            }
        }
    }



    public World(World toCopy) {
        //Erstellt eine Welt als Kopie von other.
        // Diesen Kopie-Konstruktor benötigen Sie im späteren Verlauf für die Spiellogik.
        // Achten Sie darauf, dass keine Referenzen zu Inhalten von other bestehen,
        // sprich Änderungen nur in der neuen Welt auftreten.
        this.width= toCopy.width;
        this.height= toCopy.height;
        welt = new CellState[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                welt[i][j] = toCopy.get(i, j);
            }
        }
        // this.welt=toCopy.welt;
    }

    public void checkCoordinates(int x, int y) {
        if (x < 0 || x >= width)
            throw new IllegalCoordinateException("illegal value for x");
        if (y < 0 || y >= height)
            throw new IllegalCoordinateException("illegal value for y");
    }


    public int countNeighbors(int x, int y) {
//Ist die Koordinate nicht Teil der Welt, d.h.
// außerhalb des Arrays, so wird eine IllegalCoordinateException geworfen.
//        if(x<0||x>=width||y<0||y>=height){
//            throw new IllegalCoordinateException("Koordinate ist nicht Teil der Welt");
//        }
        checkCoordinates(x, y);

//Gibt die Anzahl der lebenden Nachbarn für das Feld mit Koordinaten x und y zurück.
// Achten Sie hierbei darauf, dass die Welt einen Torus bildet,
// d.h. wenn Sie über den Feldrand hinaus kommen, geht es auf der entgegengesetzten Seite weiter.

        CellState[][]neighbours={ //9 mal 9 feld nur mit den nachbarn und x,y selber
                {get((x-1+width)%width,(y-1+height)%height)},
                {get(x,(y-1+height)%height)},                   //row 1
                {get((x+1)%width,(y-1+height)%height)},

                {get((x-1+width)%width,y)},
                {get(x,y)},                                         //row 2
                {get((x+1)%width,y)},

                {get((x-1+width)%width,(y+1)%height)},
                {get(x,(y+1)%height)},                          //row 3
                {get((x+1)%width,(y+1)%height)}
        };
        int cnt = 0;
        for(CellState[] row : neighbours) {
            for (CellState cell : row) {
                if (cell.equals(CellState.ALIVE)) {
                    cnt++;
                }
            }
        }
        if(get(x,y)==CellState.ALIVE){ //zaehlt x,y mit also -1 wenn diese selber alive ist
            cnt=cnt-1;
        }
        return cnt;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void set(int x, int y, CellState value) {
//Weißt der Koordinate (x,y) den Zustand value zu.
// Ist die Koordinate nicht Teil der Welt, so wird eine IllegalCoordinateException geworfen.
        checkCoordinates(x, y);

        this.welt[x][y] = value;
    }

    public CellState get(int x, int y) {
//Liefert den Zustand des Feldes mit den Koordinaten x und y.
// Ist die Koordinate nicht Teil der Welt, so wird eine IllegalCoordinateException geworfen.
        checkCoordinates(x, y);

        return welt[x][y];
    }

    @Override
    public String toString() {
//Überschreiben Sie die Methode toString() so, dass Sie die Welt als Rechteck zurückgibt.
// Lebende Zellen sollen durch eine 1, tote durch eine 0 repräsentiert werden.
// Das Koordinatensystem soll seinen Ursprung oben-links haben,
// die x-Achse läuft nach rechts, die y-Achse nach unten
// ( diese Wahl des Ursprungs ist z.B. auch bei Computerdisplays üblich).
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(welt[j][i].equals(CellState.ALIVE)){
                    sb.append(1);
                }
                if(welt[j][i].equals(CellState.DEAD)){
                    sb.append(0);
                }
            }
            sb.append("\n");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
//Überschreiben Sie die Methode equals(Object).
// Zwei Objekte vom Typ world sollen genau dann gleich sein,
// wenn die Arrays die selbe Gestalt und Größe besitzen und
// jedes Feld den selben Zustand hat. Für alle Koordinaten (x, y) soll wahr sein:
        if (o == this)
            return true;
        if (!(o instanceof World))
            return false;

        final World other = (World) o;

        if (this.width != other.width || this.height != other.height) {
            return false;
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (this.welt[i][j] != other.get(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
//Überschreiben Sie die Methode hashCode().
// Sie berechnet einen Hash-Wert für das Objekt.
// Beachten Sie den in Object) definierten Vertrag zwischen equals(Object) und hashCode()!
        return height * width * Arrays.deepHashCode(welt);
    }

    @Override
    public World clone() {
//Überschreiben Sie die Methode clone(). Hierbei ist zu beachten,
// dass der Klon und das Original keine gemeinsamen Ressourcen nutzen:
        return new World(this);
    }
}

