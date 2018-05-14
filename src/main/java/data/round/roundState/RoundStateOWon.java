package data.round.roundState;

import data.Symbol;
import data.messenger.MessageKeys;
import data.players.Players;

public class RoundStateOWon implements RoundState{
    @Override
    public MessageKeys getMessageKey() {
        return MessageKeys.theRoundWins;
    }

    @Override
    public Object [] getMessageKeyArguments() {
        return new Object[]{Symbol.O};
    }

    @Override
    public void addPointsAccordingRoundFinishedState(Players players) {
        players.addPointsToO(3);
    }
}
