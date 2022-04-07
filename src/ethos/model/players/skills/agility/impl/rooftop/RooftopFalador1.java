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

public class RooftopFalador1 {

	public static final int ROUGH_WALL = 10833, 
			TIGHTROPE = 10834, HOLD = 10836, 
			GAP = 11161, GAP2 = 11360, 
			TIGHTROPE2 = 11361, TIGHTROPE3 = 11364,
			GAP3 = 11365, LEDGE = 11366 , LEDGE2 = 11367,
			LEDGE3 = 11369, LEDGE4 = 11370,
			LEDGE5 = 11371;
	
	public static int[] FALADOR1_OBJECTS = { ROUGH_WALL, TIGHTROPE, HOLD, GAP, GAP2, TIGHTROPE2, TIGHTROPE3, GAP3, LEDGE, LEDGE2, LEDGE3, LEDGE4, LEDGE5 };

	public boolean execute(final Player c, final int objectId) {
		
		for (int id : FALADOR1_OBJECTS) {
			
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (id == objectId) {
				MarkOfGrace.spawnMarks(c, "FALADOR1");
			}
		}
		
		switch (objectId) {
		case ROUGH_WALL:
			AgilityHandler.delayEmote(c, "CLIMB_UP", 3036, 3343, 3, 2);
			c.getAgilityHandler().agilityProgress[0] = true;
			return true;
			
		case TIGHTROPE:		
			if (c.getAgilityHandler().hotSpot(c, 3039, 3343)) {
				AgilityHandler.delayEmote(c, "JUMP", 3048, 3343, 3, 2);
				//c.setForceMovement(3272, 3171, 3, 100, "SOUTH", 200);
				c.getAgilityHandler().agilityProgress[1] = true;
			}
			return true;
			
		case HOLD:
			AgilityHandler.delayEmote(c, "JUMP", 3049, 3357, 3, 2);
			c.getAgilityHandler().agilityProgress[2] = true;
			return true;
			
		case GAP:	
			AgilityHandler.delayEmote(c, "JUMP", 3047, 3361, 3, 1);
		//	c.setForceMovement(3316, 3163, 1, 20, "EAST", -1);
			c.getAgilityHandler().agilityProgress[3] = true;	
			return true;
		
		case GAP2:
			AgilityHandler.delayEmote(c, "JUMP", 3041, 3361, 3, 1);
			c.getAgilityHandler().agilityProgress[4] = true;
			
			return true;
			
		case TIGHTROPE2:
			AgilityHandler.delayEmote(c, "JUMP", 3028, 3354, 3, 2);
			c.getAgilityHandler().agilityProgress[5] = true;			
			//c.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.ARDOUGNE_ROOFTOP);

			
			return true;
			
			case TIGHTROPE3:
				AgilityHandler.delayEmote(c, "JUMP", 3020, 3353, 3, 2);
				c.getAgilityHandler().agilityProgress[6] = true;			
				//c.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.ARDOUGNE_ROOFTOP);

				
				return true;
				
			case GAP3:
				AgilityHandler.delayEmote(c, "JUMP", 3017, 3348, 3, 2);
				c.getAgilityHandler().agilityProgress[7] = true;			

				
				return true;
			case LEDGE:
				AgilityHandler.delayEmote(c, "JUMP", 3013, 3346, 3, 2);
				c.getAgilityHandler().agilityProgress[7] = true;			



				
				return true;
			case LEDGE2:
				AgilityHandler.delayEmote(c, "JUMP", 3012, 3342, 3, 2);
				c.getAgilityHandler().agilityProgress[7] = true;			


				
				return true;
			case LEDGE3:
				AgilityHandler.delayEmote(c, "JUMP", 3014, 3333, 3, 2);
				c.getAgilityHandler().agilityProgress[7] = true;			


				
				return true;
			case LEDGE4:
				AgilityHandler.delayEmote(c, "JUMP", 3021, 3333, 3, 2);
				c.getAgilityHandler().agilityProgress[7] = true;			


				
				return true;
			case LEDGE5:
				AgilityHandler.delayEmote(c, "JUMP", 3029, 3333, 0, 2);
				c.getAgilityHandler().agilityProgress[7] = true;			
				c.getAgilityHandler().roofTopFinished(c, 4, c.getMode().getType().equals(ModeType.OSRS) ? 1800 : 26500, 4000);
				//c.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.ARDOUGNE_ROOFTOP);

				
				return true;
			
			
		}
		return false;
		
		}
		

}

