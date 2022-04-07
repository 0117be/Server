package ethos.model.players.packets.commands.owner;

import ethos.Config;
import ethos.model.content.TriviaBot;
import ethos.model.players.Player;
import ethos.model.players.packets.commands.Command;

public class Trivia extends Command {

	@Override
	public void execute(Player c, String input) {
		TriviaBot.askQuestion();
	}
}
