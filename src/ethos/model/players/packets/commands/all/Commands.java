package ethos.model.players.packets.commands.all;

import java.util.Map.Entry;

import ethos.Server;
import ethos.model.players.Player;
import ethos.model.players.packets.commands.Command;

import java.util.Optional;

/**
 * Shows a list of commands.
 * 
 * @author Emiel
 *
 */
public class Commands extends Command {

	@Override
	public void execute(Player c, String input) {
		if (Server.getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		int counter = 8144;
		c.getPA().sendFrame126("@dre@Revelation Commands", counter++);
		c.getPA().sendFrame126("@blu@::discord @bla@- Stay in tune with the community", counter++);
		c.getPA().sendFrame126("@blu@::vote @bla@- Support the server every 12 hours ", counter++);
		c.getPA().sendFrame126("@blu@::reward 1 all @bla@- Redeem vote tickets", counter++);
		c.getPA().sendFrame126("@blu@::store @bla@- Donate for benefits", counter++);
		c.getPA().sendFrame126("@blu@::claim @bla@- Claims donation rewards", counter++);
		c.getPA().sendFrame126("@blu@::highscores @bla@- Compete with other players", counter++);
		c.getPA().sendFrame126("@blu@::rules @bla@- You must follow all of them", counter++);
		c.getPA().sendFrame126("@blu@::yell @bla@- Shout to the whole server", counter++);
		c.getPA().sendFrame126("@blu@::youtube @bla@- Frequent giveaways", counter++);
		c.getPA().sendFrame126("@blu@::twitch @bla@- Follow for a free mbox", counter++);
		c.getPA().sendFrame126("@blu@::senist @bla@- Server YouTuber", counter++);
		c.getPA().sendFrame126("@blu@::help @bla@- Send in a ticket", counter++);
		c.getPA().sendFrame126("@blu@::stuck @bla@- Wilderness Only", counter++);
		c.getPA().sendFrame126("@blu@::droprate @bla@- Shows droprate", counter++);
		c.getPA().sendFrame126("@blu@::lockexp @bla@- Lock combat XP", counter++);
		c.getPA().sendFrame126("@blu@::hosts @bla@- Shows online dicer hosts", counter++);
		c.getPA().sendFrame126("@blu@::skull @bla@- Lose everything on death", counter++);
		c.getPA().sendFrame126("@blu@ctrl + h @bla@- Teleports you home", counter++);
		c.getPA().sendFrame126("@dre@Donators Only", counter++);
		c.getPA().sendFrame126("@blu@::dz @bla@- Our lovely custom donator zone", counter++);
		c.getPA().sendFrame126("@blu@::edz @bla@- Upgraded donator zone", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().showInterface(8134);
	

	}

}
