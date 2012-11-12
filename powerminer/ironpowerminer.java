import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;

import java.awt.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.powerbot.core.event.listeners.PaintListener;
import java.awt.BasicStroke;
import java.awt.Color;
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
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;



@Manifest(authors = { "bautista4" }, name = "B4PowerMiner", description = "Powermines iron", version = 0.1)
public class ironpowerminer extends ActiveScript implements PaintListener {

	//GUI VARIABLES
	
public static int[] oresToMine;
	public static int ORE_ID = 0;
	public static Area Bank_Area;
	public static  Area Mine_Area;
	public static int[] BANKER_ID;
	Tile[] tilepath;
	
	//ORES IN MINE INVENTORY
	public static final int TIN_ORE = 438;
	public static final int GOLD_ORE = 444;
	public static final int COPPER_ORE = 436;
	public static final int MITHRIL_ORE = 447;
	public static final int COAL_ORE = 453;
	public static final int IRON_ORE = 440;
	
	
	//ROCKS TO MINE
	public static final int[] IRON_ID = { 11954, 5775, 5773, 5774, 11955,
			11956, 37307, 37309, 37308 };
	
	public static final int[] TIN_ID = { 5776, 11933, 5777, 5778, 11959, 11957 };
	
	public static final int[] COPPER_ID = { 11936, 11937, 11938, 5779, 11961,
			11960, 11962, 5780, 5781 };
	
	public static final int[] MITHRIL_ID = { 11942, 11944, 5784, 5786, 32438,
			32440, 32439, };
	
	public static final int[] COAL = { 11930, 11932, 5770, 5772, 5771, 32428,
			32427, 32426, 32247, };
	
	public static final int[] GOLD_ID = { 5768, 37312, 37310, 5769 };
	
	//FALADOR DOOR AND STAIRS
	SceneObject stairs = SceneEntities.getNearest(30944);
	SceneObject door1 = SceneEntities.getNearest(11714);
	
	
	//BANKERS
	
	
	public static final int[] FaladorBanker = {553,6200};
	public static final int[] SharedVarrockBanker = { 553, 2859};
	public static final int[] KharidBankers = {553,496};
	
	
	//PATHS 
	
	
	
	Tile[] VarrockWest = new Tile[] { new Tile(3185, 3440, 0), new Tile(3185, 3438, 0), new Tile(3185, 3436, 0), 
			new Tile(3185, 3435, 0), new Tile(3185, 3433, 0), new Tile(3185, 3431, 0), 
			new Tile(3185, 3430, 0), new Tile(3183, 3429, 0), new Tile(3181, 3429, 0), 
			new Tile(3178, 3428, 0), new Tile(3177, 3427, 0), new Tile(3175, 3427, 0), 
			new Tile(3173, 3426, 0), new Tile(3172, 3425, 0), new Tile(3171, 3424, 0), 
			new Tile(3170, 3423, 0), new Tile(3169, 3421, 0), new Tile(3169, 3419, 0), 
			new Tile(3168, 3417, 0), new Tile(3167, 3415, 0), new Tile(3167, 3413, 0), 
			new Tile(3168, 3411, 0), new Tile(3170, 3410, 0), new Tile(3171, 3408, 0), 
			new Tile(3171, 3407, 0), new Tile(3170, 3405, 0), new Tile(3169, 3403, 0), 
			new Tile(3168, 3402, 0), new Tile(3169, 3400, 0), new Tile(3169, 3398, 0), 
			new Tile(3170, 3398, 0), new Tile(3171, 3397, 0), new Tile(3172, 3395, 0), 
			new Tile(3173, 3392, 0), new Tile(3173, 3391, 0), new Tile(3173, 3389, 0), 
			new Tile(3173, 3388, 0), new Tile(3174, 3386, 0), new Tile(3175, 3383, 0), 
			new Tile(3177, 3382, 0), new Tile(3177, 3381, 0), new Tile(3179, 3380, 0), 
			new Tile(3180, 3380, 0), new Tile(3182, 3380, 0), new Tile(3182, 3379, 0), 
			new Tile(3182, 3378, 0), new Tile(3182, 3377, 0), new Tile(3182, 3375, 0), 
			new Tile(3183, 3374, 0), new Tile(3183, 3373, 0), new Tile(3182, 3371, 0), 
			new Tile(3181, 3371, 0) };
	
	Tile Tile1 = new Tile(3061, 3375, 0); //tile after falador door
		
