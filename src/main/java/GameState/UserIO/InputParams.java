package GameState.UserIO;
import Data.Symbol;
import java.util.Scanner;
import java.util.logging.Logger;

public class InputParams {
    public static final String QUIT_COMMAND = "quit";
    static final Logger LOGGER = Logger.getLogger(InputParams.class.getName());
    private InputParamsValidator inputParamsValidator;
    public final static String SEPARATOR = " ";
    private Scanner scanner;

    InputParams(Scanner scanner) {
        this.scanner = scanner;
        inputParamsValidator = new InputParamsValidator();
    }

    public String getPlayerNamesAndBoardDataFromUser() {

        System.out.println("Custom [c] or default [d] game:");
        if (scanner.nextLine().equalsIgnoreCase("c")){
            System.out.println("Custom game choosen.");
            StringBuilder builderUserInput = new StringBuilder();
            builderUserInput.append(insertAndValidatePlayerName(Symbol.O.toString())).append(SEPARATOR)
                    .append(insertAndValidatePlayerName(Symbol.X.toString())).append(SEPARATOR)
                    .append(whoStartsFirst()).append(SEPARATOR)
                    .append(howManyPointsToWinGame()).append(SEPARATOR)
                    .append(insertAndValidateTableParam("width")).append(SEPARATOR)
                    .append(insertAndValidateTableParam("height"));
            return builderUserInput.toString();
        }
        else{
            System.out.println("Default game choosen.");
            return "Damian Andrzej O 3 3 3";
        }
    }

    private String insertAndValidatePlayerName(String player) {
        String playerName = "";
        boolean validationPassed = false;
        while (!validationPassed){
            System.out.println(String.format("Insert %s player name: ", player));
            try {
                playerName = scanner.nextLine();
                validationPassed = inputParamsValidator.validateInsertedName(playerName);
            } catch (InputCannotBeEmptyException e) {
                LOGGER.warning("User tried to insert empty name");
            }
            if (!validationPassed)
                System.out.println(String.format("Name should contain only letters. \"%s\" is not acceptable name.", playerName));
        }
        return playerName;
    }

    private int insertAndValidateTableParam(String paramToGet) {
        String tableParameter = "";
        while(!inputParamsValidator.isTableParamVaild(tableParameter)){
            System.out.println(String.format("Insert board %s: ", paramToGet));
            tableParameter = scanner.nextLine();
            if (!inputParamsValidator.isTableParamVaild(tableParameter))
                System.out.println(String.format("Please insert integer value, greater or equal 3. \n \"%s\" is not acceptable table %s"
                        , tableParameter, paramToGet));
        }
        return Integer.valueOf(tableParameter);
    }

    private String whoStartsFirst() {
        String symbolToPlayFirst = "";
        while(!inputParamsValidator.isValidSymbol(symbolToPlayFirst)){
            System.out.println(String.format("Who should start first? Type player symbol (%s or %s): ", Symbol.O, Symbol.X));
            symbolToPlayFirst = scanner.nextLine();
            if (!inputParamsValidator.isValidSymbol(symbolToPlayFirst))
                System.out.println(String.format("Please insert symbol %s (big o) or %s (big x), any other symbols will not be accepted."
                        , Symbol.O, Symbol.X));
        }
        return symbolToPlayFirst;
    }

    private String howManyPointsToWinGame() {
        int minimumAmountOfPointsInRowToWin = 3;
        String pointsToWinGame = "";
        while(howManyPointsToWinGameConditionChecker(minimumAmountOfPointsInRowToWin, pointsToWinGame)){
            System.out.print("How many symbols (unbroken line) to win round: ");
            pointsToWinGame = scanner.nextLine();
            if (howManyPointsToWinGameConditionChecker(minimumAmountOfPointsInRowToWin, pointsToWinGame)) {
                System.out.println(String.format("Sorry, you must insert at least 3 symbols in row. \"%s\" is a wrong input.", pointsToWinGame));
            }
        }
        return pointsToWinGame;

    }

    private boolean howManyPointsToWinGameConditionChecker(int minimumAmountOfPointsInRowToWin, String pointsToWinGame) {
        return !inputParamsValidator.isItIntegerAndGraterOrEqualTo(pointsToWinGame, minimumAmountOfPointsInRowToWin) && !isItQuitCommand(pointsToWinGame);
    }

    public String getCoordsToPutOnBoard() {
        String fieldToMark = "";
        while(!inputParamsValidator.isVaildBoardField(fieldToMark) && !isItQuitCommand(fieldToMark)){
            System.out.print("Give me number of the field: ");
            fieldToMark = scanner.nextLine();
            if (!inputParamsValidator.isVaildBoardField(fieldToMark) && !isItQuitCommand(fieldToMark)) {
                System.out.println(String.format("Sorry, board fields are described by possitive numbers. \"%s\" is a wrong input.", fieldToMark));
            }
        }
        return fieldToMark;
    }

    boolean isItQuitCommand(String command) {
        return command.equalsIgnoreCase(QUIT_COMMAND);
    }
}