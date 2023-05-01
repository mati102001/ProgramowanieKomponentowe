package pl.first.sudoku;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {
    @Override
    public void solve(final SudokuBoard board) {

        List<Integer> tablicawartosci =
                Arrays.asList(new Integer[SudokuBoard.SIZE * SudokuBoard.SIZE]);
        for (int i = 0; i < tablicawartosci.size(); i++) {
            tablicawartosci.set(i, 0);
        }
        for (int i = 0; i < 81; i++) {

            boolean czyprawidlowe = false; //zmienna do czy trzeba zrobic krok w tyl
            int wiersz = i / 9;
            int kolumna = i % 9;
            if (tablicawartosci.get(i) == 0) {
                List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
                Collections.shuffle(numberList);
                tablicawartosci.set(i, numberList.get(i % 9));
                //nadajemy wartosc losuje z przedzialu <1;9>
                int wart = tablicawartosci.get(i);
                board.set(wiersz, kolumna, tablicawartosci.get(i));
                do {
                    if (board.get(wiersz, kolumna) != 0) {
                        czyprawidlowe = true;
                        break;
                    }
                    wart = wart % 9 + 1;
                    board.set(wiersz, kolumna, wart);
                } while (wart != tablicawartosci.get(i));

            } else {
                int stalaWartosc = board.get(wiersz, kolumna);
                int wart = board.get(wiersz, kolumna);
                wart = wart % 9 + 1;
                board.set(wiersz, kolumna, wart);
                while (wart != tablicawartosci.get(i)) {
                    if (board.get(wiersz, kolumna) != stalaWartosc) {
                        czyprawidlowe = true;
                        break;
                    }
                    wart = wart % 9 + 1;
                    board.set(wiersz, kolumna, wart);
                }
            }
            if (!czyprawidlowe) {
                tablicawartosci.set(i, 0); //tutaj tez
                board.set(wiersz, kolumna, 0); //ustawiamy wartosc na 0
                i -= 2; //cofamy do porzedniego pola
            }
        }
    }
}