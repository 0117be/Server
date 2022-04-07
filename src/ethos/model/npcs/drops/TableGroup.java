package ethos.model.npcs.drops;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Range;

import ethos.model.items.GameItem;
import ethos.model.items.Item;
import ethos.model.items.ItemAssistant;
import ethos.model.npcs.NPC;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.Right;
import ethos.net.discord.DiscordMessager;
import ethos.util.Misc;

@SuppressWarnings("serial")
public class TableGroup extends ArrayList<Table> {

	/**
	 * The non-playable character that has access to this group of tables
	 */
	private final List<Integer> npcIds;

	/**
	 * Creates a new group of tables
	 * 
	 * @param npcId the npc identification value
	 */
	public TableGroup(List<Integer> npcsIds) {
		this.npcIds = npcsIds;
	}

	/**
	 * Accesses each {@link Table} in this {@link TableGroup} with hopes of retrieving a {@link List} of {@link GameItem} objects.
	 * 
	 * @return
	 */
	public List<GameItem> access(Player player, double modifier, int repeats) {
		int rights = player.getRights().getPrimary().getValue() - 1;
		List<GameItem> items = new ArrayList<>();
		for (Table table : this) {
			TablePolicy policy = table.getPolicy();
			if (policy.equals(TablePolicy.CONSTANT)) {
				for (Drop drop : table) {
					int minimumAmount = drop.getMinimumAmount();

					items.add(new GameItem(drop.getItemId(), minimumAmount + Misc.random(drop.getMaximumAmount() - minimumAmount)));
				}
			} else {
				for (int i = 0; i < repeats; i++) {
					double chance = (1.0 / (double) (table.getAccessibility() * modifier)) * 100D;

					double roll = Misc.preciseRandom(Range.between(0.0, 100.0));

					if (chance > 100.0) {
						chance = 100.0;
					}
					if (roll <= chance) {
						Drop drop = table.fetchRandom();
						int minimumAmount = drop.getMinimumAmount();
						GameItem item = new GameItem(drop.getItemId(),
								minimumAmount + Misc.random(drop.getMaximumAmount() - minimumAmount));

						items.add(item);
						if (chance <= 1.5) {
							if (policy.equals(TablePolicy.VERY_RARE) || policy.equals(TablePolicy.RARE) || Item.getItemName(item.getId()).toLowerCase().contains("black mask")) {
								
								if (Item.getItemName(item.getId()).toLowerCase().contains("cowhide")
										|| Item.getItemName(item.getId()).toLowerCase().contains("feather")
										|| Item.getItemName(item.getId()).toLowerCase().contains("arrow")
										|| Item.getItemName(item.getId()).toLowerCase().contains("sq shield")
										|| Item.getItemName(item.getId()).toLowerCase().contains("long bone")
										|| Item.getItemName(item.getId()).toLowerCase().contains("black kiteshield")
										|| Item.getItemName(item.getId()).toLowerCase().contains("mithril mace")
										|| Item.getItemName(item.getId()).toLowerCase().contains("rune warhammer")
										|| Item.getItemName(item.getId()).toLowerCase().contains("rune battleaxe")
										|| Item.getItemName(item.getId()).toLowerCase().contains("casket")
										|| Item.getItemName(item.getId()).toLowerCase().contains("mystic")
										|| Item.getItemName(item.getId()).toLowerCase().contains("silver ore")
										|| Item.getItemName(item.getId()).toLowerCase().contains("rune spear")
										|| Item.getItemName(item.getId()).toLowerCase().contains("dragon spear")
										|| Item.getItemName(item.getId()).toLowerCase().contains("dragon longsword")
										|| Item.getItemName(item.getId()).toLowerCase().contains("dragon battleaxe")
										|| Item.getItemName(item.getId()).toLowerCase().contains("dragon halberd")
										|| Item.getItemName(item.getId()).toLowerCase().contains("dragon boots")
										|| Item.getItemName(item.getId()).toLowerCase().contains("rock-shell")
										|| Item.getItemName(item.getId()).toLowerCase().contains("spined")
										|| Item.getItemName(item.getId()).toLowerCase().contains("blood rune")
										|| Item.getItemName(item.getId()).toLowerCase().contains("death rune")
										|| Item.getItemName(item.getId()).toLowerCase().contains("rune med helm")
										|| Item.getItemName(item.getId()).toLowerCase().contains("dragon chainbody")
										|| Item.getItemName(item.getId()).toLowerCase().contains("grimy")
										|| Item.getItemName(item.getId()).toLowerCase().contains("ore")
										|| Item.getItemName(item.getId()).toLowerCase().contains("bar")
										|| Item.getItemName(item.getId()).toLowerCase().contains("clue scroll")
										|| Item.getItemName(item.getId()).toLowerCase().contains("dharok")
										|| Item.getItemName(item.getId()).toLowerCase().contains("ahrim")
										|| Item.getItemName(item.getId()).toLowerCase().contains("torag")
										|| Item.getItemName(item.getId()).toLowerCase().contains("karil")
										|| Item.getItemName(item.getId()).toLowerCase().contains("verac")
										|| Item.getItemName(item.getId()).toLowerCase().contains("guthan")
										|| Item.getItemName(item.getId()).toLowerCase().contains("seed")
										|| Item.getItemName(item.getId()).toLowerCase().contains("desert")
										|| Item.getItemName(item.getId()).toLowerCase().contains("dragon 2h")
										|| Item.getItemName(item.getId()).toLowerCase().contains("coins")
										|| Item.getItemName(item.getId()).toLowerCase().contains("blood money")
										|| item.getId() >= 554 && item.getId() <= 566) {
									 //  player.getCollectionLog().handleDrop(player, drop.getNpcIds().get(0), item.getId(), item.getAmount());
								} else {
									
									if (player.inWild() && !player.getRights().isOrInherits(Right.BRNZ)) {
										PlayerHandler.executeGlobalMessage(
												"<col=FF0000><shad=7000>[Wildy Drop] <img=27> </col><col=255><img=" + rights + ">"
														+ Misc.capitalize(player.playerName) + "</col> received <col=255>"
														+ item.getAmount() + "</col>x <col=255>"
														+ ItemAssistant.getItemName(item.getId()));
									DiscordMessager.sendLootations("```" + Misc.formatPlayerName(player.playerName) + " received a rare drop: " + Misc.format(item.getAmount()) + " x " + Item.getItemName(item.getId()) + ", congratulations.```");
								}
									if (player.getRights().isOrInherits(Right.BRNZ) && player.inWild()) {
										PlayerHandler.executeGlobalMessage(
												"<col=FF0000><shad=7000>[Wildy Drop] <img=27> </col><col=255><img=" + rights + ">"
														+ Misc.capitalize(player.playerName) + "</col> received <col=255>"
														+ item.getAmount() + "</col>x <col=255>"
														+ ItemAssistant.getItemName(item.getId()));
									DiscordMessager.bronze("```" + Misc.formatPlayerName(player.playerName) + " received a rare drop: " + Misc.format(item.getAmount()) + " x " + Item.getItemName(item.getId()) + ", congratulations.```");
								}
									if (!player.inWild() && player.getRights().isOrInherits(Right.BRNZ)) {
										
										PlayerHandler.executeGlobalMessage(
												"<col=FF0000><shad=7000>[Rare Drop] <img=27> </col><col=255><img=" + rights + ">"
														+ Misc.capitalize(player.playerName) + "</col> received <col=255>"
														+ item.getAmount() + "</col>x <col=255>" + ItemAssistant.getItemName(item.getId())
														);
										DiscordMessager.bronze("```" + Misc.formatPlayerName(player.playerName) + " received a rare drop: " + Misc.format(item.getAmount()) + " x " + Item.getItemName(item.getId()) + ", congratulations.```");
									}
									
									if (!player.inWild() && !player.getRights().isOrInherits(Right.BRNZ)) {
										
										PlayerHandler.executeGlobalMessage(
												"<col=FF0000><shad=7000>[Rare Drop] <img=27> </col><col=255><img=" + rights + ">"
														+ Misc.capitalize(player.playerName) + "</col> received <col=255>"
														+ item.getAmount() + "</col>x <col=255>" + ItemAssistant.getItemName(item.getId())
														);
										DiscordMessager.sendLootations("```" + Misc.formatPlayerName(player.playerName) + " received a rare drop: " + Misc.format(item.getAmount()) + " x " + Item.getItemName(item.getId()) + ", congratulations.```");
									}
							}
							}
						}
					}
				}
			}
		}
		return items;
	}

	/**
	 * The non-playable character identification values that have access to this group of tables.
	 * 
	 * @return the non-playable character id values
	 */
	public List<Integer> getNpcIds() {
		return npcIds;
	}
}
