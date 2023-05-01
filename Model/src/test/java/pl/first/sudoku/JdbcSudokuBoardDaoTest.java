package pl.first.sudoku;

import nl.jqno.equalsverifier.internal.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.first.sudoku.exceptions.DaoExceptions;
import pl.first.sudoku.exceptions.DataBaseException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcSudokuBoardDaoTest {


    BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard sudoku= new SudokuBoard(solver);
    SudokuBoardDaoFactory fDao1 = new SudokuBoardDaoFactory();
    JdbcSudokuBoardDao baza = fDao1.getDataBaseDao("nazwa");
    JdbcSudokuBoardDao baza2 = fDao1.getDataBaseDao("nazwa2");


    @Test
    public void createTableTest() {
        try {
            assertTrue(baza.createTables());
        } catch (Exception e) {

        }
    }

    @Test
    public void createTableException() {
        assertThrows(DataBaseException.class, () -> {
            JdbcSudokuBoardDao baza3 = new JdbcSudokuBoardDao("?");
            baza3.insertSudokuBoard(sudoku, "sudoku4");
            baza3.insertSudokuBoard(sudoku, "sudoku4");
            assertTrue(baza3.deleteName("sudoku4"));
        });
    }

    @Test
    public void insertSudoku() {
        try {
            sudoku.solveGame();
            assertTrue(baza.insertSudokuBoard(sudoku, "sudoku1"));
            sudoku.solveGame();
            assertTrue(baza.insertSudokuBoard(sudoku, "sudoku2"));
            sudoku.solveGame();
            assertTrue(baza2.insertSudokuBoard(sudoku, "sudoku3"));
            assertTrue(baza.deleteName("sudoku1"));
            assertTrue(baza.deleteName("sudoku2"));
            assertTrue(baza2.deleteName("sudoku3"));
        } catch (DataBaseException e) {
        }
    }
    @Test
    public void readWriteTest() {
        SudokuBoard readBoard;

        try (JdbcSudokuBoardDao baseSudokuDao = fDao1.getDataBaseDao("sudoku6")) {
            sudoku.solveGame();
            baseSudokuDao.write(sudoku);
            readBoard = baseSudokuDao.read();
            assertNotSame(readBoard, sudoku);
            assertTrue(sudoku.equals(readBoard));
            baseSudokuDao.close();
            assertTrue(baseSudokuDao.deleteName("sudoku6"));
        } catch (DaoExceptions e) {

        }
    }

    @Test
    public void readException() {
        Dao<SudokuBoard> baseSudokuDao = fDao1.getDataBaseDao("testowanazwaniema");
        assertThrows(DataBaseException.class, ()->{
            baseSudokuDao.read();
        });
    }

    @Test
    public void writeException() {
        Dao<SudokuBoard> baseSudokuDao = fDao1.getDataBaseDao("testowanazwaniema");
        assertThrows(DataBaseException.class, ()->{
            baseSudokuDao.write(null);
        });
    }

    @Test
    public void nameFromBaseTest() {
        try {
            assertNotNull(baza.nameFromBase());
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void transactionFalse() {
        try (JdbcSudokuBoardDao baza4 = fDao1.getDataBaseDao("baza4")) {
            int number = baza4.nameFromBase().size();
            sudoku.get_ref(1,1).removePropertyChangeListener(0);
            sudoku.set(1,1,11);
            assertEquals(sudoku.get(1,1),11);
            baza4.write(sudoku);
            assertEquals(number,baza4.nameFromBase().size());
            assertFalse(baza4.nameFromBase().contains("baza4"));
        } catch (DaoExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
    }

    @Test
    public void transactionTrue() {
        try (JdbcSudokuBoardDao baza5 = fDao1.getDataBaseDao("baza5")) {
            int number = baza5.nameFromBase().size();
            sudoku.get_ref(1,1).removePropertyChangeListener(0);
            sudoku.set(1,1,8);
            assertEquals(sudoku.get(1,1),8);
            baza5.write(sudoku);
            assertEquals(number+1,baza5.nameFromBase().size());
            assertTrue(baza5.nameFromBase().contains("baza5"));
            assertTrue(baza5.deleteName("baza5"));
        } catch (DaoExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
    }
    @Test
    public void delete() {
        try (JdbcSudokuBoardDao baza6 = fDao1.getDataBaseDao("baza6")) {
            assertThrows(DataBaseException.class, ()->{
                baza6.deleteName("niematakiejnazwy");
            });
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
    }

}
