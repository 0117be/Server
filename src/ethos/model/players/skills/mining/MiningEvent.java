package ethos.model.players.skills.mining;

import org.apache.commons.lang3.RandomUtils;

import ethos.Config;
import ethos.Server;
import ethos.event.Event;
import ethos.model.content.ShootingStar;
import ethos.model.content.SkillcapePerks;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.content.achievement_diary.desert.DesertDiaryEntry;
import ethos.model.content.achievement_diary.falador.FaladorDiaryEntry;
import ethos.model.content.achievement_diary.fremennik.FremennikDiaryEntry;
import ethos.model.content.achievement_diary.karamja.KaramjaDiaryEntry;
import ethos.model.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import ethos.model.content.achievement_diary.varrock.VarrockDiaryEntry;
import ethos.model.content.achievement_diary.wilderness.WildernessDiaryEntry;
import ethos.model.content.dailytasks.DailyTasks;
import ethos.model.content.dailytasks.DailyTasks.PossibleTasks;
import ethos.model.npcs.NPC;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.mode.ModeType;
import ethos.model.players.skills.Skill;
import ethos.model.players.skills.Smelting;
import ethos.net.discord.DiscordMessager;
import ethos.util.Location3D;
import ethos.util.Misc;
import ethos.world.objects.GlobalObject;

/**
 * Represents a singular event that is executed when a player attempts to mine.
 * 
 * @author Jason MacKeigan
 * @date Feb 18, 2015, 6:17:11 PM
 */
public class MiningEvent extends Event<Player> {
	
	public static int[] prospectorOutfit = { 12013, 12014, 12015, 12016 };

	/**
	 * The amount of cycles that must pass before the animation is updated
	 */
	private final int ANIMATION_CYCLE_DELAY = 3;

	/**
	 * The value in cycles of the last animation
	 */
	private int lastAnimation;

	/**
	 * The pickaxe being used to mine
	 */
	private final Pickaxe pickaxe;

	/**
	 * The mineral being mined
	 */
	private final Mineral mineral;

	/**
	 * The object that we are mning
	 */
	private int objectId;

	/**
	 * The location of the object we're mining
	 */
	private Location3D location;

	/**
	 * The npc the player is mining, if any
	 */
	private NPC npc;

	/**
	 * Constructs a new {@link MiningEvent} for a single player
	 * 
	 * @param player the player this is created for
	 * @param objectId the id value of the object being mined from
	 * @param location the location of the object being mined from
	 * @param mineral the mineral being mined
	 * @param pickaxe the pickaxe being used to mine
	 */
	public MiningEvent(Player attachment, int objectId, Location3D location, Mineral mineral, Pickaxe pickaxe, int time) {
		super("skilling", attachment, time);
		this.objectId = objectId;
		this.location = location;
		this.mineral = mineral;
		this.pickaxe = pickaxe;
	}

	/**
	 * Constructs a new {@link MiningEvent} for a single player
	 * 
	 * @param player the player this is created for
	 * @param npc the npc being from from
	 * @param location the location of the npc
	 * @param mineral the mineral being mined
	 * @param pickaxe the pickaxe being used to mine
	 */
	public MiningEvent(Player attachment, NPC npc, Location3D location, Mineral mineral, Pickaxe pickaxe, int time) {
		super("skilling", attachment, time);
		this.npc = npc;
		this.location = location;
		this.mineral = mineral;
		this.pickaxe = pickaxe;
	}

	@Override
	public void update() {
		if (attachment == null || attachment.disconnected || attachment.getSession() == null) {
			stop();
			return;
		}
		if (!attachment.getItems().playerHasItem(pickaxe.getItemId()) && !attachment.getItems().isWearingItem(pickaxe.getItemId())) {
			attachment.sendMessage("That is strange! The pickaxe could not be found.");
			stop();
			return;
		}
		if (attachment.getItems().freeSlots() == 0) {
			attachment.getDH().sendStatement("You have no more free slots.");
			stop();
			return;
		}
		if (RandomUtils.nextInt(1, 100) == 1 && attachment.getInterfaceEvent().isExecutable()) {
			attachment.getInterfaceEvent().execute();
			stop();
			return;
		}
		if (objectId > 0) {
			if (Server.getGlobalObjects().exists(Mineral.EMPTY_VEIN, location.getX(), location.getY(), location.getZ()) && mineral.isDepletable()) {
				attachment.sendMessage("This vein contains no more minerals.");
				stop();
				return;
			}
		} else {
			if (npc == null || npc.isDead) {
				attachment.sendMessage("This vein contains no more minerals.");
				stop();
				return;
			}
		}
		if (super.getElapsedTicks() - lastAnimation > ANIMATION_CYCLE_DELAY) {
			attachment.startAnimation(pickaxe.getAnimation());
			lastAnimation = super.getElapsedTicks();
		}
	}

