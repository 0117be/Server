package ethos.model.players.skills.firemake;

import ethos.Config;
import ethos.Server;
import ethos.clip.Region;
import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import ethos.model.content.dailytasks.DailyTasks;
import ethos.model.content.dailytasks.DailyTasks.PossibleTasks;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.mode.ModeType;
import ethos.net.discord.DiscordMessager;
import ethos.util.Misc;
import ethos.world.objects.GlobalObject;

public class Firemaking {
	
	public static int[] pyromancerOutfit = { 20704, 20706, 20708, 20710 };
	
	public static void lightFire(final Player player, final int logUsed, final String usage) {
		double osrsExperience = 0;
		double regExperience = 0;
		double xpBoostReg = 0;
		double xpBoostOSRS = 0;
		int pieces = 0;
		for (int i = 0; i < pyromancerOutfit.length; i++) {
			if (player.getItems().isWearingItem(pyromancerOutfit[i])) {
				pieces++;
			}
		}
		final int[] time = new int[3];
		final int[] coords = new int[2];
		final LogData log = LogData.getLogData(player, logUsed);
		final int level = log.getlevelRequirement();
		final String name = log.name().toLowerCase().replaceAll("_", " ");

		if (Region.getClipping(player.absX, player.absY, player.heightLevel) != 0
				|| Server.getGlobalObjects().anyExists(player.absX, player.absY, player.heightLevel) || player.inBank()
				|| Boundary.isIn(player, Boundary.DUEL_ARENA) || Boundary.isIn(player, Boundary.HALLOWEEN_ORDER_MINIGAME)) {
			player.sendMessage("You cannot light a fire here.");
			return;
		}
		if (player.playerLevel[11] < level) {
			player.sendMessage("You need a firemaking level of at least " + level + " to light the " + name + ".");
			return;
		}
		
		if (System.currentTimeMillis() - player.lastFire < 1200) {
			return;
		}
		if (player.playerIsFiremaking) {
			return;
		}
		if (log.getlogId() == logUsed) {
			if (usage != "infernal_axe") {
				if (!player.getItems().playerHasItem(logUsed)) {
					player.sendMessage("You do not have anymore of this log.");
					return;
				}
			}

			coords[0] = player.absX;
			coords[1] = player.absY;

			if (usage == "tinderbox") {
				if (System.currentTimeMillis() - player.lastFire > 3000) {
					player.startAnimation(733);
					time[0] = 4;
					time[1] = 3;
				} else {
					time[0] = 1;
					time[1] = 2;
				}
				
				player.playerIsFiremaking = true;
				if (log.getlogId() == 1521) {
					player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.BURN_OAK);
				}
				player.getItems().deleteItem(log.getlogId(), player.getItems().getItemSlot(log.getlogId()), 1);
				Server.itemHandler.createGroundItem(player, log.getlogId(), coords[0], coords[1], player.heightLevel, 1,
						player.getIndex());

				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						Server.getGlobalObjects().add(new GlobalObject(5249, coords[0], coords[1], player.heightLevel, 0, 10, 50, -1));
						Server.itemHandler.removeGroundItem(player, log.getlogId(), coords[0], coords[1], player.heightLevel, false);
						player.playerIsFiremaking = false;
						container.stop();
					}

					@Override
					public void stop() {

					}
				}, time[0]);

				if (Region.getClipping(player.getX() - 1, player.getY(), player.heightLevel, -1, 0)) {
					player.getPA().walkTo(-1, 0);
				} else if (Region.getClipping(player.getX() + 1, player.getY(), player.heightLevel, 1, 0)) {
					player.getPA().walkTo(1, 0);
				} else if (Region.getClipping(player.getX(), player.getY() - 1, player.heightLevel, 0, -1)) {
					player.getPA().walkTo(0, -1);
				} else if (Region.getClipping(player.getX(), player.getY() + 1, player.heightLevel, 0, 1)) {
					player.getPA().walkTo(0, 1);
				}
				if (Misc.random(65) == 1) {	
					player.getItems().addItemToBank(20791, 1);
					player.sendMessage("@blu@A skilling supply crate has been added to your bank!");
				}
				Achievements.increase(player, AchievementType.FIRE, 1);
				if (log.getlogId() == 1515)
					DailyTasks.increase(player, PossibleTasks.LIGHT_YEW_LOGS);
				if (log.getlogId() == 1513)
					DailyTasks.increase(player, PossibleTasks.LIGHT_MAGIC_LOGS);
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						player.startAnimation(65535);
						container.stop();
					}

					@Override
					public void stop() {

					}
				}, time[1]);
				player.turnPlayerTo(player.absX + 1, player.absY);
				player.lastFire = System.currentTimeMillis();
			}
			/**
			 * Experience calculation
			 */
			if (pieces <= 0) { // Experience Calculation with no Pyromancer Pieces
			xpBoostReg = 0;
			xpBoostOSRS = 0;
			osrsExperience = log.getExperience() + log.getExperience() / 10;
			regExperience = log.getExperience() * Config.FIREMAKING_EXPERIENCE + log.getExperience() * Config.FIREMAKING_EXPERIENCE / 10;
			}
			if (pieces == 1) { // Experience Calculation with 1 Pyromancer Pieces
				xpBoostReg = (log.getExperience() * Config.FIREMAKING_EXPERIENCE + log.getExperience() * Config.FIREMAKING_EXPERIENCE / 10) * .05;
				xpBoostOSRS = (log.getExperience() + log.getExperience() / 10 * pieces) * .05;
				osrsExperience = (log.getExperience() + log.getExperience() / 10 * pieces) + xpBoostReg;
			regExperience = (log.getExperience() * Config.FIREMAKING_EXPERIENCE + log.getExperience() * Config.FIREMAKING_EXPERIENCE / 10) + xpBoostReg;
			}
			if (pieces == 2) { // Experience Calculation with 2 Pyromancer Pieces
			xpBoostReg = (log.getExperience() * Config.FIREMAKING_EXPERIENCE + log.getExperience() * Config.FIREMAKING_EXPERIENCE / 10) * .10;
			xpBoostOSRS = (log.getExperience() + log.getExperience() / 10 * pieces) * .10;
			osrsExperience = (log.getExperience() + log.getExperience() / 10 * pieces) + xpBoostReg;
			regExperience = (log.getExperience() * Config.FIREMAKING_EXPERIENCE + log.getExperience() * Config.FIREMAKING_EXPERIENCE / 10) + xpBoostReg;
			}
			if (pieces == 3) { // Experience Calculation with 3 Pyromancer Pieces
				xpBoostReg = (log.getExperience() * Config.FIREMAKING_EXPERIENCE + log.getExperience() * Config.FIREMAKING_EXPERIENCE / 10) * .12;
				xpBoostOSRS = (log.getExperience() + log.getExperience() / 10 * pieces) * .12;
				osrsExperience = (log.getExperience() + log.getExperience() / 10 * pieces) + xpBoostReg;
			regExperience = (log.getExperience() * Config.FIREMAKING_EXPERIENCE + log.getExperience() * Config.FIREMAKING_EXPERIENCE / 10) + xpBoostReg;
			}
			if (pieces == 4) { // Experience Calculation with 4 Pyromancer Pieces
				xpBoostReg = (log.getExperience() * Config.FIREMAKING_EXPERIENCE + log.getExperience() * Config.FIREMAKING_EXPERIENCE / 10) * .2;
				xpBoostOSRS = (log.getExperience() + log.getExperience() / 10 * pieces) * .2;
			osrsExperience = (log.getExperience() + log.getExperience() / 10 * pieces) + xpBoostReg;
			regExperience = (log.getExperience() * Config.FIREMAKING_EXPERIENCE + log.getExperience() * Config.FIREMAKING_EXPERIENCE / 10) + xpBoostReg;
			}
			if (usage == "infernal_axe") {
				player.getPA().addSkillXP((int) (player.getMode().getType().equals(ModeType.OSRS) ? osrsExperience / 2 : regExperience / 2), 11, true);
			} else {
				player.getPA().addSkillXP((int) (player.getMode().getType().equals(ModeType.OSRS) ? osrsExperience * 2 : regExperience), 11, true);
			}
			if (Misc.random(10000) == 2585) {
				if (player.getItems().getItemCount(20693, false) > 0 || player.summonId == 20693) {
					return;
				}
				int rights = player.getRights().getPrimary().getValue() - 1;
				player.getItems().addItemUnderAnyCircumstance(20693, 1);
				PlayerHandler.executeGlobalMessage("[@red@PET@bla@] @cr20@<col=255> <img="+rights+">" + player.playerName + "</col> received a Phoenix pet.");
				DiscordMessager.sendPetsMessage("```[Pet Manager]" + player.playerName + " lit a special fire that birthed a Phoenix Pet! Congratulations!```");
			}
			player.sendMessage("You light the " + name + ".");
		}
	}
}
