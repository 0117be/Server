package ethos.model.players.packets.commands.owner;
import ethos.model.items.ItemDefinition;
import ethos.model.players.Player;
import ethos.model.players.packets.commands.Command;
import ethos.util.Misc;
import ethos.Server;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.packets.commands.Command;
import ethos.world.ShopHandler;

/*
 * @author - James
 *  Just sends packets for debug purposes
 */
public class Shout extends Command {
	@Override
	public void execute(Player player, String input) {
		PlayerHandler.executeGlobalMessage("<shad=7000>@cr34@<col=46FF00> " +player.playerName.toLowerCase() +" has just claimed goodies. ::donate today to support the server!");
		PlayerHandler.executeGlobalMessage("<shad=7000>@cr35@<col=FF8F00> " +player.playerName.toLowerCase() +" has just supported the server! You can ::vote every 12 hours.");
		PlayerHandler.executeGlobalMessage("<shad=7000>[@or1@WOGW@bla@] @cr37@<shad=7000> <col=FF7000> The well is no longer granting bonus experience!");
		PlayerHandler.executeGlobalMessage("<shad=7000>[@blu@BXP@bla@] @cr10@<shad=7000> <col=6666FF> Bonus EXP has ran out!");
		PlayerHandler.executeGlobalMessage("<shad=7000>[<col=6F3100>Daily Box</col>] @cr31@ <col=6F3100>" + player.playerName	+ " hit the jackpot on a Daily Gear Box!");																							
		PlayerHandler.executeGlobalMessage("<shad=7000>[@red@PET@bla@] @cr20@<col=255> " + player.playerName + "</col> received a pet from <col=255>TzTok-Jad</col>.");
		PlayerHandler.executeGlobalMessage("<shad=7000>[Raids]@red@ @cr36@ " + player.playerName + " has received a rare item @red@"+ ItemDefinition.forId(player.raidReward[0][0]).getName() + " from raids!");
		PlayerHandler.executeGlobalMessage("<col=3762><shad=7000>[Staff Team]</col></shad><img=2><col=7284><shad=7000>2x PC points event is now being hosted by "+ player.getName() + " go to pest control and join them!");

	}
}