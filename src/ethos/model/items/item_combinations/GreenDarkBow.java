package ethos.model.items.item_combinations;

import java.util.List;
import java.util.Optional;

import ethos.model.items.GameItem;
import ethos.model.items.ItemCombination;
import ethos.model.players.Player;

public class GreenDarkBow extends ItemCombination {

	public GreenDarkBow(GameItem outcome, Optional<List<GameItem>> revertedItems, GameItem[] items) {
		super(outcome, revertedItems, items);
	}

	@Override
	public void combine(Player player) {
		super.items.forEach(item -> player.getItems().deleteItem2(item.getId(), item.getAmount()));
		player.getItems().addItem(super.outcome.getId(), super.outcome.getAmount());
		player.getDH().sendItemStatement("You combined the items and created the Green dark bow.", 12766);
		player.setCurrentCombination(Optional.empty());
		player.nextChat = -1;
	}

	@Override
	public void showDialogue(Player player) {
		player.getDH().sendStatement("The Green dark bow is untradeable.", "You cannot revert this item either.");
	}

}