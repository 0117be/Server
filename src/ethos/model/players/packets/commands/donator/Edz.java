package ethos.model.players.packets.commands.donator;

import java.util.Optional;

import ethos.model.players.Player;
import ethos.model.players.packets.commands.Command;

/**
 * Teleports the player to the donator zone.
 * 
 * @author Emiel
 */
public class Edz extends Command {

	@Override
	public void execute(Player c, String input) {
		if (c.inTrade || c.inDuel || c.inWild()) {
			return;
		}
		if (c.inClanWars() || c.inClanWarsSafe()) {
			c.sendMessage("@cr10@This player is currently at the pk district.");
			return;
		}
		if (c.amDonated <= 99) {
			c.sendMessage("@cr6@@gre@You must be atleast an Extreme Donator to visit this zone.");
			return;
		}
		c.getPA().startTeleport(2039, 2847, 0, "modern", false);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Teleports you to the donator zone");
	}

}
