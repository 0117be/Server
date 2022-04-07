package ethos.model.players.packets.commands.all;

import java.util.Optional;

import ethos.model.players.Player;
import ethos.model.players.packets.commands.Command;

/**
 * Opens the vote page in the default web browser.
 * 
 * @author Emiel
 */
public class Vote extends Command {

	@Override
	public void execute(Player c, String input) {
	//	c.sendMessage("@red@Voting is not 100% in beta, but we appreciate your support!");
        c.getPA().sendFrame126("https://revelation.everythingrs.com/services/vote", 12000);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Opens a web page where you can vote for rewards");
	}

}
