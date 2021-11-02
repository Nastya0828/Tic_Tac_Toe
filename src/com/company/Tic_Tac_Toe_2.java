package com.company;

import java.util.Random;
import java.util.Scanner;

public class Tic_Tac_Toe_2 {

    // 3. Определяем размеры массива
    static final int SIZE_X = 5;
    static final int SIZE_Y = 5;
    static final int WIN_COUNT = 4; // количество победных фишек вряд

    // 1. Создаем двумерный массив
    static char[][] field = new char[SIZE_Y][SIZE_X];

    // 2. Обозначаем кто будет ходить какими фишками
    static final char PLAYER_DOT = 'X';
    static final char AI_DOT = '0';
    static final char EMPTY_DOT = '.';

    // 8. Создаем сканер
    static Scanner scanner = new Scanner(System.in);
    // 12. Создаем рандом
    static final Random rand = new Random();

    // 4. Заполняем на массив
    private static void initField() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                field[i][j] = EMPTY_DOT;
            }
        }
    }

    // 5. Выводим массив на печать
    private static void printField() {
        //6. украшаем картинку
        System.out.println("-------");
        for (int i = 0; i < SIZE_Y; i++) {
            System.out.print("|");
            for (int j = 0; j < SIZE_X; j++) {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();
        }
        //6. украшаем картинку
        System.out.println("-------");
    }

    // 7. Метод который устанавливает символ
    private static void setSym(int y, int x, char sym) {
        field[y][x] = sym;
    }

    // 9. Ход игрока
    private static Position playerStep() {
        // 11. с проверкой
        int x;
        int y;
        do {
            System.out.println("Введите координаты: X Y (1-5)");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(y, x));
        setSym(y, x, PLAYER_DOT);
        return new Position(x, y);
    }

    // 13. Ход ПК
    private static void aiStep() {
        int x;
        int y;
        do {
            x = rand.nextInt(SIZE_X);
            y = rand.nextInt(SIZE_Y);
        } while (!isCellValid(y, x));
        setSym(y, x, AI_DOT);
    }

    // 14. Проверка победы

    private static boolean checkRow(char sym, int x, int y) {
        int count = 0;
        for (int j = 0; j < SIZE_Y; j++) {
            for (int i = 0; i < SIZE_X; i++) {
                if (field[j][i] == sym) {
                    count = count + 1;
                } else {
                    count = 0;
                }
                if (count == 4) {
                    return true;
                }
                if (i == SIZE_X - 1) {
                    count = 0;
                }
            }
        }
        return false;
    }

    private static boolean checkColumn(char sym, int x, int y) {
        int count = 0;
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                if (field[j][i] == sym) {
                    count = count + 1;
                } else {
                    count = 0;
                }
                if (count == 4) {
                    return true;
                }
                if (j == SIZE_Y - 1) {
                    count = 0;
                }
            }
        }
        return false;
    }

    private static boolean checkWin1(char sym, int x, int y) {
        int count = 0;
        int d = SIZE_X; //длина стороны квадратного поля d
        if (d - WIN_COUNT >= y - x && y-x>0) {//проверка нижних диагоналей "\"
            int a = 0; // начало левый верхний х
            int b; //= y - x - a; //начало левый верхний у
            int bz = d - 1; //конец правый нижний у
            int az = x - y + bz; // конец правый нижний х
            for (a = 0; a <= az; a++) {
                b = a + (y - x);
                if (a < d && b < d && field[b][a] == sym) {
                    count = count + 1;
                } else {
                    count = 0;
                }
                if (count == 4) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkWin2(char sym, int x, int y) {
        int count = 0;
        int d = SIZE_X; //длина стороны квадратного поля d
        if (d - WIN_COUNT  >= x - y && x-y >= 0) {    // проверка верхних диагоналей "\"
            int b = 0; //начало левый верхний y
            int a = x - y; // начало левый верхний x
            int az = d; // конец правый нижний х
            int bz = y + az - x; //конец правый нижний у
            for (b = 0; b < bz; b++) {
                a = x - y + b;
                if (field[b][a] == sym) {
                    count = count + 1;
                } else {
                    count = 0;
                }
                if (count == 4) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkWin3(char sym, int x, int y) {
        int count = 0;
        int d = SIZE_X; //длина стороны квадратного поля d
        if (WIN_COUNT + 1 <= x + y + 2 && x + y <= d + 1 && x + y <= d - 1) { //проверка верхних диагоналей "/"
            int a = 0; // начало левый нижний x
            int b = 0; //= x + y-a; //начало левый нижний y
            int az = x + y + 1; // конец правый верхний х
            int bz = 0; //конец правый верхний у
            for (a = 0; a < az; a++) {
                b = x + y - a;
                if (field[a][b] == sym) {
                    count = count + 1;
                } else {
                    count = 0;
                }
                if (count == 4) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkWin4(char sym, int x, int y) {
        int count = 0;
        int d = SIZE_X; //длина стороны квадратного поля d
        if (d <= x + y && x + y <= d + d + 1 - WIN_COUNT) { //проверка нижних диагоналей "/"
            int b= d-1; //начало левый нижний y
            int a = x - d + y+1; // начало левый нижний x
            int az = b; // конец правый верхний х
            int bz = y-(d-x-1);//d-1; //конец правый верхний у
            for (az=b; az>=a; a++) {
                b = x-a+y;
                if (field[b][a] == sym) {
                    count = count + 1;
                } else {
                    count = 0;
                }
                if (count == 4) {
                    return true;
                }
            }
        }
        return false;
    }

    // 16. Проверка полное ли поле? возможно ли ходить?
    private static boolean isFieldFull() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if (field[i][j] == EMPTY_DOT) {
                    return false;
                }
            }
        }
        return true;
    }

    // 10. Проверяем возможен ли ход
    private static boolean isCellValid(int y, int x) {
        // если вываливаемся за пределы возвращаем false
        if (x < 0 || y < 0 || x > SIZE_X - 1 || y > SIZE_Y - 1) {
            return false;
        }
        // если не путое поле тоже false
        return (field[y][x] == EMPTY_DOT);
    }

    public static void main() {
        // 1 - 1 иницируем и выводим на печать
        initField();
        printField();
        // 1 - 1 иницируем и выводим на печать

        // 15 Основной ход программы

        while (true) {
            Position position = playerStep();
            printField();
            if ((checkRow(PLAYER_DOT, position.x, position.y)) |
                    (checkColumn(PLAYER_DOT, position.x, position.y)) |
                    (checkWin1(PLAYER_DOT, position.x, position.y))|
                    (checkWin2(PLAYER_DOT, position.x, position.y))|
                    (checkWin3(PLAYER_DOT, position.x, position.y))|
                    (checkWin4(PLAYER_DOT, position.x, position.y))){
                System.out.println("Player WIN!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("DRAW");
                break;
            }

            aiStep();
            printField();
            if ((checkRow(AI_DOT, position.x, position.y)) |
                    (checkColumn(AI_DOT, position.x, position.y)) |
                    (checkWin1(AI_DOT, position.x, position.y)) |
                    (checkWin2(AI_DOT, position.x, position.y)) |
                    (checkWin3(AI_DOT, position.x, position.y)) |
                    (checkWin4(AI_DOT, position.x, position.y))) {
                System.out.println("Win SkyNet!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("DRAW!");
                break;
            }
        }
    }
}
