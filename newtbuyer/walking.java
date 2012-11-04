package com.scripts.newtbuyer;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Tile;

public class walking extends Node {

	Tile[] myTiles = new Tile[] { new Tile(3091, 3244, 0),
			new Tile(3092, 3248, 0), new Tile(3086, 3248, 0),
			new Tile(3078, 3248, 0), new Tile(3078, 3253, 0),
			new Tile(3078, 3264, 0), new Tile(3072, 3264, 0),
			new Tile(3072, 3275, 0), new Tile(3072, 3277, 0),
			new Tile(3064, 3276, 0), new Tile(3061, 3268, 0),
			new Tile(3058, 3264, 0), new Tile(3051, 3264, 0),
			new Tile(3046, 3266, 0), new Tile(3043, 3265, 0),
			new Tile(3038, 3266, 0), new Tile(3036, 3264, 0),
			new Tile(3033, 3260, 0), new Tile(3032, 3256, 0),
			new Tile(3028, 3256, 0), new Tile(3024, 3256, 0),
			new Tile(3019, 3256, 0), new Tile(3018, 3258, 0),
			new Tile(3017, 3258, 0), new Tile(3016, 3258, 0) };

	@Override
	public boolean activate() {

		return ((newtbuyer.STORE_Area.contains(Players.getLocal()
				.getLocation()) && Inventory.getCount() == 28) || (newtbuyer.Bank_Area
				.contains(Players.getLocal().getLocation()) && Inventory
				.getCount() == 0));
	}

	@Override
	public void execute() {
		if (newtbuyer.STORE_Area.contains(Players.getLocal()
				.getLocation())) {
			Walking.newTilePath(myTiles).reverse().traverse();
		} else {
			Walking.newTilePath(myTiles).traverse();
		}

	}

}
