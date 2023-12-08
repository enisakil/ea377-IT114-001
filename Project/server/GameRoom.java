package Project.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import Project.common.Constants;
import Project.common.Payload;
import Project.common.PayloadType;
import Project.common.Phase;
import Project.common.Question;
import Project.common.TimedEvent;

public class GameRoom extends Room {
    Phase currentPhase = Phase.READY;
    private static Logger logger = Logger.getLogger(GameRoom.class.getName());
    private TimedEvent readyTimer = null;
    private ConcurrentHashMap<Long, ServerPlayer> players = new ConcurrentHashMap<Long, ServerPlayer>();
    //ea377 11/15/23
    private QuestionDatabase questionDatabase;
    //ea377 11/18/23
    private Map<Long, String> playerPicks = new ConcurrentHashMap<>();
    //ea377 11/18/23
    private int totalPlayers = 0;
    //ea377 11/18/23
    private TimedEvent roundTimer;

    //ea377 11/15/23
    public GameRoom(String name, QuestionDatabase questionDatabase) {
        super(name);
        this.questionDatabase = questionDatabase;
    }
    public void startGameSession() {
        // Get a random category
        String randomCategory = questionDatabase.getRandomCategory();

        if (randomCategory != null) {
            // Get a random question for the random category
            Question randomQuestion = questionDatabase.getRandomQuestion(randomCategory);

            if (randomQuestion != null) {
                // Broadcast the start of the game session and the random question
                broadcast("Game session started! Category: " + randomCategory);
                broadcast("Question: " + randomQuestion.getText()); // Updated line
                broadcast("Potential answers: " + Arrays.toString(randomQuestion.getOptions()));

                //Call the handleGameLogic method to take
                // TODO: handle the logic for answering the question here
                handleGameLogic(randomQuestion);

                // Start a timer for the game session or perform other actions
                //Probably should delete this since HandleGameLogic already makes a call to startRoundTimer
                //startRoundTimer(60);

                endRound();
            } else {
                // Handle the case where there are no questions in the random category
                broadcast("Game session cannot start. No questions available in the category.");
            }
        } else {
            // Handle the case where there are no categories available
            broadcast("Game session cannot start. No categories available.");
        }
    }
    private Question currentQuestion;

    // Set the current question for later reference

//ea377 11/21/23
    private void handleGameLogic(Question question) {
        currentQuestion = question;
        // TODO: Implement game logic using questions
        startRoundTimer(60);

        processPlayerAnswers();



    }
    private void processPlayerAnswers() {
    // Create a map to store player scores based on their response time
    Map<ServerPlayer, Long> playerScores = new HashMap<>();

    // Collect answers from players within the round timer
    new TimedEvent(60, () -> {
        playerPicks.forEach((playerId, pickedAnswer) -> {
            ServerPlayer player = getPlayerById(playerId);
            if (player != null) {
                boolean isCorrect = isAnswerCorrect(pickedAnswer);
                long responseTime = roundTimer.getRemainingTime() / 1000; // in seconds

                // Award points based on correctness and response time
                long points = calculatePoints(isCorrect, responseTime);

                // Update player scores
                playerScores.put(player, points);
                
                // Update player's total score
                player.addtoTotalScore(points);
            }
        });

        // Broadcast the scores to all players
        broadcastScores(playerScores);

        // Reset for the next round
        resetRound();
    });
}
//ea377 11/21/23
private boolean isAnswerCorrect(String pickedAnswer) {
    // Compare the picked answer with the correct answer
    int correctOptionIndex = currentQuestion.getCorrectOptionIndex();
    return currentQuestion.getOptions()[correctOptionIndex].equalsIgnoreCase(pickedAnswer);
}
private long calculatePoints(boolean isCorrect, long responseTime) {
    // TODO: Customize the points calculation based on your game's scoring rules
    // For example, you can give more points for correct answers and quicker response times
    if(isCorrect) {
        long basePoints = isCorrect ? 10 : 0;
        long timeBonus = Math.max(0, 10 - responseTime); //10 bonus points for quicker responses
        return basePoints + timeBonus;
    } else {
        return 0;
}
}
private ServerPlayer getPlayerById(long playerId) {
    return players.get(playerId);
}







    @Override
    protected void addClient(ServerThread client) {
        logger.info("Adding client as player");
        players.computeIfAbsent(client.getClientId(), id -> {
            ServerPlayer player = new ServerPlayer(client);
            super.addClient(client);
            //ea377 11/18/23
            totalPlayers++;
            client.sendPhaseSync(currentPhase);
            logger.info(String.format("Total clients %s", clients.size()));
            return player;
        });
    }
    //ea377 11/15/23
    public void setQuestionDatabase(QuestionDatabase questionDatabase) {
        this.questionDatabase = questionDatabase;
    }

