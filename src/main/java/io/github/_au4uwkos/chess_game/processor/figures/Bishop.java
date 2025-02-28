package io.github._au4uwkos.chess_game.processor.figures;

import io.github._au4uwkos.chess_game.processor.field.Coordinates;
import io.github._au4uwkos.chess_game.processor.field.Field;

import java.util.ArrayList;

public class Bishop extends Figure {

    public Bishop(boolean isWhite, int row, int pos) {
        super(isWhite, row, pos);
    }

    @Override
    public ArrayList<Coordinates> getPossibleMovements(Field field) {
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        return linearMovements(field, directions);
    }

    @Override
    public String toString() {
        return "" + (char) 9821;
    }
}

