package ethos.model.content;

import java.util.*;

import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.items.GameItem;
import ethos.model.items.ItemAssistant;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.skills.thieving.Thieving.Stall;
import ethos.util.Misc;

/**
 * Revamped a simple means of receiving a random item based on chance.
 *
 * @author Jason MacKeigan
 * @date Oct 29, 2014, 1:43:44 PM
 */
public class ScratchTicket extends CycleEvent {

    /**
     * The item id of the mystery box required to trigger the event
     */
    private static final int MYSTERY_BOX = 5020;

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
                		new GameItem(995, 735420),
                		new GameItem(995, 734420),
                		new GameItem(995, 635048),
                		new GameItem(995, 130542),
                		new GameItem(995, 635410),
                		new GameItem(995, 435890),
                		new GameItem(995, 725430),
                		new GameItem(995, 935440),
                		new GameItem(995, 735062),
                		new GameItem(995, 730547),
                		new GameItem(995, 135049),
                		new GameItem(995, 435042),
                		new GameItem(995, Misc.random(1000000))
                		//new GameItem(995, 500000 + Misc.random(995, 1000000))
                  )
        );

        items.put(Rarity.UNCOMMON,
                Arrays.asList(
                		new GameItem(995, 4730542),
                		new GameItem(995, 7344021),
                		new GameItem(995, 2635048),
                		new GameItem(995, 1135402),
                		new GameItem(995, 4630541),
                		new GameItem(995, 5430589),
                		new GameItem(995, 4725430),
                		new GameItem(995, 9350444),
                		new GameItem(995, 7435062),
                		new GameItem(995, 7073547),
                		new GameItem(995, 5135049),
                		new GameItem(995, 4354201),
                		new GameItem(995, Misc.random(25000000))
                		//new GameItem(995, 500000 + Misc.random(995, 1000000))
                  )
        );


        items.put(Rarity.RARE,
                Arrays.asList(
                		new GameItem(995, 100000000),
                		new GameItem(995, Misc.random(500000000))
                  )
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
    public ScratchTicket(Player player) {
        this.player = player;
    }

    /**
     * Opens a mystery box if possible, and ultimately triggers and event, if possible.
     */
    public void open() {
        if (System.currentTimeMillis() - player.lastMysteryBox < 150 * 4) {
            return;
        }
        if (player.getItems().freeSlots() < 1) {
            player.sendMessage("You need atleast one free slot to use a Scratch Ticket.");
            return;
        }
        if (!player.getItems().playerHasItem(MYSTERY_BOX)) {
            player.sendMessage("You need a scratchie wtf.");
            return;
        }
    	Achievements.increase(player, AchievementType.SCRATCH, 1);
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
        int random = Misc.random(100);
        List<GameItem> itemList = random < 75 ? items.get(Rarity.COMMON) : random >= 75 && random <= 95 ? items.get(Rarity.UNCOMMON) : items.get(Rarity.RARE);
        GameItem item = Misc.getRandomItem(itemList);


       
            player.getItems().addItem(item.getId(), item.getAmount());
            player.sendMessage("You receive " + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + ".");
            for(Map.Entry<Rarity, List<GameItem>> gift : items.entrySet())
                for(GameItem gift_item : gift.getValue())
                    if(gift_item == item)
                        if(gift.getKey() == Rarity.RARE)
                        	if (Misc.random(1) == 1 && gift.getKey() == Rarity.RARE) {
                          	     player.sendMessage("@blu@You scratched a @gre@80,@gre@ 80, @gre@80, @gre@80");
                  			} else if (Misc.random(2) == 2) {
                  			 player.sendMessage("@blu@You scratched a @gre@1,@gre@ 1, @gre@1, @gre@1");

                        	  PlayerHandler.executeGlobalMessage(
                           		   "<shad=7000>[<col=ffd900>Scratch Ticket</col>] @cr36@ <col=ffd900>" + Misc.capitalize(player.playerName) + " received: "
                                              + (item.getAmount() > 1 ? (item.getAmount() + " coins") : ItemAssistant.getItemName(item.getId()) + " from a Scratch Ticket."));
            
               			}
                          
                    
                            
            for(Map.Entry<Rarity, List<GameItem>> gift : items.entrySet())
                for(GameItem gift_item : gift.getValue())
            if(gift_item == item)
            if(gift.getKey() == Rarity.UNCOMMON)
            	if (Misc.random(1) == 1 && gift.getKey() == Rarity.UNCOMMON) {
            	     player.sendMessage("@blu@You scratched a @gre@10,@blu@ 7, @gre@10, @blu@8");
    			} else if (Misc.random(2) == 2) {
    			    player.sendMessage("@blu@You scratched a @gre@27,@gre@ 27, @blu@37, @blu@49");

    			}
        
           
            for(Map.Entry<Rarity, List<GameItem>> gift : items.entrySet())
                for(GameItem gift_item : gift.getValue())
            if(gift_item == item)
            if(gift.getKey() == Rarity.COMMON)          	
            	if (Misc.random(1) == 1 && gift.getKey() == Rarity.COMMON) {
            	    player.sendMessage("@blu@You scratched a @red@27,@red@ 14, @red@39, @red@10");
    			} else if (Misc.random(2) == 2) {
    			    player.sendMessage("@blu@You scratched a @red@43,@red@ 10, @red@27, @red@3");

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