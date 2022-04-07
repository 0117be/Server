package ethos.model.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.items.GameItem;
import ethos.model.items.ItemAssistant;
import ethos.model.items.ItemDefinition;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.util.Misc;

/**
 * Revamped a simple means of receiving a random item based on chance.
 * 
 * @author Jason MacKeigan
 * @date Oct 29, 2014, 1:43:44 PM
 */
public class XmasPresent {

	/**
	 * The item id of the mystery box required to trigger the event
	 */
	public static final int MYSTERY_BOX = 13656;

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
				new GameItem(6856),
				new GameItem(6860),
				new GameItem(6858),
				new GameItem(6862),	
				new GameItem(6857),
				new GameItem(6861),
				new GameItem(6859),
				new GameItem(6863),	
				new GameItem(10065),		
				new GameItem(10067),
				new GameItem(21314),
				new GameItem(12897))
		);
		
	items.put(Rarity.UNCOMMON,
			Arrays.asList(
					new GameItem(12887),
					new GameItem(12888),
					new GameItem(12889),
					new GameItem(12890),
					new GameItem(12891),
					new GameItem(12892),
					new GameItem(12893),
					new GameItem(12894),
					new GameItem(12895),
					new GameItem(12896),
					new GameItem(20834),
					new GameItem(20836),
					new GameItem(10507),
					new GameItem(4084),
					new GameItem(20832))
	);
		
		items.put(Rarity.RARE,
				Arrays.asList(
	
						new GameItem(21859),
						new GameItem(962),
						new GameItem(1050),
						new GameItem(13344),
						new GameItem(13343),
						new GameItem(30307),
						new GameItem(30308),
						new GameItem(21853),
						new GameItem(21851),
						new GameItem(21847),
						new GameItem(21855),
						new GameItem(21857),
						new GameItem(21849),
						new GameItem(32100, 1)));
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
	public XmasPresent(Player player) {
		this.player = player;
	}

	/**
	 * Can the player open the mystery box
	 */
	//private boolean canMysteryBox = true;
	
	/**
	 * The prize received
	 */
	private int mysteryPrize;

	private int mysteryAmount;
	
	private int spinNum = 0;
	
	/**
	 * The chance to obtain the item
	 */
	private int random;
	
	private final int INTERFACE_ID = 47000;
	private final int ITEM_FRAME = 47101;
	
	/**
	 * The rarity of the reward
	 */
	private Rarity rewardRarity;

	/**
	 * Represents the rarity of a certain list of items
	 */
	enum Rarity {
		UNCOMMON("<col=005eff>"),
		COMMON("<col=336600>"),
		RARE("<col=B80000>");
		
		private String color;
		
		Rarity(String color) {
			this.color = color;
		}
		
		public String getColor() {
			return color;
		}
		
	    public static Rarity forId(int id) {
	        for (Rarity tier : Rarity.values()) {
	            if (tier.ordinal() == id)
	                return tier;
	        }
	        return null;
	    }
	}
	
	public void spin() {
		// Server side checks for spin
		if (!player.canMysteryBox) {
			player.sendMessage("Please finish your current spin.");
			return;
		}
		if (!player.getItems().playerHasItem(MYSTERY_BOX)) {
			player.sendMessage("You require a mystery box to do this.");
			return;
		}

		// Delete box
		player.getItems().deleteItem(MYSTERY_BOX, 1);
		// Initiate spin
		player.sendMessage(":resetBox");
		for (int i=0; i<66; i++){
			player.getPA().mysteryBoxItemOnInterface(-1, 1, ITEM_FRAME, i);
		}
		spinNum = 0;
		player.sendMessage(":spin");
		process();
	}
	
	public void process() {
		// Reset prize
		mysteryPrize = -1;

		mysteryAmount = -1;
		// Can't spin when already in progress
		player.canMysteryBox = false;
		
		random = Misc.random(150);
		List<GameItem> itemList = random < 50 ? items.get(Rarity.COMMON) : random >= 50 && random <= 120 ? items.get(Rarity.UNCOMMON) : items.get(Rarity.RARE);
		rewardRarity = random < 50 ? Rarity.COMMON : random >= 50 && random <= 120 ? Rarity.UNCOMMON : Rarity.RARE;
		
		GameItem item = Misc.getRandomItem(itemList);

		mysteryPrize = item.getId();

		mysteryAmount = item.getAmount();

		// Send items to interface
		// Move non-prize items client side if you would like to reduce server load
		if (spinNum == 0) {
			for (int i=0; i<66; i++){
				Rarity notPrizeRarity = Rarity.values()[new Random().nextInt(Rarity.values().length)];
				GameItem NotPrize =Misc.getRandomItem(items.get(notPrizeRarity));
				final int NOT_PRIZE_ID = NotPrize.getId();
				final int NOT_PRIZE_AMOUNT = NotPrize.getAmount();
				sendItem(i, 55, mysteryPrize, NOT_PRIZE_ID,1);
			}
		} else {
			for (int i=spinNum*50 + 16; i<spinNum*50 + 66; i++){
				Rarity notPrizeRarity = Rarity.values()[new Random().nextInt(Rarity.values().length)];	
				final int NOT_PRIZE_ID = Misc.getRandomItem(items.get(notPrizeRarity)).getId();
				sendItem(i, (spinNum+1)*50 + 5, mysteryPrize, NOT_PRIZE_ID, mysteryAmount);
			}
		}
		spinNum++;
	}
	
	public void reward() {
		if (mysteryPrize == -1) {
			return;
		}
		
		//player.boxCurrentlyUsing = -1;
		
		player.getItems().addItemUnderAnyCircumstance(mysteryPrize, mysteryAmount);
		
		// Reward text colour
		String tier = rewardRarity.getColor();
		
		// Reward message
		String name = ItemDefinition.forId(mysteryPrize).getName();
		if (name.substring(name.length() - 1).equals("s")) {
			player.sendMessage("Congratulations, you have won " + tier + name + "@bla@!");
		}
		else {
			player.sendMessage("Congratulations, you have won a " + tier + name + "@bla@!");
		}
		
		if (random > 49) {
			PlayerHandler.executeGlobalMessage("<col=ffffff><shad=7000>[Xmas] " + Misc.formatPlayerName(player.playerName)
					+ " opened a present and got a "+name+"!");
		}
		
		// Can now spin again
		player.canMysteryBox = true;
	}
	
	public void sendItem(int i, int prizeSlot, int PRIZE_ID, int NOT_PRIZE_ID, int amount) {
		if (i == prizeSlot) {
			player.getPA().mysteryBoxItemOnInterface(PRIZE_ID, amount, ITEM_FRAME, i);
		}
		else {
			player.getPA().mysteryBoxItemOnInterface(NOT_PRIZE_ID, amount, ITEM_FRAME, i);
		}
	}
	
	public void openInterface() {
		player.boxCurrentlyUsing = MYSTERY_BOX;
		// Reset interface
		player.sendMessage(":resetBox");
		for (int i=0; i<66; i++){
			player.getPA().mysteryBoxItemOnInterface(-1, 1, ITEM_FRAME, i);
		}
		spinNum = 0;
		// Open
		player.getPA().sendString("Christmas Present", 47002);
		player.getPA().showInterface(INTERFACE_ID);
	}

}