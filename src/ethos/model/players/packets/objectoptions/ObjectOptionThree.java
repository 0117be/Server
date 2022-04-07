package ethos.model.players.packets.objectoptions;

import ethos.Server;
import ethos.model.content.tradingpost.Listing;
import ethos.model.players.Player;
import ethos.model.players.Right;
import ethos.model.players.PlayerAssistant.PointExchange;

/*
 * @author Matt
 * Handles all 3rd options for objects.
 */

public class ObjectOptionThree {

	public static void handleOption(final Player c, int objectType, int obX, int obY) {
		if (Server.getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		c.clickObjectType = 0;
		// c.sendMessage("Object type: " + objectType);
		if (Server.getHolidayController().clickObject(c, 3, objectType, obX, obY)) {
			return;
		}
		
		if (c.getRights().isOrInherits(Right.OWNER))
			c.sendMessage("Clicked Object Option 3:  "+objectType+"");
		
		switch (objectType) {
		case 8356://streexerics
			c.getPA().movePlayer(1311, 3614, 0);
			break;
		case 26812://Vote Ticket Machine
		case 32546:
			c.getPA().exchangeItems(PointExchange.VOTE_POINTS, 1464, 1);
			c.dialogueAction = -1;
			c.teleAction = -1;
			break;
		case 7811:
			if (!c.inClanWarsSafe()) {
				return;
			}
			c.getDH().sendDialogues(818, 6773);
			break;
		}
	}

}
