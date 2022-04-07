package ethos.model.players.skills.agility.impl.rooftop;

import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.players.Player;
import ethos.model.players.mode.ModeType;
import ethos.model.players.skills.agility.AgilityHandler;
import ethos.model.players.skills.agility.MarkOfGrace;
/**
 * Rooftop Agility Canifis
 * 
 * @author Josh RevelationPS
 */

public class RooftopCanifis {

	public static final int TALL_TREE = 10819, 
			GAP = 10820, GAP2 = 10821, 
			GAP3 = 10828, GAP4 = 10822, 
			VAULT = 10831;
	
	public static int[] CANIFIS_OBJECTS = { TALL_TREE, GAP, GAP2, GAP3, GAP4, VAULT };

	public boolean execute(final Player c, final int objectId) {
		
		for (int id : CANIFIS_OBJECTS) {
			
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (id == objectId) {
				MarkOfGrace.spawnMarks(c, "CANIFIS");
			}
		}
		
		switch (objectId) {
		case TALL_TREE:
			AgilityHandler.delayEmote(c, "CLIMB_UP", 3506, 3492, 2, 2);
			c.getAgilityHandler().agilityProgress[0] = true;
			return true;
			
		case GAP:		
			if (c.getAgilityHandler().hotSpot(c, 3505, 3497)) {
				AgilityHandler.delayEmote(c, "JUMP", 3503, 3504, 2, 2);
				//c.setForceMovement(3272, 3171, 3, 100, "SOUTH", 200);
				c.getAgilityHandler().agilityProgress[1] = true;
			}
			return true;
			
		case GAP2:
			AgilityHandler.delayEmote(c, "JUMP", 3492, 3504, 2, 2);
			c.getAgilityHandler().agilityProgress[2] = true;
			return true;
			
		case GAP3:	
			AgilityHandler.delayEmote(c, "JUMP", 3479, 3498, 3, 1);
		//	c.setForceMovement(3316, 3163, 1, 20, "EAST", -1);
			c.getAgilityHandler().agilityProgress[3] = true;	
			return true;
		
		case GAP4:
			AgilityHandler.delayEmote(c, "JUMP", 3478, 3486, 2, 1);
			c.getAgilityHandler().agilityProgress[3] = true;
			
			return true;
			
		case VAULT:
			AgilityHandler.delayEmote(c, "JUMP", 3484, 3482, 0, 2);
			c.getAgilityHandler().agilityProgress[5] = true;			
			c.getAgilityHandler().roofTopFinished(c, 7, c.getMode().getType().equals(ModeType.OSRS) ? 1150 : 12100, 10000);
			//c.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.ARDOUGNE_ROOFTOP);

			
			return true;
			
		}
		return false;
		
		}
		

}

