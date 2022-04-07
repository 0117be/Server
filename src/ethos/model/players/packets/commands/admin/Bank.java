package ethos.model.players.packets.commands.admin;

import ethos.model.players.Player;
import ethos.model.players.Right;
import ethos.model.players.packets.commands.Command;

/**
 * Open the banking interface.
 * 
 * @author Emiel
 */
public class Bank extends Command {

	@Override
	public void execute(Player c, String input) {
		
		if (c.getItems().getItemCount(8152, true) == 1 || c.getRights().isOrInherits(Right.ADMINISTRATOR))
		c.getPA().openUpBank();
	}
}
