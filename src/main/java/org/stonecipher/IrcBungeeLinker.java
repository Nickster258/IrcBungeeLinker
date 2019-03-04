package org.stonecipher;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import java.io.IOException;

public class IrcBungeeLinker extends Plugin implements Listener {

    Configuration configuration;
    PircBotX bot;

    @Override
    public void onEnable() {
        configuration = new Configuration.Builder()
                .setName("OREBotBungee") //Set the nick of the bot. CHANGE IN YOUR CODE
                .addServer("irc.esper.net") //Join the freenode network
                .addAutoJoinChannel("#orebotspam") //Join the official #pircbotx channel
                .addListener(new IrcMessageListener(this.getProxy(), this)) //Add our listener that will be called on Events
                .setAutoReconnect(true)
                .buildConfiguration();
        bot = new PircBotX(configuration);
        botThread();
        getProxy().getPluginManager().registerListener(this, this);
    }

    @EventHandler
    public void onMessage(ChatEvent e) {
        ProxiedPlayer pp = (ProxiedPlayer) e.getSender();
        bot.send().message("#orebotspam", "\u000307" + pp.getDisplayName() + "\u000f: " + e.getMessage());
    }

    private void botThread() {
        Thread botThread = new Thread(() -> {
            try {
                bot.startBot();
            } catch (IOException e) {
                getLogger().info(e.toString());
                this.getProxy().getScheduler().runAsync(this, new Runnable() {
                    @Override
                    public void run() {
                        getLogger().info(e.getMessage());
                    }
                });
            } catch (IrcException e) {
                getLogger().info(e.toString());
                this.getProxy().getScheduler().runAsync(this, new Runnable() {
                    @Override
                    public void run() {
                        getLogger().info(e.getMessage());
                    }
                });
            }
        });
        botThread.start();
    }
}
