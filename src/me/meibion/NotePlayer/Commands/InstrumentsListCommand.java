package me.meibion.NotePlayer.Commands;

import me.meibion.NotePlayer.Main;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InstrumentsListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        // Check if the sender is a player
        if(!Main.isPlayer(commandSender)) { return true; }

        // Get the player
        Player player = (Player) commandSender;

        // Check if the player has permissions
        if(!Main.hasPerms(player, "NotePlayer.Instruments")) { return true; }

        StringBuilder outputStr = new StringBuilder();
        outputStr.append(ChatColor.GRAY + "Instruments: ");

        for(Instrument instrument : Instrument.values()) {
            outputStr.append(instrument.name().toLowerCase()).append(", ");
        }

        player.sendMessage(outputStr.toString());

        return true;
    }
}
