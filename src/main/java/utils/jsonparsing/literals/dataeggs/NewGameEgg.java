package utils.jsonparsing.literals.dataeggs;

public class NewGameEgg extends DataEgg {
    private boolean creatingNewGameWasSuccessful;
    private int idOfCreatedGame;

    public NewGameEgg(boolean creatingNewGameWasSuccessful, int idOfCreatedGame) {
        super(DataEggType.NEW_GAME_EGG);
        this.creatingNewGameWasSuccessful = creatingNewGameWasSuccessful;
        this.idOfCreatedGame = idOfCreatedGame;
    }

    public boolean isCreatingNewGameWasSuccessful() {
        return creatingNewGameWasSuccessful;
    }

    public int getIdOfCreatedGame() {
        return idOfCreatedGame;
    }
}
