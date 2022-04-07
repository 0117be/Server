package ethos.model.npcs.bosses.vorkath;


import ethos.model.players.Player;
import ethos.model.players.combat.CombatType;

public interface VorkathAttack {
	
	public int hitDelay = 3;
	
	public abstract void execute(Player player);
	
	public abstract CombatType getCombatType();
	
	public abstract int getProjectileId();
	
	public abstract int getEndProjectileId();
	
	public abstract int getAnimation();
	
	public abstract void executeAfterhit(Player player);
	
	public abstract boolean appliesDamage(Player player);
	
	public abstract int getMaxHit();
}
