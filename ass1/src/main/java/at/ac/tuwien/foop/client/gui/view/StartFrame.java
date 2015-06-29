package at.ac.tuwien.foop.client.gui.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;

import at.ac.tuwien.foop.client.gui.utils.RandomNameGenerator;

public class StartFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel pnlContent;
	private JPanel pnlConnect;

	private JButton btnConnect;

	private JTextArea txtpnLog;
	private JTextField tfServerAddress;
	private JTextField tfPlayerName;

	/**
	 * Create the frame.
	 */
	public StartFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Mouse Labyrinth Game");

		pnlContent = new JPanel(new BorderLayout(2, 2));
		pnlContent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlContent, BorderLayout.CENTER);

		// control panels
		pnlConnect = prepareConnectionPanel();
		pnlContent.add(pnlConnect, BorderLayout.NORTH);

		// log field
		txtpnLog = new JTextArea();
		txtpnLog.setEditable(false);
		txtpnLog.setLineWrap(true);
		txtpnLog.setWrapStyleWord(true);
		((DefaultCaret) txtpnLog.getCaret())
				.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		pnlContent.add(new JScrollPane(txtpnLog), BorderLayout.CENTER);
		printMessage("Welcome to the Mouse Labyrinth Game!\n\nA server was started in the background so you can play on localhost or you connect to a remote server.\nChoose a name and have fun! :)\n\n");
	}

	private JPanel prepareConnectionPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

		panel.add(new JLabel("server address: ", SwingConstants.RIGHT),
				gridBagPosition(0, 0, 0.3));

		tfServerAddress = new JTextField("localhost");
		panel.add(tfServerAddress, gridBagPosition(1, 0, 0.7));

		panel.add(new JLabel("player name: ", SwingConstants.RIGHT),
				gridBagPosition(0, 1, 0.3));

		tfPlayerName = new JTextField(RandomNameGenerator.name());
		panel.add(tfPlayerName, gridBagPosition(1, 1, 0.7));

		btnConnect = new JButton("connect");
		btnConnect.addActionListener(o -> disableInputs());
		GridBagConstraints c = gridBagPosition(0, 2, 1);
		c.gridwidth = 2;
		panel.add(btnConnect, c);

		return panel;
	}

	private GridBagConstraints gridBagPosition(int x, int y, double weight) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = x;
		c.gridy = y;
		c.weightx = weight;
		c.insets = new Insets(2, 2, 2, 2);
		return c;
	}

	public void showFailure() {
		printMessage("Could not connect to server!");
		btnConnect.setEnabled(true);
		JOptionPane.showMessageDialog(null, "Try to connect again...");
	}

	public void addConnectButtonListener(ActionListener ac) {
		btnConnect.addActionListener(ac);
	}
	
	public void enableInputs() {
		btnConnect.setEnabled(true);
		tfPlayerName.setEditable(true);
		tfServerAddress.setEnabled(true);
	}
	
	public void disableInputs() {
		btnConnect.setEnabled(false);
		tfPlayerName.setEditable(false);
		tfServerAddress.setEnabled(false);
	}

	public String getServerAddress() {
		return tfServerAddress.getText();
	}

	public String getPlayerName() {
		return tfPlayerName.getText();
	}

	public void printMessage(String msg) {
		txtpnLog.append(msg + "\n");
	}
}