	Tile[] VarrockEast = new Tile[] { new Tile(3286, 3368, 0), new Tile(3287, 3370, 0), new Tile(3287, 3372, 0), 
			new Tile(3289, 3374, 0), new Tile(3291, 3376, 0), new Tile(3293, 3377, 0), 
			new Tile(3293, 3379, 0), new Tile(3293, 3381, 0), new Tile(3291, 3384, 0), 
			new Tile(3291, 3387, 0), new Tile(3291, 3390, 0), new Tile(3290, 3391, 0), 
			new Tile(3291, 3395, 0), new Tile(3291, 3398, 0), new Tile(3291, 3401, 0), 
			new Tile(3292, 3404, 0), new Tile(3291, 3405, 0), new Tile(3290, 3407, 0), 
			new Tile(3287, 3410, 0), new Tile(3286, 3411, 0), new Tile(3286, 3413, 0), 
			new Tile(3286, 3415, 0), new Tile(3287, 3416, 0), new Tile(3287, 3420, 0), 
			new Tile(3286, 3421, 0), new Tile(3284, 3423, 0), new Tile(3283, 3425, 0), 
			new Tile(3280, 3426, 0), new Tile(3279, 3426, 0), new Tile(3276, 3427, 0), 
			new Tile(3274, 3428, 0), new Tile(3273, 3428, 0), new Tile(3270, 3428, 0), 
			new Tile(3268, 3428, 0), new Tile(3264, 3427, 0), new Tile(3261, 3428, 0), 
			new Tile(3258, 3428, 0), new Tile(3256, 3429, 0), new Tile(3253, 3428, 0), 
			new Tile(3252, 3426, 0), new Tile(3252, 3425, 0), new Tile(3252, 3423, 0), 
			new Tile(3253, 3421, 0) };
	
	Tile[] FaladorWalk = new Tile[] { new Tile(3012, 3356, 0), new Tile(3012, 3359, 0), new Tile(3014, 3360, 0), 
			new Tile(3018, 3362, 0), new Tile(3022, 3362, 0), new Tile(3025, 3364, 0), 
			new Tile(3025, 3365, 0), new Tile(3028, 3366, 0), new Tile(3032, 3367, 0), 
			new Tile(3035, 3369, 0), new Tile(3037, 3369, 0), new Tile(3041, 3369, 0), 
			new Tile(3044, 3370, 0), new Tile(3049, 3369, 0), new Tile(3053, 3369, 0), 
			new Tile(3057, 3370, 0), new Tile(3059, 3372, 0), new Tile(3060, 3374, 0) };
	
	
	//BANKS 
	
	
	public static Area VarrockWestBank = new Area(new Tile[] { new Tile(3178, 3432, 0), new Tile(3193, 3432, 0), new Tile(3193, 3446, 0), 
			new Tile(3178, 3446, 0) });
	
	public static Area VarrockEastBank = new Area(new Tile[] { new Tile(3249, 3423, 0), new Tile(3249, 3416, 0), new Tile(3256, 3416, 0), 
			new Tile(3256, 3423, 0) });
	
	public static Area FaladorBank = new Area(new Tile[] { new Tile(3017, 3358, 0), new Tile(3017, 3353, 0), new Tile(3008, 3353, 0), 
			new Tile(3008, 3358, 0) });
	
	public static Area KharidBank = new Area(new Tile[] { new Tile(3272, 3162, 0), new Tile(3272, 3175, 0), new Tile(3267, 3175, 0), 
			new Tile(3268, 3162, 0) });
	
	
	
	//MINES
	
	public static Area VarrockEastMine = new Area(new Tile[] { new Tile(3278, 3372, 0), new Tile(3292, 3372, 0), new Tile(3292, 3368, 0), 
			new Tile(3294, 3362, 0), new Tile(3296, 3358, 0), new Tile(3295, 3355, 0), 
			new Tile(3291, 3353, 0), new Tile(3287, 3355, 0), new Tile(3283, 3356, 0), 
			new Tile(3277, 3357, 0), new Tile(3274, 3359, 0) });
	
	public static Area FaladorMine = new Area(new Tile[] { new Tile(3060, 9775, 0), new Tile(3053, 9770, 0), new Tile(3052, 9769, 0),
			new Tile(3051, 9760, 0), new Tile(3048, 9758, 0), new Tile(3044, 9758, 0), new Tile(3042, 9760, 0),
			new Tile(3037, 9760, 0), new Tile(3035, 9763, 0), new Tile(3033, 9768, 0), new Tile(3034, 9777, 0), new Tile(3037, 9779, 0),
			new Tile(3037, 9788, 0), new Tile(3047, 9788, 0), new Tile(3053, 9783, 0), new Tile(3057, 9780, 0), new Tile(3060, 9778, 0)});
	
