package Project.common;

import java.io.Serializable;
import java.util.List;

public class Payload implements Serializable {
    // read https://www.baeldung.com/java-serial-version-uid
    private static final long serialVersionUID = 1L;// change this if the class changes

    /**
     * Determines how to process the data on the receiver's side
     */
    private PayloadType payloadType;

    public PayloadType getPayloadType() {
        return payloadType;
    }

    public void setPayloadType(PayloadType payloadType) {
        this.payloadType = payloadType;
    }

    /**
     * Who the payload is from
     */
    private String clientName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    private long clientId;

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * Generic text based message
     */
    private String message;

    public String getMessage() {
        return message;
    }
    //ea377 11/15/23
    private List<Long> playerIds;

    public List<Long> getPlayerIds() {
        return playerIds;
    }
    public void setPlayerIds(List<Long> playerIds) {
        this.playerIds = playerIds;
    }

    private int chosenAnswer;

    public int getChosenAnswer() {
        return chosenAnswer;
    }

    public void setChosenAnswer(int chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }

    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    //ea377 11/15/23
    public void setMessage(String message) {
        this.message = message;
    }
    // ea377 11/15/23
    @Override
    public String toString() {
        return String.format("Type[%s],ClientId[%s,] ClientName[%s], Message[%s], PlayerIds[%s], ChosenAnswer[%s], Score[%s]", payloadType.toString(), clientId, clientName, message, playerIds, chosenAnswer, score);
    }
}