package at.ac.tuwien.foop.client.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(BoardFrame.class);

	public BoardFrame() {
		super();

		log.debug("create board frame");

		setTitle("Maze game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
	}

	public void setBoard(BoardPanel b, PlayerPanel p, CompassPanel c, MessagePanel m) {
		log.debug("set panels");

		JPanel sidePanel = new JPanel(new BorderLayout(2, 2));
		sidePanel.add(p, BorderLayout.CENTER);
		sidePanel.add(c, BorderLayout.SOUTH);

		b.add(m, BorderLayout.CENTER);
		
		getContentPane().add(b, BorderLayout.WEST);
		getContentPane().add(sidePanel, BorderLayout.CENTER);
	}
}
