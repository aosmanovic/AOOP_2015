package at.ac.tuwien.foop.client.shell;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextPane;

import at.ac.tuwien.foop.client.gamelogic.StartController;
import at.ac.tuwien.foop.userInterface.Maze.Maze;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Start extends JFrame {

	private JPanel contentPane;

	/**
	* Launch the application.

	/**
	* Create the frame.
	*/
	public Start() {
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
		
		JTextPane txtpnWelcomeToThe = new JTextPane();
		txtpnWelcomeToThe.setText("Welcome to the Mouse Labirint ");
		GridBagConstraints gbc_txtpnWelcomeToThe = new GridBagConstraints();
		gbc_txtpnWelcomeToThe.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnWelcomeToThe.fill = GridBagConstraints.BOTH;
		gbc_txtpnWelcomeToThe.gridx = 5;
		gbc_txtpnWelcomeToThe.gridy = 2;
		contentPane.add(txtpnWelcomeToThe, gbc_txtpnWelcomeToThe);
		
		JButton btnNewButton = new JButton("Start game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartController sc = new StartController();
				//Here we should check if client is connected
				sc.connect("localhost", "20150");
			
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 5;
		gbc_btnNewButton.gridy = 4;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
	}

	public void showError() {
		JOptionPane.showMessageDialog(null, "Try to connect again...");
	}
	
	public void showMaze() {
		new Maze();
	}
}

     