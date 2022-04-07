package ethos.model.content;

import java.util.*;

import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.content.MysteryBox.Rarity;
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
public class HerbBox extends CycleEvent {

    /**
     * The item id of the mystery box required to trigger the event
     */
    private static final int MYSTERY_BOX = 11738;

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
                		new GameItem(200, 20),
                		new GameItem(202, 20),
                		new GameItem(204, 20),
                		new GameItem(206, 20),
                		new GameItem(208, 20),
                		new GameItem(3050, 20),
                		new GameItem(210, 20),
                		new GameItem(212, 20),
                		new GameItem(214, 20),
                		new GameItem(3052, 20),
                		new GameItem(216, 20),
                		new GameItem(2486, 20),
                		new GameItem(218, 20),
                      
                		new GameItem(220, 20))
        );

        items.put(Rarity.UNCOMMON,
                Arrays.asList(
                		new GameItem(220, 20))  
        );

        items.put(Rarity.RARE,
                Arrays.asList(
                		new GameItem(220, 20)));
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
    public HerbBox(Player player) {
        this.player = player;
    }

    /**
     * Opens a mystery box if possible, and ultimately triggers and event, if possible.
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
            player.sendMessage("You need a mystery box to do this.");
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
      //  int coins = 200_000 + Misc.random(1_500_000);
       // int coinsDouble = 200_000 + Misc.random(1_500_000);
        int random = Misc.random(200);
        List<GameItem> itemList = random < 105 ? items.get(Rarity.COMMON) : random >= 105 && random <= 190 ? items.get(Rarity.UNCOMMON) : items.get(Rarity.RARE);
        GameItem item = Misc.getRandomItem(itemList);
        GameItem itemDouble = Misc.getRandomItem(itemList);

      

        if (Misc.random(10) == 0) {
        //    player.getItems().addItem(995, coins + coinsDouble);
            player.getItems().addItem(item.getId(), item.getAmount());
            player.getItems().addItem(itemDouble.getId(), itemDouble.getAmount());
            player.sendMessage("You receive " + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + ".");
            player.sendMessage("You receive " + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + ".");
           
        } else {
           // player.getItems().addItem(995, coins);
            player.getItems().addItem(item.getId(), item.getAmount());
            player.sendMessage("You receive " + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + ".");
            for(Map.Entry<Rarity, List<GameItem>> gift : items.entrySet())
                for(GameItem gift_item : gift.getValue())
                    if(gift_item == item);
                      
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