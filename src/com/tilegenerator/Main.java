/*
 * Copyright (C) 2013 Marcin Kunert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tilegenerator;

/**
 * 
 * @author Marcin Kunert
 * 
 */


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.tilegenerator.Generator;


public class Main extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField tTop;
	private JTextField tBot;
	private JTextField tLeft;
	private JTextField tRight;
	private JSpinner spinner;
	private String resizedFileLocation="";
	private String resultDir="";
	private String fileLocation="";
	JButton bDo;
	private final JLabel lbIn;
	private final JLabel lbOut;
	

	/**
	 * @wbp.nonvisual location=61,179
	 */
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		final JFrame frame = new JFrame();
		setTitle("Map Tile Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 512, 358);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		
		
		JLabel lDown = new JLabel("D\u00F3\u0142:");
		lDown.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		tTop = new JTextField();
		tTop.setText("0.00");
		tTop.setColumns(10);
		tTop.addActionListener(this);
		
		tBot = new JTextField();
		tBot.setText("0.00");
		tBot.setColumns(10);
		tBot.addActionListener(this);
		
		JLabel lLeft = new JLabel("Lewo:");
		lLeft.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lRight = new JLabel("Prawo:");
		lRight.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		tLeft = new JTextField();
		tLeft.setText("0.00");
		tLeft.setColumns(10);
		tLeft.addActionListener(this);
		
		tRight = new JTextField();
		tRight.setText("0.00");
		tRight.setColumns(10);
		
		JLabel lTop = new JLabel("G\u00F3ra:");
		lTop.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(16, 16, 22, 1));

		
		JLabel lAppro = new JLabel("Przybli¿enie");
		lAppro.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		lbIn = new JLabel("C:\\Test");
		lbOut = new JLabel("D:\\Test");
		
		
		JButton bInput = new JButton("Podaj plik wejœciowy");
		bInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{	     
			     JFileChooser j = new JFileChooser();
				 Integer opt = j.showSaveDialog(frame);
				 lbIn.setText(j.getSelectedFile().getAbsolutePath());

			}
		});
		
		
		JButton bOutput = new JButton("Podaj folder wyjœciowy");
		bOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{
				JFileChooser j = new JFileChooser();
				j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				Integer opt = j.showSaveDialog(frame);
				lbOut.setText(j.getSelectedFile().getAbsolutePath());
				
			}
		});
		
		
		
		bDo = new JButton("Wykonaj");
		bDo.addActionListener(this);
		
		
		
		

		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(8)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lTop, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(tTop, 0, 0, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lDown, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tBot, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(24)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(bOutput, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(bInput, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lLeft)
								.addComponent(lRight))
							.addGap(4)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(tRight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lAppro))
								.addComponent(tLeft, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lbOut, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbIn, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE))))
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(101, Short.MAX_VALUE)
					.addComponent(bDo, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)
					.addGap(92))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(26)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lTop)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(tTop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lLeft)
									.addComponent(tLeft, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)))
							.addGap(8)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lDown, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addComponent(tBot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tRight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lRight)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lAppro)
								.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))))
					.addGap(38)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(bInput, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbIn, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(bOutput, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbOut, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(39)
					.addComponent(bDo, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source== bDo){
		long start = System.currentTimeMillis();
		double topLat = Double.parseDouble(tTop.getText()); 
		double leftLng = Double.parseDouble(tLeft.getText()); 
		double bottomLat = Double.parseDouble(tBot.getText()); 
		double rightLng = Double.parseDouble(tRight.getText()); 
		Integer zoom = (Integer) spinner.getValue();
		fileLocation=lbIn.getText();
		resizedFileLocation=lbOut.getText()+"\\source-resized.png";
		resultDir=lbOut.getText();
		
		Generator generator = new Generator();

		generator.setMapImageLocation(fileLocation);
		generator.setResizedMapImageLocation(resizedFileLocation);
		generator.setResultDir(resultDir);
		generator.setLeftTopLat(topLat);
		generator.setLeftTopLng(leftLng);
		generator.setRightBottomLat(bottomLat);
		generator.setRightBottomLng(rightLng);
		generator.setZoom(zoom);


	    try {
			generator.generateTiles();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		

		System.out.println("Zakoñczono.\nCzas trwania: " + (System.currentTimeMillis() - start) + "ms");
		}
        
        
        
	}
}
