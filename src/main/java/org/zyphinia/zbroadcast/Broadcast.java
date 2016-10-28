package org.zyphinia.zbroadcast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

/*
 * Main Broadcasting Class, contains all the functions needed to broadcast messages
 */
class Broadcast {

    private List<String> _msgList = new ArrayList<>(); //Message list

    private int _currentIndex = 0; //What are we currently on?

    private int schedulerTask; //We use CraftBukkit's scheduler system

    //Loads all the variables we need
    Broadcast() {
    }

    void Start() throws Error {
        //if (zbroadcast.GetInstance().getConfig().getLong("delay") < 1000) {
        //    throw new Error("msg delay must be above or equal to one second (Are you sure you want to lag bukkit/spigot to death?)");
        //}

        //might not be the safest idea
        for (Object l: zbroadcast.GetInstance().getConfig().getList("messages")) {
            _msgList.add(l.toString());
        }

        //get task ID before going anywhere
        this.schedulerTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(zbroadcast.GetInstance(), new Runnable() {
            @Override
            public void run() {
                next();
            }
        }, 0L, zbroadcast.GetInstance().getConfig().getLong("delay") * 20L);
    }

    //Stops the broadcasting class
    void Stop() {
        Bukkit.getScheduler().cancelTask(schedulerTask);
    }

    //Broadcast next message
    private void next() {
        //Makes sure our index does not go over the message's array length
        if(_currentIndex > (_msgList.toArray().length - 1)) {
            _currentIndex = 0;
        }

        this.BroadcastMsg(_currentIndex);
        _currentIndex++;
    }

    //Broadcasts a message from the list, has a catch all failure.
    void BroadcastMsg(int index) throws Error {
        if (index > _msgList.toArray().length -1) {
            throw new Error("Index is out of bounds");
        }

        //Send to the other broadcast message
        BroadcastMsg(ChatColor.translateAlternateColorCodes('&',zbroadcast.GetInstance().getConfig().getString("broadcast-tag") + _msgList.toArray()[_currentIndex].toString()));
    }

    //Allows broadcasting straight up, blank for now.
    void BroadcastMsg(String s) {
        for(Player p : Bukkit.getOnlinePlayers()) { p.sendMessage(s); }

        //Sends a Message to console if configured
        if (zbroadcast.GetInstance().getConfig().getBoolean("send-to-console", true)) {
            Bukkit.getConsoleSender().sendMessage(s);
        }
    }

    //Adds a Message to the list
    void AddMsg(String msg) {
        _msgList.add(msg);
        zbroadcast.UpdateList(this._msgList);
    }

    //Adds a message at a specific index
    void InsertMsg(int index, String msg) {
        _msgList.add(index, msg);
        zbroadcast.UpdateList(this._msgList);
    }

    //Removes a message from the list
    void RemoveMsg(int index) {
        _msgList.remove(index);
        zbroadcast.UpdateList(this._msgList);
    }
}
