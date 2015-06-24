package at.ac.tuwien.foop.client.gui.view;

import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel pnlSpectator;
	private JPanel pnlPlayer;

	public PlayerPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		pnlPlayer = prepareListPanel();
		pnlSpectator = prepareListPanel();

		add(new JLabel("Players"));
		add(pnlPlayer);
		add(new JLabel("Spectators"));
		add(pnlSpectator);
	}

	private JPanel prepareListPanel() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		return p;
	}

	public void setPlayer(List<String> names) {
		pnlPlayer.removeAll();
		names.forEach(name -> pnlPlayer.add(new JLabel("   " + name)));
	}

	public void setSpectators(List<String> names) {
		pnlPlayer.removeAll();
		names.forEach(name -> pnlPlayer.add(new JLabel("   " + name)));
	}
	
	// TODO: remove when tested
	public static void main(String args[]) {
		JFrame frame = new JFrame("test");
		PlayerPanel panel = new PlayerPanel();
		frame.add(panel);
		frame.setBounds(10, 10, 300, 300);
		frame.setVisible(true);
		
		panel.setPlayer(Arrays.asList("foo", "bar"));
	}
}
