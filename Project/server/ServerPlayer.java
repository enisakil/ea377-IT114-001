package Project.server;

import Project.common.Player;

public class ServerPlayer extends Player {
    private ServerThread client;
    //ea377 11/18/23
    private String roundPick;
    private int TotalScore = 0;

    public void setClient(ServerThread client) {
        this.client = client;
    }
    
    public ServerThread getClient() {
        return this.client;
    }

    public ServerPlayer(ServerThread client) {
        setClient(client);
    }
    //ea377 11/18/23
    public String getRoundPick() {
        return roundPick;
    }

    public void setRoundPick(String pick) {
        this.roundPick = pick;
    }

    public void addtoTotalScore(long RoundScore) {
        TotalScore+=RoundScore;
    }
    public int getTotalScore() {
        return TotalScore;
    }
}

