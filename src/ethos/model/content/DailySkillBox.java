package ethos.model.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.items.GameItem;
import ethos.model.items.ItemAssistant;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.util.Misc;

/**
 * Revamped a simple means of receiving a random item based on chance.
 * 
 * @author Jason MacKeigan
 * @date Oct 29, 2014, 1:43:44 PM
 */
public class DailySkillBox extends CycleEvent {

	/**
	 * The item id of the mystery box required to trigger the event
	 */
	public static final int MYSTERY_BOX = 20791;

	/**
	 * A map containing a List of {@link GameItem}'s that contain items relevant to their rarity.
	 */
	private static Map<Rarity, List<GameItem>> items = new HashMap<>();

	/**
	 * Stores an array of items into each map with the corresponding rarity to the list
	 */
	static {
		items.put(Rarity.COMMON, 
			Arrays.asList(
				new GameItem(11849, 10 + Misc.random(15)), 
				new GameItem(1518, 10 + Misc.random(15)),
				new GameItem(450, 10 + Misc.random(15)),
				new GameItem(2360, 10 + Misc.random(15)),
				new GameItem(450, 10 + Misc.random(15)),
				new GameItem(2362, 10 + Misc.random(15)),
				new GameItem(264, 10 + Misc.random(15)),
				new GameItem(3001, 10 + Misc.random(15)),
				new GameItem(266, 10 + Misc.random(15)),
				new GameItem(2506, 10 + Misc.random(15)),
				new GameItem(2508, 10 + Misc.random(15)),
				new GameItem(1620, 10 + Misc.random(15)),
				new GameItem(1618, 10 + Misc.random(15)),
				new GameItem(7937, 10 + Misc.random(20)),
				new GameItem(11940, 100),
				new GameItem(13431, 100),
				new GameItem(311, 1),
				new GameItem(5340, 1),
				new GameItem(1925, 1),
				new GameItem(5341, 1),
				new GameItem(5340, 1),
				new GameItem(5343, 1),
				new GameItem(1592, 1),
				new GameItem(1595, 1),
				new GameItem(1597, 1),
				new GameItem(946, 1),
				new GameItem(590, 1),
				new GameItem(10008, 1),
				new GameItem(10010, 1),
				new GameItem(11261, 100),
				new GameItem(10006, 1),
				new GameItem(2347, 1),
				new GameItem(228, 100),
				new GameItem(1733, 5),
				new GameItem(1734, 100),
				new GameItem(1755, 1),
				new GameItem(303, 1),
				new GameItem(305, 1),
				new GameItem(307, 1),
				new GameItem(309, 1),
				new GameItem(301, 1),
				new GameItem(313, 100),
				new GameItem(995, 150000),
				new GameItem(2677))
		);
		
	items.put(Rarity.UNCOMMON,
			Arrays.asList(
					new GameItem(11849, 10 + Misc.random(15)), 
					new GameItem(1518, 10 + Misc.random(15)),
					new GameItem(450, 10 + Misc.random(15)),
					new GameItem(2360, 10 + Misc.random(15)),
					new GameItem(450, 10 + Misc.random(15)),
					new GameItem(2362, 10 + Misc.random(15)),
					new GameItem(264, 10 + Misc.random(15)),
					new GameItem(3001, 10 + Misc.random(15)),
					new GameItem(266, 10 + Misc.random(15)),
					new GameItem(2506, 10 + Misc.random(15)),
					new GameItem(2508, 10 + Misc.random(15)),
					new GameItem(7937, 30 + Misc.random(100)),
					new GameItem(1620, 10 + Misc.random(15)),
					new GameItem(1618, 10 + Misc.random(15)),
					new GameItem(995, 200000),
					new GameItem(1275, 1),
					new GameItem(1359, 1),
					new GameItem(2801))
	);
		
		items.put(Rarity.RARE,
				Arrays.asList(
						new GameItem(1514, 10 + Misc.random(15)),
						new GameItem(452, 10 + Misc.random(15)),
						new GameItem(2364, 10 + Misc.random(15)),
						new GameItem(1624, 10 + Misc.random(15)),
						new GameItem(2482, 10 + Misc.random(15)),
						new GameItem(268, 10 + Misc.random(15)),
						new GameItem(270, 10 + Misc.random(15)),
						new GameItem(2510, 10 + Misc.random(15)),
						new GameItem(995, 300000),
						new GameItem(2722)));
	}

	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new myster box to handle item receiving for this player and this player alone
	 * 
	 * @param player the player
	 */
	public DailySkillBox(Player player) {
		this.player = player;
	}

	/**
	 * Opens a mystery box if possible, and ultimately triggers and event, if possible.
	 * 
	 * @param player the player triggering the evnet
	 */
	public void open() {
		if (System.currentTimeMillis() - player.lastMysteryBox < 150 * 4) {
			return;
		}
		if (player.getItems().freeSlots() < 2) {
			player.sendMessage("You need atleast two free slots to open a mystery box.");
			return;
		}
		if (!player.getItems().playerHasItem(MYSTERY_BOX)) {
			player.sendMessage("You need a daily gear box to do this.");
			return;
		}
		Achievements.increase(player, AchievementType.SKILLINGCRATE, 1);
		player.getItems().deleteItem(MYSTERY_BOX, 1);
		player.lastMysteryBox = System.currentTimeMillis();
		CycleEventHandler.getSingleton().stopEvents(this);
		CycleEventHandler.getSingleton().addEvent(this, this, 2);
	}

