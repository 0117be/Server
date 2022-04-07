package ethos.model.players.packets.commands.all;

import ethos.model.npcs.drops.DropManager;
import ethos.model.players.Player;

public class Fame extends Commands {
    @Override
    public void execute(Player player, String input) {
        player.setFame(500000);
    }
}
