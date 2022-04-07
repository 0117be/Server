package ethos.model.players.packets.commands.all;

import java.util.Optional;

import ethos.model.players.Player;
import ethos.model.players.packets.commands.Command;

/**
 * Opens the highscores in the default web browser.
 * 
 * @author Emiel
 */
public class Twitch extends Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().sendString("https://www.twitch.tv/senistyt", 12000);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Opens a webpage with the highscores");
	}

}
