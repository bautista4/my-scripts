package com.scripts.cammyfisher;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

public class FisherGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FisherGUI frame = new FisherGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FisherGUI() {
		setTitle("B4CammyFisher 0.1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(5, 5, 424, 0);
		contentPane.add(label);
		
		JComboBox ComboBox = new JComboBox();
		ComboBox.setModel(new DefaultComboBoxModel(new String[] {"sharks", "swordfish and tuna", "lobster"}));
		ComboBox.setName("fish types");
		ComboBox.setBounds(123, 64, 157, 20);
		contentPane.add(ComboBox);
		
		JLabel lblFishTypes = new JLabel("Fish Types :");
		lblFishTypes.setBounds(60, 67, 64, 14);
		contentPane.add(lblFishTypes);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(154, 193, 89, 23);
		contentPane.add(btnStart);
	}
}
