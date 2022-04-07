package ethos.model.holiday.christmas;

import java.awt.Point;

import ethos.model.npcs.NPCHandler;
import ethos.util.Misc;

public class AntisantaMinion {

	/**
	 * The id of the minion
	 */
	static final int MINION_ID = 1049;

	/**
	 * The x and y location of the npcs
	 */
	private int xLocation, yLocation;

	/**
	 * The spawn locations
	 */
	private static final int[][] MINION_SPAWNS = new int[][] { 
	 { 1111, 1111 } };

	/**
	 * Updates and spawns the minions
	 */
	public void update() {
		generateLocation();
		NPCHandler.spawnNpc(MINION_ID, xLocation + Misc.random(2), yLocation + Misc.random(2), 0, 1, 130, 10, 180, 300);
		
		@SuppressWarnings("unused")
		int index = 0;
		for (int i = 0; i < MINION_SPAWNS.length; i++) {
			if (xLocation == MINION_SPAWNS[i][0] && yLocation == MINION_SPAWNS[i][1]) {
				index = i + 1;
			}
		}
	}

	/**
	 * Generates a new, random location for the container.
	 */
	private void generateLocation() {
		int oldX = xLocation;
		int oldY = yLocation;
		int attempts = 0;
		while (oldX == xLocation && oldY == yLocation && attempts++ < 50) {
			int index = Misc.random(MINION_SPAWNS.length - 1);
			int locX = MINION_SPAWNS[index][0];
			int locY = MINION_SPAWNS[index][1];
			if (locX != oldX && locY != oldY) {
				xLocation = locX;
				yLocation = locY;
				break;
			}
		}
	}

	/**
	 * Returns the base location
	 * 
	 * @return
	 */
	public Point getLocation() {
		return new Point(xLocation, yLocation);
	}

}

