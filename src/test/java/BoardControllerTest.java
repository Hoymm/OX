import data.Symbol;
import data.board.BoardController;
import data.board.BoardData;
import data.round.roundState.RoundStateDraw;
import data.round.roundState.RoundStateXWon;
import data.round.roundState.RoundStateOWon;
import data.round.roundState.RoundStateUnfinished;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BoardControllerTest {
    private BoardController boardController3x3;
    private BoardData gameBoardData;

    @BeforeMethod
    public void setUp(){
        gameBoardData = Mockito.mock(BoardData.class);
        gameBoardData = new BoardData(3, 3);
        boardController3x3 = new BoardController(gameBoardData, 3);
    }

    @Test
    public void getRoundState_newlyCreatedBoardController_shouldReturn_roundStateUnfinished() {
        assertEquals(boardController3x3.getRoundState(), new RoundStateUnfinished());
    }

    @Test
    public void getRoundState_makeOWinHorizontally_returns_roundStateOWon() {
        putSymbolsOnCoordinates(Symbol.O, 1, 2, 3);
        assertEquals(boardController3x3.getRoundState(), new RoundStateOWon());
    }

    @Test
    public void getRoundState_makeOWinVertically_returns_roundStateOWon() {
        putSymbolsOnCoordinates(Symbol.O, 1, 4, 7);
        assertEquals(boardController3x3.getRoundState(), new RoundStateOWon());
    }

    @Test
    public void getRoundState_makeOWinDiagonalDecreasing_returns_roundStateOWon() {
        putSymbolsOnCoordinates(Symbol.O, 1, 5, 9);
        assertEquals(boardController3x3.getRoundState(), new RoundStateOWon());
    }

    @Test
    public void getRoundState_makeOWinDiagonalIncreasing_returns_roundStateOWon() {
        putSymbolsOnCoordinates(Symbol.O, 3, 5, 7);
        assertEquals(boardController3x3.getRoundState(), new RoundStateOWon());
    }

    @Test
    public void getRoundState_makeXWinHorizontally_returns_roundStateXWon() {
        putSymbolsOnCoordinates(Symbol.X, 1, 2, 3);
        assertEquals(boardController3x3.getRoundState(), new RoundStateXWon());
    }

    @Test
    public void getRoundState_makeXWinVertically_returns_roundStateXWon() {
        putSymbolsOnCoordinates(Symbol.X, 1, 4, 7);
        assertEquals(boardController3x3.getRoundState(), new RoundStateXWon());
    }

    @Test
    public void getRoundState_makeXWinDiagonalDecreasing_returns_roundStateXWon() {
        putSymbolsOnCoordinates(Symbol.X, 1, 5, 9);
        assertEquals(boardController3x3.getRoundState(), new RoundStateXWon());
    }

    @Test
    public void getRoundState_makeXWinDiagonalIncreasing_returns_roundStateXWon() {
        putSymbolsOnCoordinates(Symbol.X, 3, 5, 7);
        assertEquals(boardController3x3.getRoundState(), new RoundStateXWon());
    }

    @Test
    public void getRoundState_doNotFinishRound_returns_roundUnfinishedState() {
        putSymbolsOnCoordinates(Symbol.X, 3, 7);
        putSymbolsOnCoordinates(Symbol.O, 5);
        assertEquals(boardController3x3.getRoundState(), new RoundStateUnfinished());
    }

    @Test
    public void getRoundState_putSymbolsToMakeDraw_returns_roundStateDraw() {
        putSymbolsOnBoard3x3ToMakeADraw();
        assertEquals(boardController3x3.getRoundState(), new RoundStateDraw());
    }

    @Test
    public void prepareNewRound_putSymbolsToMakeBoardFull_returns_nullForEachFieldSymbolGet() {
        putSymbolsOnBoard3x3ToMakeADraw();
        boardController3x3.prepareNewRound();
        boolean result = isGetSymbolFromFieldsShouldAlwaysReturnNull(1,2,3,4,5,6,7,8,9);
        assertTrue(result);
    }

    @Test
    public void prepareNewRound_makeADraw_returns_RoundStateUnfinished() {
        putSymbolsOnBoard3x3ToMakeADraw();
        boardController3x3.prepareNewRound();
        assertEquals(boardController3x3.getRoundState(), new RoundStateUnfinished());
    }

    @Test
    public void toString_empty3x3Board_outputMatchingPattern() {
        Integer [] numbersFrom1To9 = getIntegerArrayFromToIncludingBoth(1, 9);
        assertEquals(boardController3x3.toString(),
                    String.format("_____________\n" +
                        "|   |   |   |\n" +
                        "| %d | %d | %d |\n" +
                        "|___|___|___|\n" +
                        "|   |   |   |\n" +
                        "| %d | %d | %d |\n" +
                        "|___|___|___|\n" +
                        "|   |   |   |\n" +
                        "| %d | %d | %d |\n" +
                        "|___|___|___|\n", numbersFrom1To9)
                );
    }

    @Test
    public void toString_putEverywhereOSymbols3x3Board_outputMatchingPattern() {
        Integer [] numbersFrom1To9 = getIntegerArrayFromToIncludingBoth(1, 9);
        putSymbolsOnCoordinates(Symbol.O, numbersFrom1To9);
        Symbol [] nineSymbolsO = getSymbolsArray(Symbol.O, 9);

        assertEquals(boardController3x3.toString(), String.format(getFormatBoard3x3(), nineSymbolsO));
    }

    private Symbol[] getSymbolsArray(Symbol symbolToInsert, int howMany) {
        Symbol [] symbols = new Symbol[howMany];
        for (int i = 0; i < symbols.length; ++i){
            symbols[i] = symbolToInsert;
        }
        return symbols;
    }

    @Test
    public void toString_putSymbolsSomewhereOn3x3Board_outputMatchingPattern() {
        putSymbolsOnCoordinates(Symbol.O, 1, 2, 5);
        putSymbolsOnCoordinates(Symbol.X, 3, 4, 6);
        assertEquals(boardController3x3.toString(),
                String.format(getFormatBoard3x3()
                        , Symbol.O.toString()
                        , Symbol.O.toString()
                        , Symbol.X.toString()
                        , Symbol.X.toString()
                        , Symbol.O.toString()
                        , Symbol.X.toString()
                        , "7", "8", "9")
        );
    }

    private String getFormatBoard3x3() {
        return "_____________\n" +
                "|   |   |   |\n" +
                "| %s | %s | %s |\n" +
                "|___|___|___|\n" +
                "|   |   |   |\n" +
                "| %s | %s | %s |\n" +
                "|___|___|___|\n" +
                "|   |   |   |\n" +
                "| %s | %s | %s |\n" +
                "|___|___|___|\n";
    }

    @Test
    public void toString_empty5x5Board_outputMatchingPattern() {
        BoardController boardController5x5 = new BoardController(new BoardData(5,5), 3);
        Integer [] numbersFrom1To25 = getIntegerArrayFromToIncludingBoth(1, 25);

        assertEquals(boardController5x5.toString(),
                String.format(getFormatBoard5x5(), numbersFrom1To25));
    }

    private String getFormatBoard5x5() {
        return "_____________________\n" +
                "|   |   |   |   |   |\n" +
                "| %d | %d | %d | %d | %d |\n" +
                "|___|___|___|___|___|\n" +
                "|   |   |   |   |   |\n" +
                "| %d | %d | %d | %d | %d|\n" +
                "|___|___|___|___|___|\n" +
                "|   |   |   |   |   |\n" +
                "| %d| %d| %d| %d| %d|\n" +
                "|___|___|___|___|___|\n" +
                "|   |   |   |   |   |\n" +
                "| %d| %d| %d| %d| %d|\n" +
                "|___|___|___|___|___|\n" +
                "|   |   |   |   |   |\n" +
                "| %d| %d| %d| %d| %d|\n" +
                "|___|___|___|___|___|\n";
    }

    @Test
    public void toString_empty10x10Board_outputMatchingPattern() {
        BoardController boardController10x10 = new BoardController(new BoardData(10, 10), 3);
        Integer [] numbersFrom1To100 = getIntegerArrayFromToIncludingBoth(1, 100);

        String formatForBoard10x10NoSymbols = "_________________________________________\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s | %s | %s | %s | %s | %s | %s | %s | %s | %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s|%s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n";

        assertEquals(boardController10x10.toString(),
                String.format(formatForBoard10x10NoSymbols, numbersFrom1To100));
    }

    @Test
    public void toString_putSymbolsOOnEachField_outputMatchingPattern() {
        BoardController boardController10x10 = new BoardController(new BoardData(10, 10), 3);
        Integer [] numbersFrom1To100 = getIntegerArrayFromToIncludingBoth(1, 100);
        putSymbolsOnCoordinates(boardController10x10, Symbol.O, numbersFrom1To100);

        Symbol [] oneHundredSymbolsO = getSymbolsArray(Symbol.O, 100);

        String boardFullOfSymbolsOFormat = "_________________________________________\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s | %s | %s | %s | %s | %s | %s | %s | %s | %s |\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s | %s | %s | %s | %s | %s | %s | %s | %s | %s |\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s | %s | %s | %s | %s | %s | %s | %s | %s | %s |\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s | %s | %s | %s | %s | %s | %s | %s | %s | %s |\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s | %s | %s | %s | %s | %s | %s | %s | %s | %s |\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s | %s | %s | %s | %s | %s | %s | %s | %s | %s |\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s | %s | %s | %s | %s | %s | %s | %s | %s | %s |\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s | %s | %s | %s | %s | %s | %s | %s | %s | %s |\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s | %s | %s | %s | %s | %s | %s | %s | %s | %s |\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s | %s | %s | %s | %s | %s | %s | %s | %s | %s |\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n";

        assertEquals(boardController10x10.toString(), String.format(boardFullOfSymbolsOFormat, oneHundredSymbolsO));
    }

    @Test
    public void toString_putSymbolsOn99And100FieldNumber_outputMatchingPattern() {
        BoardController boardController10x10 = new BoardController(new BoardData(10, 10), 3);
        String [] stringNumbersFrom1To98 = getStringIntegerArrayFromToIncludingBoth(1, 98);
        String [] twoSymbolsO = getSymbolsArrayToString(Symbol.O, 2);

        List<String> stringArgs = new ArrayList<>(100);
        Collections.addAll(stringArgs, stringNumbersFrom1To98);
        Collections.addAll(stringArgs, twoSymbolsO);

        String format10x10WithSymbolsAt99And100 = "_________________________________________\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s | %s | %s | %s | %s | %s | %s | %s | %s | %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s| %s|\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n" +
                "|   |   |   |   |   |   |   |   |   |   |\n" +
                "| %s| %s| %s| %s| %s| %s| %s| %s| %s | %s |\n" +
                "|___|___|___|___|___|___|___|___|___|___|\n";

        putSymbolsOnCoordinates(boardController10x10, Symbol.O, 99, 100);

        String expectedDisplayFormat = String.format(format10x10WithSymbolsAt99And100, stringArgs.toArray());
        assertEquals(boardController10x10.toString(), expectedDisplayFormat);
    }

    private String [] getSymbolsArrayToString(Symbol symbol, int howMany) {
        String [] symbolsToStringArray = new String[howMany];
        for (int i = 0; i < symbolsToStringArray.length; ++i){
            symbolsToStringArray[i] = symbol.toString();
        }
        return symbolsToStringArray;
    }

    private Integer[] getIntegerArrayFromToIncludingBoth(int min, int max) {
        Integer [] numbersFromMinToMax = new Integer[max];
        for (int i = 0; i < numbersFromMinToMax.length; ++i){
            numbersFromMinToMax[i] = min+i;
        }
        return numbersFromMinToMax;
    }

    private String[] getStringIntegerArrayFromToIncludingBoth(int min, int max) {
        String [] stringNumbersFromMinToMax = new String[max];
        for (int i = 0; i < stringNumbersFromMinToMax.length; ++i){
            stringNumbersFromMinToMax[i] = String.valueOf(min+i);
        }
        return stringNumbersFromMinToMax;
    }

    private boolean isGetSymbolFromFieldsShouldAlwaysReturnNull(int... fieldNumbers){
        for (int i : fieldNumbers) {
            if (gameBoardData.tryGetSymbolFromField(i) != null) {
                return false;
            }
        }
        return true;
    }

    private void putSymbolsOnBoard3x3ToMakeADraw(){
        putSymbolsOnCoordinates(Symbol.X, 2, 5, 7, 9);
        putSymbolsOnCoordinates(Symbol.O, 1, 3, 4, 6, 8);
    }

    private void putSymbolsOnCoordinates(Symbol symbol, Integer... fieldNumbers){
        putSymbolsOnCoordinates(boardController3x3, symbol, fieldNumbers);
    }

    private void putSymbolsOnCoordinates(BoardController boardController, Symbol symbol, Integer... fieldNumbers) {
        for (int i : fieldNumbers)
            boardController.tryMarkFieldAndChangeWinnerStateIfNeeded(i, symbol);

    }
}
