package at.ac.tuwien.foop.client.userInterface.Views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import at.ac.tuwien.foop.client.domain.Game;

public class StartFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel pnlContent;
	private JPanel pnlConnect;
	private JPanel pnlNewGame;
	private JButton btnConnect;
	private JButton btnNewGame;
	private JTextPane txtpnLog;
	private Game game;

	private JTextField jtfServerAddress;

	/**
	 * Create the frame.
	 */
	public StartFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		pnlContent = new JPanel(new BorderLayout(2, 2));
		getContentPane().add(pnlContent, BorderLayout.CENTER);
//		Container cp = getContentPane();
		pnlContent.setLayout(new BorderLayout(2, 2));

		// TODO: somehow set an outer margin

		// bottom panels
		pnlConnect = prepareServerPanel();
		pnlNewGame = prepareNewGamePanel();
		pnlContent.add(pnlConnect, BorderLayout.SOUTH);

		// log field
		txtpnLog = new JTextPane();
		pnlContent.add(txtpnLog, BorderLayout.CENTER);
		printMessage();
	}

	private JPanel prepareServerPanel() {
		JPanel panel = new JPanel(new BorderLayout(2, 2));

		JLabel label = new JLabel("server address: ");
		panel.add(label, BorderLayout.WEST);

		jtfServerAddress = new JTextField("");
		panel.add(jtfServerAddress, BorderLayout.CENTER);

		btnConnect = new JButton("connect");
		btnConnect.addActionListener(o -> btnConnect.setEnabled(false));
		panel.add(btnConnect, BorderLayout.EAST);

		return panel;
	}

	private JPanel prepareNewGamePanel() {
		JPanel panel = new JPanel(new BorderLayout(2, 2));

		btnNewGame = new JButton("Start");
		btnNewGame.setEnabled(false);
		btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnNewGame.setEnabled(false);
			}
		});
		panel.add(btnNewGame, BorderLayout.EAST);
		return panel;
	}

	public void showFailure() {
		btnNewGame.setEnabled(true);
		JOptionPane.showMessageDialog(null, "Try to connect again...");
	}

	public void showAlreadyConnected() {
		JOptionPane.showMessageDialog(null, "You are already connected...");
	}

	public void addNewGameButtonListener(ActionListener ac) {
		this.btnNewGame.addActionListener(ac);
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
	
	public void showConnectPanel() {
		pnlContent.add(pnlConnect, BorderLayout.SOUTH);
		
	}
	public void showNewGamePanel() {
		pnlContent.add(pnlNewGame, BorderLayout.SOUTH);

	}

	public void printMessage() {
		txtpnLog.setText("Welcome to the Mouse Labyrinth Game! "
				+ "\n \n \n \n \nThere are " + this.countPlayers()
				+ " players connected"
				+ "\n \n \n \n Press start to start the game!");
	}

	public void enableStartButton() {
		btnNewGame.setEnabled(true);
	}

	public void enableConnectButton() {
		btnNewGame.setEnabled(true);
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
