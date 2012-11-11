import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import org.powerbot.core.event.listeners.PaintListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;


@Manifest(authors = { "bautista4" }, name = "B4PowerMiner", description = "Powermines iron", version = 0.1)
public class ironpowerminer extends ActiveScript implements PaintListener {

	int[] ROCK_ID = { 11954, 11955, 11956 };
	int[] MINNED_ID = { 11555, 11556, 11556 };
	public static final int[] IRON_ID = {11954, 5775, 5773, 5774, 11955, 11956, 37307, 37309,37308} ;
	public static final int[] TIN_ID = { 5776, 11933, 5777,5778, 11959, 11957} ;
	public static final int[] COPPER_ID = {11936, 11937, 11938, 5779, 11961, 11960, 11962, 5780, 5781} ;
	public static final int[] MITHRIL_ID = {11942, 11944, 5784, 5786, 32438, 32440, 32439,  } ;
	public static final int[] COAL = { 11930, 11932, 5770, 5772, 5771, 32428, 32427, 32426, 32247,  } ;
	public static final int[] GOLD_ID = { 5768, 37312, 37310, 5769} ;
	public static final int MINNING_ID = 625;
	public static int[] oresToMine;
	public static int ORE_ID = 0;
	public static final int TIN_ORE= 438;
	public static final int GOLD_ORE = 444;
	public static final int COPPER_ORE = 436;
	public static final int MITHRIL_ORE = 447;
	public static final int COAL_ORE = 453;
	public static final int IRON_ORE = 440;

	gui m = new gui();
	Tree jobs = null;

	
	// error here^
	
	public void onStart() {
		m.setVisible(true);
		
	}
	
	
	public int loop() {
		final Node job = jobs.state();
		if (job != null) {
			jobs.set(job);
			getContainer().submit(job);
			job.join();
			return 0;
		}
		return Random.nextInt(200, 300);
	}

	public class Mining extends Node {

		public boolean activate() {
			return (!Inventory.isFull() && !Players.getLocal().isMoving() && Players
					.getLocal().getAnimation() == -1);
		}

		public void execute() {
			Mouse.setSpeed(Speed.VERY_FAST);
			System.out.println("Executing Mining");
			
			final SceneObject rock = SceneEntities.getNearest(oresToMine);
			  double distanceOfTiles = Calculations.distanceTo(rock);
			if (rock != null && rock.isOnScreen()
					&& Players.getLocal().getAnimation() == -1
					&& !Players.getLocal().isMoving())
			

			{
				Mouse.setSpeed(Speed.VERY_FAST);
				System.out.println("Rock found, on screen");
				rock.interact("Mine");
				Task.sleep(200, 500);
				System.out.println("Done sleeping. look for new rock");

			} else if (rock != null && !rock.isOnScreen()
					&& Players.getLocal().getAnimation() == -1
					&& !Players.getLocal().isMoving())
			
			{
				Mouse.setSpeed(Speed.VERY_FAST);
				System.out.println("Turning screen");
				Camera.turnTo(rock);
				System.out.println("Camera turned, mining.");
				rock.interact("Mine");
				Task.sleep(400, 600);
			} else if (rock != null && !rock.isOnScreen()
					&& Players.getLocal().getAnimation() == -1
					&& !Players.getLocal().isMoving()
					&& Calculations.distanceTo(rock) > 6){
				
			Walking.walk(rock);
			
			} else {
				System.out.println("No case found");
			}
			System.out.println("Out of IF");
		}
	}

	public class Dropping extends Node {

		public boolean activate() {
			return Inventory.getCount() == 28 && !Players.getLocal().isMoving()
					&& Players.getLocal().getAnimation() == -1;
		}

		public void execute() {
			Mouse.setSpeed(Speed.VERY_FAST);
			System.out.println("Attempting to drop");
			for (Item i : Inventory.getItems()) {
				if (i.getId() == ORE_ID) {
					i.getWidgetChild().interact("Drop");
					sleep(Random.nextInt(200, 500));
				}
			}
		}

	}

