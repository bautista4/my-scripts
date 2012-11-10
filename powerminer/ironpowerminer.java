import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
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
	private int[] oresToMine;
	private int ORE_ID = 0;
	private static final int TIN_ORE= 438;
	private static final int GOLD_ORE = 444;
	private static final int COPPER_ORE = 436;
	private static final int MITHRIL_ORE = 447;
	private static final int COAL_ORE = 453;
	private static final int IRON_ORE = 440;
	
	
	Tree jobs = null;

	private boolean guiWait = true;
	gui g = new gui();
	// error here^
	
	public boolean onStart() {
		// error here^
		
		g.setVisible(true);
		while(guiWait) sleep(500);
		// error here^
		return true;
		
	}
	
	
	public int loop() {
		if (jobs == null) {
			jobs = new Tree(new Node[] { new Mining(), new Dropping() });
		}

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
				Mouse.setSpeed(Speed.FAST);
				System.out.println("Turning screen");
				Camera.turnTo(rock);
				System.out.println("Camera turned, mining.");
				rock.interact("Mine");
				Task.sleep(400, 600);
			} else if (rock != null && !rock.isOnScreen()
					&& Players.getLocal().getAnimation() == -1
					&& !Players.getLocal().isMoving()
					&& Players.getLocal().getLocation().distanceOfTiles(rock) > 6){
				// error on line right above this....^
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

}


class Minegui extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Minegui frame = new Minegui();
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
	public Minegui() {
		setTitle("B4PowerMiner  V 1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFishTypes = new JLabel("Ore Types :");
		lblFishTypes.setBounds(57, 116, 63, 14);
		contentPane.add(lblFishTypes);
		
		JComboBox oreSelected = new JComboBox();
		oreSelected.setModel(new DefaultComboBoxModel(new String[] {"iron", "tin", "copper", "coal", "gold", "mithril"}));
		oreSelected.setBounds(130, 113, 126, 20);
		contentPane.add(oreSelected);
		
		JButton btnStart = new JButton("start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String chosen = oreSelected.getSelectedItem().toString();
			if(chosen.equals("tin")) {
				ORE_ID = TIN_ORE;
				oresToMine = TIN_ID;
			} else if (chosen.equals("copper")) {
				ORE_ID = COPPER_ORE;
				oresToMine = COPPER_ID;
				
			}else if (chosen.equals("gold"))
				
			{	ORE_ID = GOLD_ORE;		 
			oresToMine = GOLD_ID;
				
			}else if (chosen.equals("iron")) {
				ORE_ID = IRON_ORE;
				oresToMine = IRON_ID;
		}else if (chosen.equals("coal")) {
			ORE_ID = COAL_ORE;
			oresToMine = COAL_ID;
			// error right here....i dont know why
		}else (chosen.equals("mithril")) {
			ORE_ID = MITRHIL_ORE;
			oresToMine = MITHRIL_ID;
			
			}
			
			guiWait = false;
			g.dispose();
			
			}
		});
		btnStart.setBounds(141, 209, 89, 23);
		contentPane.add(btnStart);
	}
}