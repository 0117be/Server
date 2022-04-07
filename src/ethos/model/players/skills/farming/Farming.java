package ethos.model.players.skills.farming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import ethos.Config;
import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.content.SkillcapePerks;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.content.achievement_diary.falador.FaladorDiaryEntry;
import ethos.model.content.dailytasks.DailyTasks;
import ethos.model.content.dailytasks.DailyTasks.PossibleTasks;
import ethos.model.entity.HealthStatus;
import ethos.model.players.Boundary;
import ethos.model.players.ClientGameTimer;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.mode.ModeType;
import ethos.model.players.skills.Skill;
import ethos.net.discord.DiscordMessager;
import ethos.util.Misc;


/**
 * 
 * @author Jason http://www.rune-server.org/members/jason
 * @Revamped majorly by Tyler or imtyler7 on Rune-Server
 * @date Oct 27, 2013
 */

public class Farming {
	
	public static int[] farmersOutfit = { 13640, 13642, 13644, 13646 };
	
	public static final int MAX_PATCHES = 10;
	private Player player;
	private int weeds;
	
	public Farming(Player player) {
		this.player = player;
	}
	private long lastPoisonBerryFarm;
	
	private boolean hasMagicSecateurs() {
		return player.getItems().playerHasItem(7409) || player.getItems().isWearingItem(7409, 3) || SkillcapePerks.FARMING.isWearing(player) || SkillcapePerks.isWearingMaxCape(player);
	}
	public void handleCompostAdd() {
			if (player.getItems().playerHasItem(FarmingConstants.WEED)) {
				player.compostBin += player.getItems().getItemAmount(FarmingConstants.WEED);
				player.sendMessage("You have added "+player.getItems().getItemAmount(FarmingConstants.WEED)+" weed(s) to the compost bin.");
				player.sendMessage("You have "+player.compostBin+" buckets worth of compost left in the compost bin.");
				player.getItems().deleteItem2(FarmingConstants.WEED, player.getItems().getItemAmount(FarmingConstants.WEED));
			} else {
				player.sendMessage("You have no weeds to add to the compost bin. You currently have "+player.compostBin+" buckets worth of compost.");
			}
	}
	public void handleCompostRemoval() {
		if (!player.getItems().playerHasItem(FarmingConstants.BUCKET)) {
			
			player.sendMessage("You must have a bucket to take compost out of the bin!");
			
		} else if (player.compostBin <= 0) {
			
			player.sendMessage("You have no compost in the compost bin!");
			
		} else if (player.getItems().playerHasItem(FarmingConstants.BUCKET) && player.getItems().getItemAmount(FarmingConstants.BUCKET) <= player.compostBin) {
			
			//int bucketsTotal = player.getItems().getItemAmount(FarmingConstants.BUCKET);
			player.compostBin -= 1;
			player.sendMessage("You have filled a bucket of compost.");
			if (player.compostBin % 5 == 0)
				player.sendMessage("You have "+player.compostBin+" buckets worth of compost left in the compost bin.");
			player.getItems().deleteItem2(FarmingConstants.BUCKET, 1);
			player.getItems().addItem(FarmingConstants.COMPOST, 1);
			if (Boundary.isIn(player, Boundary.FALADOR_BOUNDARY)) {
				player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.COMPOST_BUCKET);
			}
			
		} else if (player.getItems().playerHasItem(FarmingConstants.BUCKET) && player.getItems().getItemAmount(FarmingConstants.BUCKET) > player.compostBin) {
			
			int compostTotal = player.compostBin;
			player.compostBin -= compostTotal;
			player.sendMessage("You have filled "+compostTotal+" buckets of compost.");
			player.sendMessage("You have ran out of compost in the compost bin!");
			player.getItems().deleteItem2(FarmingConstants.BUCKET, compostTotal);
			player.getItems().addItem(FarmingConstants.COMPOST, compostTotal);
			if (Boundary.isIn(player, Boundary.FALADOR_BOUNDARY)) {
				player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.COMPOST_BUCKET);
			}
			
		} else {
			
			player.sendMessage("Code Error - Please report this!");
			
		}
	}
	public boolean patchObjectInteraction(final int objectId, final int itemId, final int x, final int y) {
		
		/**
		 * Skilling outfit pieces
		 */
		int pieces = 0;
		for (int aFarmersOutfit : farmersOutfit) {
			if (player.getItems().isWearingItem(aFarmersOutfit)) {
				pieces += .05;
			}
		}
		
		Patch patch = Patch.get(x, y);
		if(patch == null)
			return false;
		final int id = patch.getId();
		player.turnPlayerTo(x, y);
		if(objectId == FarmingConstants.GRASS_OBJECT ||  objectId == FarmingConstants.HERB_PATCH_DEPLETED //Object Ids
		|| objectId == FarmingConstants.HERB_GROWING_OBJECT1 || objectId == FarmingConstants.HERB_GROWING_OBJECT2
		|| objectId == FarmingConstants.HERB_GROWING_OBJECT3 || objectId == FarmingConstants.HERB_GROWING_OBJECT4) {
			if(player.getFarmingState(id) < State.RAKED.getId()) {
				if(!player.getItems().playerHasItem(FarmingConstants.RAKE, 1)) {
					player.sendMessage("You need to rake this patch to remove all the weeds.", 600000);
					return true;
				}
				
/*				if (id >= 4 && player.amountDonated < 150) {
					player.sendMessage("You must be a Legendary Donator to use the 4 middle patches.", 600000);
					return true;
				}*/
				else if (itemId == FarmingConstants.RAKE || player.getItems().playerHasItem(FarmingConstants.RAKE)) {
					if (player.stopPlayerSkill)
						return true;
					player.stopPlayerSkill = true;
					player.startAnimation(FarmingConstants.RAKING_ANIM);
					player.turnPlayerTo(x, y);
					if(weeds <= 0)
						weeds = 3;
					CycleEventHandler.getSingleton().stopEvents(this);
					CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							if(player == null) {
								container.stop();
								return;
							}
							if (!player.stopPlayerSkill) {
								container.stop();
								return;
							}
							if(weeds > 0) {
								weeds--;
								player.turnPlayerTo(x, y);
								player.getItems().addItem(FarmingConstants.WEED, 1);
								player.startAnimation(FarmingConstants.RAKING_ANIM);
							} else if(weeds == 0) {
								player.setFarmingState(id, State.RAKED.getId());
								player.sendMessage("You raked the patch of all it's weeds, now the patch is ready for compost.", 255);
								player.startAnimation(65535);
								updateObjects();
								container.stop();
							}
						}
	
						@Override
						public void stop() {
							player.stopPlayerSkill = false;
						}
						
					}, 3);
				}
			} else if(player.getFarmingState(id) >= State.RAKED.getId() && player.getFarmingState(id) < State.COMPOST.getId()) {
				if(!player.getItems().playerHasItem(FarmingConstants.COMPOST, 1))
					player.sendMessage("You need to put compost on this to enrich the soil.", 600000);
				else if (itemId == FarmingConstants.COMPOST || player.getItems().playerHasItem(FarmingConstants.COMPOST) && itemId == -1) {
					player.turnPlayerTo(x, y);
					player.startAnimation(FarmingConstants.PUTTING_COMPOST);
					if (itemId == 6032) { 
					player.getItems().deleteItem2(FarmingConstants.COMPOST, 1);
					player.getItems().addItem(FarmingConstants.BUCKET, 1);
					}
					player.setFarmingState(id, State.COMPOST.getId());
					player.sendMessage("You put compost on the soil, it is now time to seed it.", 255);
				}
			} else if(player.getFarmingState(id) >= State.COMPOST.getId() && player.getFarmingState(id) < State.SEEDED.getId()) {
				if(!player.getItems().playerHasItem(FarmingConstants.SEED_DIBBER, 1)) {
					player.sendMessage("You need to use a seed on this patch with a seed dibber in your inventory.", 600000);
					return true;
				}
				final FarmingHerb.Herb herb = FarmingHerb.getHerbForSeed(itemId);
				if (herb == null) {
					player.sendMessage("You must use an appropriate seed on the patch at this stage.", 600000);
					return true;
				}
				if (player.getLevelForXP(player.playerXP[Skill.FARMING.getId()]) < herb.getLevelRequired()) {
					player.sendMessage("You need a farming level of "+herb.getLevelRequired()+" to grow "+herb.getSeedName().replaceAll(" seed", "")+".", 600000);
					return true;
				}
				if (itemId == herb.getSeedId() && player.getItems().playerHasItem(FarmingConstants.SEED_DIBBER)) {
					if (player.stopPlayerSkill)
						return true;
					player.stopPlayerSkill = true;
					player.turnPlayerTo(x, y);
					player.startAnimation(FarmingConstants.SEED_DIBBING);
					/**
					 * Calculate experience
					 */
					double xpBoostOSRS = (herb.getPlantingXp() + herb.getPlantingXp() / 20) * pieces;
					double xpBoostReg = (herb.getPlantingXp() * (Config.FARMING_EXPERIENCE + (hasMagicSecateurs() ? 1 : 0)) + herb.getPlantingXp() * Config.FARMING_EXPERIENCE / 20) * pieces;
					double osrsExperience = (herb.getPlantingXp() + herb.getPlantingXp() / 20) + xpBoostOSRS;
					double regExperience = (herb.getPlantingXp() * (Config.FARMING_EXPERIENCE + (hasMagicSecateurs() ? 1 : 0)) + herb.getPlantingXp() * Config.FARMING_EXPERIENCE / 20) + xpBoostReg;
					if (player.debugMessage)
					System.out.println("Plant xp: " + herb.getPlantingXp() * (Config.FARMING_EXPERIENCE + (hasMagicSecateurs() ? 5 : 0)) + herb.getPlantingXp() + ", Pieces: " + pieces);
					CycleEventHandler.getSingleton().stopEvents(this);
					CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {

						@Override
						public void execute(CycleEventContainer container) {
							if(player == null) {
								container.stop();
								return;
							}
							if (!player.stopPlayerSkill) {
								container.stop();
								return;
							}
							if(!player.getItems().playerHasItem(herb.getSeedId())) {
								container.stop();
								return;
							}
							player.getItems().deleteItem2(herb.getSeedId(), 1);
							player.setFarmingState(id, State.SEEDED.getId());
							player.setFarmingSeedId(id, herb.getSeedId());
							player.setFarmingTime(id, hasMagicSecateurs() ? herb.getGrowthTime() / 2 : herb.getGrowthTime());
							player.setOriginalFarmingTime(id, hasMagicSecateurs() ? herb.getGrowthTime() / 2 : herb.getGrowthTime());
							player.setFarmingHarvest(id, 3 + Misc.random(hasMagicSecateurs() ? 7 : 4));
							player.getPA().addSkillXP((int) (player.getMode().getType().equals(ModeType.OSRS) ? osrsExperience : regExperience), 19, true);
							player.sendMessage("You dib a seed into the soil, it is now time to water it.", 255);
							updateObjects();
							container.stop();
						}

						@Override
						public void stop() {
							player.stopPlayerSkill = false;
						}
						
					}, 3);
				}
			} else if(player.getFarmingState(id) >= State.SEEDED.getId() && player.getFarmingState(id) < State.GROWTH.getId()) {
				if(!player.getItems().playerHasItem(FarmingConstants.WATERING_CAN, 1))
					player.sendMessage("You need to water the herb before you can harvest it.", 600000);
				else if (itemId == FarmingConstants.WATERING_CAN || player.getItems().playerHasItem(FarmingConstants.WATERING_CAN) && itemId == -1) {
					int time = (int) Math.round(player.getFarmingTime(id) * .6);
					player.turnPlayerTo(x, y);
					player.startAnimation(FarmingConstants.WATERING_CAN_ANIM);
					player.setFarmingState(id, State.GROWTH.getId());
					player.sendMessage("You water the herb, wait "+((int) (player.getFarmingTime(id) * .6))+" seconds for the herb to mature.", 255);
					player.getPA().sendGameTimer(ClientGameTimer.FARMING, TimeUnit.SECONDS, time);
					return true;
				}
			} else if(player.getFarmingState(id) == State.GROWTH.getId()) {
				if(player.getFarmingTime(id) > 0) {
					player.sendMessage("You need to wait another "+((int) (player.getFarmingTime(id) * .6))+" seconds until the herb is mature.", 600000);
					return true;
				}
			}
			return true;
		} else if(objectId == FarmingConstants.HERB_OBJECT) {
			if(player.getFarmingState(id) == State.HARVEST.getId()) {
				if(player.getItems().freeSlots() < 1) {
					player.getDH().sendStatement("You need atleast 1 free space to harvest some herbs.");
					player.nextChat = -1;
					return true;
				}
				if(player.getFarmingHarvest(id) == 0 || player.getFarmingState(id) != State.HARVEST.getId()) {
					resetValues(id);
					updateObjects();
					return true;
				}
				final FarmingHerb.Herb herb = FarmingHerb.getHerbForSeed(player.getFarmingSeedId(id));
				/**
				 * Experience calculation
				 */
				double harvestXpBoostOSRS = (herb.getHarvestingXp() + herb.getHarvestingXp() / 5) * pieces;
				double harvestXpBoostReg = (herb.getHarvestingXp() * Config.FARMING_EXPERIENCE + herb.getHarvestingXp() * Config.FARMING_EXPERIENCE / 5) * pieces;
				double osrsHarvestExperience = (herb.getHarvestingXp() + herb.getHarvestingXp() / 5) + harvestXpBoostOSRS;
				double regHarvestExperience = (herb.getHarvestingXp() * Config.FARMING_EXPERIENCE + herb.getHarvestingXp() * Config.FARMING_EXPERIENCE / 5) + harvestXpBoostReg;
				if(player.debugMessage)
					System.out.println("Harvest xp: " + herb.getHarvestingXp() * Config.FARMING_EXPERIENCE + herb.getHarvestingXp() + ", Pieces: " + pieces);
				if(herb != null) {
					if (player.stopPlayerSkill)
						return true;
					player.stopPlayerSkill = true;
					player.startAnimation(FarmingConstants.PICKING_HERB_ANIM);
					Achievements.increase(player, AchievementType.FARM, 1);
					player.sendMessage("You start picking your herbs...", 600000);
					CycleEventHandler.getSingleton().stopEvents(this);
					CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {

						@Override
						public void execute(CycleEventContainer container) {
							if(player == null) {
								container.stop();
								return ;
							}
							if (!player.stopPlayerSkill) {
								container.stop();
								return;
							}
							if(player.getItems().freeSlots() < 1) {
								player.getDH().sendStatement("You need atleast 1 free space to harvest some herbs.");
								player.nextChat = -1;
								player.startAnimation(65535);
								container.stop();
								return;
							}
							if(player.getFarmingHarvest(id) <= 0) {
								player.sendMessage("The herb patch has completely depleted...", 600000);
								Achievements.increase(player, AchievementType.FARM, 1);
								player.startAnimation(65535);
								resetValues(id);
								updateObjects();
								container.stop();
								return;
							}
							switch (herb) {
							case AVANTOE:
								break;
							case CADANTINE:
								break;
							case DWARF_WEED:
								break;
							case GUAM:
								break;
							case HARRALANDER:
								break;
							case IRIT:
								break;
							case KWUARM:
								break;
							case LANTADYME:
								break;
							case MARRENTIL:
								break;
							case RANARR:
								break;
							case SNAP_DRAGON:
								DailyTasks.increase(player, PossibleTasks.SNAPDRAGONS);
								break;
							case TARROMIN:
								break;
							case TOADFLAX:
								break;
							case TORSTOL:
								if (Boundary.isIn(player, Boundary.FALADOR_BOUNDARY)) {
									player.getDiaryManager().getFaladorDiary()
											.progress(FaladorDiaryEntry.HARVEST_TORSTOL);
								}
								break;
							default:
								break;
							
							}
							player.startAnimation(FarmingConstants.PICKING_HERB_ANIM);
							player.setFarmingHarvest(id , player.getFarmingHarvest(id) - 1);
							//if (perk)
								//player.getItems().addItem(herb.getGrimyId()+1, 1);
							//else
							player.getItems().addItem(herb.getGrimyId(), 1);
							player.getPA().addSkillXP((int) (player.getMode().getType().equals(ModeType.OSRS) ? osrsHarvestExperience : regHarvestExperience), 19, true);
							if (Misc.random(herb.getPetChance()) == 20 && player.getItems().getItemCount(20661, false) == 0 && player.summonId != 20661) {
								 PlayerHandler.executeGlobalMessage("[<col=CC0000>News</col>] @cr20@ <col=255>" + player.playerName + "</col> harvested some crops and found a <col=CC0000>Tangleroot</col> pet!");
									DiscordMessager.sendPetsMessage("[Pet Manager]" + player.playerName + " harvested some crops and found a Tangleroot pet!");
								 player.getItems().addItemUnderAnyCircumstance(20661, 1);
							 }
						}

						@Override
						public void stop() {
							player.stopPlayerSkill = false;
						}
						
					}, 3);
				}
			}
			return true;
		}
		return false;
	}
	
	public void farmingProcess() {
		for(int i = 0; i < Farming.MAX_PATCHES; i++) {
	        if (player.getFarmingTime(i) > 0 && player.getFarmingState(i) == Farming.State.GROWTH.getId()) {
	        	player.setFarmingTime(i, player.getFarmingTime(i) - 1);
 				if (player.getFarmingTime(i) > player.getOriginalFarmingTime(i) * .75) {
					if (!player.farmingLagReducer[i]) {
						updateObjects();
						player.farmingLagReducer[i] = true;
					}
				} else if (player.getFarmingTime(i) > player.getOriginalFarmingTime(i) * .50 && player.getFarmingTime(i) < player.getOriginalFarmingTime(i) * .75) {
					player.farmingLagReducer[i] = false;
					if (!player.farmingLagReducer2[i]) {
						updateObjects();
						player.farmingLagReducer2[i] = true;
					}
				} else if (player.getFarmingTime(i) > player.getOriginalFarmingTime(i) * .25 && player.getFarmingTime(i) < player.getOriginalFarmingTime(i) * .50) {
					player.farmingLagReducer2[i] = false;
					if (!player.farmingLagReducer3[i]) {
						updateObjects();
						player.farmingLagReducer3[i] = true;
					}
				} else if (player.getFarmingTime(i) < player.getOriginalFarmingTime(i) * .25 && player.getFarmingTime(i) != 0) {
					player.farmingLagReducer[i] = false;
					if (!player.farmingLagReducer4[i]) {
						updateObjects();
						player.farmingLagReducer4[i] = true;
					}
				} else
	        	if(player.getFarmingTime(i) == 0) {
					player.farmingLagReducer4[i] = false;
	        		FarmingHerb.Herb herb = FarmingHerb.getHerbForSeed(player.getFarmingSeedId(i));
	        		if(herb != null)
	        			player.sendMessage("Your farming patch of "+herb.getSeedName().replaceAll(" seed", "")+" is ready to be harvested.", 255);
	        		player.setFarmingState(i, Farming.State.HARVEST.getId());
					updateObjects();
	        	}
	        }
		}
	}
	
	public void farmPoisonBerry() {
		if (System.currentTimeMillis() - lastPoisonBerryFarm < TimeUnit.MINUTES.toMillis(5)) {
			player.sendMessage("You can only pick berries from this bush every 5 minutes.");
			return;
		}
		int level = player.playerLevel[Skill.FARMING.getId()];
		if (level < 70) {
			player.sendMessage("You need a farming level of 70 to get this.");
			return;
		}
		if (player.getItems().freeSlots() < (hasMagicSecateurs() ? 4 : 2)) {
			player.sendMessage("You need at least " + (hasMagicSecateurs() ? 4 : 2) + " free slots to do this.");
			return;
		}
		int maximum = player.getLevelForXP(player.playerXP[Skill.FARMING.getId()]);
		if (Misc.random(100) < (10 + (maximum - level))) {
			player.getHealth().proposeStatus(HealthStatus.POISON, 6, Optional.empty());
		}
		if (Boundary.isIn(player, Boundary.FALADOR_BOUNDARY)) {
			player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.PICK_POSION_BERRY);
		}
		player.startAnimation(881);
		lastPoisonBerryFarm = System.currentTimeMillis();
		player.getItems().addItem(6018, hasMagicSecateurs() ? 4 : 2);
		player.getPA().addSkillXP(player.getMode().getType().equals(ModeType.OSRS) ? 50 : 2_000, Skill.FARMING.getId(), true);

	}
	
	public void resetValues(int id) {
		player.setFarmingHarvest(id, 0);
		player.setFarmingSeedId(id, 0);
		player.setFarmingState(id, 0);
		player.setFarmingTime(id, 0);
		player.setOriginalFarmingTime(id, 0);
	}
	
	public void updateObjects() {
		for(int i = 0; i < Farming.MAX_PATCHES; i++) {
			Patch patch = Patch.get(i);
			if(patch == null)
				continue;
			if(player.distanceToPoint(patch.getX(), patch.getY()) > 60)
				continue;
			if(player.getFarmingState(i) < State.RAKED.getId()) {
				player.getPA().checkObjectSpawn(FarmingConstants.GRASS_OBJECT, patch.getX(), patch.getY(), 0, 10);
			} else if(player.getFarmingState(i) >= State.RAKED.getId() && player.getFarmingState(i) <= State.SEEDED.getId()) {
				player.getPA().checkObjectSpawn(FarmingConstants.HERB_PATCH_DEPLETED, patch.getX(), patch.getY(), 0, 10);
			} else if(player.getFarmingState(i) == State.GROWTH.getId()) {// 4 stages of herbs
				if (player.getFarmingTime(i) >= player.getOriginalFarmingTime(i) * .75)
					player.getPA().checkObjectSpawn(FarmingConstants.HERB_GROWING_OBJECT1, patch.getX(), patch.getY(), 0, 10);
				else if (player.getFarmingTime(i) >= player.getOriginalFarmingTime(i) * .50 && player.getFarmingTime(i) < player.getOriginalFarmingTime(i) * .75)
					player.getPA().checkObjectSpawn(FarmingConstants.HERB_GROWING_OBJECT2, patch.getX(), patch.getY(), 0, 10);
				else if (player.getFarmingTime(i) > player.getOriginalFarmingTime(i) * .25 && player.getFarmingTime(i) < player.getOriginalFarmingTime(i) * .50)
					player.getPA().checkObjectSpawn(FarmingConstants.HERB_GROWING_OBJECT3, patch.getX(), patch.getY(), 0, 10);
				else if (player.getFarmingTime(i) <= player.getOriginalFarmingTime(i) * .25)
					player.getPA().checkObjectSpawn(FarmingConstants.HERB_GROWING_OBJECT4, patch.getX(), patch.getY(), 0, 10);
			}  else if(player.getFarmingState(i) >= State.HARVEST.getId()) {
				player.getPA().checkObjectSpawn(FarmingConstants.HERB_OBJECT, patch.getX(), patch.getY(), 0, 10);
			}
		}
	}
	
	public boolean isHarvestable(int id) {
		return player.getFarmingState(id) == State.HARVEST.getId();
	}
	public long getLastBerryFarm() {
		return lastPoisonBerryFarm;
	}

	public void setLastBerryFarm(long millis) {
		this.lastPoisonBerryFarm = millis;
	}
	public enum State {
		NONE(0),
		RAKED(1),
		COMPOST(2),
		SEEDED(3),
		WATERED(4),
		GROWTH(5),
		HARVEST(6);
		
		private int id;
		State(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
	}
	
	enum Patch {
		ENTRANA1(0, 3003, 3372),
		ENTRANA2(1, 3003, 3374),
		ENTRANA3(2, 3005, 3372),
		ENTRANA4(3, 3005, 3374),
		DONATOR(4, 2079, 2831),
		DONATOR2(5, 2080, 2831),
		DONATOR3(6, 2080, 2834),
		DONATOR4(7, 2079, 2834),
		DONATOR5(8, 2080, 2837),
		DONATOR6(9, 2079, 2837),
		FARMINGGUILD(10, 3034, 4501),
		FARMINGGUILD1(11, 3036, 4501),
		FARMINGGUILD2(12, 3034, 4499),
		FARMINGGUILD3(13, 3036, 4499),
		FARMINGGUILD4(14, 3031, 4499),
		FARMINGGUILD5(15, 3029, 4499),
		FARMINGGUILD6(16, 3031, 4501),
		FARMINGGUILD7(17, 3029, 4501),
		FARMINGGUILD8(18, 1245, 3750),
		FARMINGGUILD9(19, 1245, 3749),
		FARMINGGUILD10(20, 1243, 3758),
		FARMINGGUILD11(21, 1243, 3758),		
		
		
		FALADOR1(22, 3055, 3304),
		FALADOR2(23, 3055, 3303),
		FALADOR3(24, 3054, 3307),
		FALADOR4(25, 3055, 3307),
		FALADOR5(26, 3055, 3308),
		FALADOR6(27, 3054, 3308),
		FALADOR7(28, 3051, 3307),
		FALADOR8(29, 3050, 3307),
		FALADOR9(30, 3051, 3308),
		FALADOR10(31, 3050, 3308),
		FALADOR11(32, 3050, 3309),
		FALADOR12(33, 3051, 3309),
		FALADOR13(34, 3051, 3310),
		FALADOR14(35, 3050, 3310),
		FALADOR15(36, 3050, 3311),
		FALADOR16(37, 3051, 3311),
		FALADOR17(38, 3050, 3312),
		FALADOR18(39, 3051, 3312),
		FALADOR19(40, 3052, 3311),
		FALADOR20(41, 3053, 3311),
		FALADOR21(42, 3054, 3311),
		FALADOR22(43, 3052, 3312),
		FALADOR23(44, 3053, 3312),
		FALADOR24(45, 3054, 3312),
		FALADOR25(46, 3058, 3311),
		FALADOR26(47, 3059, 3311),
		FALADOR27(48, 3059, 3312),
		FALADOR28(49, 3058, 3312),
		
		FALADOR29(50, 3058, 3308),
		FALADOR30(51, 3059, 3308),
		FALADOR31(52, 3058, 3307),
		FALADOR32(53, 3059, 3307),
		FALADOR33(54, 3059, 3306),
		FALADOR34(55, 3058, 3306),
		FALADOR35(56, 3059, 3305),
		FALADOR36(57, 3058, 3305),
		FALADOR37(58, 3058, 3304),
		FALADOR38(59, 3059, 3304),
		FALADOR39(60, 3058, 3303),
		FALADOR40(61, 3059, 3303),
		FALADOR41(62, 3057, 3304),
		FALADOR42(63, 3056, 3304),
		FALADOR43(64, 3055, 3304),
		FALADOR44(65, 3057, 3303),
		FALADOR45(66, 3056, 3303),
		FALADOR46(67, 3055, 3303),
		
		CATHERBY1(68, 2806, 3466),
		CATHERBY2(69, 2805, 3466),
		CATHERBY3(70, 2805, 3467),
		CATHERBY4(71, 2806, 3467),
		CATHERBY5(72, 2807, 3467),
		CATHERBY6(73, 2808, 3467),
		CATHERBY7(74, 2809, 3467),
		CATHERBY8(75, 2810, 3467),
		CATHERBY9(76, 2811, 3467),
		CATHERBY10(77, 2812, 3467),
		CATHERBY11(78, 2813, 3467),
		CATHERBY12(79, 2814, 3467),
		CATHERBY13(80, 2813, 3468),
		CATHERBY14(81, 2812, 3468),
		CATHERBY15(82, 2811, 3468),
		CATHERBY16(83, 2810, 3468),
		CATHERBY17(84, 2809, 3468),
		CATHERBY18(85, 2808, 3468),
		CATHERBY19(86, 2807, 3468),
		CATHERBY20(87, 2806, 3468),
		CATHERBY21(88, 2805, 3468),
		CATHERBY23(89, 2809, 3464),
		CATHERBY24(90, 2809, 3463),
		CATHERBY25(91, 2810, 3464),
		CATHERBY26(92, 2810, 3463),
		CATHERBY27(93, 2813, 3463),
		CATHERBY28(94, 2814, 3463),
		CATHERBY29(95, 2814, 3464),
		CATHERBY30(96, 2813, 3464),
		CATHERBY31(97, 2814, 3460),
		CATHERBY32(98, 2813, 3460),
		CATHERBY33(99, 2812, 3460),
		CATHERBY34(100, 2811, 3460),
		CATHERBY35(101, 2810, 3460),
		CATHERBY36(102, 2809, 3460),
		CATHERBY37(103, 2808, 3460),
		CATHERBY38(104, 2807, 3460),
		CATHERBY39(105, 2806, 3460),
		CATHERBY40(106, 2805, 3460),
		CATHERBY41(107, 2814, 3459),
		CATHERBY42(108, 2813, 3459),
		CATHERBY43(109, 2812, 3459),
		CATHERBY44(110, 2811, 3459),
		CATHERBY45(111, 2810, 3459),
		CATHERBY46(112, 2809, 3459),
		CATHERBY47(113, 2808, 3459),
		CATHERBY48(114, 2807, 3459),
		CATHERBY49(115, 2806, 3459),
		CATHERBY50(116, 2805, 3459),
		CATHERBY51(117, 2806, 3461),
		CATHERBY52(118, 2805, 3461),
		
		
		HARMONY1(119, 3794, 2833),
		HARMONY2(120, 3794, 2834),
		HARMONY3(121, 3794, 2835),
		HARMONY4(122, 3794, 2836),
		HARMONY5(123, 3794, 2837),
		HARMONY6(124, 3794, 2838),
		HARMONY7(125, 3790, 2837),
		HARMONY8(126, 3789, 2837),
		HARMONY9(127, 3789, 2838),
		HARMONY10(128, 3790, 2838);
		
	

/*		DONATOR1(4, 2810, 3338),
		DONATOR2(5, 2811, 3338),
		DONATOR3(6, 2810, 3335),
		DONATOR4(7, 2811, 3335);*/
		
		
		int id, x, y;
		Patch(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}
		
		public int getId() {
			return this.id;
		}
		
		public int getX() {
			return this.x;
		}
		
		public int getY() {
			return this.y;
		}
		
		static List<Patch> patches = new ArrayList<>();
		
		static {
			Collections.addAll(patches, Patch.values());
		}
		
		public static Patch get(int x, int y) {
			for(Patch patch : patches)
				if(patch.getX() == x && patch.getY() == y)
					return patch;
			return null;
		}
		
		public static Patch get(int id) {
			for(Patch patch : patches)
				if(patch.getId() == id)
					return patch;
			return null;
		}
	}
}