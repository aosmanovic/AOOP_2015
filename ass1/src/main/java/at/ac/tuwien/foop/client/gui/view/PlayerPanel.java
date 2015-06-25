package at.ac.tuwien.foop.client.gui.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.domain.ClientPlayer;

public class PlayerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(PlayerPanel.class);

	private JPanel pnlSpectator;
	private JPanel pnlPlayer;

	public PlayerPanel() {
		super();

		log.debug("create player panel");

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

	public void setPlayer(List<ClientPlayer> players) {
		pnlPlayer.removeAll();
		pnlSpectator.removeAll();
		new ArrayList<>(players).forEach(p -> {
			if (p.active()) {
				pnlPlayer.add(new JLabel("   " + p.name()));
			} else {
				pnlSpectator.add(new JLabel("   " + p.name()));
			}
			;
		});
		revalidate();
	}
}