	/**
	 * Executes the event for receiving the mystery box
	 */
	@Override
	public void execute(CycleEventContainer container) {
		if (player.disconnected || Objects.isNull(player)) {
			container.stop();
			return;
		}
		int random = Misc.random(10);
		List<GameItem> itemList = random < 5 ? items.get(Rarity.COMMON) : random >= 5 && random <= 8 ? items.get(Rarity.UNCOMMON) : items.get(Rarity.RARE);
		GameItem item = Misc.getRandomItem(itemList);
		GameItem itemDouble = Misc.getRandomItem(itemList);
		
		if (Misc.random(75) == 1) {

			switch(Misc.random(30)) {
			case 0:
				player.getItems().addItemUnderAnyCircumstance(1632, 500);
				break;
			case 1:
				player.getItems().addItemUnderAnyCircumstance(13646, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a farmer's strawhat from a Skilling supply crate!");
				break;
			case 2:
				player.getItems().addItemUnderAnyCircumstance(13640, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a farmer's trousers from a Skilling supply crate!");
				break;
			case 3:
				player.getItems().addItemUnderAnyCircumstance(13642, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a farmer's jacket from a Skilling supply crate!");
				break;
			case 4:
				player.getItems().addItemUnderAnyCircumstance(13644, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a farmer's strawhat from a Skilling supply crate!");				
				break;
			case 5:
				player.getItems().addItemUnderAnyCircumstance(10941, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a lumberjack hat from a Skilling supply crate!");
				break;
			case 6:
				player.getItems().addItemUnderAnyCircumstance(10940, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a lumberjack legs from a Skilling supply crate!");
				break;
			case 7:
				player.getItems().addItemUnderAnyCircumstance(10939, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a lumberjack top from a Skilling supply crate!");
				break;
			case 8:
				player.getItems().addItemUnderAnyCircumstance(10933, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a lumberjack boots from a Skilling supply crate!");
				break;
			case 9:
				player.getItems().addItemUnderAnyCircumstance(12013, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a prospector helmet from a Skilling supply crate!");
				break;
			case 10:
				player.getItems().addItemUnderAnyCircumstance(12015, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a prospector legs from a Skilling supply crate!");
				break;
			case 11:
				player.getItems().addItemUnderAnyCircumstance(12014, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a prospector jacket from a Skilling supply crate!");
				break;
			case 12:
				player.getItems().addItemUnderAnyCircumstance(12016, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a prospector boots from a Skilling supply crate!");
				break;
			case 13:
				player.getItems().addItemUnderAnyCircumstance(19988, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a blachsmith's helmet from a Skilling supply crate!");
				break;
			case 14:
				player.getItems().addItemUnderAnyCircumstance(20708, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a pyromancer hood from a Skilling supply crate!");
				break;
			case 15:
				player.getItems().addItemUnderAnyCircumstance(20706, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a pyromancer robe from a Skilling supply crate!");
				break;
			case 16:
				player.getItems().addItemUnderAnyCircumstance(20704, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a pyromancer garb from a Skilling supply crate!");
				break;
			case 17:
				player.getItems().addItemUnderAnyCircumstance(20710, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a pyromancer boots from a Skilling supply crate!");
				break;
			case 18:
				player.getItems().addItemUnderAnyCircumstance(13258, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a angler hat from a Skilling supply crate!");
				break;
			case 19:
				player.getItems().addItemUnderAnyCircumstance(13260, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a angler waders from a Skilling supply crate!");
				break;
			case 20:
				player.getItems().addItemUnderAnyCircumstance(13259, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a angler top from a Skilling supply crate!");
				break;
			case 21:
				player.getItems().addItemUnderAnyCircumstance(13261, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a angler boots from a Skilling supply crate!");
				break;
			case 22:
				player.getItems().addItemUnderAnyCircumstance(20014, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a 3rd age pickaxe from a Skilling supply crate!");
				break;
			case 23:
				player.getItems().addItemUnderAnyCircumstance(20011, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a 3rd age axe from a Skilling supply crate!");
				break;
			case 24:
				player.getItems().addItemUnderAnyCircumstance(11920, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a dragon pickaxe from a Skilling supply crate!");
				break;
			case 25:
				player.getItems().addItemUnderAnyCircumstance(11920, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a dragon pickaxe from a Skilling supply crate!");
				break;
			case 26:
				player.getItems().addItemUnderAnyCircumstance(6739, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a dragon axe from a Skilling supply crate!");
				break;
			case 27:
				player.getItems().addItemUnderAnyCircumstance(6739, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a dragon axe from a Skilling supply crate!");
				break;
			case 28:
				player.getItems().addItemUnderAnyCircumstance(21028, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a dragon harpoon from a Skilling supply crate!");
				break;
			case 29:
				player.getItems().addItemUnderAnyCircumstance(21028, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a dragon harpoon from a Skilling supply crate!");
				break;
			case 30:
				player.getItems().addItemUnderAnyCircumstance(20005, 1);
				PlayerHandler.executeGlobalMessage("<shad=7000>[<col=CC0000>Skilling</col>] @cr31@ <col=255>" + player.playerName
						+ "</col> obtained a ring of nature from a Skilling supply crate!");
				break;
			}
		}

		if (Misc.random(25) == 0) {
			player.getItems().addItem(item.getId(), item.getAmount());
			player.getItems().addItem(itemDouble.getId(), itemDouble.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>.");
			player.sendMessage("You receive <col=255>" + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + "</col>.");
		} else {
			player.getItems().addItem(item.getId(), item.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>.");
		}
		container.stop();
	}

	/**
	 * Represents the rarity of a certain list of items
	 */
	enum Rarity {
		UNCOMMON, COMMON, RARE
	}

}