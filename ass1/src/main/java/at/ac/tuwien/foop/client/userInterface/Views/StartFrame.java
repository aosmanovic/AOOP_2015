package at.ac.tuwien.foop.client.userInterface.Views;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

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

	private JTextPane txtpnLog;
	private JTextField jtfServerAddress;
	private JTextField jtfPlayerName;

	/**
	 * Create the frame.
	 */
	public StartFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		pnlContent = new JPanel(new BorderLayout(2, 2));
		getContentPane().add(pnlContent, BorderLayout.CENTER);
		// Container cp = getContentPane();
		pnlContent.setLayout(new BorderLayout(2, 2));

		// TODO: somehow set an outer margin

		// bottom panels
		pnlConnect = prepareServerPanel();
		pnlJoinGame = prepareJoinGamePanel();
		pnlContent.add(pnlConnect, BorderLayout.SOUTH);

		// log field
		txtpnLog = new JTextPane();
		txtpnLog.setEditable(false);
		pnlContent.add(txtpnLog, BorderLayout.CENTER);
		printMessage("Welcome to the Mouse Labyrinth Game!\n\n");
	}

	private JPanel prepareServerPanel() {
		JPanel panel = new JPanel(new BorderLayout(2, 2));

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
		JPanel panel = new JPanel(new BorderLayout(2, 2));
		
		jtfPlayerName = new JTextField(RandomNameGenerator.name());
		panel.add(jtfPlayerName, BorderLayout.CENTER);
		
		btnStartGame = new JButton("start");
		btnStartGame.addActionListener(e -> btnJoinGame.setEnabled(false));
		panel.add(btnStartGame, BorderLayout.EAST);
		
		btnLeaveGame = new JButton("leave");
		btnLeaveGame.addActionListener(e -> btnJoinGame.setEnabled(false));
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
		pnlContent.add(pnlConnect, BorderLayout.SOUTH);
		pnlContent.remove(pnlJoinGame);
	}

	public void showJoinGamePanel() {
		pnlContent.add(pnlJoinGame, BorderLayout.SOUTH);
		pnlContent.remove(pnlConnect);
	}

	public void printMessage(String msg) {
		txtpnLog.setText(String.join("\n", txtpnLog.getText(), msg));
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
