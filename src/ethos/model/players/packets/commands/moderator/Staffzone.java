package ethos.model.players.packets.commands.moderator;

import ethos.model.players.Player;
import ethos.model.players.packets.commands.Command;

/**
 * Teleport the player to the staffzone.
 * 
 * @author Emiel
 */
public class Staffzone extends Command {

	@Override
	public void execute(Player c, String input) {
		if (c.inClanWars() || c.inClanWarsSafe()) {
			c.sendMessage("");
			return;
		}
		c.getPA().startTeleport(2208, 4960, 0, "modern", false);
	}
}
