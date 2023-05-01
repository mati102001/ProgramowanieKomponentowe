package pl.first.sudoku;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.first.sudoku.exceptions.CompareNullException;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuFieldTest {

    SudokuField[] board;
    private SudokuSolver solver;
    private SudokuBoard sudokuBoard;
    private SudokuBoard sudokuBoard2;
    private SudokuField field;
    private SudokuField field2;
    private SudokuField field3;
    private SudokuField fieldCopy;

    @BeforeEach
    public void start(){
        sudokuBoard = new SudokuBoard(solver);
        board = new SudokuField[SudokuBoard.SIZE];
        field = new SudokuField();
        field2 = new SudokuField();
        for(int i=0 ; i < SudokuBoard.SIZE; i++)
        {
            board[i] = new SudokuField();
        }
    }

    @Test
    public void SudokuFieldTestSetGet(){
        board[0].setFieldValue(7);
        assertEquals(board[0].getFieldValue(), 7);
        board[0].setFieldValue(11);
        assertEquals(board[0].getFieldValue(), 11);
        board[0].setFieldValue(-1);
        assertEquals(board[0].getFieldValue(), -1);
        board[0].setFieldValue(5);
        assertEquals(board[0].getFieldValue(), 5);
    }
    @Test
    public void FieldOutOfBoard(){
        board[0].addPropertyChangeListener(new SudokuFieldValueChangeListener(sudokuBoard));
        board[0].setFieldValue(11);
    }


    @Test
    public void toStringTest() {
        ToStringVerifier.forClass(SudokuField.class)
                .withClassName(NameStyle.SIMPLE_NAME)
                .withIgnoredFields("listBundle", "log", "isEditable")
                .verify();
    }
    @Test
    public void equalsTest() {
        assertTrue(field.equals(field2)
                && field2.equals(field));
        assertTrue(field.equals(field));
    }
    @Test
    public void notEqualsTest() {
        assertFalse(field.equals(null));
        BacktrackingSudokuSolver bss = new BacktrackingSudokuSolver();
        assertFalse(field.equals(bss));
    }
    @Test
    public void hashCodeTest() {
        assertEquals(field.hashCode(), field2.hashCode());
    }

    @Test
    public void compareToTest() {
        assertThrows(CompareNullException.class, () -> {
            field = null;
            field2.compareTo(field);
        });
        field3 = new SudokuField();
        field3.setFieldValue(5);
        field2 = new SudokuField();
        field2.setFieldValue(6);
        field = new SudokuField();
        field.setFieldValue(5);
        assertEquals(1,field2.compareTo(field3));
        assertEquals(0,field.compareTo(field3));
        assertEquals(0,field3.compareTo(field));
        assertEquals(-1,field.compareTo(field2));
        field2.setFieldValue(5);
        assertTrue(field.compareTo(field2) == 0
                && field2.compareTo(field3) == 0
                && field.compareTo(field3) == 0);
        field2.setFieldValue(6);
        field.setFieldValue(7);
        assertTrue(field.compareTo(field2) == 1
                && field2.compareTo(field3) == 1
                && field.compareTo(field3) == 1);
    }

    @Test
    public void FieldCloneTest() throws CloneNotSupportedException {
        fieldCopy = field.clone();
        assertTrue(fieldCopy.equals(field)
                && field.equals(fieldCopy));
        fieldCopy.setFieldValue(5);
        assertTrue(fieldCopy.getFieldValue() != field.getFieldValue());
    }

}