	public static Area VarrockWestMine = new Area(new Tile[] { new Tile(3170, 3370, 0), new Tile(3170, 3362, 0), new Tile(3180, 3362, 0), 
			new Tile(3186, 3370, 0), new Tile(3190, 3374, 0), new Tile(3189, 3376, 0), 
			new Tile(3186, 3378, 0), new Tile(3183, 3380, 0), new Tile(3178, 3381, 0) });

	public static Area KharidMine = new Area(new Tile[] { new Tile(3292, 3318, 0), new Tile(3291, 3312, 0), new Tile(3288, 3303, 0), 
			new Tile(3287, 3295, 0), new Tile(3290, 3285, 0), new Tile(3297, 3279, 0), 
			new Tile(3308, 3278, 0), new Tile(3308, 3318, 0), new Tile(3298, 3322, 0) });
	
	
	public Tree jobContainer = null;
private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());	
	
	public final void provide(final Node... jobs) {
		for (final Node job : jobs) {
			jobsCollection.add(job);
		}
		jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
	}
	
	gui m = new gui();


	public void onStart() {
		m.setVisible(true);

	}

	public int loop() {
		if (jobContainer != null) {
			final Node job = jobContainer.state();
			if (job != null) {
				jobContainer.set(job);
				getContainer().submit(job);
				job.join();
			}
		}
		return Random.nextInt(100, 200);
	}
	public class Mining extends Node {

		public boolean activate() {
			return (!Inventory.isFull());
		}

		public void execute() {
			Mouse.setSpeed(Speed.VERY_FAST);
			System.out.println("Executing Mining");

			final SceneObject rock = SceneEntities.getNearest(oresToMine);
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
					&& !Players.getLocal().isMoving()
					&& Calculations.distanceTo(rock) < 6)

			{
				Mouse.setSpeed(Speed.VERY_FAST);
				System.out.println("Turning screen");
				Camera.turnTo(rock);
				System.out.println("Camera turned, mining.");
				rock.interact("Mine");
				Task.sleep(Random.nextInt(200, 500));
			} else if (rock != null && !rock.isOnScreen()
					&& Players.getLocal().getAnimation() == -1
					&& !Players.getLocal().isMoving()
					&& Calculations.distanceTo(rock) > 6) {
				System.out.println("turning camera to rock");
				Camera.turnTo(rock);
				System.out.println("walking to rock");
				Walking.walk(rock);
				Task.sleep(Random.nextInt(200, 500));
				rock.interact("Mine");
				Task.sleep(Random.nextInt(200, 500));

			} else {
				System.out.println("No case found");
			}
			System.out.println("Out of IF");
		}
	}
public class WalkingToBanking extends Node {

	
	public boolean activate() {
		return (Inventory.isFull()
				&& Mine_Area.contains(Players.getLocal()))
				&& Players.getLocal().getAnimation() == -1
				&& !Players.getLocal().isMoving() || 
				(!Inventory.isFull()
						&& Bank_Area.contains(Players.getLocal())
						&& Players.getLocal().getAnimation() == -1
								&& !Players.getLocal().isMoving());
		
	}


	public void execute() {
		if (Bank_Area.contains(Players.getLocal())) {
			System.out.println("walking back to bank");
			Walking.newTilePath(tilepath).reverse().traverse();
			Task.sleep(1000,2000);
		} else if (!Bank_Area.contains(Players.getLocal())){
			System.out.println("walking back to mining area");
			Walking.newTilePath(tilepath).traverse();
			Task.sleep(1000, 2000);

		} else if (Bank_Area.equals(FaladorBank) && Bank_Area.contains(Players.getLocal())) {
			Walking.newTilePath(FaladorWalk).traverse();
			if(!Tile1.canReach() && door1.isOnScreen()) { 
				Camera.turnTo(door1);
				door1.interact("Open"); 
				Task.sleep(800, 900);
				Camera.turnTo(stairs);
				stairs.interact("Climb-down");
				}
			
		}

	
		
		
		
	}
	
}

public class Banking extends Node {

	
	public boolean activate() {
		return (Inventory.isFull()
				&& Bank_Area.contains(Players.getLocal())
				&& Players.getLocal().getAnimation() == -1
				&& !Players.getLocal().isMoving()); 
			
		
		
		//
	}

