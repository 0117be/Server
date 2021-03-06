package ethos.model.players.packets.commands.all;

import java.util.Optional;

import ethos.model.players.Player;
import ethos.model.players.packets.commands.Command;

/**
 * Opens the game rule page in the default web browser.
 * 
 * @author Emiel
 */
public class Rules extends Command {

	@Override
	public void execute(Player c, String input) {
		//c.getPA().sendFrame126("http://YOURLINKHERE.com/forums/forumdisplay.php?fid=5", 12000);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Opens a web page with in-game rules");
	}

}
