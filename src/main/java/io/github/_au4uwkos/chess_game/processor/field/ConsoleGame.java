package io.github._au4uwkos.chess_game.processor.field;

import io.github._au4uwkos.chess_game.processor.figures.Figure;
import io.github._au4uwkos.chess_game.processor.figures.King;
import io.github._au4uwkos.chess_game.processor.figures.Rook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ConsoleGame extends Game {

    private Coordinates whiteKing;
    private Coordinates blackKing;

    private Scanner scanner;
    private Field field;
    private HashMap<Coordinates, Figure> figures;

    public ConsoleGame() {
        super();
        Scanner scanner = new Scanner(System.in);
    }

    public Coordinates chooseWhite() {
        while (true) {
            String temp = scanner.nextLine();
            int pos = temp.charAt(0) - 'A';
            int row = (temp.charAt(1) - '0') - 1;
            Coordinates input = new Coordinates(row, pos);
            if (figures.containsKey(input)) {
                if (figures.get(input).isWhite()) {
                    if (!figures.get(input).getAllowedMovements(field).isEmpty()) return input;
                }
            }
            System.out.println("Choose another checkerboard cell");
        }
    }

    public void move(Coordinates begin) {
        ArrayList<Coordinates> movements = figures.get(begin).getAllowedMovements(field);
        if (figures.get(begin).isWhite()) ConsoleRender.showWhitePlayerChoice(field, movements, begin);
        else ConsoleRender.showBlackPlayerChoice(field, movements, begin);
        while (true) {
            String temp = scanner.nextLine();
            int pos = temp.charAt(0) - 'A';
            int row = (temp.charAt(1) - '0') - 1;
            Coordinates input = new Coordinates(row, pos);
            if (!movements.contains(input)) {
                System.out.println("Choose another checkerboard cell");
                continue;
            }
            Figure figure = figures.get(begin);
            if (figure.getClass() == Rook.class) ((Rook) figure).setIsMoved();
            if (figure.getClass() == King.class) {
                ((King) figure).setWasUnderAttack();
                if (Math.abs(input.getPosition() - begin.getPosition()) == 2) {
                    if (figure.isWhite() && begin.equals(new Coordinates(0, 4))) {
                        Figure rook;
                        if (input.getPosition() > begin.getPosition()) {
                            rook = figures.get(new Coordinates(0, 7));
                            if (((Rook) rook).isMoved()) continue;
                            figures.remove(rook.getCoordinates());
                            rook.setCoordinates(new Coordinates(0, 5));
                        } else {
                            rook = figures.get(new Coordinates(0, 0));
                            if (((Rook) rook).isMoved()) continue;
                            figures.remove(rook.getCoordinates());
                            rook.setCoordinates(new Coordinates(0, 3));
                        }
                        figures.put(rook.getCoordinates(), rook);
                    } else if (!figure.isWhite() && begin.equals(new Coordinates(7, 4))) {
                        Figure rook;
                        if (input.getPosition() > begin.getPosition()) {
                            rook = figures.get(new Coordinates(7, 7));
                            if (((Rook) rook).isMoved()) continue;
                            figures.remove(rook.getCoordinates());
                            rook.setCoordinates(new Coordinates(7, 5));
                        } else {
                            rook = figures.get(new Coordinates(7, 0));
                            if (((Rook) rook).isMoved()) continue;
                            figures.remove(rook.getCoordinates());
                            rook.setCoordinates(new Coordinates(7, 3));
                        }
                        figures.put(rook.getCoordinates(), rook);
                    }
                }
                if (figure.isWhite()) whiteKing = input;
                else blackKing = input;
            }
            figure.setCoordinates(input);
            figures.put(input, figure);
            figures.remove(begin);
            break;
        }
    }

    public Coordinates chooseBlack() {
        while (true) {
            String temp = scanner.nextLine();
            int pos = temp.charAt(0) - 'A';
            int row = (temp.charAt(1) - '0') - 1;
            Coordinates input = new Coordinates(row, pos);
            if (figures.containsKey(input)) {
                if (!figures.get(input).isWhite()) {
                    if (!figures.get(input).getAllowedMovements(field).isEmpty()) return input;
                }
            }
            System.out.println("Choose another checkerboard cell");
        }
    }


    public void startConsoleGame() {
        while (true) {
            field.checkAttack();
            ConsoleRender.showWhitePlayer(field);
            if (field.getIsUnderBlackAttack().contains(whiteKing)) {
                if (checkmateWhite()) {
                    System.out.println("Black has won");
                    break;
                }
            }
            Field previous = new Field(field);
            move(chooseWhite());
            field.checkAttack();
            while (field.getIsUnderBlackAttack().contains(whiteKing)) {
                System.out.println("Make another move");
                field = new Field(previous);
                ConsoleRender.showWhitePlayer(field);
                move(chooseWhite());
                field.checkAttack();
            }

            System.out.println("\n\n\n");

            ConsoleRender.showBlackPlayer(field);

            if (field.getIsUnderWhiteAttack().contains(blackKing)) {
                if (checkmateBlack()) {
                    System.out.println("White has won");
                    break;
                }
            }
            previous = new Field(field);
            move(chooseBlack());
            field.checkAttack();
            while (field.getIsUnderWhiteAttack().contains(blackKing)) {
                System.out.println("Make another move");
                field = new Field(previous);
                ConsoleRender.showBlackPlayer(field);
                move(chooseBlack());
                field.checkAttack();
            }
        }
    }

}