	@Override
	public void execute() {
		NPC banker = NPCs.getNearest(BANKER_ID);
		if (Bank.open()) {
			System.out.println("depositing ores");
			Bank.deposit(ORE_ID, ORE_ID);
			Task.sleep(200, 500);
			System.out.println("closing bank");
			Bank.close();
			Task.sleep(400, 500);
			
			
		} else if (!Bank.open()
				&& banker !=null 
				&& !banker.isOnScreen()){
			Camera.turnTo(banker);
			Task.sleep(300, 700);
			banker.interact("Bank");
			Task.sleep(800, 1000);
		} else if (!Bank.open()
				&& banker !=null 
				&& banker.isOnScreen())
			banker.interact("Bank");
		Task.sleep(500, 600);
		
	}
	
	
}
	public class Dropping extends Node {

		public boolean activate() {
			return Inventory.getCount() == 28 && !Players.getLocal().isMoving()
					&& Players.getLocal().getAnimation() == -1;
		}

		public void execute() {
			Mouse.setSpeed(Speed.VERY_FAST);
			System.out.println("Attempting to drop ores");
			for (Item i : Inventory.getItems()) {
				if (i.getId() == ORE_ID) {
					i.getWidgetChild().interact("Drop");
					sleep(Random.nextInt(200, 500));
				}
			}
		}

	}

	
		
public class gui extends JFrame  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6172794982225660789L;
	public gui() {
		initComponents();
	}
private void StartButtonActionPerformed(ActionEvent e) {
					String chosen = comboBox1.getSelectedItem().toString();
					String chosenarea = comboBox2.getSelectedItem().toString();
					if (chosen.equals("tin")) {
						ORE_ID = TIN_ORE;
						ORE_ID = TIN_ORE;
						oresToMine = TIN_ID;
					} else if (chosen.equals("copper")) {
						ORE_ID = COPPER_ORE;
						oresToMine = COPPER_ID;

					} else if (chosen.equals("gold"))

					{
						ORE_ID = GOLD_ORE;
						oresToMine = GOLD_ID;

					} else if (chosen.equals("iron")) {
						ORE_ID = IRON_ORE;
						oresToMine = IRON_ID;
					} else if (chosen.equals("coal")) {
						ORE_ID = COAL_ORE;
						oresToMine = COAL;
					} else if (chosen.equals("mithril")) {
						ORE_ID = MITHRIL_ORE;
						oresToMine = MITHRIL_ID;
					}
					if (chosenarea.equals("varrock west")) {
						Bank_Area = VarrockWestBank;
						Mine_Area = VarrockWestMine;
						tilepath = VarrockWest;
						BANKER_ID = SharedVarrockBanker;
					}
					else if (chosenarea.equals("al-kharid")) {
						BANKER_ID = KharidBankers;
						Bank_Area = KharidBank;
						
					}
					else if (chosenarea.equals("varrock east")) {
						Bank_Area = VarrockEastBank;
						Mine_Area = VarrockEastMine;
						tilepath = VarrockEast;
						BANKER_ID = SharedVarrockBanker;
						
	
}
					else if (chosen.equals("falador")) {
						Bank_Area = FaladorBank;
						tilepath = FaladorWalk;
						Mine_Area = FaladorMine;
						BANKER_ID = FaladorBanker;
						
					}
					provide(new Mining(), new Dropping(), new WalkingToBanking(), new Banking());
					m.dispose();
				}
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Carson Teaff
		label1 = new JLabel();
		comboBox1 = new JComboBox<>();
		comboBox2 = new JComboBox<>();
		button1 = new JButton();

		//======== this ========
		setTitle("IronPowerMiner - Made by: Bautista4");
		Container contentPane = getContentPane();

		//---- label1 ----
		label1.setText("IronPowerMiner");
		label1.setFont(new Font("Segoe UI", Font.BOLD, 34));
		label1.setForeground(Color.blue);

		//---- comboBox1 ----
		comboBox1.setForeground(Color.blue);
		comboBox1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
			"varrock west",
			"varrock east",
			"al-kharid",
			"falador"
		}));

		//---- comboBox2 ----
		comboBox2.setForeground(Color.blue);
		comboBox2.setFont(new Font("Segoe UI", Font.BOLD, 14));
		comboBox2.setModel(new DefaultComboBoxModel<>(new String[] {
			"mithril",
			"iron",
			"coal",
			"tin",
			"copper",
			"gold"
		}));

		//---- button1 ----
		button1.setText("Start!");
		button1.setFont(new Font("Segoe UI", Font.BOLD, 24));
		button1.setForeground(Color.blue);
		button1.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartButtonActionPerformed(e);
			}
		});

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(button1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(label1)
								.addComponent(comboBox2, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label1)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(button1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license 
	private JLabel label1;
	private JComboBox<String> comboBox1;
	private JComboBox<String> comboBox2;
	private JButton button1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
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



