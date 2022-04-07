package ethos.model.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ethos.Server;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.items.GameItem;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.Right;
import ethos.util.Misc;

public class BloodAltar {

	private static final int KEY = 22405;
	private static final int DRAGONSTONE = 3339;
	private static final int KEY_HALVE1 = 985;
	private static final int KEY_HALVE2 = 987;
	private static final int ANIMATION = 881;

	private static final Map<Rarity, List<GameItem>> items = new HashMap<>();

	static {
		items.put(Rarity.COMMON, Arrays.asList(
				new GameItem(995, 250000),
				new GameItem(995, 500000),
				new GameItem(995, 750000),
				new GameItem(995, 250000),
				new GameItem(995, 500000),
				new GameItem(995, 750000),
				new GameItem(995, 250000),
				new GameItem(995, 750000),
				new GameItem(995, 250000),
				new GameItem(995, 500000),
				new GameItem(995, 500000),
				new GameItem(995, 750000),
				new GameItem(995, 200000),
				new GameItem(995, 5000000),
				new GameItem(995, 8000000),
				new GameItem(995, 2500000),
				new GameItem(995, 5000000),
				new GameItem(995, 7500000),
				new GameItem(995, 1000000)));
		
		items.put(Rarity.UNCOMMON, Arrays.asList(
				new GameItem(7806, 1),
				new GameItem(7807, 1),
				new GameItem(7808, 1),
				new GameItem(7806, 1),
				new GameItem(7807, 1),
				new GameItem(7808, 1),
				new GameItem(7809, 1),
				new GameItem(32099, 1),
				new GameItem(7809, 1)));

}

	private static GameItem randomChestRewards(int chance) {
		int random = Misc.random(chance);
		List<GameItem> itemList = random < chance ? items.get(Rarity.COMMON) : items.get(Rarity.UNCOMMON);
		return Misc.getRandomItem(itemList);
	}

	public static void makeKey(Player c) {
		if (c.getItems().playerHasItem(KEY_HALVE1, 1) && c.getItems().playerHasItem(KEY_HALVE2, 1)) {
			c.getItems().deleteItem(KEY_HALVE1, 1);
			c.getItems().deleteItem(KEY_HALVE2, 1);
			c.getItems().addItem(KEY, 1);
		}
	}

	public static void searchChest(Player c) {
		if (c.getItems().playerHasItem(KEY)) {
			c.getItems().deleteItem(KEY, 1);
			c.startAnimation(ANIMATION);
			c.getItems().addItem(DRAGONSTONE, 1);
			Achievements.increase(c, AchievementType.BLOODALTAR, 1);
			GameItem reward = Boundary.isIn(c, Boundary.DONATOR_ZONE) && c.getRights().isOrInherits(Right.CONTRIBUTOR) ? randomChestRewards(2) : randomChestRewards(9);
			if (!c.getItems().addItem(reward.getId(), reward.getAmount())) {
				Server.itemHandler.createGroundItem(c, reward.getId(), c.getX(), c.getY(), c.heightLevel, reward.getAmount());
			}
			c.sendMessage("@red@You stick your hand in the chest and pull out cash and a blood shard.");
		} else {
			c.sendMessage("@red@You need a blood vial before you use this altar.");
		}
	}

	enum Rarity {
		UNCOMMON, COMMON, RARE
	}

}