	@Override
	public void execute() {
		double osrsExperience = 0;
		double regExperience = 0;
		double xpBoostReg = 0;
		double xpBoostOSRS = 0;
		int pieces = 0;
		for (int i = 0; i < prospectorOutfit.length; i++) {
			if (attachment.getItems().isWearingItem(prospectorOutfit[i])) {
				pieces++;
			}
		}
		if (attachment == null || attachment.disconnected || attachment.getSession() == null) {
			stop();
			return;
		}
		if (mineral.isDepletable()) {
			if (RandomUtils.nextInt(0, mineral.getDepletionProbability()) == 0
					|| mineral.getDepletionProbability() == 0) {
				if (objectId > 0) {
					Server.getGlobalObjects().add(new GlobalObject(Mineral.EMPTY_VEIN, location.getX(), location.getY(),
							location.getZ(), 0, 10, mineral.getRespawnRate(), objectId));
				} else {
					npc.isDead = true;
					npc.actionTimer = 0;
					npc.needRespawn = false;
				}
			}
		}
		if (objectId == 5895) {
			if(ShootingStar.CRASHED_STAR == null || ShootingStar.CRASHED_STAR.getStarObject().getPickAmount() >= ShootingStar.MAXIMUM_MINING_AMOUNT) {
				attachment.getSkilling().stop();
				return;
			} else {
				ShootingStar.CRASHED_STAR.getStarObject().incrementPickAmount();
			}
		}
		attachment.turnPlayerTo(location.getX(), location.getY());
		Achievements.increase(attachment, AchievementType.MINE, 1);
		
		if (Boundary.isIn(attachment, Boundary.RESOURCE_AREA)) {
			if (Misc.random(20) == 5) {
				int randomAmount = Misc.random(3) + 1;
			//	attachment.sendMessage("You received " + randomAmount + " coins while mining!");
			//	attachment.getItems().addItem(995, randomAmount);
			}
		}
		
		/**
		 * Experience calculation
		 */
		if (pieces <= 0) { // Experience Calculation with no Prospector Pieces
			xpBoostReg = 0;
			xpBoostOSRS = 0;
		osrsExperience = mineral.getExperience() + mineral.getExperience() / 10 + xpBoostOSRS;
		regExperience = (mineral.getExperience() * Config.MINING_EXPERIENCE + mineral.getExperience() * Config.MINING_EXPERIENCE / 10) + xpBoostReg;
		}
		if (pieces == 1) { // Experience Calculation with 1 Prospector Pieces
			xpBoostReg = (mineral.getExperience() * Config.MINING_EXPERIENCE + mineral.getExperience() * Config.MINING_EXPERIENCE / 10) * 1;
			xpBoostOSRS = (mineral.getExperience() + mineral.getExperience() / 10) * 1;
		osrsExperience = (mineral.getExperience() + mineral.getExperience() / 10) + xpBoostOSRS;
		regExperience = (mineral.getExperience() * Config.MINING_EXPERIENCE + mineral.getExperience() * Config.MINING_EXPERIENCE / 10) + xpBoostReg;
		}
		if (pieces == 2) { // Experience Calculation with 2 Prospector Pieces
			xpBoostReg = (mineral.getExperience() * Config.MINING_EXPERIENCE + mineral.getExperience() * Config.MINING_EXPERIENCE / 10) * 1.5;
			xpBoostOSRS = (mineral.getExperience() + mineral.getExperience() / 10) * 1.5;
		osrsExperience = (mineral.getExperience() + mineral.getExperience() / 10) + xpBoostOSRS;
		regExperience = (mineral.getExperience() * Config.MINING_EXPERIENCE + mineral.getExperience() * Config.MINING_EXPERIENCE / 10) + xpBoostReg;
		}
		if (pieces == 3) { // Experience Calculation with 3 Prospector Pieces
			xpBoostReg = (mineral.getExperience() * Config.MINING_EXPERIENCE + mineral.getExperience() * Config.MINING_EXPERIENCE / 10) * 1.8;
			xpBoostOSRS = (mineral.getExperience() + mineral.getExperience() / 10) * 1.8;
		osrsExperience = (mineral.getExperience() + mineral.getExperience() / 10) + xpBoostOSRS;
		regExperience = (mineral.getExperience() * Config.MINING_EXPERIENCE + mineral.getExperience() * Config.MINING_EXPERIENCE / 10) + xpBoostReg;
		}
		if (pieces == 4) { // Experience Calculation with 4 Prospector Pieces
			xpBoostReg = (mineral.getExperience() * Config.MINING_EXPERIENCE + mineral.getExperience() * Config.MINING_EXPERIENCE / 10) * 2.2;
			xpBoostOSRS = (mineral.getExperience() + mineral.getExperience() / 10) * 2.2;
		osrsExperience = (mineral.getExperience() + mineral.getExperience() / 10) + xpBoostOSRS;
		regExperience = (mineral.getExperience() * Config.MINING_EXPERIENCE + mineral.getExperience() * Config.MINING_EXPERIENCE / 10) + xpBoostReg;
		}
		attachment.getPA().addSkillXP((int) (attachment.getMode().getType().equals(ModeType.OSRS) ? osrsExperience * 2 : regExperience), Skill.MINING.getId(), true);
		switch (mineral) {
		case ADAMANT:
			Achievements.increase(attachment, AchievementType.MINE_ADAMANT, 1);
			break;
		case COAL:
			if (Boundary.isIn(attachment, Boundary.RELLEKKA_BOUNDARY)) {
				attachment.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.MINE_COAL_FREM);
			}
			break;
		case COPPER:
			break;
		case ESSENCE:
			attachment.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.MINE_ESSENCE);
			break;
		case GEM:
			if (Boundary.isIn(attachment, Boundary.CRAFTING_GUILD_BOUNDARY)) {
				attachment.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.MINE_GEM_FAL);
			}
			DailyTasks.increase(attachment, PossibleTasks.GEM_ROCK);
			break;
		case GOLD:
			if (Boundary.isIn(attachment, Boundary.TZHAAR_CITY_BOUNDARY)) {
				attachment.getDiaryManager().getKaramjaDiary().progress(KaramjaDiaryEntry.MINE_GOLD_KAR);
			}
			break;
		case IRON:
			if (attachment.inWild()) {
				attachment.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.MINE_IRON_WILD);
			}
			if (Boundary.isIn(attachment, Boundary.VARROCK_BOUNDARY)) {
				attachment.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.MINE_IRON);
			}
			if (Boundary.isIn(attachment, Boundary.AL_KHARID_BOUNDARY)) {
				attachment.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.MINE_IRON_LUM);
			}
			break;
		case MITHRIL:
			if (attachment.inWild()) {
				attachment.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.MINE_MITHRIL_WILD);
			}
			if (Boundary.isIn(attachment, Boundary.CRAFTING_GUILD_BOUNDARY)) {
				attachment.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.MINE_MITHRIL);
			}
			break;
		//case RUNE:
			//DailyTasks.increase(attachment, PossibleTasks.RUNITE_ORE);
			//Achievements.increase(attachment, AchievementType.MINE_RUNE, 1);
			//break;
		case TIN:
			break;
		case CLAY:
			attachment.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.MINE_CLAY);
			break;
		default:
			break;
		
		}
		int amount = SkillcapePerks.MINING.isWearing(attachment) || (SkillcapePerks.isWearingMaxCape(attachment) && attachment.getMiningEffect()) && Misc.random(2) == 1 ? 2 :
					attachment.getRechargeItems().hasItem(13104) && Misc.random(9) == 2 ? 2 :
					attachment.getRechargeItems().hasItem(13105) && Misc.random(8) == 2 ? 2 :
					attachment.getRechargeItems().hasItem(13106) && Misc.random(6) == 2 ? 2 :
					attachment.getRechargeItems().hasItem(13107) && Misc.random(4) == 2 ? 2 : 1;
		attachment.getItems().addItem(mineral.getMineralReturn().generate(), amount);
		if (mineral.getBarName() != "none") {
			if (Misc.random(3) == 2) {
				if (attachment.getItems().isWearingItem(13243) || attachment.getItems().playerHasItem(13243)) {
					Smelting.startSmelting(attachment, mineral.getBarName(), "ONE", "INFERNAL");
					return;
				}
			}
		}
		
		if (Misc.random(mineral.getPetChance() / 50) == 10) {
			switch (Misc.random(1)) {
			case 0:
				attachment.getItems().addItemToBank(20358, 1);
				break;
				
			case 1:
				attachment.getItems().addItemToBank(20360, 1);
				break;
			}
			attachment.sendMessage("@blu@You appear to see a clue geode fall within the rock, and pick it up.");
		}
		if (Misc.random(mineral.getPetChance() / 25) == 10) {
			attachment.getItems().addItem(20362, 1);
			attachment.sendMessage("@blu@You appear to see a clue geode fall within the rock, and pick it up.");
		}
		if (Misc.random(mineral.getPetChance() / 25) == 10) {
			attachment.getItems().addItemToBank(19841, 1);
			attachment.sendMessage("@blu@You recieve a master casket!");
		}
		if (Misc.random(30) == 1) {	
			attachment.getItems().addItemToBank(20791, 1);
			attachment.sendMessage("@blu@A skilling supply crate has been added to your bank!");
		}
		

		if (Misc.random(mineral.getPetChance()) == 2 && attachment.getItems().getItemCount(13321, false) == 0
				&& attachment.summonId != 7439) {
			PlayerHandler.executeGlobalMessage("[<col=CC0000>News</col>] @cr20@ <col=255>" + attachment.playerName
					+ "</col> mined a rock mined a rock and a <col=CC0000>Rock golem</col> pet formed from the rubble!");
			DiscordMessager.sendPetsMessage("```[Pet Manager]" + attachment.playerName + " mined a rock and a Rock golem pet formed from the rubble!```");
			attachment.getItems().addItemUnderAnyCircumstance(13321, 1);
		}
	}

	@Override
	public void stop() {
		super.stop();
		if (attachment == null) {
			return;
		}
		attachment.stopAnimation();
	}
}
