package jpp.gol;

import jpp.gol.io.StandardWorldLoader;
import jpp.gol.logic.GameLogic;
import jpp.gol.logic.ObservableGameLogicDecorator;
import jpp.gol.logic.StandardGameLogic;
import jpp.gol.logic.WorldChangedListener;
import jpp.gol.model.World;
import jpp.gol.rules.StandardRules;

import java.io.*;

public class Test {


    public static void main(String[] args) throws IOException {
        StandardWorldLoader test = new StandardWorldLoader();
        String input = """
                15x15
                000000000000000
                000000000000000
                000000110000000
                000000110000000
                000000000000000
                000000000000000
                000011000000000
                000011000000000
                000000000000000
                000000000000000
                000000000000000
                000000000000000
                000110000000000
                000110000000000
                000000000000000
                """;
        String path = "C:/Users/napha/IdeaProjects/gameoflife/resources/demo_worlds/World.txt";
        File world = new File(path);
        InputStream inputStream = new FileInputStream(world);

        InputStream in = new ByteArrayInputStream( input.getBytes());
        World welt = test.load(inputStream);
        String raus = welt.toString();
        System.out.println(raus);
        System.out.println(welt.get(0,1));
        StandardRules rules = new StandardRules();
        GameLogic gameLogic = new StandardGameLogic(welt,rules);
        ObservableGameLogicDecorator observableGameLogicDecorator = new ObservableGameLogicDecorator(gameLogic);
        WorldChangedListener l1= new WorldChangedListener() {
            @Override
            public void onChange(World world) {

            }
        };
        observableGameLogicDecorator.addWorldChangedListener(l1);
        //World welt2=new World(welt);
        //System.out.println(welt2.toString());
    }
}
