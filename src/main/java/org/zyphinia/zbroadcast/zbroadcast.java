package org.zyphinia.zbroadcast;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.zyphinia.zbroadcast.Commands.CmdBroadcast;

public final class zbroadcast  extends JavaPlugin {

    private Broadcast _broadcast = null;

    private static zbroadcast pluginInstance = null;

    private boolean _isRunning = false;

    @Override
    public void onEnable() {
        pluginInstance = this;

        //load config/write new config
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }

        _broadcast = new Broadcast();

        try {
            _broadcast.Start();
            _isRunning = true;
        } catch (Error ex) {
            throw ex;
        }

        this.getCommand("zbroadcast").setExecutor(new CmdBroadcast());

        getLogger().log(Level.INFO, "Simple Broadcast Enabled");
    }

    @Override
    public void onDisable() {
        //stop all broadcasting
        _broadcast.Stop();

        _isRunning = false;

        //remove all variables
        _broadcast = null;
    }


    //Reload our config safely
    public void Reload() {
        _broadcast.Stop();

        reloadConfig();

        _broadcast.Start();
    }

    public boolean IsRunning() {
        return _isRunning;
    }

    public Broadcast GetBroadcast() {
        return _broadcast;
    }

    //Updates the list and saves it
    static void UpdateList(List<String> list) {
        GetInstance().getConfig().set("messages", list);
        GetInstance().saveConfig();
    }

    public static zbroadcast GetInstance() {
        return pluginInstance;
    }
}
