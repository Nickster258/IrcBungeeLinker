package org.stonecipher;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class IrcMessageListener extends ListenerAdapter {

    private ProxyServer ps;
    private Plugin p;

    public IrcMessageListener(ProxyServer ps, Plugin p) {
        this.ps = ps;
        this.p = p;
    }

    @Override
    public void onMessage(MessageEvent event) throws Exception {
        ps.getScheduler().runAsync(p, new Runnable() {
            @Override
            public void run() {
                TextComponent bs = new TextComponent("§cIRC §7| §f" + event.getUser().getNick() + "§7:§r " + event.getMessage());
                for (ProxiedPlayer player : ps.getPlayers()) {
                    player.sendMessage(bs);
                }
            }
        });
    }
}
