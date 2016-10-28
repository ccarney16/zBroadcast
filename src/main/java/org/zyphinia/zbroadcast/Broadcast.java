package org.zyphinia.zbroadcast;

import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import net.md_5.bungee.api.ChatColor;

/*
 * Main Broadcasting Class, contains all the functions needed to broadcast messages
 */
public class Broadcast {

    private Timer _broadcastTimer = null; //Our timer, currently null

    private String _tag = null; //Broadcast tag, typically in the form of [Broadcast]
    private List<String> _msgList = null; //Message list
    private int _msgIndex = 0; //What are we currently on?

    private long _msgDelay = 0;  //Delay in java ticks, so 1000 is 1 second

    //simple task
    TimerTask _task = new TimerTask() {
        @Override
        public void run() {
            Next();
            _broadcastTimer.purge();
        }
    };

    //Loads all the variables we need
    public Broadcast(String tag, List<String> msgList, long msgDelay) {
        _tag = tag;
        _msgList = msgList;
        _msgDelay = msgDelay;
    }

    public void Start() throws Error {
        if (_msgDelay < 1000) {
            throw new Error("msg delay must be above or equal to one second (Are you sure you want to lag bukkit/spigot to death?)");
        }

        _broadcastTimer = new Timer("_broadcastTimer");

        //Start the schedule
        _broadcastTimer.scheduleAtFixedRate(_task, 1000, _msgDelay);
    }

    //Stops the broadcasting class
    public void Stop() {
        try {
            _broadcastTimer.cancel();
            _broadcastTimer = null;
        } catch (Exception e) {
            //ignore the problem entirely
        }
    }

    //Broadcast next message
    public void Next() {
        //Makes sure our index does not go over the message's array length
        if(_msgIndex > (_msgList.toArray().length - 1)) {
            _msgIndex = 0;
        }

        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',_tag + _msgList.toArray()[_msgIndex].toString()));
        _msgIndex++;
    }
}