    protected void setReady(ServerThread client) {
        logger.info("Ready check triggered");
        if (currentPhase != Phase.READY) {
            logger.warning(String.format("readyCheck() incorrect phase: %s", Phase.READY.name()));
            return;
        }
        if (readyTimer == null) {
            sendMessage(null, "Ready Check Initiated, 30 seconds to join");
            readyTimer = new TimedEvent(30, () -> {
                readyTimer = null;
                readyCheck(true);
            });
        }
        players.values().stream().filter(p -> p.getClient().getClientId() == client.getClientId()).findFirst()
                .ifPresent(p -> {
                    p.setReady(true);
                    logger.info(String.format("Marked player %s[%s] as ready", p.getClient().getClientName(), p
                            .getClient().getClientId()));
                    syncReadyStatus(p.getClient().getClientId());
                });
        readyCheck(false);
    }
// ea377 11/18/23
    private void readyCheck(boolean timerExpired) {
        if (currentPhase != Phase.READY) {
            return;
        }
        // two examples for the same result
        // int numReady = players.values().stream().mapToInt((p) -> p.isReady() ? 1 :
        // 0).sum();
        long numReady = players.values().stream().filter(ServerPlayer::isReady).count();
        if (numReady >= Constants.MINIMUM_PLAYERS) {
            // ea377 11/15/23
            startGameSession();
            sendMessage(null, "Starting session");

            if (timerExpired) {
                sendMessage(null, "Ready Timer expired, starting session");
                start();
            } else if (numReady >= players.size()) {
                sendMessage(null, "Everyone in the room marked themselves ready, starting session");
                if (readyTimer != null) {
                    readyTimer.cancel();
                    readyTimer = null;
                }
                startGameSession();
            }

        } else {
            if (timerExpired) {
                resetSession();
                sendMessage(null, "Ready Timer expired, not enough players. Resetting ready check");
            }
        }
    }
    //ea377 11/18/23
    private void startRoundTimer(int seconds) {
        updatePhase(Phase.IN_PROGRESS);
    // TODO example
    sendMessage(null, "New Round started");
    roundTimer = new TimedEvent(seconds, () -> {
        // Action to perform when the timer reaches 0 (e.g., reset the session)
        endRound();
    }); // Start the timer
        }

        
    

    //ea37 11/18/23
    public void handlePlayerPick(ServerThread client, String pickedAnswer) {
        // Store the pick in a data structure, e.g., a Map<Long, String>
        playerPicks.put(client.getClientId(), pickedAnswer);
        client.sendMessage(Constants.DEFAULT_CLIENT_ID, "Recorded pick of " + pickedAnswer);
    
        // Check if it's the end of the round
        if (allPlayersPicked() || roundTimerExpired()) {
            endRound();
        }
    }
    
    
    private boolean allPlayersPicked() {
        // Check if picks have been received from all players
        return playerPicks.size() == totalPlayers;
    }
    
    private boolean roundTimerExpired() {
        // Check if the round timer has expired
        // Implement your logic to track the round time
        return roundTimer.getRemainingTime() <= 0;
    }
    
