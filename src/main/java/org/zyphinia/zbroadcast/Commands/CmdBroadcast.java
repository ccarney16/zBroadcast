package org.zyphinia.zbroadcast.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.zyphinia.zbroadcast.zbroadcast;
import net.md_5.bungee.api.ChatColor;

//Will reimplement this into a core system.
public class CmdBroadcast implements CommandExecutor {

    private static final int MAXPAGELIST = 5; //limit to how much /broadcast list can display on a page

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            returnInfo(sender);
        }

        switch (args[0]) {
            case "info":
                returnInfo(sender);
                break;
            case "list":
                //page index
                int pgIndex = 1;

                int pages = 0;

                if (args.length >= 2) {
                    pgIndex = Integer.parseInt(args[1]);

                    if (pgIndex == 0) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cinvalid page number, please try again"));
                        return true;
                    }
                }

                break;
            default:
                returnInfo(sender);
                break;
        }


        return true;
    }

    private void returnInfo(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8------ [&3zBroadcast&8] -----"));
        sender.sendMessage("Enabled: " + Boolean.toString(zbroadcast.GetInstance().IsRunning()));
        if (zbroadcast.GetInstance().IsRunning()) {
            sender.sendMessage("List of Messages: " + Integer.toString(zbroadcast.GetInstance().GetBroadcast().GetMessageListSize()));
            sender.sendMessage("Current Index: " + Integer.toString(zbroadcast.GetInstance().GetBroadcast().GetCurrentIndex()));
        }
    }
}
