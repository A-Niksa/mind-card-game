package api.dataeggs;

public class NewGameEgg {
    private boolean creatingNewGameWasSuccessful;
    private int idOfCreatedGame;

    public NewGameEgg(boolean creatingNewGameWasSuccessful, int idOfCreatedGame) {
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
