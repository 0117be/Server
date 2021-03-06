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

public class Vespula {
	
	public static int specialAmount = 0;
	
	public static void vespulaSpecial(Player player) {
		NPC VESPULA = NPCHandler.getNpc(7531);
		
		if (VESPULA.isDead) {
			return;
		}
		
		if (VESPULA.getHealth().getAmount() < 1400 && specialAmount == 0 ||
				VESPULA.getHealth().getAmount() < 1100 && specialAmount == 1 ||
						VESPULA.getHealth().getAmount() < 900 && specialAmount == 2 ||
								VESPULA.getHealth().getAmount() < 700 && specialAmount == 3 ||
										VESPULA.getHealth().getAmount() < 400 && specialAmount == 4 ||
												VESPULA.getHealth().getAmount() < 100 && specialAmount == 5) {
				//NPCHandler.npcs[VESPULA.getIndex()].forceChat("Vespula ATTACK!");
				VESPULA.startAnimation(7455);
				VESPULA.underAttackBy = -1;
				VESPULA.underAttack = false;
				NPCHandler.VESPULAAttack = "SPECIAL";
				specialAmount++;
				PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.PURSUIT_AREAS))
				.forEach(p -> {
					p.appendDamage(Misc.random(25) + 13, Hitmark.HIT);
					p.sendMessage("Vespulas strikes you very quickly.");
				});
			}
		}
	
	public static void rewardPlayers(Player player) {
		PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.PURSUIT_AREAS))
		.forEach(p -> {
				if (p.getVespulaDamageCounter() >= 1) {
					p.sendMessage("@blu@The boss in pursuit has been killed!");
					PlayerHandler.executeGlobalMessage("<shad=7000>@red@[PVM Event]@cr33@<col=E70202> The boss has been slain!");
					p.sendMessage("@blu@You receive a Pursuit Crate for damaging the boss!");
					//if (p.getItems().freeSlots() >= 1) {
					p.getItems().addItem(23071, 1);
					Achievements.increase(p, AchievementType.EVENTBOSS, 1);
					p.sendMessage("@blu@You have recieved 1 boss point.");
					p.bossPoints += 1;
					//} 
					//if (p.getItems().freeSlots() < 1) {
					//	Server.itemHandler.createGroundItem(player, 23071, player.getX(), player.getY(), player.heightLevel, 1);
					//	p.sendMessage("@blu@You did not have inventory space so the box from the world boss has dropped to the ground.");
					//} 
				} else {
					p.sendMessage("@blu@You didn't do enough damage to the boss!");
				}
				p.setVespulaDamageCounter(0);
		});
	}
}
