package api.dataeggs.gamestate;

import api.dataeggs.DataEgg;
import api.dataeggs.DataEggType;
import api.dataeggs.ninjarequest.NinjaRequestStatus;

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
