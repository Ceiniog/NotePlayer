package me.meibion.NotePlayer.Commands;

import me.meibion.NotePlayer.Main;
import me.meibion.NotePlayer.NoteHandler;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayNoteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        // Check if the sender is a player
        if(!Main.isPlayer(commandSender)) { return true; }

        // Get the player
        Player player = (Player) commandSender;

        // Check if the player has permissions
        if(!Main.hasPerms(player, "NotePlayer.Play")) { return true; }

        if(args.length != 3) {
            player.sendMessage(ChatColor.RED + "Usage: /playnote [Instrument] [A-G (#)] [0-1]");
            return true;
        }

        // Get the instrument
        Instrument instrument = getInstrument(args[0]);
        if(instrument == null) {
            player.sendMessage(ChatColor.RED + "Invalid instrument");
            return true;
        }

        // Get the tone
        Note.Tone tone = getTone(args[1]);
        if(tone == null) {
            player.sendMessage(ChatColor.RED + "Invalid tone");
            return true;
        }

        // Get the octave
        byte octave;
        try { octave = getOctave(args[2]); }
        catch (NullPointerException e) {
            player.sendMessage(ChatColor.RED + "Invalid octave");
            return true;
        }

        // Create the note
        Note note = new Note(octave, tone, isNoteSharp(args[1]));

        NoteHandler.playNote(player, instrument, note);

        return true;
    }

    private Instrument getInstrument(String instrumentName) {
        for (Instrument instrument : Instrument.values()) {
            if (instrument.name().equalsIgnoreCase(instrumentName)) {
                return instrument;
            }
        }

        return null;
    }

    private Note.Tone getTone(String toneStr) {

        toneStr = toneStr.replace("#", "");

        for (Note.Tone tone : Note.Tone.values()) {
            if (tone.name().equalsIgnoreCase(toneStr)) {
                return tone;
            }
        }

        return null;
    }

    private boolean isNoteSharp (String toneStr) {
        return toneStr.contains("#");
    }

    private Byte getOctave(String octaveStr) {
        int octaveNum;
        try{ octaveNum = Integer.parseInt(octaveStr); }
        catch (NumberFormatException ignore) { return null;}

        if(octaveNum < 0 || octaveNum > 1) { return null; }

        return (byte) octaveNum;
    }
}
