package ethos.model.players.packets.commands.all;

import ethos.Config;
import ethos.model.players.Player;
import ethos.model.players.packets.commands.Command;

/**
 * Show a red skull above the player's head.
 * 
 * @author Emiel
 *
 */
public class Skull extends Command {

	@Override
	public void execute(Player c, String input) {
		if (c.getItems().playerHasItem(995, 75000)) {
		c.headIconPk = 0;
		c.isSkulled = true;
		c.skullTimer = Config.SKULL_TIMER ;
		c.getItems().deleteItem(995, 75000);
		c.getPA().requestUpdates();
	} else {
		c.sendMessage("You need 75k to quick skull!");
	}
}
}
