package me.meibion.NotePlayer;

import me.meibion.NotePlayer.Commands.InstrumentsListCommand;
import me.meibion.NotePlayer.Commands.PlayNoteCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main plugin;
    public static Server server = Bukkit.getServer();

    @Override
    public void onDisable() {
        server.getLogger().info("[NotePlayer] - Plugin Disabled");
    }

    @Override
    public void onEnable() {
        plugin = this;
        server.getLogger().info("[NotePlayer] - Plugin Enabled");

        // Commands
        getCommand("playnote").setExecutor(new PlayNoteCommand()); // Plays a note
        getCommand("instruments").setExecutor(new InstrumentsListCommand()); // Outputs a list of instruments
    }

    public static Main getPlugin() { return plugin; }

    // Checks if a player has permissions
    public static boolean hasPerms(Player player, String perm) {
        boolean result = player.hasPermission(perm);
        if(!result) { player.sendMessage(ChatColor.RED + "You do not have permission to use this command!"); }
        return result;
    }

    // Checks if a CommandSender is a player
    public static boolean isPlayer(CommandSender sender) {
        boolean result = sender instanceof Player;
        if(!result) { server.getLogger().info("You must be a player to run this command!"); }
        return result;
    }
}
