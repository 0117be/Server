package ethos.model.npcs.drops;

import ethos.Config;
import ethos.model.content.SkillcapePerks;
import ethos.model.content.bonus.DropRateBoost;
import ethos.model.content.fame.fameBoosts;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ethos.Server;
import ethos.model.content.godwars.Godwars;
import ethos.model.items.GameItem;
import ethos.model.items.Item;
import ethos.model.items.ItemAssistant;
import ethos.model.npcs.NPC;
import ethos.model.npcs.NPCDefinitions;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.Right;
import ethos.model.players.combat.CombatType;
import ethos.model.players.skills.slayer.SlayerMaster;
import ethos.model.players.skills.slayer.Task;
import ethos.net.discord.DiscordMessager;
import ethos.util.Location3D;
import ethos.util.Misc;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DropManager {


	public static int AMOUNT_OF_TABLES = 0;

	private static final Comparator<Integer> COMPARE_NAMES =(o1, o2) -> {
		String name1 = NPCDefinitions.get(o1).getNpcName();
		String name2 = NPCDefinitions.get(o2).getNpcName();
		return name1.compareToIgnoreCase(name2);
	};

	private Map<List<Integer>, TableGroup> groups = new HashMap<>();

	private List<Integer> ordered = new ArrayList<>();

	private static int amount;

	@SuppressWarnings("unchecked")
	public void read() {
		JSONParser parser = new JSONParser();
		try {
			fileReader = new FileReader("./data/json/npc_droptable.json");
			JSONArray data = (JSONArray) parser.parse(fileReader);
			for (Object aData : data) {
				JSONObject drop=(JSONObject) aData;
				List<Integer> npcIds=new ArrayList<>();
				if (drop.get("npc_id") instanceof JSONArray) {
					JSONArray idArray=(JSONArray) drop.get("npc_id");
					idArray.forEach(id -> npcIds.add(((Long) id).intValue()));
				} else {
					npcIds.add(((Long) drop.get("npc_id")).intValue());
				}
				TableGroup group=new TableGroup(npcIds);
				for (TablePolicy policy : TablePolicy.POLICIES) {
					if (!drop.containsKey(policy.name().toLowerCase())) {
						continue;
					}
					JSONObject dropTable=(JSONObject) drop.get(policy.name().toLowerCase());
					Table table=new Table(policy, ((Long) dropTable.get("accessibility")).intValue());
					JSONArray tableItems=(JSONArray) dropTable.get("items");
					for (Object tableItem : tableItems) {
						JSONObject item=(JSONObject) tableItem;
						int id=((Long) item.get("item")).intValue();
						int minimumAmount=((Long) item.get("minimum")).intValue();
						int maximumAmount=((Long) item.get("maximum")).intValue();
						table.add(new Drop(npcIds, id, minimumAmount, maximumAmount));
					}
					group.add(table);
				}
				groups.put(npcIds, group);
			}
			ordered.clear();

			for (TableGroup group : groups.values()) {
				if (group.getNpcIds().size() == 1) {
					ordered.add(group.getNpcIds().get(0));
					continue;
				}
				for (int id : group.getNpcIds()) {
					String name = NPCDefinitions.get(id).getNpcName();
					if (ordered.stream().noneMatch(i -> NPCDefinitions.get(i).getNpcName().equals(name))) {
						ordered.add(id);
					}
				}
			}

			ordered.sort(COMPARE_NAMES);
			Misc.println("Loaded " + ordered.size() + " drop tables.");
			AMOUNT_OF_TABLES = ordered.size();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	 public List<GameItem> getNPCdrops(int id) {
	        Optional<TableGroup> group = groups.values().stream().filter(g -> g.getNpcIds().contains(id)).findFirst();
	        try {
	            return group.map(g -> {
	                List<GameItem> items = new ArrayList<>();
	                for (TablePolicy policy : TablePolicy.POLICIES) {
	                    if (policy == TablePolicy.RARE || policy == TablePolicy.VERY_RARE) {
	                        Optional<Table> table = g.stream().filter(t -> t.getPolicy() == policy).findFirst();
	                        if (table.isPresent()) {
	                            for (Drop d : table.get()) {
	                                items.add(new GameItem(d.getItemId(), d.getMaximumAmount()));
	                            }
	                        }
	                    }
	                }
	                return items;
	            }).orElse(new ArrayList<>());
	        } catch (Exception e) {
	          //  logger.error("Error getting npc drops {}", id);
	            e.printStackTrace();
	        }
	        return null;
	    }
	public void getDrops(Player player, int id) {
        Optional<TableGroup> group = groups.values().stream().filter(g -> g.getNpcIds().contains(id)).findFirst();
        group.ifPresent(g -> {
            List<GameItem> items = new ArrayList<>();
            for (TablePolicy policy : TablePolicy.POLICIES) {
                if (policy == TablePolicy.RARE || policy == TablePolicy.VERY_RARE) {
                    Optional<Table> table = g.stream().filter(t -> t.getPolicy() == policy).findFirst();
                    if (table.isPresent()) {
                        for(Drop d : table.get()) {
                            items.add(new GameItem(d.getItemId(), d.getMaximumAmount()));
                        }
                    }
                }
            }
            player.dropItems = items;
        });
    }

	/**
	 * Attempts to create a drop for a player after killing a non-playable character
	 *
	 * @param player the player receiving a possible drop
	 * @param npc the npc dropping the items
	 */
	static boolean test = false;

	static int[] bosses = {
			/* Misc bosses */
			6619, 6618, 6615, 6766, 963, 965, 5890, 6609, 319, 6610, 6611, 5779, 6342, 2205, 2215, 3129, 3162, 2054, 2265, 2266, 2267, 7884, 7853,
			7544, 7604, 7605, 7606, 7888,
			/* Godwars minions */
			2206, 2207, 2208, 3130, 3131, 3132, 2216, 2217, 2218, 3163, 3164, 3165
	};
	static int[] normalbosses = { 
			
			5890, 319, 5862, 2265, 2266, 8621, 2267 ,5779, 2215, 3129, 3162, 2205, 494, 6766, 499, 8061, 2042, 2043, 2044, 7144, 965, 8261, 6342 
	};
	static int[] wildernessbosses = {
			
			6611, 6609, 6615, 6610, 2054, 6618, 239, 6619, 7884, 7853, 7888
	};
	static int[] onebosspoint = { 
			
			2265, 2266, 2267, 5779, 6766, 499, 6342 
	};
	static int[] twobosspoint = { 
		
		5890, 5862, 8621, 2215, 3129, 3162, 2205, 494, 8061, 7144, 965
	};
	static int[] threebosspoint = { 
			
			319, 2042, 2043, 2044
	};




	public void testOpen(Player player) {
		for(int i = 0; i < 100; i++) {
			player.getPA().sendFrame126("", (33008  + i));
		}
		for (int index = 0; index < ordered.size(); index++) {
			player.getPA().sendFrame126(StringUtils.capitalize(NPCDefinitions.get(ordered.get(index)).getNpcName().toLowerCase().replaceAll("_", " ")), 33008 + index);
		}

		player.getPA().showInterface(33000);
	}

	public void create(Player player, NPC npc, Location3D location, int repeats) {
		Optional<TableGroup> group = groups.values().stream().filter(g -> g.getNpcIds().contains(npc.npcType)).findFirst();
		group.ifPresent(g -> {
			double modifier = getModifier(player);
			List<GameItem> drops = g.access(player, modifier, repeats);
			for (GameItem item : drops) {
				amount = item.getAmount();
				if (item.getId() == 536) {
					if (player.getRechargeItems().hasItem(13111) && player.inWild()) {
						item.changeDrop(537, item.getAmount());
					}
				}
				if (player.getItems().isWearingItem(22557) && player.inWild())  {
				if (item.getId() == 1073) {//Adamant Platelegs
						item.changeDrop(1074, item.getAmount());
					}
				if (item.getId() == 1213) { // Rune Dagger
					item.changeDrop(1214, item.getAmount());
				}
				if (item.getId() == 1303) { // Rune Longsword
					item.changeDrop(1304, item.getAmount());
				}
				if (item.getId() == 1215) { // Dragon Dagger
					item.changeDrop(1216, item.getAmount());
				}
				if (item.getId() == 1201) { // Rune Kiteshield
					item.changeDrop(1202, item.getAmount());
				}
				if (item.getId() == 1163) { // Rune Fullhelm
					item.changeDrop(1164, item.getAmount());
				}
				if (item.getId() == 1127) { // Rune Platebody
					item.changeDrop(1128, item.getAmount());
				}
				if (item.getId() == 1079) { // Rune Platelegs
					item.changeDrop(1080, item.getAmount());
				}
				if (item.getId() == 1347) { // Rune Warhammer
					item.changeDrop(1348, item.getAmount());
				}
				if (item.getId() == 4087) { // Dragon Platelegs
					item.changeDrop(4088, item.getAmount());
				}
				if (item.getId() == 4585) { // Dragon Plateskirt
					item.changeDrop(4586, item.getAmount());
				}
				if (item.getId() == 1377) { // Dragon Battleaxe
					item.changeDrop(1378, item.getAmount());
				}
				if (item.getId() == 1305) { // Dragon Longsword
					item.changeDrop(1306, item.getAmount());
				}
				if (item.getId() == 4585) { // Dragon Plateskirt
					item.changeDrop(4586, item.getAmount());
				}
			}
			/*	if(item.isStackable()) {
					amount *= fameBoosts.getBoost(player);
					player.sendMessage("Fame: " + fameBoosts.getBoost(player) + "base drop: " + item.getAmount() + " after fame: " + amount);
				}*/
				if (item.getId() == 6529) {
					if (player.getRechargeItems().hasItem(11136)) {
						item.changeDrop(6529, (int) (amountt * 1.20));
					}
					if (player.getRechargeItems().hasItem(11138)) {
						item.changeDrop(6529, (int) (amount * 1.50));
					}
					if (player.getRechargeItems().hasItem(11140)) {
						item.changeDrop(6529, (int) (amount * 1.70));
					}
					if (player.getRechargeItems().hasItem(13103)) {
						item.changeDrop(6529, (int) (amount * 1.90));
					}
				}
		
				if (item.getId() == 6729 && player.getRechargeItems().hasItem(13132)) {
					item.changeDrop(6730, amount);
				}
				if (item.getId() == 13233 && !Boundary.isIn(player, Boundary.CERBERUS_BOSSROOMS)) {
					player.sendMessage("@red@Something hot drops from the body of your vanquished foe.");
				}
				if (item.getId() == 989) {
					player.sendMessage("@blu@A crystal key appears on the ground.");
				}
				if (item.getId() == 21817 && Boundary.isIn(player, Boundary.REV_CAVE)) {
					PlayerHandler.nonNullStream()
					.filter(p -> p.distanceToPoint(player.absX, player.absY) < 10 && p.heightLevel == player.heightLevel)
					.forEach(p -> {
					p.sendMessage("@mag@" + Misc.formatPlayerName(player.playerName) + " received a: " + Misc.format(item.getAmount()) + " x " + Item.getItemName(item.getId()) + ", congratulations.");
					});
			}
				
				if (IntStream.of(bosses).anyMatch(id -> id == npc.npcType)) {
					PlayerHandler.nonNullStream()
					
					.filter(p -> p.distanceToPoint(player.absX, player.absY) < 10 && p.heightLevel == player.heightLevel)
					.forEach(p -> {
						if (item.getAmount() > 1)
							p.sendMessage("@mag@" + Misc.formatPlayerName(player.playerName) + " received a rare drop: " + Misc.format(item.getAmount()) + " x " + Item.getItemName(item.getId()) + ", congratulations.");
						else
							p.sendMessage("@mag@" + Misc.formatPlayerName(player.playerName) + " received a rare drop: " + Item.getItemName(item.getId()) + ", congratulations.");
					});
				}
				if (player.getItems().isWearingItem(12785) && (item.getId() == 995)) {
					player.getItems().addItem(995, item.getAmount());
			} else 
				if (player.getItems().isWearingItem(2572) && (item.getId() == 995)) {
					player.getItems().addItem(995, item.getAmount());
			} else 
				if (player.getItems().isWearingItem(21126) && (item.getId() == 995)) {
					player.getItems().addItem(995, item.getAmount());
				} else 
					if (player.getItems().isWearingItem(21126) && (item.getId() == 2996)) {
						player.getItems().addItem(2996, item.getAmount());
					} else 
						if (player.getItems().isWearingItem(21126) && (item.getId() == 21820)) {
							player.getItems().addItem(21820, item.getAmount());
						} else 
							if (player.getItems().isWearingItem(21126) && (item.getId() == 12934)) {
								player.getItems().addItem(12934, item.getAmount());
							} else 
								if (player.getItems().isWearingItem(21126) && (item.getId() == 989)) {
									player.getItems().addItem(989, item.getAmount());
								} else 
									if (player.getItems().isWearingItem(21126) && (item.getId() == 13307)) {
										player.getItems().addItem(13307, item.getAmount());
									} else 
									if (player.getItems().isWearingItem(21126) && (item.getId() == 3464)) {
										player.getItems().addItem(3464, item.getAmount());
									} else 
										if (player.getItems().isWearingItem(21126) && (item.getId() == 11943)) {
											player.getItems().addItem(11943, item.getAmount());
										} else 
											if (player.getItems().isWearingItem(21126) && (item.getId() == 536)) {
												player.getItems().addItem(537, item.getAmount());
											} else 
												if (player.getItems().isWearingItem(21126) && (item.getId() == 22124)) {
													player.getItems().addItem(22125, item.getAmount());
			} else 
				if (player.getItems().isWearingItem(21816) && player.absorptionToggle == true && (item.getId() == 21820)) {
					player.setEtherCharge(player.getEtherCharge() + item.getAmount());
				}
				else {
				
					
				Server.itemHandler.createGroundItem(player, item.getId(), location.getX(), location.getY(),
						location.getZ(), amount, player.getIndex());

			}
			
		}


			if (IntStream.of(onebosspoint).anyMatch(id -> id == npc.npcType)  && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
				PlayerHandler.nonNullStream();
				player.sendMessage("@blu@You have recieved 1 boss point.");
				player.bossPoints += 1;
						
			}
			if (IntStream.of(onebosspoint).anyMatch(id -> id == npc.npcType)  && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
				PlayerHandler.nonNullStream();
				player.sendMessage("@blu@You have recieved 2 boss points today only!");
				player.bossPoints += 2;
						
			}
			if (IntStream.of(twobosspoint).anyMatch(id -> id == npc.npcType)  && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
				PlayerHandler.nonNullStream();
				player.sendMessage("@blu@You have recieved 2 boss points.");
				player.bossPoints += 2;
						
			}
			if (IntStream.of(twobosspoint).anyMatch(id -> id == npc.npcType)  && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
				PlayerHandler.nonNullStream();
				player.sendMessage("@blu@You have recieved 4 boss points today only!");
				player.bossPoints += 4;
						
			}
			if (IntStream.of(threebosspoint).anyMatch(id -> id == npc.npcType)  && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
				PlayerHandler.nonNullStream();
				player.sendMessage("@blu@You have recieved 3 boss points.");
				player.bossPoints += 3;
						
			}
			if (IntStream.of(threebosspoint).anyMatch(id -> id == npc.npcType)  && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
				PlayerHandler.nonNullStream();
				player.sendMessage("@blu@You have recieved 6 boss points today only!");
				player.bossPoints += 6;
						
			}
			
			
			
			
			
			
			
			
			
				if (Misc.random(1000) == 1 && npc != null && npc.npcType == 7286 && player.getItems().getItemCount(21273, false) == 0 && player.summonId != 425) {
				PlayerHandler.executeGlobalMessage("[<col=CC0000>News</col>] @cr20@ <col=255>" + player.playerName
						+ " received a Skotos pet! Congratulations!");
				DiscordMessager.sendPetsMessage("```[Pet Manager]" + player.playerName + " received a Skotos pet! Congratulations!```");
				player.getItems().addItemUnderAnyCircumstance(21273, 1);
				}
			if (npc != null && npc.npcType == 7888) {
				PlayerHandler.executeGlobalMessage("<col=FF0000><shad=7000>[Wildy Activity] @cr27@ "+ Misc.capitalize(player.playerName) + " received a Wilderness Key from Dusk. Kill them!</col>");
			}
			if (IntStream.of(wildernessbosses).anyMatch(id -> id == npc.npcType) && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
				PlayerHandler.nonNullStream();
				player.sendMessage("@blu@You have recieved 4 boss points.");
				player.bossPoints += 2;
			}
			if (IntStream.of(wildernessbosses).anyMatch(id -> id == npc.npcType) && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
				PlayerHandler.nonNullStream();
				player.sendMessage("@blu@You have recieved 8 boss points today only!");
				player.bossPoints += 8;
						
	}
			/**
			 * Looting bag and rune pouch
			 */
			if (npc.inWild()) {
				switch (Misc.random(60)) {
				case 2:
					if (player.getItems().getItemCount(11941, true) < 1) {
						Server.itemHandler.createGroundItem(player, 11941, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
					}
					break;

			//	case 8:
					//if (player.getItems().getItemCount(12791, true) < 1) {
				///		Server.itemHandler.createGroundItem(player, 12791, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
				//	}
				//	break;
				}
			}
			
	/**
	 * Slayer's staff enchantment and Emblems
	 */
	Optional<Task> task = player.getSlayer().getTask();
	Optional<SlayerMaster> myMaster = SlayerMaster.get(player.getSlayer().getMaster());
	task.ifPresent(t -> {
	String name = npc.getDefinition().getNpcName().toLowerCase().replaceAll("_", " ");
	
		if (name.equals(t.getPrimaryName()) || ArrayUtils.contains(t.getNames(), name)) {
			myMaster.ifPresent(m -> {
				if (npc.inWild() && m.getId() == 7663) {
					int slayerChance = 750;
					int emblemChance = 55;
					int emblemChance2 = 250;
					int emblemChance3 = 500;
					int emblemChance4 = 750;
					int emblemChance5 = 1000;
					int emblemChance6 = 1250;
					int emblemChance7 = 1750;
					int emblemChance8 = 2500;
					int emblemChance9 = 3000;
					int emblemChance10 = 4500;
					int emblemChance11 = 100;
					int pkpChance = 100;
					int bmChance = 100;
					int zamorakChance = 6392;
					int guthixChance = 6392;
					int saradominChance = 6392;

					if (Misc.random(zamorakChance) == 1063) {
						if (player.getItems().getItemCount(21795, false) < 1 && player.getItems().getItemCount(21780, false) < 1) {
						Server.itemHandler.createGroundItem(player, 21795, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@The great power of Zamorak was hidden inside the creature!");
							PlayerHandler.executeGlobalMessage("@red@Someone has received an Imbued Zamorak Cape from a Wilderness Task!@red@");

					}
				}
					if (Misc.random(guthixChance) == 1062) {
						if (player.getItems().getItemCount(21793, false) < 1 && player.getItems().getItemCount(21784, false) < 1) {
						Server.itemHandler.createGroundItem(player, 21793, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@gre@The great power of Guthix was hidden inside the creature!");
						PlayerHandler.executeGlobalMessage("@gre@Someone has received an Imbued Guthix Cape from a Wilderness Task!@gre@");

					}
				}
					if (Misc.random(saradominChance) == 1061) {
						if (player.getItems().getItemCount(21791, false) < 1 && player.getItems().getItemCount(21776, false) < 1) {
						Server.itemHandler.createGroundItem(player, 21791, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("<col=39423><SHAD=39423>The great power of Saradomin was hidden inside the creature!</col>.</SHAD>");
						PlayerHandler.executeGlobalMessage("<col=39423><SHAD=39423>Someone has received an Imbued Saradomin Cape from a Wilderness Task!</col>.</SHAD>");

					}
				}
					if (Misc.random(emblemChance) == 1) {
						Server.itemHandler.createGroundItem(player, 12746, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A mysterious emblem has dropped from your foe!");
							//PlayerHandler.executeGlobalMessage("<SHAD=324432>Someone has received a Myserious Emblem</col>.</SHAD>");

					}
					if (Misc.random(emblemChance2) == 5 ) {
						Server.itemHandler.createGroundItem(player, 12748, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A mysterious emblem has dropped from your foe!");
							//PlayerHandler.executeGlobalMessage("<SHAD=324432>Someone has received a Myserious Emblem</col>.</SHAD>");

					}
					if (Misc.random(emblemChance3) == 10 ) {
						Server.itemHandler.createGroundItem(player, 12749, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A mysterious emblem has dropped from your foe!");
							//PlayerHandler.executeGlobalMessage("<SHAD=324432>Someone has received a Myserious Emblem</col>.</SHAD>");

					}
					if (Misc.random(emblemChance4) == 15 ) {
						Server.itemHandler.createGroundItem(player, 12750, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A mysterious emblem has dropped from your foe!");
							//PlayerHandler.executeGlobalMessage("<SHAD=324432>Someone has received a Myserious Emblem</col>.</SHAD>");

					}
					if (Misc.random(emblemChance5) == 20) {
						Server.itemHandler.createGroundItem(player, 12751, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A mysterious emblem has dropped from your foe!");
							//PlayerHandler.executeGlobalMessage("<SHAD=324432>Someone has received a Myserious Emblem</col>.</SHAD>");

					}
					if (Misc.random(emblemChance6) == 25 ) {
						Server.itemHandler.createGroundItem(player, 12752, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A mysterious emblem has dropped from your foe!");
							//PlayerHandler.executeGlobalMessage("<SHAD=324432>Someone has received a Myserious Emblem</col>.</SHAD>");

					}
					if (Misc.random(emblemChance7) == 30) {
						Server.itemHandler.createGroundItem(player, 12753, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A mysterious emblem has dropped from your foe!");
							//PlayerHandler.executeGlobalMessage("<SHAD=324432>Someone has received a Myserious Emblem</col>.</SHAD>");

					}
					if (Misc.random(emblemChance8) == 40 ) {
						Server.itemHandler.createGroundItem(player, 12754, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A mysterious emblem has dropped from your foe!");
							//PlayerHandler.executeGlobalMessage("<SHAD=324432>Someone has received a Myserious Emblem</col>.</SHAD>");

					}
					if (Misc.random(emblemChance9) == 45 ) {
						Server.itemHandler.createGroundItem(player, 12755, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A mysterious emblem has dropped from your foe!");
							//PlayerHandler.executeGlobalMessage("<SHAD=324432>Someone has received a Myserious Emblem</col>.</SHAD>");

					}
					if (Misc.random(emblemChance10) == 50) {
						Server.itemHandler.createGroundItem(player, 12756, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A mysterious emblem has dropped from your foe!");
							//PlayerHandler.executeGlobalMessage("<SHAD=324432>Someone has received a Myserious Emblem</col>.</SHAD>");

					}
					if (Misc.random(pkpChance) == 26) {
						Server.itemHandler.createGroundItem(player, 2996, location.getX(), location.getY(), location.getZ(), (1 + Misc.random(10)), player.getIndex());
						player.sendMessage("@red@Your foe has dropped some PK Points!");
					}
					if (Misc.random(bmChance) == 30) {
						Server.itemHandler.createGroundItem(player, 13307, location.getX(), location.getY(), location.getZ(), (1 + Misc.random(25)), player.getIndex());
						player.sendMessage("@red@Your foe has dropped some Blood Money!");
					}
					if (Misc.random(emblemChance11) == 75) {
						if (player.getItems().isWearingItem(21126)) {
							player.getItems().addItemUnderAnyCircumstance(3464, 1);
							player.sendMessage("@red@A Slayer key has been added to your inventory!");
						}
							else 
						Server.itemHandler.createGroundItem(player, 3464, location.getX(), location.getY(), location.getZ(), (1 + Misc.random(1)), player.getIndex());
						player.sendMessage("@red@Your foe has dropped a slayer key!");
						PlayerHandler.executeGlobalMessage("<col=FF0000><shad=7000>[Wildy Drop] @cr27@ "+ Misc.capitalize(player.playerName) + " received a Slayer Key! The Hunt is on.</col>.");
						PlayerHandler.executeGlobalMessage("<col=FF0000><shad=7000>"+ Misc.capitalize(player.playerName) + " killed - "+ player.getSlayer().getTask().get().getPrimaryName() + " at level " + player.wildLevel +" wilderness");
					}
					if (Misc.random(slayerChance) == 19 ) {
						Server.itemHandler.createGroundItem(player, 21257, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A slayer's enchantment has dropped from your foe!");
					//	PlayerHandler.executeGlobalMessage("<col=FF0000>[Lootations] @cr19@ </col><col=255>"+ Misc.capitalize(player.playerName) + "</col> received a <col=255>Slayer's Enchantment</col>.");

									
					}
				}
			});
		}
	});
			/**
			 * Clue scrolls
			 */
			int chance = player.getRechargeItems().hasItem(13118) ? 142 : player.getRechargeItems().hasItem(13119) ? 135 : player.getRechargeItems().hasItem(13120) ? 120 : 150;
			if (Misc.random(chance) == 1) {
				//player.sendMessage("@pur@You sense a @red@clue scroll @pur@being dropped to the ground.");
				if (npc.getDefinition().getNpcCombat() > 0 && npc.getDefinition().getNpcCombat() <= 70) {
					Server.itemHandler.createGroundItem(player, 2677, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
				}
				if (npc.getDefinition().getNpcCombat() > 70 && npc.getDefinition().getNpcCombat() <= 110) {
					Server.itemHandler.createGroundItem(player, 2801, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
				}
				if (npc.getDefinition().getNpcCombat() > 110) {
					Server.itemHandler.createGroundItem(player, 2722, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
				}
			}


			/**
			 * Runecrafting pouches
			 */
			if (Misc.random(10) == 5) {
				if (npc.getDefinition().getNpcCombat() >= 70 && npc.getDefinition().getNpcCombat() <= 100 && player.getItems().getItemCount(5509, true) == 1 && player.getItems().getItemCount(5510, true) != 1) {
					Server.itemHandler.createGroundItem(player, 5510, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
					player.sendMessage("@pur@The monster has dropped a Runecrafting Pouch!");
				} else if (npc.getDefinition().getNpcCombat() > 100 && player.getItems().getItemCount(5510, true) == 1 && player.getItems().getItemCount(6819, true) != 1) {
					Server.itemHandler.createGroundItem(player, 6819, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
					player.sendMessage("@pur@The monster has dropped a Runecrafting Pouch!");
				}
			}

			/**
			 * Crystal keys
			 */
			if (Misc.random(115) == 1) {
				//player.sendMessage("@pur@You sense a @red@crystal key @pur@being dropped to the ground.");
				if (player.getItems().isWearingItem(21126)) {
					player.getItems().addItemUnderAnyCircumstance(989, 1);
					player.sendMessage("@blu@A crystal key has been added to your inventory!");
				}
					else 
				Server.itemHandler.createGroundItem(player, 989, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
				if (player.getItems().isWearingItem(2572) || player.getItems().isWearingItem(12785)) {
				player.sendMessage("@blu@A crystal key has been dropped!");
				}
		
				}
			

			/**
			 * Ecumenical Keys
			 */
			if (Boundary.isIn(npc, Boundary.WILDERNESS_GOD_WARS_BOUNDARY)) {
				if (Misc.random(60 + 10 * player.getItems().getItemCount(Godwars.KEY_ID, true)) == 1) {
					/**
					 * Key will not drop if player owns more than 3 keys already
					 */
					int key_amount = player.getDiaryManager().getWildernessDiary().hasCompleted("ELITE") ? 6 : 3;
					if (player.getItems().getItemCount(Godwars.KEY_ID, true) > key_amount) {
						return;
					}
					Server.itemHandler.createGroundItem(player, Godwars.KEY_ID, npc.getX(), npc.getY(), player.heightLevel, 1, player.getIndex());
					player.sendMessage("@pur@An Ecumenical Key drops from your foe.");
				}
			}
			if (Boundary.isIn(npc, Boundary.BARROWS)) {
				if (Misc.random(50) == 1) {
					player.getItems().addItemUnderAnyCircumstance(3451, 1);
					player.sendMessage("@or1@A barrows key has been added to your inventory.");
				}
			}


			/**
			 * Dark Light
			 */
			if (Boundary.isIn(npc, Boundary.CHASM_OF_FIRE)) {
				if (Misc.random(50) == 1) {
					player.sendMessage("@red@You notice a dark energy coming from a sword on the ground");
					PlayerHandler.executeGlobalMessage("<col=FF0000>"+ Misc.capitalize(player.playerName) + "</col> received a <col=255>Darklight from Chasm of Fire!</col>.");
					Server.itemHandler.createGroundItem(player, 6746, location.getX(), location.getY(), location.getZ(), 3, player.getIndex());	
				}
			}

			/**
			 * Dark totem Pieces
			 */
			if (Boundary.isIn(npc, Boundary.CATACOMBS)) {
				switch (Misc.random(25)) {
				case 1:
					if (player.getItems().getItemCount(19679, false) < 1) {
						Server.itemHandler.createGroundItem(player, 19679, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A surge of dark energy fills your body as you notice something on the ground.");
					}
					break;

				case 2:
					if (player.getItems().getItemCount(19681, false) < 1) {
						Server.itemHandler.createGroundItem(player, 19681, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A surge of dark energy fills your body as you notice something on the ground.");
					}
					break;


				case 3:
					if (player.getItems().getItemCount(19683, false) < 1) {
						Server.itemHandler.createGroundItem(player, 19683, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A surge of dark energy fills your body as you notice something on the ground.");
					}
					break;
				}
			}
		});
	}

	private double getModifier(Player player) {
		double modifier = 1.0;
		if(Config.dropBoost) {
			modifier -= 0.15;
		}
		if(player.getMode().isOsrs()) {
			modifier -= 0.10;
		}
		if (player.amDonated >= 2000) {
			modifier -= 0.35;
		} else if(player.amDonated >= 1500) {
			modifier -= 0.325;
		} else if (player.amDonated >= 1000) {
			modifier -= 0.30;
		} else if(player.amDonated >= 750) {
			modifier -= 0.275;
		} else if (player.amDonated >= 500) {
			modifier -= 0.25;
		} else if(player.amDonated >= 375) {
			modifier -= 0.225;
		} else if (player.amDonated >= 250) {
			modifier -= 0.20;
		} else if(player.amDonated >= 175) {
			modifier -= 0.175;
		} else if (player.amDonated >= 100) {
			modifier -= 0.15;
		} else if(player.amDonated >= 75) {
			modifier -= 0.125;		
		} else if (player.amDonated >= 50) {
			modifier -= 0.10;
		} else if(player.amDonated >= 25) {
			modifier -= 0.075;
		} else if (player.amDonated >= 10) {
			modifier -= 0.050;
		
		}
			if (Boundary.isIn(player, Boundary.EDONATOR_ZONE)) {
				modifier -= .15;
		}
		if (player.summonId == 7891 && player.inWild()) {
			modifier -= 0.050;
		}
		if (player.summonId == 7893 && player.inWild()) {
			modifier -= 0.050;
		}
		if (player.getItems().isWearingItem(21126)) {
			modifier -= .20;
		}
		if (player.getItems().isWearingItem(21126)) {
			modifier -= .20;
		}
		if (SkillcapePerks.isWearingMaxCape(player)) {
			modifier -= .05;
		}
		
		if (player.getItems().getItemCount(30017, true) >= 1 && Boundary.isIn(player, Boundary.GODWARS_BOSSROOMS)) {	
			modifier -= .10;
		}
		if (player.getItems().getItemCount(30016, true) >= 1 && Boundary.isIn(player, Boundary.ZULRAH)) {	
			modifier -= .10;
		}
		if (player.getItems().getItemCount(30019, true) >= 1 && Boundary.isIn(player, Boundary.KRAKEN_CAVE)) {	
			modifier -= .10;
		}
		if (player.getItems().getItemCount(30020, true) >= 1 && Boundary.isIn(player, Boundary.CERBERUS_BOSSROOMS)) {	
			modifier -= .10;
		}
		if (player.getItems().getItemCount(30018, true) >= 1 && Boundary.isIn(player, Boundary.VORKATH)) {	
			modifier -= .10;
		}
		if (player.getItems().getItemCount(30018, true) >= 1 && Boundary.isIn(player, Boundary.DRAGONS)) {	
			modifier -= .10;
		}
		if (player.getItems().getItemCount(30018, true) >= 1 && Boundary.isIn(player, Boundary.	LAVA_DRAGON_ISLE)) {	
			modifier -= .10;
		}
		if (player.getItems().isWearingItem(773)) {
			modifier -= .99;
		}
		
		if (player.getItems().isWearingItem(2572)) {
			modifier -= .05;
		} else if (player.getItems().isWearingItem(12785)) {
			modifier -= .10;
		}
		if (DropRateBoost.isDropRateBoost()) {
			modifier -= .05;
	}
		if (Boundary.isIn(player, Boundary.REV_CAVE)) {
			if (player.isSkulled) {
			modifier -= .05;
		}
		}
		if(player.getRights().isOrInherits(Right.HARDCORE)){
			modifier -= .07;
		}
		return modifier;
	}
	public static double getModifier1(Player player) {
		int modifier = 0;
		if(player.getMode().isOsrs()){
			modifier += 10;
		}
		if(player.getRights().isOrInherits(Right.HARDCORE)){
			modifier += 7;
		}
		if (player.getItems().isWearingItem(773)) {
			modifier += 900;
		}
		if (player.summonId == 9959) {
			modifier += 15;
		}else if (player.summonId == 13326) {
			modifier += 10;
		}
		if (player.summonId == 7891 && player.inWild()) {
			modifier += 5;
		}
		if (player.summonId == 7893&& player.inWild()) {
			modifier += 5;
		}
		if (Boundary.isIn(player, Boundary.EDONATOR_ZONE)) {
			modifier += 15;
		}
		if (Boundary.isIn(player, Boundary.REV_CAVE)) {
			if (player.isSkulled) {
			modifier += 5;
		}
		}
		if (DropRateBoost.isDropRateBoost()) {
			modifier += 5;
	}
		if (player.amDonated >= 2000) {
			modifier += 35;
		} else if(player.amDonated >= 1500) {
				modifier += 32.5;
		} else if (player.amDonated >= 1000) {
			modifier += 30;
		} else if(player.amDonated >= 750) {
				modifier += 27.5;
		} else if (player.amDonated >= 500) {
			modifier += 25;			
		} else if(player.amDonated >= 375) {
			modifier += 22.5;
		} else if (player.amDonated >= 250) {
			modifier += 20;			
		} else if(player.amDonated >= 175) {
					modifier += 17.5;
		} else if (player.amDonated >= 100) {
			modifier += 15;			
		} else if(player.amDonated >= 75) {
			modifier += 12.5;				
		} else if (player.amDonated	>= 50) {
			modifier += 10;			
		} else if(player.amDonated >= 25) {
				modifier += 7.5;				
		} else if (player.amDonated >= 10) {
			modifier += 5;

		
		}

		
		if ( SkillcapePerks.isWearingMaxCape(player)) {
				modifier += 5;
		}
		if (player.getItems().getItemCount(30017, true) >= 1 && Boundary.isIn(player, Boundary.GODWARS_BOSSROOMS)) {	
			modifier += 10;
		}
		if (player.getItems().getItemCount(30016, true) >= 1 && Boundary.isIn(player, Boundary.ZULRAH)) {	
			modifier += 10;
		}
		if (player.getItems().getItemCount(30019, true) >= 1 && Boundary.isIn(player, Boundary.KRAKEN_CAVE)) {	
			modifier += 10;
		}
		if (player.getItems().getItemCount(30020, true) >= 1 && Boundary.isIn(player, Boundary.CERBERUS_BOSSROOMS)) {	
			modifier += 10;
		}
		if (player.getItems().getItemCount(30018, true) >= 1 && Boundary.isIn(player, Boundary.VORKATH)) {	
			modifier += 10;
		}
		if (player.getItems().getItemCount(30018, true) >= 1 && Boundary.isIn(player, Boundary.DRAGONS)) {	
			modifier += 10;
		}
		if (player.getItems().getItemCount(30018, true) >= 1 && Boundary.isIn(player, Boundary.	LAVA_DRAGON_ISLE)) {	
			modifier += 10;
		}
		
		if (player.getItems().isWearingItem(21126)) {
			modifier += 20;
		}
		if (player.getItems().isWearingItem(2572)) {
			modifier += 5;
		} else if (player.getItems().isWearingItem(12785)) {
			modifier += 10;

		}
		 if(Config.dropBoost) {
			modifier += 15;
		}
			if (Boundary.isIn(player, Boundary.REV_CAVE)) {
				if (player.isSkulled) {
				modifier += 5;
			}
		}
		return modifier;
	}
	/**
	 * Clears the interface of all parts.
	 *
	 * Used on searching and initial load.
	 * @param player
	 */
	public void clear(Player player) {
		for(int i = 0; i < 150; i++) {
			player.getPA().sendFrame126("", 33008 + i);
		}

		player.getPA().sendFrame126("", 43110);
		player.getPA().sendFrame126("", 43111);
		player.getPA().sendFrame126("", 43112);
		player.getPA().sendFrame126("", 43113);

		for(int i = 0;i<80;i++){
			player.getPA().itemOnInterface(-1, 0, 34010+i, 0);
			player.getPA().sendString("", 34200+i);
			player.getPA().sendString("", 34300+i);
			player.getPA().sendString("", 34100+i);
			player.getPA().sendString("", 34400+i);
		}
		player.searchList.clear();
	}

	public void open2(Player player) {
		clear(player);

		for (int index = 0; index < ordered.size(); index++) {
			player.getPA().sendFrame126(StringUtils.capitalize(NPCDefinitions.get(ordered.get(index)).getNpcName().toLowerCase().replaceAll("_", " ")), 33008 + index);
		}

		player.getPA().showInterface(33000);
	}

	/**
	 * Searchers after the player inputs a npc name
	 * @param player
	 * @param name
	 */
	public void search(Player player, String name) {
		if(name.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")) {
			player.sendMessage("You may not search for alphabetical and numerical combinations.");
			return;
		}
		if(name.matches("vorkath")) {
			player.sendMessage("@red@Please note the drop viewer doesn't currently display vorkaths drop table.");
		}
		if (System.currentTimeMillis() - player.lastDropTableSearch < TimeUnit.SECONDS.toMillis(5)) {
			player.sendMessage("You can only do this once every 5 seconds.");
			return;
		}
		player.lastDropTableSearch = System.currentTimeMillis();

		clear(player);

		List<Integer> definitions = ordered.stream().filter(Objects::nonNull).filter(def -> NPCDefinitions.get(def).getNpcName() != null).filter(def -> NPCDefinitions.get(def).getNpcName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());

		if(definitions.isEmpty()) {
			definitions = ordered.stream().filter(Objects::nonNull).collect(Collectors.toList());
			List<Integer> npcs = new ArrayList<>();
			int count = 0;
			for(Integer index : definitions) {
				Optional<TableGroup> group = groups.values().stream().filter(g -> g.getNpcIds().contains(NPCDefinitions.get(index).getNpcId())).findFirst();
				if(group.isPresent()) {
					TableGroup g = group.get();

					for(TablePolicy policy : TablePolicy.values()) {
						Optional<Table> table = g.stream().filter(t -> t.getPolicy() == policy).findFirst();
						if(table.isPresent()) {
							for(Drop drop : table.get()) {
								if(drop == null) {
									continue;
								}

								if(ItemAssistant.getItemName(drop.getItemId()).toLowerCase().contains(name.toLowerCase())) {
									npcs.add(index);
									player.getPA().sendFrame126(StringUtils.capitalize(NPCDefinitions.get(NPCDefinitions.get(index).getNpcId()).getNpcName().toLowerCase().replaceAll("_", " ")), 33008 + count);
									count++;
								}
							}
						}
					}
				}

			}

			player.searchList = npcs;
			return;

		}

		for(int index = 0; index < definitions.size(); index++) {
			if(index >= 150) {
				break;
			}
			player.getPA().sendFrame126(StringUtils.capitalize(NPCDefinitions.get(definitions.get(index)).getNpcName().toLowerCase().replaceAll("_", " ")), 33008 + index);
		}

		player.searchList = definitions;
	}

	/**
	 * Loads the selected npc choosen by the player to view their drops
	 * @param player
	 * @param button
	 */
	public void select(Player player, int button) {
		int listIndex;

		//So the idiot client dev didn't organize the buttons in a singulatiry order. So i had to shift around the id's
		//so if you have 50 npcs in the search you can click them all fine
		if(button <= 128255) {
			listIndex = button - 128240;
		} else {
			listIndex = (128255 - 128240) + 1 + button - 129000;
		}

		if (listIndex < 0 || listIndex > ordered.size() - 1) {
			return;
		}

		//Finding NPC ID
		int npcId = player.searchList.isEmpty() ? ordered.get(listIndex) : player.searchList.get(listIndex);

		Optional<TableGroup> group = groups.values().stream().filter(g -> g.getNpcIds().contains(npcId)).findFirst();

		//If the group in the search area contains this NPC
		group.ifPresent(g -> {
			if (System.currentTimeMillis() - player.lastDropTableSelected < TimeUnit.SECONDS.toMillis(5)) {
				player.sendMessage("You can only do this once every 5 seconds.");
				return;
			}

			//Loads the definition and maxhit/aggressiveness to display
			NPCDefinitions npcDef = NPCDefinitions.get(npcId);

			player.getPA().sendFrame126("Health: @whi@" + npcDef.getNpcHealth(), 43110);
			player.getPA().sendFrame126("Combat Level: @whi@" + npcDef.getNpcCombat(), 43111);
			if(NPCHandler.getNpc(npcId) != null){
				player.getPA().sendFrame126("Max Hit: @whi@" + NPCHandler.getNpc(npcId).maxHit, 43112);
			} else {
				player.getPA().sendFrame126("Max Hit: @whi@?", 43112);
			}
			player.getPA().sendFrame126("Aggressive: @whi@" + (Server.npcHandler.isAggressive(npcId, true) ? "true" : "false"), 43113);

			player.lastDropTableSelected = System.currentTimeMillis();

			double modifier = getModifier(player);

			//Iterates through all 5 drop table's (Found in TablePolicy -> Enum)
			for (TablePolicy policy : TablePolicy.POLICIES) {
				Optional<Table> table = g.stream().filter(t -> t.getPolicy() == policy).findFirst();
				if (table.isPresent()) {
					double chance = (1.0 /(table.get().getAccessibility() * modifier)) * 100D;
					int in_kills = (int) (100 / chance);
					if (chance > 100.0) {
						chance = 100.0;
					}
					if (in_kills == 0) {
						in_kills = 1;
					}

					//Updates the interface with all new information
					updateAmounts(player, policy, table.get(), in_kills);
				} else {
					updateAmounts(player, policy, new ArrayList<>(), -10);
				}
			}

			//If the game has displayed all drops and there are empty slots that haven't been filled, clear them
			if(player.dropSize < 80) {
				for(int i = player.dropSize;i<80;i++){
					player.getPA().sendString("", 34200+i);
					player.getPA().itemOnInterface(-1, 0, 34010+i, 0);
					player.getPA().sendString("", 34300+i);
					player.getPA().sendString("", 34100+i);
					player.getPA().sendString("", 34400+i);
				}
			}
			player.dropSize = 0;
		});
	}

	/**
	 * Updates the interface for the selected NPC
	 * @param player
	 * @param policy
	 * @param drops
	 * @param kills
	 */
	private void updateAmounts(Player player, TablePolicy policy, List<Drop> drops, int kills) {

		//Iterates through all drops in that catagory
		for (int index = 0; index < drops.size(); index++) {
			Drop drop = drops.get(index);
			int minimum = drop.getMinimumAmount();
			int maximum = drop.getMaximumAmount();
			int frame = (34200 + player.dropSize + index);//collumnOffset + (index * 2);

			//if max = min, just send the max
			if (minimum == maximum) {
				player.getPA().sendString(Misc.getValueWithoutRepresentation(drop.getMaximumAmount()), frame);
			} else {
				player.getPA().sendString(Misc.getValueWithoutRepresentation(drop.getMinimumAmount()) + " - " + Misc.getValueWithoutRepresentation(drop.getMaximumAmount()), frame);
			}
			player.getPA().itemOnInterface(drop.getItemId(), 1, 34010+player.dropSize + index, 0);
			player.getPA().sendString(Misc.optimizeText(policy.name().toLowerCase()), 34300+player.dropSize + index);
			player.getPA().sendString(Server.itemHandler.getItemList(drop.getItemId()).itemName, 34100 + player.dropSize + index);
			if(kills == -10){
				player.getPA().sendString(1 + "/?", 34400 + player.dropSize + index);
			} else {
				player.getPA().sendString(1 + "/"+kills, 34400 + player.dropSize + index);
			}
		}

		player.dropSize += drops.size();
	}

	static int amountt = 0;

	private FileReader fileReader;

	/**
	 * Testing droptables of chosen npcId
	 * @param player		The player who is testing the droptable
	 * @param npcId			The npc who of which the player is testing the droptable from
	 * @param amount		The amount of times the player want to grab a drop from the npc droptable
	 */
	public void test(Player player, int npcId, int amount) {
		Optional<TableGroup> group = groups.values().stream().filter(g -> g.getNpcIds().contains(npcId)).findFirst();

		amountt = amount;

		while (amount-- > 0) {
			group.ifPresent(g -> {
				List<GameItem> drops = g.access(player, 1.0, 1);

				for (GameItem item : drops) {
					player.getItems().addItemToBank(item.getId(), item.getAmount());
				}
			});
		}
		player.sendMessage("Completed " + amountt + " drops from " + Server.npcHandler.getNpcName(npcId) + ".");
	}


}
