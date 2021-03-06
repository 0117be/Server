package ethos.model.npcs.bosses.wildypursuit;

import ethos.Server;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.npcs.NPC;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.combat.Hitmark;
import ethos.util.Misc;

public class Glod {
	
	public static int specialAmount = 0;
	
	public static void glodSpecial(Player player) {
		NPC GLOD = NPCHandler.getNpc(5129);
		
		if (GLOD.isDead) {
			return;
		}
		
		if (GLOD.getHealth().getAmount() < 1400 && specialAmount == 0 ||
			GLOD.getHealth().getAmount() < 1100 && specialAmount == 1 ||
			GLOD.getHealth().getAmount() < 900 && specialAmount == 2 ||
			GLOD.getHealth().getAmount() < 700 && specialAmount == 3 ||
			GLOD.getHealth().getAmount() < 400 && specialAmount == 4 ||
			GLOD.getHealth().getAmount() < 100 && specialAmount == 5) {
				NPCHandler.npcs[GLOD.getIndex()].forceChat("Glod Smash!");
				GLOD.startAnimation(6501);
				GLOD.underAttackBy = -1;
				GLOD.underAttack = false;
				NPCHandler.glodAttack = "SPECIAL";
				specialAmount++;
				PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.PURSUIT_AREAS))
				.forEach(p -> {
					p.appendDamage(Misc.random(25) + 13, Hitmark.HIT);
					p.sendMessage("Glod's hit trembles through your body.");
				});
			}
		}
	
	public static void rewardPlayers(Player player) {
		PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.PURSUIT_AREAS))
		.forEach(p -> {
				//int reward = (p.getGlodDamageCounter() >= 50 ? Misc.random(5) + 3 : 0);
				if (p.getGlodDamageCounter() >= 1) {
					p.sendMessage("@blu@The boss in pursuit has been killed!");
					p.sendMessage("@blu@You receive a Pursuit Crate for damaging the boss!");
					PlayerHandler.executeGlobalMessage("<shad=7000>@red@[PVM Event]@cr33@<col=E70202> The boss has been slain!");
				//	if (p.getItems().freeSlots() >= 1) {
					p.getItems().addItemUnderAnyCircumstance(23071, 1);
					Achievements.increase(p, AchievementType.EVENTBOSS, 1);
					p.sendMessage("@blu@You have recieved 1 boss point.");
					p.bossPoints += 1;
					//} 
				//	if (p.getItems().freeSlots() < 1) {
					//	Server.itemHandler.createGroundItem(player, 23071, player.getX(), player.getY(), player.heightLevel, 1);
					//	p.sendMessage("@blu@You did not have inventory space so the box from the world boss has dropped to the ground.");
					//} 		
					} else {
					p.sendMessage("@blu@You didn't do enough damage to the boss!");
				}
				p.setGlodDamageCounter(0);
		});
	}
}
