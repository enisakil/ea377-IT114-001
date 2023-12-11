package Project.client.views;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Project.client.Card;
import Project.client.Client;
import Project.client.ICardControls;
import Project.client.IGameEvents;
import Project.common.Phase;
import Project.common.Question;
import Project.server.QuestionDatabase;

public class GamePanel extends JPanel implements IGameEvents {
    private CardLayout cardLayout;
    private JPanel questionPanel;
    private JPanel answersPanel;
    private String[] answerList = Question.getOptions();

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
        createAnswersPanel(answerList);

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
        JLabel questionLabel = new JLabel();
        questionPanel.add(questionLabel);
        this.add(questionPanel, "questionPanel");
    }

    private void createAnswersPanel(String[] answers) {
        answersPanel = new JPanel(new GridLayout(0, 1));

        for (String answer : answers) {
            JButton answerButton = new JButton(answer);
            answerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Send the selected answer to the server
                        Client.INSTANCE.sendAnswer(answer);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            answersPanel.add(answerButton);
        }
        this.add(answersPanel, "answersPanel");
         revalidate();
        repaint();
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
            if (!isVisible()) {
                setVisible(true);
                getParent().revalidate();
                getParent().repaint();
                System.out.println("GamePanel visible");
            } else {
                cardLayout.next(this);
            }
        }
        
    }

    @Override
    public void onReceiveReady(long clientId) {
    }

    @Override
    public void onReceiveQuestionAndAnswers(String question, List<String> answers) {
        JLabel questionLabel = (JLabel) questionPanel.getComponent(0);
        questionLabel.setText(question);

        answersPanel.removeAll();

        for (String answer : answers) {
            JButton answerButton = new JButton(answer);
            answerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Send the selected answer to the server
                        Client.INSTANCE.sendAnswer(answer);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            answersPanel.add(answerButton);
        }
        answersPanel.revalidate();
        answersPanel.repaint();

        // Switch to the answers panel
        cardLayout.show(this, "answersPanel");
    }

}