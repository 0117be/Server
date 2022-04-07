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

public class RooftopRelleka {

	public static final int ROUGH_WALL = 11391, 
			GAP = 11392, TIGHTROPE = 11393, 
			GAP2 = 11395, 
			GAP3 = 11396, TIGHTROPE2 = 11397,
			JUMP = 11404;
	
	public static int[] RELLEKA_OBJECTS = { ROUGH_WALL, GAP, TIGHTROPE, GAP2, GAP3, TIGHTROPE2, JUMP };

	public boolean execute(final Player c, final int objectId) {
		
		for (int id : RELLEKA_OBJECTS) {
			
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (id == objectId) {
				MarkOfGrace.spawnMarks(c, "RELLEKA");
			}
		}
		
		switch (objectId) {
		case ROUGH_WALL:
			AgilityHandler.delayEmote(c, "CLIMB_UP", 2622, 3672, 3, 2);
			c.getAgilityHandler().agilityProgress[0] = true;
			return true;
			
		case GAP:		
				AgilityHandler.delayEmote(c, "JUMP", 2621, 3668, 3, 2);
				//c.setForceMovement(3272, 3171, 3, 100, "SOUTH", 200);
				c.getAgilityHandler().agilityProgress[1] = true;
			return true;
			
		case TIGHTROPE:
			AgilityHandler.delayEmote(c, "JUMP", 2627, 3654, 3, 2);
			c.getAgilityHandler().agilityProgress[2] = true;
			return true;
			
		
		case GAP2:
			AgilityHandler.delayEmote(c, "JUMP", 2640, 3652, 3, 1);
			c.getAgilityHandler().agilityProgress[3] = true;
			
			return true;
			
		case GAP3:
			AgilityHandler.delayEmote(c, "JUMP", 2643, 3657, 3, 2);
			c.getAgilityHandler().agilityProgress[4] = true;			
			//c.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.ARDOUGNE_ROOFTOP);

			
			return true;
			
			case TIGHTROPE2:
				AgilityHandler.delayEmote(c, "JUMP", 2655, 3670, 3, 2);
				c.getAgilityHandler().agilityProgress[5] = true;			
				//c.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.ARDOUGNE_ROOFTOP);

				
				return true;
				
		
			case JUMP:
				AgilityHandler.delayEmote(c, "JUMP", 2649, 3678, 0, 2);
				c.getAgilityHandler().agilityProgress[6] = true;			


				c.getAgilityHandler().roofTopFinished(c, 5, c.getMode().getType().equals(ModeType.OSRS) ? 2800 : 36300, 3000);
				
				return true;
		
			
			
			
		}
		return false;
		
		}
		

}

