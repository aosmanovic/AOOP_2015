package at.ac.tuwien.foop.client.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import at.ac.tuwien.foop.client.gui.utils.FontStore;

public class MessagePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel lbltopMessage;
	private JLabel lblbottomMessage;

	public MessagePanel() {
		super();

		Font bigFont = FontStore.getInstance().getGameFont().deriveFont(32f);
		
		lbltopMessage = new JLabel("", JLabel.CENTER);
		lbltopMessage.setFont(bigFont);
		lbltopMessage.setForeground(Color.RED);
		lbltopMessage.setOpaque(false);

		lblbottomMessage = new JLabel("", JLabel.CENTER);
		lblbottomMessage.setFont(bigFont);
		lblbottomMessage.setForeground(Color.RED);
		lblbottomMessage.setOpaque(false);

		setLayout(new BorderLayout(10, 10));
		add(lbltopMessage, BorderLayout.NORTH);
		add(lblbottomMessage, BorderLayout.SOUTH);
		
		setOpaque(false);
	}

	public void setTopMessage(String msg) {
		lbltopMessage.setText(msg);
	}

	public void setBottomMessage(String msg) {
		lblbottomMessage.setText(msg);
	}
}