	private Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			return null;
		}
	}

	private final Color color1 = new Color(0, 0, 0);
	private final Color color2 = new Color(204, 0, 0);

	private final BasicStroke stroke1 = new BasicStroke(4);

	private final Font font1 = new Font("Arial", 1, 20);

	private final Image img1 = getImage("http://runescape.neoseeker.com/w/i/runescape/7/76/Mining_Cape_(t).gif");
	private final Image img2 = getImage("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQ4tJsNJo6puEKul6HQCBRlpUyt50YFlwkuqz7tnA29Mn-3fa3NQA");

	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		g.setColor(color1);
		g.fillRoundRect(3, 342, 513, 133, 16, 16);
		g.setColor(color2);
		g.setStroke(stroke1);
		g.drawRoundRect(3, 342, 513, 133, 16, 16);
		g.drawImage(img1, 341, 346, null);
		g.drawImage(img1, 129, 346, null);
		g.setFont(font1);
		g.drawString("B4PowerMiner", 170, 374);
		g.drawString("Author: bautista", 178, 399);
		g.drawString("Enjoy 99 mining! :D", 161, 441);
		g.drawImage(img2, 357, 417, null);
		g.drawImage(img2, 117, 417, null);
	}
	
	@SuppressWarnings("serial")
	public class gui extends JFrame  {
		public gui() {
			initComponents();
		}
		
		private void StartButtonActionPerformed(ActionEvent e )
		{
			String chosen = comboBox1.getSelectedItem().toString();
			if(chosen.equals("tin")) {
				ORE_ID = TIN_ORE;
			    ORE_ID = TIN_ORE;
			    oresToMine = TIN_ID;
			   } else if (chosen.equals("copper")) {
			    ORE_ID = COPPER_ORE;
			    oresToMine = COPPER_ID;
			    
			   }else if (chosen.equals("gold"))
			    
			   { ORE_ID = GOLD_ORE;   
			   oresToMine = GOLD_ID;
			    
			   }else if (chosen.equals("iron")) {
			    ORE_ID = IRON_ORE;
			    oresToMine = IRON_ID;
			  }else if (chosen.equals("coal")) {
			   ORE_ID = COAL_ORE;
			   oresToMine = COAL;
			  }else if (chosen.equals("mithril")) {
			   ORE_ID = MITHRIL_ORE;
			   oresToMine = MITHRIL_ID;
			   
			  }
			
			jobs = new Tree(new Node[] { new Mining(), new Dropping() });
			m.dispose();
		}

		private void initComponents() {
			
			B4PowerMiner = new JPanel();
			label1 = new JLabel();
			comboBox1 = new JComboBox<>();
			button1 = new JButton();

			//======== B4PowerMiner ========
			{

				// JFormDesigner evaluation mark
				B4PowerMiner.setBorder(new javax.swing.border.CompoundBorder(
					new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
						"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
						javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
						java.awt.Color.red), B4PowerMiner.getBorder())); B4PowerMiner.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


				//---- label1 ----
				label1.setText("ore types :");

				//---- comboBox1 ----
				comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
					"mithril",
					"iron",
					"copper",
					"tin",
					"coal",
					"gold"
				}));

				//---- button1 ----
				button1.setText("start");
				button1.addActionListener (new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						StartButtonActionPerformed(e);
					}
				});

				GroupLayout B4PowerMinerLayout = new GroupLayout(B4PowerMiner);
				B4PowerMiner.setLayout(B4PowerMinerLayout);
				B4PowerMinerLayout.setHorizontalGroup(
					B4PowerMinerLayout.createParallelGroup()
						.addGroup(B4PowerMinerLayout.createSequentialGroup()
							.addGroup(B4PowerMinerLayout.createParallelGroup()
								.addGroup(B4PowerMinerLayout.createSequentialGroup()
									.addGap(64, 64, 64)
									.addComponent(label1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
									.addGap(18, 18, 18)
									.addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
								.addGroup(B4PowerMinerLayout.createSequentialGroup()
									.addGap(83, 83, 83)
									.addComponent(button1, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)))
							.addGap(62, 62, 62))
				);
				B4PowerMinerLayout.setVerticalGroup(
					B4PowerMinerLayout.createParallelGroup()
						.addGroup(B4PowerMinerLayout.createSequentialGroup()
							.addGap(26, 26, 26)
							.addGroup(B4PowerMinerLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label1))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
							.addComponent(button1, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
							.addGap(31, 31, 31))
				);
			}
			
		}

		
		private JPanel B4PowerMiner;
		private JLabel label1;
		private JComboBox<String> comboBox1;
		private JButton button1;
		
	}




}

