package utils.jsonparsing.literals.dataeggs.gamestate;

import utils.jsonparsing.literals.dataeggs.DataEgg;
import utils.jsonparsing.literals.dataeggs.DataEggType;
import utils.jsonparsing.literals.dataeggs.ninjarequest.NinjaRequestStatus;

public class NinjaRequestEgg extends DataEgg implements Comparable<NinjaRequestEgg> {
    private int playerId;
    private NinjaRequestStatus status;

    public NinjaRequestEgg(int playerId, NinjaRequestStatus status) {
        super(DataEggType.NINJA_REQUEST_EGG);
        this.playerId = playerId;
        this.status = status;
    }

    public int getPlayerId() {
        return playerId;
    }

    public NinjaRequestStatus getStatus() {
        return status;
    }

    @Override
    public int compareTo(NinjaRequestEgg ninjaRequestEgg) {
        return playerId - ninjaRequestEgg.getPlayerId();
    }
}
