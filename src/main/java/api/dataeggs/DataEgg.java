package api.dataeggs;

public abstract class DataEgg {
    private DataEggType eggType;

    protected DataEgg(DataEggType eggType) {
        this.eggType = eggType;
    }
}
