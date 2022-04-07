package ethos.model.players.skills.agility.impl;

//import ethos.model.content.achievement_diary.western_provinces.WesternDiaryEntry;
import ethos.model.players.Player;
import ethos.model.players.mode.ModeType;
import ethos.model.players.skills.agility.AgilityHandler;

/**
 * Werewolf Agility
 * 
 * @author Bandit (RevelationPS | Rune-server )
 */

public class WerewolfAgility {

	private static long clickTimer = 0;

	public static final int STEPPING_STONE = 11643, HURDLE = 11640, TUNNEL1 = 11657, SKULLS = 11641, ZIPLINE = 11645;// Object for Werewolf Agility Course
															

	public boolean werewolfCourse(Player c, int objectId) {
		switch (objectId) {
		case STEPPING_STONE:
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (c.getAgilityHandler().hotSpot(c, 3538, 9873)) {
			}
			c.setForceMovement(3540, 9882, 0, 225, "NORTH", c.getAgilityHandler().getAnimation(objectId));
			if (c.getAgilityHandler().agilityProgress[0] == true) {
				
			}

			return true;
		case HURDLE: 
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (c.getAgilityHandler().hotSpot(c, 3540, 9893)) {
				c.setForceMovement(3540, 9895, 0, 15, "NORTH", c.getAgilityHandler().getAnimation(objectId));
			}
			c.getAgilityHandler().resetAgilityProgress();			
			c.getAgilityHandler().agilityProgress[1] = true;
			
			if (c.getAgilityHandler().hotSpot(c, 3540, 9896)) {
				c.setForceMovement(3540, 9898, 0, 15, "NORTH", c.getAgilityHandler().getAnimation(objectId));
			}
			c.getAgilityHandler().resetAgilityProgress();
			c.getAgilityHandler().agilityProgress[1] = true;
			
			if (c.getAgilityHandler().hotSpot(c, 3538, 9893)) {
				c.setForceMovement(3538, 9895, 0, 15, "NORTH", c.getAgilityHandler().getAnimation(objectId));
			}
			c.getAgilityHandler().resetAgilityProgress();
			c.getAgilityHandler().agilityProgress[1] = true;
			
			if (c.getAgilityHandler().hotSpot(c, 3538, 9896)) {
				c.setForceMovement(3538, 9898, 0, 15, "NORTH", c.getAgilityHandler().getAnimation(objectId));
			}
			c.getAgilityHandler().resetAgilityProgress();
			c.getAgilityHandler().agilityProgress[1] = true;
			
			if (c.getAgilityHandler().hotSpot(c, 3542, 9893)) {
				c.setForceMovement(3542, 9895, 0, 400, "NORTH", c.getAgilityHandler().getAnimation(objectId));
			}
			c.getAgilityHandler().resetAgilityProgress();
			c.getAgilityHandler().agilityProgress[1] = true;
			if (c.getAgilityHandler().hotSpot(c, 3542, 9896)) {
				c.setForceMovement(3542, 9898, 0, 400, "NORTH", c.getAgilityHandler().getAnimation(objectId));
			}
			c.getAgilityHandler().resetAgilityProgress();
			c.getAgilityHandler().agilityProgress[1] = true;

			return true;
		case TUNNEL1:
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (c.getAgilityHandler().hotSpot(c, 3541, 9904)) {
			}
			c.setForceMovement(3541, 9910, 0, 200, "NORTH", c.getAgilityHandler().getAnimation(objectId));
			if (c.getAgilityHandler().agilityProgress[2] == true) {

			}

			if (c.getAgilityHandler().hotSpot(c, 3538, 9904)) {
				c.setForceMovement(3538, 9910, 0, 200, "NORTH", c.getAgilityHandler().getAnimation(objectId));
			}
			c.getAgilityHandler().resetAgilityProgress();
			c.getAgilityHandler().agilityProgress[2] = true;

			if (c.getAgilityHandler().hotSpot(c, 3544, 9904)) {
				c.setForceMovement(3544, 9910, 0, 200, "NORTH", c.getAgilityHandler().getAnimation(objectId));
			}
			c.getAgilityHandler().resetAgilityProgress();
			c.getAgilityHandler().agilityProgress[2] = true;
			return true;
		case SKULLS:
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (c.getAgilityHandler().hotSpot(c, 3533, 9910)) {
			}
			c.setForceMovement(3530, 9910, 0, 20, "WEST", c.getAgilityHandler().getAnimation(objectId));
			c.getAgilityHandler().resetAgilityProgress();
			c.getAgilityHandler().agilityProgress[3] = true;
			return true;
		case ZIPLINE:
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (c.getAgilityHandler().hotSpot(c, 3528, 9910)) {
			}
			c.getPA().movePlayer(3528, 9869, 0);
			c.getItems().addItemUnderAnyCircumstance(4179, 1);
			c.getAgilityHandler().agilityProgress[4] = true;
			c.getAgilityHandler().lapFinished(c, 4, c.getMode().getType().equals(ModeType.OSRS) ? 200 : 4500, 9000);
		//	c.getAgilityHandler().resetAgilityProgress();
		//	c.getAgilityHandler().agilityProgress[0] = true;
		//	c.stopMovement();
		 
			return true;
		}
		return false;
	}
}
