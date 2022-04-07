package ethos.model.content;

import java.util.*;

import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
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
public class PvmCasket extends CycleEvent {

	/**
	 * The item id of the PvM Casket required to trigger the event
	 */
	public static final int MYSTERY_BOX = 405; //Casket

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
					new GameItem(995, 15000000),
					new GameItem(995, 8000000), 
					new GameItem(995, 13000000), 
					new GameItem(995, 25000000), 
		             new GameItem(11944, 50),
		             new GameItem(13307, 250),
		             new GameItem(13307, 300),
		             new GameItem(13307, 75),
		             new GameItem(13307, 150),
					new GameItem(995, 10000000))
					
			);
			
		items.put(Rarity.UNCOMMON,
				Arrays.asList(
					    new GameItem(4716),
                        new GameItem(4718),
                        new GameItem(4720),
                        new GameItem(4722),
                        new GameItem(4724),
                        new GameItem(4726),
                        new GameItem(4728),
                        new GameItem(4730),
                        new GameItem(4732),
                        new GameItem(4734),
                        new GameItem(4736),
                        new GameItem(4738),
                        new GameItem(13307, 500),
                        new GameItem(4745),
                        new GameItem(7158),
                        new GameItem(4747),
                        new GameItem(4749),
             
                        new GameItem(13307, 1000),
       
                        new GameItem(4751),
                        new GameItem(4753),
                
                        new GameItem(4755),
                        new GameItem(4757),
                        new GameItem(4759),
						new GameItem(995, 100000))
						
		);
			
			items.put(Rarity.RARE,
					Arrays.asList(
							new GameItem(12007), 
							new GameItem(12885),
							new GameItem(12885),
							new GameItem(13245)));
							
							
	}

	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new PvM Casket to handle item receiving for this player and this player alone
	 * 
	 * @param player the player
	 */
	public PvmCasket(Player player) {
		this.player = player;
	}

	/**
	 * Opens a PvM Casket if possible, and ultimately triggers and event, if possible.
	 *
	 */
	public void open() {
		if (System.currentTimeMillis() - player.lastMysteryBox < 150 * 4) {
			return;
		}
		if (player.getItems().freeSlots() < 2) {
			player.sendMessage("You need atleast two free slots to open a PvM Casket.");
			return;
		}
		if (!player.getItems().playerHasItem(MYSTERY_BOX)) {
			player.sendMessage("You need PvM Casket to do this.");
			return;
		}
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
	//	int coins = 50000 + Misc.random(15000);
	//	int coinsDouble = 100000 + Misc.random(50000);
		int random = Misc.random(100);
		List<GameItem> itemList = random < 40 ? items.get(Rarity.COMMON) : random >= 40 && random <= 90 ? items.get(Rarity.UNCOMMON) : items.get(Rarity.RARE);
		GameItem item = Misc.getRandomItem(itemList);
		GameItem itemDouble = Misc.getRandomItem(itemList);

		if (Misc.random(10) == 0) {
			//player.getItems().addItem(995, coins + coinsDouble);
			player.getItems().addItem(item.getId(), item.getAmount());
			player.getItems().addItem(itemDouble.getId(), itemDouble.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col><col=255>");
			player.sendMessage("You receive <col=255>" + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + "</col><col=255>");
			//PlayerHandler.executeGlobalMessage("<img=10>" + Misc.formatPlayerName(player.playerName) + " just got very lucky and hit the double!");
		//	PlayerHandler.executeGlobalMessage("<img=10>" + Misc.formatPlayerName(player.playerName) + " has received <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId())
			//		+ "</col> and <col=255>" + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + "</col> from a PvM Casket.");
		} else {
		//	player.getItems().addItem(995, coins);
			player.getItems().addItem(item.getId(), item.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col><col=255>");
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