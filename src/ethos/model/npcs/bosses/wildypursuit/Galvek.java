package ethos.model.npcs.bosses.wildypursuit;

import ethos.Server;
import ethos.model.npcs.NPC;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.combat.Hitmark;
import ethos.net.discord.DiscordMessager;
import ethos.util.Misc;
import ethos.world.objects.GlobalObject;

public class Galvek {
	
	public static int specialAmount = 0;
	
	public static void galvekSpecial(Player player) {
		NPC GALVEK = NPCHandler.getNpc(8095);
		
		if (GALVEK.isDead) {
			return;
		}
		
		if (GALVEK.getHealth().getAmount() < 3000 && specialAmount == 0 ||
			GALVEK.getHealth().getAmount() < 2700 && specialAmount == 1 ||
			GALVEK.getHealth().getAmount() < 2350 && specialAmount == 2 ||
			GALVEK.getHealth().getAmount() < 2000 && specialAmount == 3 ||
			GALVEK.getHealth().getAmount() < 1700 && specialAmount == 4 ||
			GALVEK.getHealth().getAmount() < 1400 && specialAmount == 5 ||
			GALVEK.getHealth().getAmount() < 1100 && specialAmount == 6 ||
			GALVEK.getHealth().getAmount() < 900 && specialAmount == 7 ||
			GALVEK.getHealth().getAmount() < 700 && specialAmount == 8 ||
			GALVEK.getHealth().getAmount() < 400 && specialAmount == 9 ||
			GALVEK.getHealth().getAmount() < 100 && specialAmount == 10) {
				NPCHandler.npcs[GALVEK.getIndex()].forceChat("You will burn, humans!");
				GALVEK.startAnimation(7910);
				GALVEK.underAttackBy = -1;
				GALVEK.underAttack = false;
				NPCHandler.galvekAttack = "SPECIAL";
				specialAmount++;
				Server.getGlobalObjects().add(new GlobalObject(23105, 3283, 5070, 0, 2, 10, -1, -1));
				Server.getGlobalObjects().add(new GlobalObject(23105, 3288, 5070, 0, 2, 10, -1, -1));
				Server.getGlobalObjects().add(new GlobalObject(23105, 3285, 5067, 0, 2, 10, -1, -1));
				Server.getGlobalObjects().add(new GlobalObject(23105, 3285, 5072, 0, 2, 10, -1, -1));
				Server.getGlobalObjects().add(new GlobalObject(23105, 3292, 5085, 0, 2, 10, -1, -1));
				Server.getGlobalObjects().add(new GlobalObject(23105, 3285, 5085, 0, 2, 10, -1, -1));
				Server.getGlobalObjects().add(new GlobalObject(23105, 3284, 5086, 0, 2, 10, -1, -1));
				Server.getGlobalObjects().add(new GlobalObject(23105, 3278, 5079, 0, 2, 10, -1, -1));
				Server.getGlobalObjects().add(new GlobalObject(23105, 3282, 5065, 0, 2, 10, -1, -1));
				PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.GALVEK))
				.forEach(p -> {
					p.appendDamage(Misc.random(25) + 13, Hitmark.HIT);
					p.sendMessage("Galvek's attack burns the area around you!");
				});
			}
		}
	
	public static void rewardPlayers(Player player) {
		PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.PURSUIT_AREAS))
		.forEach(p -> {
				//int reward = (p.getGlodDamageCounter() >= 50 ? Misc.random(5) + 3 : 0);
			Server.getGlobalObjects().remove(23105, 3169, 3485, 2);
			Server.getGlobalObjects().remove(23105, 3168, 3494, 2);
			Server.getGlobalObjects().remove(23105, 3159, 3493, 2);
			Server.getGlobalObjects().remove(23105, 3165, 3488, 2);
			Server.getGlobalObjects().remove(23105, 3161, 3484, 2);
			Server.getGlobalObjects().remove(23105, 3157, 3488, 2);
			Server.getGlobalObjects().remove(23105, 3165, 3482, 2);
			Server.getGlobalObjects().remove(23105, 3172, 3490, 2);
			Server.getGlobalObjects().remove(23105, 3163, 3496, 2);
				if (p.getGalvekDamageCounter() >= 1) {
					p.sendMessage("@blu@Galvek has been slain!");
					p.sendMessage("@blu@You receive rewards for your heroics.");
					DiscordMessager.sendAnnouncement("```[PVM]Galvek has been slain! The brave players who have defeated the beast have been rewarded!```");
					p.getItems().addItemUnderAnyCircumstance(10834, 1);
					p.getItems().addItemUnderAnyCircumstance(13307, 100);
					p.getPA().movePlayer(3088, 3505, 0);
				} else {
					p.sendMessage("@blu@You didn't do enough damage to the boss!");
					p.getPA().movePlayer(3088, 3505, 0);
				}
				p.setGalvekDamageCounter(0);
		});
	}
}
