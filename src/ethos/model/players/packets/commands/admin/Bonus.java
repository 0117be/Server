package ethos.model.players.packets.commands.admin;

import ethos.Config;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.packets.commands.Command;

/**
 * Executing bonus events by {String input}
 * 
 * @author Matt
 */

public class Bonus extends Command {

	public void execute(Player player, String input) {
		
		switch (input) {
		case "":
			player.sendMessage("@red@Usage: ::bonus xp, vote, pc, pkp, drops");
			break;
		
		case "xp":
			Config.BONUS_WEEKEND = Config.BONUS_WEEKEND ? false : true;
			player.sendMessage("Bonus XP is now " + (Config.BONUS_WEEKEND ? "enabled" : "disabled") + ".");
			break;

		case "vote":
			Config.DOUBLE_VOTE_INCENTIVES = Config.DOUBLE_VOTE_INCENTIVES ? false : true;
			player.sendMessage("Double vote incentives are now " + (Config.DOUBLE_VOTE_INCENTIVES ? "enabled" : "disabled") + ".");
			break;
//add discord messages to these too
		case "pc":
			if (!Config.BONUS_PC) {
				PlayerHandler.executeGlobalMessage("<col=3762>[Staff Team]</col></shad><img=2><col=7284>2x PC points event is now being hosted by "+ player.getName() + " go to pest control and join them!");
			}
			Config.BONUS_PC = Config.BONUS_PC ? false : true;
			player.sendMessage("Bonus pc is now " + (Config.BONUS_PC ? "enabled" : "disabled") + ".");
				break;

		case "pkp":
			if (!Config.DOUBLE_PKP) {
				PlayerHandler.executeGlobalMessage("<col=3762>[Staff Team]</col></shad><img=2><col=7284>2x PKP is now being hosted by "+ player.getName() + " this should get the wilderness active!");
			}
			Config.DOUBLE_PKP = Config.DOUBLE_PKP ? false : true;
			player.sendMessage("Double pkp is now " + (Config.DOUBLE_PKP ? "enabled" : "disabled") + ".");
			break;

		case "drops":
			if (!Config.DOUBLE_DROPS) {
				PlayerHandler.executeGlobalMessage("<col=3762>[Staff Team]</col></shad><img=2><col=7284>Double Drops is now being hosted by "+ player.getName() + " 2 drops, 1 PVM kill!");
			}
			Config.DOUBLE_DROPS = Config.DOUBLE_DROPS ? false : true;
			player.sendMessage("Double drops are now " + (Config.DOUBLE_DROPS ? "enabled" : "disabled") + ".");
			break;	
			
		case "droprate":
			if (!Config.DOUBLE_DROPS) {
			PlayerHandler.executeGlobalMessage("<col=3762>[Staff Team]</col></shad><img=2><col=7284>15% Drop Rate boost is now being hosted by "+ player.getName() + ". Enjoy!");
			}
			Config.dropBoost = Config.dropBoost ? false : true;
			player.sendMessage("15% Drop Rate boost are now " + (Config.dropBoost ? "enabled" : "disabled") + ".");
			break;	
			
		case "superior":
			if (Config.superiorSlayerActivated) {
			PlayerHandler.executeGlobalMessage("<col=3762>[Staff Team]</col></shad><img=2><col=7284>Superior Slayer spawns have been disabled by "+ player.getName() + ".");
			}
			Config.superiorSlayerActivated = Config.superiorSlayerActivated ? false : true;
			player.sendMessage("Superior slayer is now " + (Config.superiorSlayerActivated ? "enabled" : "disabled") + ".");
			break;
			
		case "slayerPets":
			if (Config.slayerPetSpawns) {
			PlayerHandler.executeGlobalMessage("<col=3762>[Staff Team]</col></shad><img=2><col=7284>Superior Slayer spawns have been disabled by "+ player.getName() + ".");
			}
			Config.superiorSlayerActivated = Config.superiorSlayerActivated ? false : true;
			player.sendMessage("Superior slayer is now " + (Config.superiorSlayerActivated ? "enabled" : "disabled") + ".");
			break;
		case "pursuit":
			Config.wildyPursuit = Config.wildyPursuit ? false : true;
			player.sendMessage("Wildy Pursuit is now " + (Config.wildyPursuit ? "enabled" : "disabled") + ".");
			break;		
		}
	}

}
