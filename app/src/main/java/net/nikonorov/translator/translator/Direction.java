package net.nikonorov.translator.translator;

/**
 * Created by vitaly on 30.09.15.
 */
public class Direction {
    int langFrom;
    int langTo;
    String direction;

    Direction(int langFrom, int langTo, String direction){
        this.langFrom = langFrom;
        this.langTo = langTo;
        this.direction = direction;
    }
}