    //ea377 11/21/23
    private void endRound() {
        // Process picks and calculate scores only if everyone has picked or timer expired
    if (allPlayersPicked() || roundTimerExpired()) {
        // Iterate through all players
        for (ServerPlayer player : players.values()) {
            long playerId = player.getClient().getClientId();

            if (playerPicks.containsKey(playerId)) {
                // Player submitted an answer, evaluate and calculate points
                String pickedAnswer = playerPicks.get(playerId);
                boolean isCorrect = isAnswerCorrect(pickedAnswer);
                long responseTime = roundTimer.getRemainingTime() / 1000; // in seconds
                long playerScore = calculatePoints(isCorrect, responseTime);

                // Update player's total score
                player.addtoTotalScore(playerScore);

                // Send each player their correct answer and score
                String correctAnswer = currentQuestion.getOptions()[currentQuestion.getCorrectOptionIndex()];
                String message = "The correct answer was: " + correctAnswer +
                        ". Your score for the round is: " + playerScore +
                        ". Your total score is now: " + player.getTotalScore();
                player.getClient().sendMessage(Constants.DEFAULT_CLIENT_ID, message);
            } else {
                // Player did not submit an answer, present the correct answer
                String correctAnswer = currentQuestion.getOptions()[currentQuestion.getCorrectOptionIndex()];
                String message = "The correct answer was: " + correctAnswer +
                        ". You did not submit an answer for this round.";
                player.getClient().sendMessage(Constants.DEFAULT_CLIENT_ID, message);
            }
        }
        broadcastPicks();
        // Delay before starting the next round (adjust the duration as needed)
        int delayInSeconds = 5; // 5 seconds delay
        new TimedEvent(delayInSeconds, this::startNextRound);
        // Calculate scores, broadcast results, etc.
        // Reset for the next roundF
        resetRound();
    }
}
private void startNextRound() {
    // Add logic to start the next round
    // For example, you can call startGameSession() or handleGameLogic() with a new question
    startGameSession();
}
    private void broadcastPicks() {
    // Broadcast picks to all players
    playerPicks.forEach((playerId, pick) -> {
        // Create a Payload and send it to each player
        Payload pickPayload = new Payload();
        pickPayload.setPayloadType(PayloadType.PICK);
        pickPayload.setClientId(playerId);
        pickPayload.setClientName(getPlayerNameById(playerId));
        pickPayload.setMessage(pick);
        broadcast(pickPayload.toString());
    });
    }
    private void broadcastScores(Map<ServerPlayer, Long> playerScores) {
        // Broadcast scores to all players
        playerScores.forEach((player, points) -> {
            Payload scorePayload = new Payload();
            scorePayload.setPayloadType(PayloadType.SCORE);
            scorePayload.setClientId(player.getClient().getClientId());
            scorePayload.setClientName(player.getClient().getClientName());
            scorePayload.setMessage(String.valueOf(points));
    
            broadcast(scorePayload.toString());
        });
    }
    
    private void resetRound() {
        // Reset any data structures used for the round
        playerPicks.clear();
        // Other reset logic
    }

    private String getPlayerNameById(long playerId) {
        ServerPlayer player = players.get(playerId);
        return (player != null) ? player.getClient().getClientName() : "Unknown";
    }

    //^ea377 11/18/2023

    //WILL NOT BE USING START METHOD BELOW
    private void start() {
        updatePhase(Phase.IN_PROGRESS);
        // TODO example
        sendMessage(null, "Session started");
        new TimedEvent(30, () -> resetSession())
                .setTickCallback((time) -> {
                    sendMessage(null, String.format("Example running session, time remaining: %s", time));
                });
        Question randomQuestion = questionDatabase.getRandomQuestion("Geography"); // Replace "SomeCategory" with the actual category
    if (randomQuestion != null) {
        sendMessage(null, "Session started. Here's your question:");
        sendMessage(null, randomQuestion.getText());
        sendMessage(null, "Options: " + String.join(", ", randomQuestion.getOptions()));
    } else {
        sendMessage(null, "No questions available in the specified category. Ending session.");
        resetSession();
    }

    new TimedEvent(30, () -> resetSession())
        .setTickCallback((time) -> {
            sendMessage(null, String.format("Example running session, time remaining: %s", time));
        });
    }

    private synchronized void resetSession() {
        players.values().stream().forEach(p -> p.setReady(false));
        updatePhase(Phase.READY);
        sendMessage(null, "Session ended, please intiate ready check to begin a new one");
    }

    private void updatePhase(Phase phase) {
        if (currentPhase == phase) {
            return;
        }
        currentPhase = phase;
        // NOTE: since the collection can yield a removal during iteration, an iterator
        // is better than relying on forEach
        Iterator<ServerPlayer> iter = players.values().stream().iterator();
        while (iter.hasNext()) {
            ServerPlayer client = iter.next();
            boolean success = client.getClient().sendPhaseSync(currentPhase);
            if (!success) {
                handleDisconnect(client);
            }
        }
    }

    protected void handleDisconnect(ServerPlayer player) {
        if (players.containsKey(player.getClient().getClientId())) {
            players.remove(player.getClient().getClientId());
            super.handleDisconnect(null, player.getClient());
            totalPlayers--;
            logger.info(String.format("Total clients %s", clients.size()));
            sendMessage(null, player.getClient().getClientName() + " disconnected");
            if (players.isEmpty()) {
                close();
            }
        }
    }

    private void syncReadyStatus(long clientId) {
        Iterator<ServerPlayer> iter = players.values().stream().iterator();
        while (iter.hasNext()) {
            ServerPlayer client = iter.next();
            boolean success = client.getClient().sendReadyStatus(clientId);
            if (!success) {
                handleDisconnect(client);
            }
        }
    }

}