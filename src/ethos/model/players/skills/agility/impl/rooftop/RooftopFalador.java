package ethos.model.players.skills.agility.impl.rooftop;

import ethos.model.players.Player;
import ethos.model.players.mode.ModeType;
import ethos.model.players.skills.agility.AgilityHandler;
import ethos.util.Misc;

/**
 * Rooftop Agility Draynor
 * 
 * @author Matt
 */

public class RooftopFalador {

	public static final int ROUGH_WALL = 10073, TIGHT_ROPE = 10074, TIGHT_ROPE_2 = 10075, NARROW_WALL = 10077,
			JUMP_UP_WALL = 10084, JUMP_GAP = 10085, CLIMB_DOWN_CRATE = 10086;

	public static int[] DRAYNOR_OBJECTS = { ROUGH_WALL, NARROW_WALL, JUMP_UP_WALL, JUMP_GAP, CLIMB_DOWN_CRATE };

	public boolean execute(final Player c, final int objectId) {

		for (int id : DRAYNOR_OBJECTS) {
			if (System.currentTimeMillis() - c.lastObstacleFail < 3000) {
				return false;
			}
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (id == objectId) {
				if (System.currentTimeMillis() > 1500 * 3) {
					if (Misc.random(12) == 5) {
						c.getItems().addItemUnderAnyCircumstance(11849, 1);
					}
				}
			}
		}

		switch (objectId) {
		case ROUGH_WALL:
			AgilityHandler.delayEmote(c, "CLIMB_UP", 3102, 3279, 3, 2);
			c.getAgilityHandler().agilityProgress[0] = true;
			return true;
		case TIGHT_ROPE:
			c.getAgilityHandler().move(c, -9, 0, 762, -1);
			c.getAgilityHandler().agilityProgress[1] = true;
			return true;
		case TIGHT_ROPE_2:
			AgilityHandler.delayEmote(c, 762, 3092, 3267, 3, 2);
			c.getAgilityHandler().agilityProgress[2] = true;
			return true;
		case NARROW_WALL:
			AgilityHandler.delayEmote(c, "JUMP", 3088, 3261, 3, 2);
			// c.getAgilityHandler().move(c, 4, 0, -1, -1);
			c.getAgilityHandler().agilityProgress[3] = true;
			return true;
		case JUMP_UP_WALL:
			AgilityHandler.delayEmote(c, "JUMP", c.absX, 3255, 3, 2);
			c.getAgilityHandler().agilityProgress[4] = true;
			return true;
		case JUMP_GAP:
			AgilityHandler.delayEmote(c, "JUMP", 3096, 3256, 3, 2);
			c.getAgilityHandler().agilityProgress[5] = true;
			return true;
		case CLIMB_DOWN_CRATE:
			c.getAgilityHandler().roofTopFinished(c, 5, c.getMode().getType().equals(ModeType.OSRS) ? 720 : 6000, 1000);
			AgilityHandler.delayEmote(c, "CLIMB_DOWN", 3103, 3261, 0, 2);
			return true;
		}
		return false;
	}

}
