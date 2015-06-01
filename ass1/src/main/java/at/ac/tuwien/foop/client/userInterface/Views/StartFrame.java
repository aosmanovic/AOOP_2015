package at.ac.tuwien.foop.client.userInterface.Views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import at.ac.tuwien.foop.client.RandomNameGenerator;
import at.ac.tuwien.foop.client.domain.Game;

public class StartFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private Game game;

	private JPanel pnlContent;
	private JPanel pnlConnect;
	private JPanel pnlJoinGame;
	private JPanel pnlStartGame;

	private JButton btnConnect;
	private JButton btnJoinGame;
	private JButton btnStartGame;
	private JButton btnLeaveGame;

	private JTextArea txtpnLog;
	private JTextField jtfServerAddress;
	private JTextField jtfPlayerName;

	/**
	 * Create the frame.
	 */
	public StartFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		pnlContent = new JPanel(new BorderLayout(2, 2));
		pnlContent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(new BorderLayout(2, 2));

		// TODO: somehow set an outer margin

		// bottom panels
		pnlConnect = prepareServerPanel();
		pnlJoinGame = prepareJoinGamePanel();
		pnlStartGame = prepareStartGamePanel();
		pnlContent.add(pnlConnect, BorderLayout.SOUTH);

		// log field
		txtpnLog = new JTextArea();
		txtpnLog.setEditable(false);
		((DefaultCaret)txtpnLog.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		pnlContent.add(new JScrollPane(txtpnLog), BorderLayout.CENTER);
		printMessage("Welcome to the Mouse Labyrinth Game!\n\n");
	}

	private JPanel prepareServerPanel() {
		JPanel panel = new JPanel(new BorderLayout(2, 2));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JLabel label = new JLabel("server address: ");
		panel.add(label, BorderLayout.WEST);

		jtfServerAddress = new JTextField("localhost");
		panel.add(jtfServerAddress, BorderLayout.CENTER);

		btnConnect = new JButton("connect");
		btnConnect.addActionListener(o -> btnConnect.setEnabled(false));
		panel.add(btnConnect, BorderLayout.EAST);

		return panel;
	}

	private JPanel prepareJoinGamePanel() {
		JPanel panel = new JPanel(new BorderLayout(2, 2));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JLabel label = new JLabel("player name: ");
		panel.add(label, BorderLayout.WEST);

		jtfPlayerName = new JTextField(RandomNameGenerator.name());
		panel.add(jtfPlayerName, BorderLayout.CENTER);

		btnJoinGame = new JButton("join");
		btnJoinGame.addActionListener(e -> btnJoinGame.setEnabled(false));
		panel.add(btnJoinGame, BorderLayout.EAST);
		return panel;
	}

	private JPanel prepareStartGamePanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 0));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		btnStartGame = new JButton("start");
		btnStartGame.addActionListener(e -> btnStartGame.setEnabled(false));
		panel.add(btnStartGame, BorderLayout.EAST);

		btnLeaveGame = new JButton("disconnect");
		btnLeaveGame.addActionListener(e -> showConnectPanel());
		panel.add(btnLeaveGame, BorderLayout.EAST);
		return panel;
	}

	public void showFailure() {
		btnConnect.setEnabled(true);
		JOptionPane.showMessageDialog(null, "Try to connect again...");
	}

	public void showAlreadyConnected() {
		JOptionPane.showMessageDialog(null, "You are already connected...");
	}

	public void addJoinGameButtonListener(ActionListener ac) {
		this.btnJoinGame.addActionListener(ac);
	}

	public void addConnectButtonListener(ActionListener ac) {
		this.btnConnect.addActionListener(ac);
	}

	public void addDisconnectButtonListener(ActionListener ac) {
		this.btnLeaveGame.addActionListener(ac);
	}

	public void addStartGameButtonListener(ActionListener ac) {
		this.btnStartGame.addActionListener(ac);
	}

	public int countPlayers() {
		int i = game == null ? 0 : game.getPlayers().size();
		return i;
	}

	public String getServerAddress() {
		return jtfServerAddress.getText();
	}

	public String getPlayerName() {
		return jtfPlayerName.getText();
	}

	public void showConnectPanel() {
		btnConnect.setEnabled(true);

		pnlContent.remove(pnlStartGame);
		pnlContent.add(pnlConnect, BorderLayout.SOUTH);
		pnlContent.validate();
		pnlContent.repaint();
	}

	public void showJoinGamePanel() {
		btnJoinGame.setEnabled(true);

		pnlContent.remove(pnlConnect);
		pnlContent.add(pnlJoinGame, BorderLayout.SOUTH);
		pnlContent.validate();
		pnlContent.repaint();
	}

	public void showStartGamePanel() {
		btnStartGame.setEnabled(true);

		pnlContent.remove(pnlJoinGame);
		pnlContent.add(pnlStartGame, BorderLayout.SOUTH);
		pnlContent.validate();
		pnlContent.repaint();
	}

	public void printMessage(String msg) {
		txtpnLog.append(msg + "\n");
	}

	public void enableStartButton() {
		btnJoinGame.setEnabled(true);
	}

	public void enableConnectButton() {
		btnJoinGame.setEnabled(true);
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
