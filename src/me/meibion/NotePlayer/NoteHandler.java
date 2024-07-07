package me.meibion.NotePlayer;

import org.bukkit.*;
import org.bukkit.entity.Player;

public abstract class NoteHandler {

    public static void playNote(Player player, Instrument instrument, Note note) {

        // Get block at 3 blocks under player
        Location noteblockPos = player.getLocation().add(0d, -3d, 0d);
        final byte ogData = noteblockPos.getBlock().getData();
        final Material ogType = noteblockPos.getBlock().getType();

        // Check if block is a chest or furnace
        if(ogType == Material.CHEST || ogType == Material.LOCKED_CHEST
        || ogType == Material.FURNACE || ogType == Material.BURNING_FURNACE) {
            player.sendMessage(ChatColor.RED + "Could not play note under " + ogType.name().toLowerCase());
            return;
        }


        // Set block to noteblock
        noteblockPos.getBlock().setType(Material.NOTE_BLOCK);

        // Schedule noteblock to play and reset to original block after 1 tick
        Main.server.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {

            // Play sound at this location
            player.playNote(noteblockPos, instrument, note);

            // Set block back to original type
            noteblockPos.getBlock().setData(ogData);
            noteblockPos.getBlock().setType(ogType);

        }, 1); // 1 tick
    }
}
