package ethos.model.players.packets.commands.all;

import java.util.Optional;

import ethos.model.players.Player;
import ethos.model.players.packets.commands.Command;

/**
 * Opens the store page in the default web browser.
 * 
 * @author Emiel
 */
public class Store extends Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().sendFrame126("https://revelation.everythingrs.com/services/store", 12000);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Same as @blu@::donate@blu@");
	}

}
