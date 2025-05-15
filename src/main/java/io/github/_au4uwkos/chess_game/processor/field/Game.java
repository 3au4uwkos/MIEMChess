package io.github._au4uwkos.chess_game.processor.field;

import io.github._au4uwkos.chess_game.processor.figures.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Game {

    private Coordinates whiteKing;
    private Coordinates blackKing;

    private Field field;
    private HashMap<Coordinates, Figure> figures;

    public Game() {
        whiteKing = new Coordinates(0, 4);
        blackKing = new Coordinates(7, 4);
        field = FieldInitializer.init();
        figures = field.getFigures();
    }

    public boolean checkmateWhite() {
        ArrayList<Coordinates> whiteFigures = new ArrayList<>(16);
        for (Coordinates key : figures.keySet()) if (figures.get(key).isWhite()) whiteFigures.add(key);
        for (Coordinates key : whiteFigures) {
            Figure temp = figures.get(key);
            Field copy = new Field(field);
            ArrayList<Coordinates> movements = temp.getAllowedMovements(field);
            for (Coordinates movement : movements) {
                figures.remove(key);
                temp.setCoordinates(movement);
                figures.put(temp.getCoordinates(), temp);
                field.checkAttack();
                if (!field.getIsUnderBlackAttack().contains(whiteKing)) return false;
                field = new Field(copy);
            }
        }
        return true;
    }

    public boolean checkmateBlack() {
        ArrayList<Coordinates> blackFigures = new ArrayList<>(16);
        for (Coordinates key : figures.keySet()) if (!figures.get(key).isWhite()) blackFigures.add(key);
        for (Coordinates key : blackFigures) {
            Figure temp = figures.get(key);
            Field copy = new Field(field);
            ArrayList<Coordinates> movements = temp.getAllowedMovements(field);
            for (Coordinates movement : movements) {
                figures.remove(key);
                temp.setCoordinates(movement);
                figures.put(temp.getCoordinates(), temp);
                field.checkAttack();
                if (!field.getIsUnderWhiteAttack().contains(blackKing)) return false;
                field = new Field(copy);
            }
        }
        return true;
    }
}
