package com.scripts.cammyfisher;

import java.awt.*;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Walking;
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
import org.powerbot.game.api.wrappers.node.SceneObject;

@Manifest(authors = { "bautista4" }, name = "B4CammyFisher", description = "Fishes swordfish and tuna in cammy and banks them", version = 0.1)
public class cammyfisher extends ActiveScript implements PaintListener {

	public static final int[] BANKER_ID = { 494, 495 };

	public static Area Bank_Area = new Area(new Tile[] {
			new Tile(2805, 3445, 0), new Tile(2805, 3438, 0),
			new Tile(2811, 3438, 0), new Tile(2811, 3445, 0) });

	public static Area Fish_Area = new Area(new Tile[] {
			new Tile(2829, 3436, 0), new Tile(2831, 3424, 0),
			new Tile(2864, 3417, 0), new Tile(2860, 3434, 0) });

	public static final int FISH_SPOT_ID = 2759;

	public static final int[] FISH = { 371, 359 };

	Tile[] fishingpath = new Tile[] { new Tile(2808, 3440, 0),
			new Tile(2808, 3435, 0), new Tile(2814, 3436, 0),
			new Tile(2818, 3436, 0), new Tile(2824, 3436, 0),
			new Tile(2829, 3436, 0), new Tile(2832, 3435, 0),
			new Tile(2837, 3433, 0) };

	Tree jobs = null;

	public int loop() {
		if (jobs == null) {
			jobs = new Tree(new Node[] { new Fishing(), new Banking(),
					new WalkingtoBank() });
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

	public class Fishing extends Node {

		public boolean activate() {
			return (!Inventory.isFull() && !Players.getLocal().isMoving() && Players
					.getLocal().getAnimation() == -1);

		}

		public void execute() {
			SceneObject fishspot = SceneEntities.getNearest(FISH_SPOT_ID);
			if (fishspot != null && fishspot.isOnScreen()
					&& !Inventory.isFull() && !Players.getLocal().isMoving()
					&& Players.getLocal().getAnimation() == -1
					&& Fish_Area.contains(Players.getLocal().getLocation())) {
				System.out.println("trying to catch fish....");
				fishspot.interact("Harpoon");
				Task.sleep(1000, 2000);

			} else {
				System.out.println("turning camera to fishing spot");
				Camera.turnTo(fishspot);
				System.out.println("turned camera to fishing spot");
				Walking.walk(fishspot);
				System.out.println("walking to fishing spot");
				fishspot.interact("Harpoon");
				System.out.println("trying to catch some fish.....");
				Task.sleep(1000, 2000);
			}

		}

	}

	public class WalkingtoBank extends Node {

		public boolean activate() {
			return (Inventory.getItem(371, 359) != null && Inventory.isFull()
					&& !Players.getLocal().isMoving()
					&& Players.getLocal().getAnimation() == -1 && Fish_Area
						.contains(Players.getLocal().getLocation()))
					|| (!Inventory.isFull() && !Players.getLocal().isMoving()
							&& Players.getLocal().getAnimation() == -1 && Bank_Area
								.contains(Players.getLocal().getLocation()));
		}

		public void execute() {

			if (Fish_Area.contains(Players.getLocal().getLocation())) {
				System.out.println("walking back to bank");
				Walking.newTilePath(fishingpath).reverse().traverse();
				Task.sleep(1000, 2000);

			} else {

				System.out.println("walking back to fishing area");
				Walking.newTilePath(fishingpath).traverse();
				Task.sleep(1000, 2000);

			}

		}

	}

	public class Banking extends Node {

		public boolean activate() {
			return (Inventory.isFull() && !Players.getLocal().isMoving()
					&& Players.getLocal().getAnimation() == -1 && Bank_Area
						.contains(Players.getLocal().getLocation()));
		}

		public void execute() {
			NPC BANKER = NPCs.getNearest(BANKER_ID);
			if (BANKER != null && !BANKER.isOnScreen() && !Bank.open()) {
				System.out.println("turning camera to bank");
				Camera.turnTo(BANKER);
				System.out.println("opening bank");
				Bank.open();
				System.out.println("depositing raw fish in bank");
				Bank.deposit(371, 359);
				Task.sleep(400, 500);
				System.out.println("closing bank");
				Bank.close();

			} else {
				System.out.println("opening bank");
				Bank.open();
				System.out.println("depositing raw fish in bank");
				Bank.deposit(371, 359);
				Task.sleep(400, 500);
				System.out.println("closing bank");
				Bank.close();

			}

		}

	}
    
    private final Color color1 = new Color(0, 0, 204);
    private final Color color2 = new Color(0, 0, 0);

    private final BasicStroke stroke1 = new BasicStroke(4);

    private final Font font1 = new Font("Arial", 1, 20);

    public void onRepaint(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;
        g.setColor(color1);
        g.fillRoundRect(6, 343, 506, 132, 16, 16);
        g.setColor(color2);
        g.setStroke(stroke1);
        g.drawRoundRect(6, 343, 506, 132, 16, 16);
        g.setFont(font1);
        g.drawString("Author: bautista4", 169, 386);
        g.drawString("B4CammyFisher", 176, 361);
        g.drawString("Enjoy 99 fishing!", 174, 421);
    }
    
}
