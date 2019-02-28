package org.stonecipher;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import java.io.IOException;

public class IrcBungeeLinker extends Plugin {

    @Override
    public void onEnable() {
        initializeListener();
    }

    private void initializeListener() {
        this.getProxy().getScheduler().runAsync(this, new Runnable() {
            @Override
            public void run() {
                getLogger().info("initializing listener");
            }
        });
        Thread ircMessageListener = new Thread(() -> {
            while (true) {
                Configuration configuration = new Configuration.Builder()
                        .setName("OREBotBungee") //Set the nick of the bot. CHANGE IN YOUR CODE
                        .addServer("irc.esper.net") //Join the freenode network
                        .addAutoJoinChannel("#openredstone") //Join the official #pircbotx channel
                        .addListener(new IrcMessageListener(this.getProxy(), this)) //Add our listener that will be called on Events
                        .buildConfiguration();

                //Create our bot with the configuration
                PircBotX bot = new PircBotX(configuration);
                //Connect to the server
                try {
                    bot.startBot();
                    this.getProxy().getScheduler().runAsync(this, new Runnable() {
                        @Override
                        public void run() {
                            getLogger().info("initialized bot");
                        }
                    });
                } catch (IOException e) {
                    this.getProxy().getScheduler().runAsync(this, new Runnable() {
                        @Override
                        public void run() {
                            getLogger().info(e.getMessage());
                        }
                    });
                } catch (IrcException e) {
                    this.getProxy().getScheduler().runAsync(this, new Runnable() {
                        @Override
                        public void run() {
                            getLogger().info(e.getMessage());
                        }
                    });
                }
            }
        });
    }
}
