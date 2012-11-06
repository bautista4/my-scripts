import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import org.powerbot.core.event.listeners.PaintListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
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
	public static final int IRON_ID = 440;
	
	
	
	Tree jobs = null;

	
	
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
			Mouse.setSpeed(Speed.FAST);
			System.out.println("Executing Mining");
			SceneObject rock = SceneEntities.getNearest(ROCK_ID);
			if (rock != null && rock.isOnScreen()
					&& Players.getLocal().getAnimation() == -1
					&& !Players.getLocal().isMoving())
			

			{
				Mouse.setSpeed(Speed.FAST);
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
			Mouse.setSpeed(Speed.FAST);
			System.out.println("Attempting to drop");
			for (Item i : Inventory.getItems()) {
				if (i.getId() == IRON_ID) {
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
		g.drawString("B4IronPowerMiner", 170, 374);
		g.drawString("Author: bautista", 178, 399);
		g.drawString("Enjoy 99 mining! :D", 161, 441);
		g.drawImage(img2, 357, 417, null);
		g.drawImage(img2, 117, 417, null);
	}

}