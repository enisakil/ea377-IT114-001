package Project.client;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Project.client.views.ChatGamePanel;
import Project.client.views.ConnectionPanel;
import Project.client.views.Menu;
import Project.client.views.RoomsPanel;
import Project.client.views.UserInputPanel;
import Project.common.Constants;
import Project.common.Payload;

public class ClientUI extends JFrame implements IClientEvents, ICardControls {
    CardLayout card = null;// accessible so we can call next() and previous()
    Container container;// accessible to be passed to card methods
    String originalTitle = null;
    private static Logger logger = Logger.getLogger(ClientUI.class.getName());
    private JPanel currentCardPanel = null;
    private Card currentCard = Card.CONNECT;

    private Hashtable<Long, String> userList = new Hashtable<Long, String>();

    private long myId = Constants.DEFAULT_CLIENT_ID;
    private JMenuBar menu;
    // Panels
    private ConnectionPanel csPanel;
    private UserInputPanel inputPanel;
    private RoomsPanel roomsPanel;
    private ChatGamePanel chatGamePanel;

    public ClientUI(String title) {
        super(title);// call the parent's constructor
        originalTitle = title;
        container = getContentPane();

        setMinimumSize(new Dimension(600, 400));
        // centers window
        setLocationRelativeTo(null);
        card = new CardLayout();
        setLayout(card);
        // menu
        menu = new Menu(this);
        this.setJMenuBar(menu);
        // separate views
        csPanel = new ConnectionPanel(this);
        inputPanel = new UserInputPanel(this);
        chatGamePanel = new ChatGamePanel(this);

        roomsPanel = new RoomsPanel(this);

        // https://stackoverflow.com/a/9093526
        // this tells the x button what to do (updated to be controlled via a prompt)
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                int response = JOptionPane.showConfirmDialog(container,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    try {
                        Client.INSTANCE.sendDisconnect();
                    } catch (NullPointerException | IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        });
        // lastly
        pack();// tells the window to resize itself and do the layout management
        setVisible(true);
    }

    private void findAndSetCurrentPanel() {
        for (Component c : container.getComponents()) {
            if (c.isVisible()) {
                currentCardPanel = (JPanel) c;
                currentCard = Enum.valueOf(Card.class, currentCardPanel.getName());
                // if we're not connected don't access anything that requires a connection
                if (myId == Constants.DEFAULT_CLIENT_ID && currentCard.ordinal() >= Card.CHAT.ordinal()) {
                    show(Card.CONNECT.name());
                }
                break;
            }
        }
        System.out.println(currentCardPanel.getName());
    }

    @Override
    public void next() {
        card.next(container);
        findAndSetCurrentPanel();
    }

    @Override
    public void previous() {
        card.previous(container);
        findAndSetCurrentPanel();
    }

    @Override
    public void show(String cardName) {
        card.show(container, cardName);
        findAndSetCurrentPanel();
    }

    @Override
    public void addPanel(String cardName, JPanel panel) {
        this.add(cardName, panel);
    }

    @Override
    public void connect() {
        String username = inputPanel.getUsername();
        String host = csPanel.getHost();
        int port = csPanel.getPort();
        setTitle(originalTitle + " - " + username);
        Client.INSTANCE.connect(host, port, username, this);
        // TODO add connecting screen/notice
    }

    public static void main(String[] args) {
        new ClientUI("Client");
    }

    private String mapClientId(long clientId) {
        String clientName = userList.get(clientId);
        if (clientName == null) {
            clientName = "Server";
        }
        return clientName;
    }

    /**
     * Used to handle new client connects/disconnects or existing client lists (one
     * by one)
     * 
     * @param clientId
     * @param clientName
     * @param isConnect
     */
    private synchronized void processClientConnectionStatus(long clientId, String clientName, boolean isConnect) {
        if (isConnect) {
            if (!userList.containsKey(clientId)) {
                logger.log(Level.INFO, String.format("Adding %s[%s]", clientName, clientId));
                userList.put(clientId, clientName);
                chatGamePanel.getChatPanel().addUserListItem(clientId, String.format("%s (%s)", clientName, clientId));
            }
        } else {
            if (userList.containsKey(clientId)) {
                logger.log(Level.INFO, String.format("Removing %s[%s]", clientName, clientId));
                userList.remove(clientId);
                chatGamePanel.getChatPanel().removeUserListItem(clientId);
            }
            if (clientId == myId) {
                logger.log(Level.INFO, "I disconnected");
                myId = Constants.DEFAULT_CLIENT_ID;
                previous();
            }
        }
    }

    @Override
    public void onClientConnect(long clientId, String clientName, String message) {
        if (currentCard.ordinal() >= Card.CHAT.ordinal()) {
            processClientConnectionStatus(clientId, clientName, true);
            chatGamePanel.getChatPanel().addText(String.format("*%s %s*", clientName, message));
        }
    }

    @Override
    public void onClientDisconnect(long clientId, String clientName, String message) {
        if (currentCard.ordinal() >= Card.CHAT.ordinal()) {
            processClientConnectionStatus(clientId, clientName, false);
            chatGamePanel.getChatPanel().addText(String.format("*%s %s*", clientName, message));
        }
    }

    @Override
    public void onMessageReceive(long clientId, String message) {
        if (currentCard.ordinal() >= Card.CHAT.ordinal()) {
            String clientName = mapClientId(clientId);
            chatGamePanel.getChatPanel().addText(String.format("%s: %s", clientName, message));
        }
    }

    @Override
    public void onReceiveClientId(long id) {
        if (myId == Constants.DEFAULT_CLIENT_ID) {
            myId = id;
            // show(Card.CHAT.name());
            show(Card.CHAT_GAME_SCREEN.name());
        } else {
            logger.log(Level.WARNING, "Received client id after already being set, this shouldn't happen");
        }
    }

    @Override
    public void onResetUserList() {
        userList.clear();
        chatGamePanel.getChatPanel().clearUserList();
    }

    @Override
    public void onSyncClient(long clientId, String clientName) {
        if (currentCard.ordinal() >= Card.CHAT.ordinal()) {
            processClientConnectionStatus(clientId, clientName, true);
        }
    }

    @Override
    public void onReceiveRoomList(String[] rooms, String message) {
        roomsPanel.removeAllRooms();
        if (message != null && message.length() > 0) {
            roomsPanel.setMessage(message);
        }
        if (rooms != null) {
            for (String room : rooms) {
                roomsPanel.addRoom(room);
            }
        }
    }
    @Override
    public void onRoomJoin(String roomName) {
        if (currentCard.ordinal() >= Card.CHAT.ordinal()) {
            chatGamePanel.getChatPanel().addText("Joined room " + roomName);
        }
    }
    @Override
    public void onReceiveQuestionAndAnswers(String question, List<String> answers) {
        // Update your UI with the received question and answers
        // Example: Assuming you have a method updateUIWithQuestionAndAnswers in your client UI class
        updateUIWithQuestionAndAnswers(question, answers);
    }

    private void updateUIWithQuestionAndAnswers(String question, List<String> answers) {
        // Add your logic to update the UI with the question and answers
        // For example, you might want to display the question and answers in a specific panel
        chatGamePanel.getChatPanel().addText("Received Question: " + question);
        chatGamePanel.getChatPanel().addText("Possible Answers: " + String.join(", ", answers));
        // Add more UI update logic as needed
    }
}