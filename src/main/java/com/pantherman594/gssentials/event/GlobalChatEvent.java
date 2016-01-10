/*
 * BungeeEssentials: Full customization of a few necessary features for your server!
 * Copyright (C) 2015  David Shen (PantherMan594)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.pantherman594.gssentials.event;

import com.pantherman594.gssentials.BungeeEssentials;
import com.pantherman594.gssentials.utils.Dictionary;
import com.pantherman594.gssentials.utils.Messenger;
import com.pantherman594.gssentials.utils.Permissions;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class GlobalChatEvent extends Event {
    private String server;
    private String sender;
    private String msg;

    public GlobalChatEvent(String server, String sender, String msg) {
        this.server = server;
        this.sender = sender;
        this.msg = msg;

        if (msg != null) {
            msg = Messenger.filter(ProxyServer.getInstance().getPlayer(sender), msg, Messenger.ChatType.GLOBAL);
            msg = Dictionary.format(Dictionary.FORMAT_GCHAT, "SERVER", server, "SENDER", sender, "MESSAGE", msg);
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if ((player.hasPermission(Permissions.General.CHAT + "." + server) || player.hasPermission(Permissions.General.CHAT)) && (!BungeeEssentials.getInstance().contains("ignore") || !Messenger.isIgnoring(player, ProxyServer.getInstance().getPlayer(sender))))
                    player.sendMessage(msg);
            }
            ProxyServer.getInstance().getConsole().sendMessage(msg);
        }
    }

    public String getServer() {
        return server;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return msg;
    }
}
