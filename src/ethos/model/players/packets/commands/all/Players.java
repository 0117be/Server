package ethos.model.players.packets.commands.all;

import ethos.Config;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.packets.commands.Command;


public class Players extends Command {
	
	@Override
	public void execute(Player c, String input) {
		
			c.sendMessage("@cr10@ There are currently: [ @blu@" + PlayerHandler.getPlayerCount() + " @bla@] Players Online. ");
			c.getPA().sendFrame126(Config.SERVER_NAME+" - Online Players", 8144);
			c.getPA().sendFrame126("Online players(" + PlayerHandler.getPlayerCount()+ "):", 8145);
			int line = 8147;
			for (int i = 1; i < Config.MAX_PLAYERS; i++) {
				Player p = c.getClient(i);
				if (!c.validClient(i))
					continue;
				if (p.playerName != null) {
					String title = "";
					
					title += "level-" + p.combatLevel;
					String extra = "(" + (i) + ") ";
					if(c.getRights().getPrimary().isOwner()) {
						c.getPA().sendFrame126("@dre@" + extra + p.playerName + " ("+ title + ") is at " + p.absX + ", "+ p.absY, line);
					} else {
						c.getPA().sendFrame126("@dre@" + extra + p.playerName + " ("+ title + ")", line);
					}
					line++;
				}
			}
			c.getPA().showInterface(8134);
			c.flushOutStream();
		}
	
	
}
