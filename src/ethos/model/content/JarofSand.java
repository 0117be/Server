package ethos.model.content;

import java.util.*;

import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.content.JarofDirt.Rarity;
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
public class JarofSand extends CycleEvent {

    /**
     * The item id of the mystery box required to trigger the event
     */
    private static final int MYSTERY_BOX = 12885;

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
                        new GameItem(995, 20000000))
        );

        items.put(Rarity.UNCOMMON,
                Arrays.asList(
                       
                        new GameItem(7981),
                        new GameItem(7981))
        );

      
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
    public JarofSand(Player player) {
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
            player.sendMessage("You need atleast two free slots to open a jar.");
            return;
        }
        if (!player.getItems().playerHasItem(MYSTERY_BOX)) {
            player.sendMessage("You need a mystery box to do this.");
            return;
        }
    	Achievements.increase(player, AchievementType.JAR, 1);
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

        if (Misc.random(200) == 2 && player.getItems().getItemCount(19730, true) == 0 && player.summonId != 19730) {
            PlayerHandler.executeGlobalMessage("@red@" + player.playerName
                    + " has found a Bloodhound!");
            player.getItems().addItemUnderAnyCircumstance(19730, 1);
        }

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
                    if(gift_item == item)
                    	  if(gift.getKey() == Rarity.UNCOMMON)
                              PlayerHandler.executeGlobalMessage(
                                      "<shad=7000>[<col=6F3100>Jar</col>] @cr31@ <col=6F3100>" + Misc.capitalize(player.playerName) + " received a rare item: "
                                              + (item.getAmount() > 1 ? (item.getAmount() + "x ") : ItemAssistant.getItemName(item.getId()) + " from a jar."));
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