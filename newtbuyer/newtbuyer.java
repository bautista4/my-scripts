package com.scripts.newtbuyer;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import java.awt.*;

@Manifest(authors = { "bautista4" }, name = "B4EyeOfNewtBuyer", description = "Buys eyes of newt from Port Sarim and then banks them in Draynor for massive profit", version = 0.1)
public class newtbuyer extends ActiveScript implements PaintListener {

	public int[] NEWT_ID = { 221 };
	public int[] BANKER_ID = { 4458 };
	public int[] SELLER_ID = { 583 };
	public static Area Bank_Area = new Area(new Tile(3091, 3246, 0), new Tile(
			3096, 3240, 0), new Tile(3091, 3240, 0));
	public static Area STORE_Area = new Area(new Tile(3010, 3261, 0), new Tile(
			3010, 3256, 0), new Tile(3015, 3261, 0));

	Tree jobs = null;

	public int loop() {
		if (jobs == null) {
			jobs = new Tree(new Node[] { new banking(), new buying(),
					new walking() });
			if (jobs != null) {
				Node job = jobs.state();
				if (job != null) {
					jobs.set(job);
					getContainer().submit(job);
					job.join();
				}
			}
		}
		return 25;
	}

	public class banking extends Node {
		public boolean activate() {
			return Bank_Area.contains(Players.getLocal().getLocation())
					&& Inventory.getCount(NEWT_ID) > 0;
		}

		public void execute() {
			NPC BANKER = NPCs.getNearest(BANKER_ID);
			if (!BANKER.isOnScreen()) {
				Walking.walk(BANKER.getLocation());
				Task.sleep(2000, 3000);
			} else {
				if (!Bank.isOpen()) {
					Bank.open();
					Task.sleep(800, 1200);
				} else {
					Bank.depositInventory();
					Task.sleep(600, 800);
				}
			}
		}
	}

	public class buying extends Node {

		public boolean activate() {
			return STORE_Area.contains(Players.getLocal().getLocation())
					&& Inventory.getCount(NEWT_ID) < 28;

		}

		public void execute() {
			SceneObject store = SceneEntities.getNearest(SELLER_ID);
			if (!store.isOnScreen()) {
				Walking.walk(store.getLocation());
				Task.sleep(200, 250);
			}
			if (store.validate() && store.isOnScreen()
					&& Inventory.getCount(NEWT_ID) < 1) {
				store.interact("Trade");
				Task.sleep(300, 400);
				WidgetChild NEWT = Widgets.get(1265, 20).getChild(8);
				NEWT.interact("Buy all");
				Task.sleep(200, 400);

			}

		}

	}

	private final Color color1 = new Color(0, 0, 204);
	private final Color color2 = new Color(0, 0, 0);

	private final BasicStroke stroke1 = new BasicStroke(1);

	private final Font font1 = new Font("Arial", 1, 20);
	private final Font font2 = new Font("Arial", 1, 15);

	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		g.setColor(color1);
		g.fillRoundRect(6, 345, 507, 124, 16, 16);
		g.setColor(color2);
		g.setStroke(stroke1);
		g.drawRoundRect(6, 345, 507, 124, 16, 16);
		g.setFont(font1);
		g.drawString("B4EyeOfNewtBuyer", 165, 371);
		g.setFont(font2);
		g.drawString("Author: bautista4", 192, 405);
		g.drawString("Huge thanks for helping to: Alaineman and NKN", 85, 434);
	}

}
