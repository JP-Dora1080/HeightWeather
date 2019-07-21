package jp.dora.hw;

import net.minecraft.server.v1_12_R1.PacketPlayOutGameStateChange;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    //プラグインを有効化
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    //プラグインを無効化
    @Override
    public void onDisable() {}

    //プレイヤーが動いたら発生するイベント
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        boolean weather = p.getWorld().hasStorm();

        //130ブロックの高さにいるかどうか
        if(loc.getY() > 130) {
            //高さ131ブロックに達したら晴れ
            sendGameState(p, 7);
        } else if(weather) {//雲の下が雨かどうか
            //雨降っていれば、雨
            sendGameState(p, 2);
        } else {
            //晴れであれば、晴れ
            sendGameState(p, 7);
        }
    }

    //GameStateパケットを送る
    private static void sendGameState(Player p, int i) {
        PacketPlayOutGameStateChange packet = new PacketPlayOutGameStateChange(i, 0);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

}
