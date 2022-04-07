package ethos.model.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ethos.Server;
import ethos.model.items.GameItem;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.Right;
import ethos.util.Misc;

public class BloodSlayer {
	
	private static final int KEY = 13302;
	private static final int PIECE1 = 964;
	private static final int PIECE2 = 6104;
	private static final int ANIMATION = 881;
	
	private static final Map<Rarity, List<GameItem>> items = new HashMap<>();

	static {
		items.put(Rarity.COMMON, Arrays.asList(
				new GameItem(11286, 1),
				new GameItem(11772, 1),
				new GameItem(12526, 1),
				new GameItem(12851, 1),
				new GameItem(6585, 1),
				new GameItem(13233, 1),
				new GameItem(11772, 1),
				new GameItem(11770, 1),
				new GameItem(21326, 250),
				new GameItem(11832, 1),
				new GameItem(11834, 1),
				new GameItem(11804, 1)));
		
		items.put(Rarity.UNCOMMON, Arrays.asList(
				new GameItem(19550, 1),
				new GameItem(12791, 1),
				new GameItem(22296, 1),
				new GameItem(19550, 1),
				new GameItem(12809, 1),
				new GameItem(11828, 1),
				new GameItem(11773, 1),
				new GameItem(19553, 1),
				new GameItem(19547, 1),
				new GameItem(19544, 1)));				
	}
	
	private static GameItem randomChestRewards(int chance) {
		int random = Misc.random(chance);
		List<GameItem> itemList = random < chance ? items.get(Rarity.COMMON) : items.get(Rarity.UNCOMMON) ;
		return Misc.getRandomItem(itemList);
	}
	
	public static void makeKey(Player c) {
		if (c.getItems().playerHasItem(PIECE1, 1) && c.getItems().playerHasItem(PIECE2, 1)) {
			c.getItems().deleteItem(PIECE1, 1);
			c.getItems().deleteItem(PIECE2, 1);
			c.getItems().addItem(KEY, 1);
		}
	}
	
	public static void searchChest(Player c) {
		if (c.getItems().playerHasItem(KEY)) {
			c.getItems().deleteItem(KEY, 1);
			c.startAnimation(ANIMATION);
			GameItem reward = Boundary.isIn(c, Boundary.DONATOR_ZONE) && c.getRights().isOrInherits(Right.DONATOR) ? randomChestRewards(2) : randomChestRewards(9);
			if (!c.getItems().addItem(reward.getId(), reward.getAmount())) {
				Server.itemHandler.createGroundItem(c, reward.getId(), c.getX(), c.getY(), c.heightLevel, reward.getAmount());
			}
			c.sendMessage("@blu@You reach into the Chest of Anguish and pull out an item.");
		} else {
			c.sendMessage("@blu@You need a Key of Anguish to open this chest.");
		}
	}

	enum Rarity {
		UNCOMMON, COMMON, RARE
	}

}
	
	


