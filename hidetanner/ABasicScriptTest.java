package com.scripts.hidetanner;

import java.awt.Graphics;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;

@Manifest(authors = { "Shantred" }, name = "ABasicScriptTest", description = "Test Script", version = 0.1)
public class ABasicScriptTest extends ActiveScript implements PaintListener {

	public static final int TANNED_ID[] = { 1739 };
	public static final int COWHIDE_ID[] = { 1739 };
	public static final int NPC_IDS[] = { 14877 };

	public static final Area bankArea = new Area(new Tile[] {
			new Tile(2892, 3533, 0), new Tile(2888, 3533, 0),
			new Tile(2888, 3527, 0), new Tile(2892, 3526, 0) });
	public static final Area tanArea = new Area(new Tile[] {
			new Tile(2889, 3502, 0), new Tile(2884, 3502, 0),
			new Tile(2883, 3498, 0), new Tile(2888, 3498, 0) });

	Tile[] craftPath = new Tile[] { new Tile(2890, 3530, 0),
			new Tile(2890, 3525, 0), new Tile(2898, 3523, 0),
			new Tile(2898, 3517, 0), new Tile(2897, 3511, 0),
			new Tile(2895, 3508, 0), new Tile(2893, 3505, 0),
			new Tile(2890, 3502, 0), new Tile(2888, 3501, 0) };

	NPC jack = NPCs.getNearest(NPC_IDS);

	private Tree jobs = null;

	@Override
	public void onRepaint(Graphics arg0) {
		

	}

	@Override
	public int loop() {
		if (jobs == null) {
			jobs = new Tree(new Node[] { new Banker(), new TanHides(),
					new MoveToTanner() });
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

	private class TanHides extends Node {
		@Override
		public boolean activate() {

			return jack.isOnScreen() && Inventory.getItem(COWHIDE_ID) != null;
		}

		@Override
		public void execute() {
			System.out.println("Interacting with Tanner");
			jack.interact("Tan-hide");
			Task.sleep(1000, 2000);
			Widgets.get(905, 14).interact("Make All");

		}
	}

	private class MoveToTanner extends Node {
		@Override
		public boolean activate() {
			return (Inventory.getItem(COWHIDE_ID) == null && Inventory
					.getItem(TANNED_ID) != null)
					|| (Inventory.getItem(COWHIDE_ID) != null && Inventory
							.getItem(TANNED_ID) == null);
		}

		@Override
		public void execute() {
			System.out.println("moving");
			if (Inventory.getItem(COWHIDE_ID) != null
					&& Inventory.getItem(TANNED_ID) == null) {
				Walking.newTilePath(craftPath).traverse();
			} else if (Inventory.getItem(COWHIDE_ID) == null
					&& Inventory.getItem(TANNED_ID) != null) {
				Walking.newTilePath(craftPath).reverse().traverse();
			}
		}
	}

	private class Banker extends Node {
		@Override
		public boolean activate() {
			return Inventory.getItem(COWHIDE_ID) == null
					&& bankArea.contains(Players.getLocal().getLocation());
		}

		@Override
		public void execute() {
			System.out.println("Starting Banker");
			/*
			 * Open bank -> Check inventory for Tanned Leather, deposit if any
			 * -> Look for cowhide. Withdraw or close bot. -> Close Tanned
			 * Leather ID: 1741
			 */
			Bank.open();
			if (Inventory.getItem(TANNED_ID) != null) {
				Bank.depositInventory();
				Task.sleep(1000, 2000);
			}

			if (Bank.getItem(COWHIDE_ID).getStackSize() > 0) {
				// Check how many inventory slots they have free
				final int freeSlots = 28 - Inventory.getCount();
				Bank.withdraw(1739, freeSlots);
				Bank.close();
				Task.sleep(1000, 2000);
				System.out.println("Got hides");
				Walking.newTilePath(craftPath).traverse();
			} else {
				Bank.close();
				System.out.println("Ending script");
				stop();

			}
		}

	}

}