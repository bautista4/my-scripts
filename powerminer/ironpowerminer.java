package com.scripts.powerminer;

import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class ironpowerminer extends ActiveScript {

	public int[] ROCK_ID = { 11954 };
	public int[] MINNED_ID = { 11555 };
	public int[] IRON_ID = { 440 };

	Tree jobs = null;

	public int loop() {
		if (jobs == null) {
			jobs = new Tree(new Node[] { new mining(), new dropping() });
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

	public class mining extends Node {

		public boolean activate() {
			return (Inventory.getCount() < 28 && !Players.getLocal().isMoving() && Players
					.getLocal().getAnimation() == -1);
		}

		public void execute() {
			SceneObject rock = SceneEntities.getNearest(ROCK_ID);
			if (rock != null && rock.isOnScreen()
					&& Players.getLocal().getAnimation() == -1
					&& !Players.getLocal().isMoving())
				;
			Mouse.click(rock.getCentralPoint(), true);
			Task.sleep(200, 500);

			if (rock != null && !rock.isOnScreen()
					&& Players.getLocal().getAnimation() == -1
					&& !Players.getLocal().isMoving())
				;
			Camera.turnTo(rock);
			Task.sleep(500, 800);
			Mouse.click(rock.getCentralPoint(), true);
			Task.sleep(400, 600);

		}
	}

	public class dropping extends Node {

		public boolean activate() {
			return Inventory.getCount() == 28 && !Players.getLocal().isMoving()
					&& Players.getLocal().getAnimation() == -1;
		}

		public void execute() {
			if (Inventory.getCount() == 28)
				;
			Inventory.getItem(IRON_ID).getWidgetChild().interact("Drop");
			Task.sleep(100, 150);

		}

	}
}