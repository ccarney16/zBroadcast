package org.zyphinia.zbroadcast;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

/*
 * Main Broadcasting Class, contains all the functions needed to broadcast messages
 */
public class Broadcast {

    private List<String> _msgList = new ArrayList<>(); //Message list

    private int _currentIndex = 0; //What are we currently on?

    private int schedulerTask; //We use CraftBukkit's scheduler system

    Broadcast() { }

    void Start() throws Error {

        if (zbroadcast.GetInstance().getConfig().getLong("delay") < 1) {
            throw new Error("msg delay must be above or equal to one second (Are you sure you want to lag bukkit/spigot to death?)");
        }

        _msgList = new ArrayList<>();

        //Allows us to get the message list safely without major problems.
        for (Object l: zbroadcast.GetInstance().getConfig().getList("messages")) {
            _msgList.add(l.toString());
        }
        
        _currentIndex = 0; //resets current index

        //get task ID before going anywhere
        this.schedulerTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(zbroadcast.GetInstance(), new Runnable() {
            @Override
            public void run() {
                next();
            }
        }, 0L, zbroadcast.GetInstance().getConfig().getLong("delay") * 20L);
    }

    //Stops the broadcasting class
    public void Stop() {
        Bukkit.getScheduler().cancelTask(schedulerTask);
    }

    //Broadcast next message
    private void next() {
        //Makes sure our index does not go over the message's array length
        if(_currentIndex > (_msgList.size() - 1)) {
            _currentIndex = 0;
        }

        this.BroadcastMsg(_currentIndex);
        _currentIndex++;
    }

    //Broadcasts a message from the list, has a catch all failure.
    void BroadcastMsg(int index) throws Error {
        if (index > _msgList.size() -1) {
            throw new Error("Index is out of bounds");
        }

        //Send to the other broadcast message
        BroadcastMsg(ChatColor.translateAlternateColorCodes('&',zbroadcast.GetInstance().getConfig().getString("broadcast-tag") + _msgList.get(_currentIndex)));
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
    public void AddMsg(String msg) {
        _msgList.add(msg);
        zbroadcast.UpdateList(this._msgList);
    }

    //Adds a message at a specific index
    public void InsertMsg(int index, String msg) {
        _msgList.add(index, msg);
        zbroadcast.UpdateList(this._msgList);
    }

    //Removes a message from the list
    public void RemoveMsg(int index) {
        _msgList.remove(index);
        zbroadcast.UpdateList(this._msgList);
    }

    //Returns our current index, add 1 so that we are not starting with 0.
    public int GetCurrentIndex() { return _currentIndex + 1; }

    //Returns our list size
    public int GetMessageListSize() { return _msgList.size(); }

    //Returns the actual list in readonly format
    public List<String> GetMessageList() { return _msgList; }
}
