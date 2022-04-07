package ethos.model.players.skills.agility.impl.rooftop;

import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.players.Player;
import ethos.model.players.mode.ModeType;
import ethos.model.players.skills.agility.AgilityHandler;
import ethos.model.players.skills.agility.MarkOfGrace;

/**
 * Rooftop Agility Al Kharid
 * 
 * @author Josh RevelationPS
 */

public class RooftopAlkharid {

	public static final int ROUGH_WALL1 = 10093, 
			TIGHTROPE = 10284, CABLE = 10355, 
			ZIP = 10356, TROP_TREE = 10357, 
			ROOF_BEAM = 10094, TIGHTROPE2 = 10583,
			JUMP_END = 10352;
	
	public static int[] ALKHARID_OBJECTS = { ROUGH_WALL1, TIGHTROPE, CABLE, ZIP, TROP_TREE, ROOF_BEAM, TIGHTROPE2, JUMP_END };

	public boolean execute(final Player c, final int objectId) {
		
		for (int id : ALKHARID_OBJECTS) {
			
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (id == objectId) {
				MarkOfGrace.spawnMarks(c, "ALKHARID");
			}
		}
		
		switch (objectId) {
		case ROUGH_WALL1:
			AgilityHandler.delayEmote(c, "CLIMB_UP", 3273, 3192, 3, 2);
			c.getAgilityHandler().agilityProgress[0] = true;
			return true;
			
		case TIGHTROPE:		
			if (c.getAgilityHandler().hotSpot(c, 3272, 3182)) {
				AgilityHandler.delayEmote(c, "JUMP", 3272, 3171, 3, 2);
				//c.setForceMovement(3272, 3171, 3, 100, "SOUTH", 200);
				c.getAgilityHandler().agilityProgress[1] = true;
			}
			return true;
			
		case CABLE:
			AgilityHandler.delayEmote(c, "JUMP", 3284, 3166, 3, 2);
			c.getAgilityHandler().agilityProgress[2] = true;
			return true;
			
		case ZIP:	
			AgilityHandler.delayEmote(c, "JUMP", 3316, 3163, 1, 1);
		//	c.setForceMovement(3316, 3163, 1, 20, "EAST", -1);
			c.getAgilityHandler().agilityProgress[3] = true;	
			return true;
		
		case TROP_TREE:
			AgilityHandler.delayEmote(c, "JUMP", 3318, 3169, 1, 1);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				int ticks = 0;
				@Override
				public void execute(CycleEventContainer container) {
					if (c.disconnected) {
						stop();
						return;
					}
					switch (ticks++) {			
						
					case 3:
						AgilityHandler.delayEmote(c, "JUMP", 3317, 3174, 2, 1);
						c.getAgilityHandler().agilityProgress[1] = true;
						container.stop();
						break;
					}
				}

				@Override
				public void stop() {

				}
			}, 1);
			return true;
			
		case ROOF_BEAM:
			AgilityHandler.delayEmote(c, "CLIMB", 3316, 3180, 3, 2);
			c.getAgilityHandler().agilityProgress[5] = true;
			return true;
			
		case TIGHTROPE2:
			if (c.getAgilityHandler().agilityProgress[5] == true) {
				AgilityHandler.delayEmote(c, "JUMP", 3301, 3187, 3, 2);
				c.getAgilityHandler().agilityProgress[6] = true;		
			return true;

			}
		case JUMP_END:
			AgilityHandler.delayEmote(c, "JUMP", 3299, 3195, 0, 2);
			c.getAgilityHandler().agilityProgress[7] = true;
			c.getAgilityHandler().roofTopFinished(c, 5, c.getMode().getType().equals(ModeType.OSRS) ? 1440 : 12000, 2000);
			//c.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.ARDOUGNE_ROOFTOP);

			
			return true;
			
		}
		return false;
		
		}
		

}
