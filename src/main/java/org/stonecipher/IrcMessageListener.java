package org.stonecipher;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.concurrent.TimeUnit;

public class IrcMessageListener extends ListenerAdapter {

    private ProxyServer ps;
    private Plugin p;

    public IrcMessageListener(ProxyServer ps, Plugin p) {
        this.ps = ps;
        this.p = p;
    }

    public void onEvent(GenericMessageEvent event) throws Exception {
        ps.getScheduler().schedule(p, new Runnable() {
            @Override
            public void run() {
                TextComponent bs = new TextComponent("IRC | " + event.getUser() + ": " + event.getMessage());
                ps.broadcast(bs);
            }
        }, 0, TimeUnit.SECONDS);

    }
}
