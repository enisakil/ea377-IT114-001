package Project.common;

//ea377 11/15/23
//ea377 11/18/23
//Added READY, PICK, START_GAME, SCORE payload types
public enum PayloadType {
    CONNECT, DISCONNECT, MESSAGE, CLIENT_ID, RESET_USER_LIST,
    SYNC_CLIENT, CREATE_ROOM, JOIN_ROOM, GET_ROOMS,
    READY, PHASE, PICK, START_GAME, SCORE, ANSWER, QUESTION_ANSWERS
}