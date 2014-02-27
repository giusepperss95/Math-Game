package com.mathgame.panels;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;
import com.mathgame.cardmanager.UndoButton;
import com.mathgame.math.MathGame;
import com.mathgame.math.Menu;
import com.mathgame.math.NumberType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 
 * 
 * The side panel on the right side of the GUI which contains accessory
 * functions
 */
public class SidePanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1209424284690306920L;

	MathGame mathgame;
	NumberType typeManager;

	JLabel clock;
	JLabel pass;// count how many you get right
	JLabel fail;// how many you got wrong
	JLabel score;// TODO: Determine how to calculate the score!

	JButton help;
	JButton exit;
	JButton checkAns;
	public UndoButton undo;
	JButton reset;

	Font sansSerif36 = new Font("SansSerif", Font.PLAIN, 36);

	final String imageFile = "/images/control bar.png";
	
	final String buttonImageFile = "/images/DefaultButtonImage1.png";
	final String buttonRollOverImageFile = "/images/DefaultButtonImage2.png";
	final String buttonPressedImageFile = "/images/DefaultButtonImage3.png";

	static ImageIcon buttonImage;
	static ImageIcon buttonRollOverImage;
	static ImageIcon buttonPressedImage;
	
	static ImageIcon background;

	// JTextArea error;

	JButton toggle;

	int correct = 0;
	int wrong = 0;
	int points = 0;

	Timer timer;
	// StopWatch stopWatch;

	boolean pressed = true;//timer starts automatically

	long startTime = 0;
	long endTime = 0;

	Insets insets = getInsets(); // insets for the side panel for layout purposes

	/**
	 * Initialization of side panel & side panel buttons
	 * 
	 * @param mathgame
	 */
	public void init(MathGame mathgame) {
		this.mathgame = mathgame;
		this.typeManager = mathgame.typeManager;

		// this.setBorder(new LineBorder(Color.BLACK));
		this.setBounds(750, 0, 150, 620);

		this.setLayout(null);

		// instantiate controls
		clock = new JLabel("00:00");
		toggle = new JButton("Start/Stop");
		score = new JLabel("0");
		help = new JButton("Help");
		exit = new JButton("Back");
		checkAns = new JButton("Check Answer");
		undo = new UndoButton("Undo Move", mathgame);
		reset = new JButton("Reset");

		pass = new JLabel("Correct: " + correct);
		fail = new JLabel("Wrong: " + wrong);

		background = new ImageIcon(SidePanel.class.getResource(imageFile));
		buttonImage = new ImageIcon(Menu.class.getResource(buttonImageFile));
		buttonRollOverImage = new ImageIcon(Menu.class.getResource(buttonRollOverImageFile));
		buttonPressedImage = new ImageIcon(Menu.class.getResource(buttonPressedImageFile));

		add(clock);
		add(toggle);
		add(score);
		add(help);
		add(exit);
		add(checkAns);
		add(undo);
		add(reset);

		// define properties of controls
		clock.setBounds(10, 10, 130, 60);
		clock.setFont(sansSerif36);
		clock.setHorizontalAlignment(SwingConstants.CENTER);

		score.setBounds(10, 80, 130, 60);
		score.setFont(sansSerif36);
		score.setHorizontalAlignment(SwingConstants.CENTER);

		toggle.setBounds(10, 150, 130, 30);
		toggle.addActionListener(this);
	    toggle.setHorizontalTextPosition(JButton.CENTER);
	    toggle.setVerticalTextPosition(JButton.CENTER);
		toggle.setBorderPainted(false);

		undo.setBounds(10, 190, 130, 30);
		undo.addActionListener(this);
	    undo.setHorizontalTextPosition(JButton.CENTER);
	    undo.setVerticalTextPosition(JButton.CENTER);
		undo.setBorderPainted(false);

		reset.setBounds(10, 230, 130, 30);
		reset.addActionListener(this);
	    reset.setHorizontalTextPosition(JButton.CENTER);
	    reset.setVerticalTextPosition(JButton.CENTER);
		reset.setBorderPainted(false);

		checkAns.setBounds(10, 270, 130, 30);
		checkAns.addActionListener(this);
	    checkAns.setHorizontalTextPosition(JButton.CENTER);
	    checkAns.setVerticalTextPosition(JButton.CENTER);
		checkAns.setBorderPainted(false);

		help.setBounds(10, 540, 130, 30);
		help.setHorizontalAlignment(SwingConstants.CENTER);
		help.addActionListener(this);
	    help.setHorizontalTextPosition(JButton.CENTER);
	    help.setVerticalTextPosition(JButton.CENTER);
		help.setBorderPainted(false);

		exit.setBounds(10, 580, 130, 30);
		exit.setHorizontalAlignment(SwingConstants.CENTER);
		exit.addActionListener(this);
	    exit.setHorizontalTextPosition(JButton.CENTER);
	    exit.setVerticalTextPosition(JButton.CENTER);
		exit.setBorderPainted(false);

		timer = new Timer(1000, this);
		timer.setRepeats(true);

		try {
		    toggle.setIcon(buttonImage);
		    toggle.setRolloverIcon(buttonRollOverImage);
		    toggle.setPressedIcon(buttonPressedImage);
		    help.setIcon(buttonImage);
		    help.setRolloverIcon(buttonRollOverImage);
		    help.setPressedIcon(buttonPressedImage);
		    undo.setIcon(buttonImage);
		    undo.setRolloverIcon(buttonRollOverImage);
		    undo.setPressedIcon(buttonPressedImage);
		    reset.setIcon(buttonImage);
		    reset.setRolloverIcon(buttonRollOverImage);
		    reset.setPressedIcon(buttonPressedImage);
		    checkAns.setIcon(buttonImage);
		    checkAns.setRolloverIcon(buttonRollOverImage);
		    checkAns.setPressedIcon(buttonPressedImage);
		    exit.setIcon(buttonImage);
		    exit.setRolloverIcon(buttonRollOverImage);
		    exit.setPressedIcon(buttonPressedImage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, SidePanel.this);
	}

	/**
	 * input/button presses on side panel
	 * 
	 * @param ActionEvent e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == toggle) {

			if (!pressed) {
				timer.start();
				startTime = System.currentTimeMillis();
				pressed = true;
			} else {
				timer.stop();
				pressed = false;
			}
		}
		if (e.getSource() == help) {// TODO: Decide function of Help (on website or in game?)
			JOptionPane.showMessageDialog(this, "Instructions go here");
			// perhaps link to a help webpage on the website? maybe turn into a hint button?
		}

		if (e.getSource() == checkAns) {
			// System.out.println("SCORE: "+Double.parseDouble(score.getText()));
			if (mathgame.workPanel.getComponentCount() == 1) {
				NumberCard finalAnsCard;
				Component finalAnsComp = mathgame.workPanel.getComponent(0);
				String computedAns;// answer user got
				String actualAns;// actual answer to compare to
				if (finalAnsComp instanceof NumberCard) {
					finalAnsCard = (NumberCard) finalAnsComp;
					actualAns = mathgame.cardPanel.ans.getValue();
					computedAns = finalAnsCard.getValue(); 
					System.out.println(actualAns + " ?= " + computedAns);
					if (actualAns.equals(computedAns)
							|| mathgame.cardPanel.ans
									.parseNumFromText(actualAns) == finalAnsCard
									.parseNumFromText(computedAns)) {
						JOptionPane.showMessageDialog(this,
								"Congratulations!  Victory is yours!");
						// later on change to something else... victory song? who knows...
						resetFunction();
						score.setText(Double.toString(Double.parseDouble(score
								.getText()) + 20));//TODO determine scoring algorithm
					}
				} else {
					JOptionPane.showMessageDialog(this,
							"Error.  Cannot evaluate answer");
					System.out.println("ERROR.. cannot check answer for this");
				}

			}
		}

		if(e.getSource() == undo)	{
			undoFunction();
		}
		if (e.getSource() == reset) {
			// mathgame.cardPanel.randomize( mathgame.cardPanel.randomValues() );
			// while ( undo.getIndex() > 0 ) {
			// undoFunction();

			resetFunction();
		}

		if (timer.isRunning()) {
			endTime = System.currentTimeMillis();

			clock.setText(timeFormat((int) (endTime - startTime)));

		}
		
		if (e.getSource() == exit) {
			if (JOptionPane.showOptionDialog(this,
					"Are you sure you want to exit?", "Exit",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, null, null) == 0) {
				mathgame.cl.show(mathgame.cardLayoutPanels, mathgame.MENU);//open the menu
				score.setText("0.0");//reset the score
				//reset data validation boxes
				mathgame.cardPanel.v1.reset();
				mathgame.cardPanel.v2.reset();
				mathgame.cardPanel.v3.reset();
				mathgame.cardPanel.v4.reset();
				mathgame.cardPanel.v5.reset();
				mathgame.cardPanel.v6.reset();
				mathgame.cardPanel.v_ans.reset();
			}
		}
	}

	// returns time in form xx:xx
	/**
	 * returns time in form xx:xx
	 * 
	 * @param millis
	 * @return time (in string)
	 */
	private String timeFormat(int millis) {
		// converts from millis to secs
		int secs = millis / 1000;
		int mins = secs / 60;
		// TODO add hours just in case

		// mods 60 to make sure it is always from 1 to 59
		// is doen after mins so that mins can actually increment
		secs = secs % 60;

		if (mins < 10) {
			if (secs < 10)
				return ("0" + String.valueOf(mins) + ":" + "0" + String
						.valueOf(secs));
			else
				return ("0" + String.valueOf(mins) + ":" + String.valueOf(secs));
		} else {
			if (secs < 10)
				return (String.valueOf(mins) + ":" + "0" + String.valueOf(secs));
			else
				return (String.valueOf(mins) + ":" + String.valueOf(secs));
		}

	}

	/**
	 * Carries out the undo function
	 */
	public void undoFunction() {
		NumberCard tempnum1 = undo.getPrevNum1();
		NumberCard tempnum2 = undo.getPrevNum2();

		// no need to restore the operator b/c it is automatically regenerated

		if (tempnum1 == null || tempnum2 == null) {// there's no more moves... too many undos!
			return;
		}
		if (tempnum1.getHome() == "home") {// originally in card panel
			System.out.println("restore card1; value: " + tempnum1.getText());
			mathgame.cardPanel.restoreCard(tempnum1.getText());
		} else if (tempnum1.getHome() == "hold") {// new card in holding area
			for (int x = 0; x < mathgame.holdPanel.getComponentCount(); x++) {
				NumberCard temp = (NumberCard) mathgame.holdPanel
						.getComponent(0);
				if (temp.getHome() == "home") {
					mathgame.cardPanel.restoreCard(temp.getText());
					;
				} // check for cards that were dragged from home into workspace
					// and restores them
			}
			mathgame.holdPanel.add(tempnum1);
		}

		if (tempnum2.getHome() == "home") {
			System.out.println("restore card2; value: " + tempnum2.getText());
			mathgame.cardPanel.restoreCard(tempnum2.getText());
		} else if (tempnum2.getHome() == "hold") {
			for (int x = 0; x < mathgame.holdPanel.getComponentCount(); x++) {
				NumberCard temp = (NumberCard) mathgame.holdPanel
						.getComponent(0);
				if (temp.getHome() == "home") {
					mathgame.cardPanel.restoreCard(temp.getText());
				}
			}
			mathgame.holdPanel.add(tempnum2);
		}

		// covers scenario in which the previously created card was put in hold
		if (mathgame.workPanel.getComponentCount() == 0) {
			NumberCard prevAns = undo.getPrevNewNum();// holds the previously
														// calculated answer
			NumberCard temp;
			// cycle through cards in hold
			for (int i = 0; i < mathgame.holdPanel.getComponentCount(); i++) {
				temp = (NumberCard) mathgame.holdPanel.getComponent(i);
				// note: cast (NumberCard) assumes that only NumberCards will be in holdpanel
				if (temp.getText() == prevAns.getText()) {// check to see if the checked card is the previous answer
					System.out.println("Deleting card in hold");
					mathgame.holdPanel.remove(i);
					i = mathgame.holdPanel.getComponentCount() + 1;// so we can exit this loop
				}
			}
		}
		// covers scenario in which previously created card is still in workpanel
		else {
			NumberCard prevAns = undo.getPrevNewNum();// holds the previously calculated answer
			NumberCard temp;
			// cycle through cards in workspace
			for (int i = 0; i < mathgame.workPanel.getComponentCount(); i++) {
				if (mathgame.workPanel.getComponent(i) instanceof NumberCard) {
					temp = (NumberCard) mathgame.workPanel.getComponent(i);
					if (temp.getValue() == prevAns.getValue()) {// check to see if the checked card is the previous answer
						mathgame.workPanel.remove(i);
						i = mathgame.workPanel.getComponentCount() + 1;// so we can exit this loop
					}
				}
			}
		}

		undo.completeUndo();
		mathgame.workPanel.revalidate();
		mathgame.workPanel.repaint();
		mathgame.holdPanel.revalidate();
		mathgame.holdPanel.repaint();
		mathgame.cardPanel.revalidate();
	}

	/**
	 * reset function
	 */
	private void resetFunction()	{
		
		timer.stop();
		
		mathgame.cardPanel.resetValidationBoxes();
		
		while ( undo.getIndex() > 0 ) {
			undoFunction();

		}

		if (mathgame.workPanel.getComponentCount() > 0) {
			NumberCard temp;
			OperationCard temp2;
			for (int x = 0; x < mathgame.workPanel.getComponentCount(); x++) {
				if (mathgame.workPanel.getComponent(0) instanceof NumberCard) {
					temp = (NumberCard) mathgame.workPanel.getComponent(0);
					mathgame.cardPanel.restoreCard(temp.getText());
				} else if (mathgame.workPanel.getComponent(0) instanceof OperationCard) {
					temp2 = (OperationCard) mathgame.workPanel.getComponent(0);
					mathgame.opPanel.addOperator(temp2.getOperation());
				}
			}
		}

		if (mathgame.holdPanel.getComponentCount() > 0) {
			NumberCard temp;
			OperationCard temp2;
			for (int x = 0; x < mathgame.holdPanel.getComponentCount(); x++) {
				if (mathgame.holdPanel.getComponent(0) instanceof NumberCard) {
					temp = (NumberCard) mathgame.holdPanel.getComponent(0);
					mathgame.cardPanel.restoreCard(temp.getText());
				} else if (mathgame.holdPanel.getComponent(0) instanceof OperationCard) {
					temp2 = (OperationCard) mathgame.holdPanel.getComponent(0);
					mathgame.opPanel.addOperator(temp2.getOperation());
				}
			}
			
		}
		mathgame.typeManager.randomize();
		mathgame.workPanel.revalidate();
		mathgame.workPanel.repaint();
		mathgame.holdPanel.revalidate();
		mathgame.holdPanel.repaint();
		mathgame.cardPanel.revalidate();
		
		timer.start();
		startTime = System.currentTimeMillis();
	}
}
