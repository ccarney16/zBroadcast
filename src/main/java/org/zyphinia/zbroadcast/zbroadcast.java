package org.zyphinia.zbroadcast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public final class zbroadcast  extends JavaPlugin {

    private Broadcast _broadcast = null;

    @Override
    public void onEnable() {
        //load config/write new config
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }

        List<String> _msgList = new ArrayList<String>();

        //might not be the safest idea
        for (Object l: getConfig().getList("messages")) {
                _msgList.add(l.toString());
        }

        //setup variables
        _broadcast = new Broadcast(getConfig().getString("broadcast-tag"), _msgList, getConfig().getLong("delay"));


        try {
            _broadcast.Start();
        } catch (Error ex) {
            throw ex;
        }

        getLogger().log(Level.INFO, "Simple Broadcast Enabled");
    }

    @Override
    public void onDisable() {
        //stop all broadcasting
        _broadcast.Stop();

        //remove all variables
        _broadcast = null;
    }
}
