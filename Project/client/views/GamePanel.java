package Project.client.views;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Project.client.Card;
import Project.client.Client;
import Project.client.ICardControls;
import Project.client.IGameEvents;
import Project.common.Phase;
import Project.common.Question;
import Project.common.TimedEvent;
import Project.server.QuestionDatabase;
import Project.server.GameRoom;
import Project.common.TimedEvent;

public class GamePanel extends JPanel implements IGameEvents {
    private CardLayout cardLayout;
    private JPanel questionPanel;
    private JLabel timerLabel;
    private TimedEvent timer;
    private Map<Long, JLabel> scoreLabels = new HashMap<>();

    public GamePanel(ICardControls controls) {
        super(new CardLayout());
        cardLayout = (CardLayout) this.getLayout();
        this.setName(Card.GAME_SCREEN.name());
        Client.INSTANCE.addCallback(this);
        // this is purely for debugging
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("GamePanel Resized to " + e.getComponent().getSize());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                // System.out.println("Moved to " + e.getComponent().getLocation());
            }
        });

        createReadyPanel();
        createQuestionPanel();

        setVisible(false);
        // don't need to add this to ClientUI as this isn't a primary panel(it's nested
        // in ChatGamePanel)
        // controls.addPanel(Card.GAME_SCREEN.name(), this);
    }

    

    private void createReadyPanel() {
        JPanel readyPanel = new JPanel();
        JButton readyButton = new JButton();
        readyButton.setText("Ready");
        readyButton.addActionListener(l -> {
            try {
                Client.INSTANCE.sendReadyStatus();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        readyPanel.add(readyButton);
        this.add(readyPanel);
    }

    private void createQuestionPanel() {
        questionPanel = new JPanel();

        JLabel timerLabel = new JLabel("Timer: 60 seconds"); // Initialize with the starting time
        questionPanel.add(timerLabel);

        JLabel questionLabel = new JLabel("Question: ");
        JButton a = new JButton();
        a.setText("A");
        a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button A clicked");
                try {
                    Client.INSTANCE.sendAnswer("A");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        JButton b = new JButton();
        b.setText("B");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button B clicked");
                try {
                    Client.INSTANCE.sendAnswer("B");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        JButton c = new JButton();
        c.setText("C");
        c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button C clicked");
                try {
                    Client.INSTANCE.sendAnswer("C");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        
        JButton d = new JButton();
        d.setText("D");
        d.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button D clicked");
                try {
                    Client.INSTANCE.sendAnswer("D");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        questionPanel.add(questionLabel);
        questionPanel.add(a);
        questionPanel.add(b);
        questionPanel.add(c);
        questionPanel.add(d);
        this.add(questionPanel, "questionPanel");

        timer = new TimedEvent(60, () -> {
            timerLabel.setText("Time's up!");
            // Handle timer expiration logic
        });
        timer.setTickCallback(tick -> {
            timerLabel.setText("Time remaining: " + tick + " seconds");
        });

    questionPanel.add(timerLabel);

    }

    private void createTimerLabel() {
        timerLabel = new JLabel("Timer: ");
        this.add(timerLabel);
    }

        private void createScorePanel() {
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(0, 1));
    
        for (Map.Entry<Long, JLabel> entry : scoreLabels.entrySet()) {
            JLabel scoreLabel = new JLabel();
            scoreLabel.setText(entry.getValue().getText());
            scorePanel.add(scoreLabel);
        }
    
        this.add(scorePanel, "scorePanel");
        cardLayout.show(this, "scorePanel");
    }

    
        



    @Override
    public void onClientConnect(long id, String clientName, String message) {
    }

    @Override
    public void onClientDisconnect(long id, String clientName, String message) {
    }

    @Override
    public void onMessageReceive(long id, String message) {
    }

    @Override
    public void onReceiveClientId(long id) {
    }

    @Override
    public void onSyncClient(long id, String clientName) {
    }

    @Override
    public void onResetUserList() {
    }

    @Override
    public void onReceiveRoomList(String[] rooms, String message) {
    }

    @Override
    public void onRoomJoin(String roomName) {
    }

    @Override
    public void onReceivePhase(Phase phase) {
        System.out.println("Received phase: " + phase.name());
        if (phase == Phase.READY) {
            setVisible(true);
            getParent().revalidate();
            getParent().repaint();
            System.out.println("GamePanel visible");
        } else if (phase == Phase.END_ROUND){
            cardLayout.show(this, "scorePanel");
            setVisible(true);
            getParent().revalidate();
            getParent().repaint();
        } else {
            // Assuming roundTimer is the duration of the round in seconds
            cardLayout.show(this, "questionPanel");
            setVisible(true);
            getParent().revalidate();
            getParent().repaint();
            }
        }
        

    @Override
    public void onReceiveReady(long clientId) {
    }

    //ea377 12/12/23
    @Override
    public void onReceiveQuestionAndAnswers(String question, String[] answers) {
        System.out.println("question: " + question);
        System.out.println("Answers: " + String.join(",", answers));

        JLabel questionLabel = (JLabel) questionPanel.getComponent(0);
        questionLabel.setText(question);
        

        Component[] components = questionPanel.getComponents();
        List<JButton> answerButtons = new ArrayList<>();

        for (Component component : components) {
            if (component instanceof JButton) {
                answerButtons.add((JButton) component);
            }
        }

        for (int i = 0; i < answers.length && i < answerButtons.size(); i++) {
            // Set the text for the answer button (e.g., A, B, C, D)
            answerButtons.get(i).setText(Character.toString((char) ('A' + i)) + ". " + answers[i]);
        }

        // Switch to the answers panel
        cardLayout.show(this, "questionPanel");
    }

    @Override
    public void onUpdateScore(long playerId, int score) {
        // Update the score label for the specified player
    SwingUtilities.invokeLater(() -> {
        JLabel scoreLabel = scoreLabels.get(playerId);
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + score);
        } else {
            JLabel newScoreLabel = new JLabel("Score: " + score);
            scoreLabels.put(playerId, newScoreLabel);
            add(newScoreLabel);
            revalidate();
            repaint();
        }
        createScorePanel();
    });
}


}