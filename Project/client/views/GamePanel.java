package Project.client.views;

import java.awt.CardLayout;
import java.awt.Component;
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

    private void resetView() {
        removeAll();
        revalidate();
        repaint();
    }

    private void showQuestion(String question, List<String> answerOptions) {
        resetView();

        JLabel questionLabel = new JLabel(question);
        add(questionLabel);

        for (String answer : answerOptions) {
            JButton answerButton = new JButton(answer);
            answerButton.addActionListener(e -> handleAnswer(answer));
            add(answerButton);
        }
        revalidate();
        repaint();
    }
    private void handleAnswer(String answer) {
        try {
            Client.INSTANCE.sendAnswer(answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }

}