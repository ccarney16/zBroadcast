package org.zyphinia.zbroadcast.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.zyphinia.zbroadcast.zbroadcast;
import net.md_5.bungee.api.ChatColor;

import java.util.List;

//Will reimplement this into a core system.
public class CmdBroadcast implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("zyphinia.broadcast")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have access to /zbroadcast"));
            return true;
        }

        if (args.length == 0) {
            returnInfo(sender);

            //Send if player or console did not input any sort of command
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cTo see all commands, type /zbroadcast help"));
            return true;
        }

        List<String> msglist = zbroadcast.GetInstance().GetBroadcast().GetMessageList();

        switch (args[0]) {
            case "info":
                returnInfo(sender);
                break;
            case "help":
                break;
            case "list":
                int maxPageSize = 5;
                int totalPages = (int) Math.ceil(msglist.size() / (double) maxPageSize);

                int page = 1;

                //check if an argument was given
                if (args.length >= 2) {
                    try {
                        page = Integer.parseInt(args[1]);
                    } catch (NumberFormatException ex){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage /zbroadcast list <number>"));
                        return true;
                    }

                    if (page <= 0) {
                        page = 1;
                    }
                }

                if(page > totalPages) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cInvalid page number!"));
                    return true;
                }

                page--; //set page to 0;

                int offset = maxPageSize * page;

                int index = offset + 1;
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9Broadcast List " + (page + 1) +"/" + totalPages +"&8]"));
                for(String s : msglist.subList(offset, Math.min(offset + maxPageSize, msglist.size()))) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', index + "). " +  s));
                    index++;
                }
                break;
            case "reload":
                try {
                    zbroadcast.GetInstance().Reload();
                } catch(Error ex) {
                    sender.sendMessage("An error occured while reloading, please check your config and do '/zbroadcast reload' once fixed");
                    zbroadcast.GetInstance().GetBroadcast().Stop();
                }
                break;
            default:
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUnknown Command, To see all commands, type /zbroadcast help"));
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
