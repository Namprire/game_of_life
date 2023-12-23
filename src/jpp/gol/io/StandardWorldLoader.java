package jpp.gol.io;

import jpp.gol.model.CellState;
import jpp.gol.model.World;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Scanner;

public class StandardWorldLoader implements WorldLoader {


    @Override
    public World load(InputStream in) throws IOException {
        if(in==null){
            throw new IOException(in+"ist null");
        }
        //Lesen Sie eine Welt im Textformat von in ein.
        // Tritt ein Fehler im Format auf, werfen Sie eine IOException mit aussagekräftiger Nachricht.
        Scanner s = new Scanner(in).useDelimiter("\\A"); //convert an InputStream to a String
        String input = s.hasNext() ? s.next() : "";
        String[] result = input.split("\n", 2);

        String dimensions=result[0];
        String worldinput=result[1];
        String[] widthheight = dimensions.split("x", 2);
        String[] worldinputzeilen = worldinput.split("\n");

        String heightstring = widthheight[1];
        heightstring=heightstring.replace("[^\\d.]", "");
//System.out.println(widthheight[0]+heightstring); //es erkennt width und height und beides sind int 15,15
        heightstring=heightstring.trim();
//        System.out.println(heightstring);

        int width= Integer.parseInt(widthheight[0]);
        int height= Integer.parseInt(heightstring);

        for (int i = 0; i < worldinputzeilen.length; i++) {
            String zeile = worldinputzeilen[i].trim();
            //fixme -1 weg
            if(zeile.length()!=(width)){
                if(!(i== worldinputzeilen.length-1)){
                    throw new IOException("width an einer zeile passt nicht");
                }

            }

        }
        World world = new World(width,height);
        //worldinput ist string mit zb
        //010\n
        //101\n
        //010\n
        int n =0;
        char character1 = '1';
        char character2 = '0';
        char character3 = '\n';
        char character4 = Character.MIN_VALUE;;



        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char cellstate= worldinput.charAt(n);
                //debug
                //System.out.println(cellstate);

                if(character1==cellstate){
                    world.set(j,i, CellState.ALIVE);
                }else{
                    if(character2==cellstate){
                        world.set(j,i, CellState.DEAD);
                    }else{
                        if((character3==cellstate)){
                            n++;
                        }else{
                            if(character4==cellstate){

                            }else{
                                System.out.println("char was gelesen wurde:"+cellstate);
                                throw new IOException("weder null noch eins");
                            }

                        }

                    }
                }
                if(j==width-1){
                    n++;
                }
                n++;
            }
        }
        return world;
    }

    @Override
    public void save(World world, OutputStream out) throws IOException {
        if(world==null){
            throw new IOException("world ist null");
        }
        if(out==null){
            throw new IOException("out ist null");
        }
        //Schreiben Sie die Welt world im oben definierten Textformat auf den Ausgabestrom out.
        // Greifen Sie, wo möglich, auf bereits zuvor implementierte Methoden zurück.
        String content = world.getWidth() +
                "x" +
                world.getHeight() +
                "\n" +
                world.toString();
        out.write(content.getBytes());
    }
}
