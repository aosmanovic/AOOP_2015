package at.ac.tuwien.foop.client.userInterface.Views;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import at.ac.tuwien.foop.client.domain.Game;


public class StartView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JButton btnNewButton;
	private JTextPane txtpnWelcomeToThe;
	private GridBagConstraints gbc_btnNewButton;
	private Game game;
	
	/**
	* Launch the application.

	/**
	* Create the frame.
	*/
	public StartView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		txtpnWelcomeToThe = new JTextPane();
		printMessage();

		GridBagConstraints gbc_txtpnWelcomeToThe = new GridBagConstraints();
		gbc_txtpnWelcomeToThe.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnWelcomeToThe.fill = GridBagConstraints.BOTH;
		gbc_txtpnWelcomeToThe.gridx = 5;
		gbc_txtpnWelcomeToThe.gridy = 2;
		contentPane.add(txtpnWelcomeToThe, gbc_txtpnWelcomeToThe);
		

		btnNewButton = new JButton("Start");
		btnNewButton.setEnabled(false);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnNewButton.setEnabled(false);
			}
		});
		
		
		gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 5;
		gbc_btnNewButton.gridy = 4;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
	}

	public void showFailure() {
		btnNewButton.setEnabled(true);
		JOptionPane.showMessageDialog(null, "Try to connect again...");
	}
	
	public void showAlreadyConnected() {
		JOptionPane.showMessageDialog(null, "You are already connected...");
	}
	
	public void showMaze() {
		new Maze();
	}
	
	public void setStartControllerListener(ActionListener ac) {
		this.btnNewButton.addActionListener(ac);
	}
	
	public int countPlayers() {
		int i = game == null ? 0 : game.getPlayers().size();
		return i;
	}
	
	public void printMessage() {
		txtpnWelcomeToThe.setText("Welcome to the Mouse Labyrinth Game! " + "\n \n \n \n \nThere are " + this.countPlayers() +  " players connected" + 
				"\n \n \n \n Press start to start the game!");
	}

	public void setStart () {
		btnNewButton.setEnabled(true);
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	
}

     