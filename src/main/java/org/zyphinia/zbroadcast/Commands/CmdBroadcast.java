package org.zyphinia.zbroadcast.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.zyphinia.zbroadcast.zbroadcast;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

//Will reimplement this into a core system.
public class CmdBroadcast implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            returnInfo(sender);
        }

        List<String> msglist = zbroadcast.GetInstance().GetBroadcast().GetMessageList();

        switch (args[0]) {
            case "info":
                returnInfo(sender);
                break;
            case "list":
                int maxPageSize = 5;
                int totalPages = (int) Math.ceil(msglist.size() / (double) maxPageSize);

                int page = 1;

                //check if an arguement was given
                if (args.length >= 2) {
                    page = Integer.parseInt(args[1]);

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

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9Broadcast List " + (page + 1) +"/" + totalPages +"&8]"));
                for(String s : msglist.subList(offset, Math.min(offset + maxPageSize, msglist.size()))) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
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
