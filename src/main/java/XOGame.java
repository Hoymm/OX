import Data.Data;
import Data.DisplayInfoProvider;
import InfoDisplayer.InfoDisplayer;
import UserIO.UserIO;

public class XOGame {
    private DisplayInfoProvider displayInfoProvider;
    private Data data;
    private InfoDisplayer infoDisplayer;
    private UserIO userIO;

    public XOGame() {
        initGameStartConditions();
    }

    private void initGameStartConditions() {
        userIO = new UserIO();
        data = new Data(userIO.getStartGameParametersFromUser());
        displayInfoProvider = data.getDisplayInfoProvider();
        infoDisplayer = new InfoDisplayer();
    }

    public void runGame() {
        while (userDontWantLeaveGame()){
            infoDisplayer.displayCurGameState(displayInfoProvider);
            userIO.askUserToInputData();
        }
    }

    private boolean userDontWantLeaveGame() {
        return !userIO.lastUserInput().equalsIgnoreCase("quit");
    }
}
