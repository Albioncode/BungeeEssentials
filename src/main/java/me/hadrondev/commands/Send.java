/*
 * Copyright (c) 2014 Connor Spencer Harries
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.hadrondev.commands;

import me.hadrondev.BungeeEssentials;
import me.hadrondev.Messages;
import me.hadrondev.permissions.Permission;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

@SuppressWarnings("deprecation")
public class Send extends Command {
    public Send() {
        super("send", Permission.ADMIN_SEND.toString());
    }

    @Override
    public void execute(final CommandSender sender, String[] args) {
        if (args.length > 1) {
            final ProxiedPlayer player = BungeeEssentials.me.getProxy().getPlayer(args[0]);
            if (player != null) {
                String msg = Messages.SEND;
                msg = msg.replace("{PLAYER}", args[0]);
                msg = msg.replace("{SERVER}", args[1]);
                sender.sendMessage(Messages.lazyColour(msg));

                player.connect(BungeeEssentials.me.getProxy().getServerInfo(args[1]),
                        new Callback<Boolean>() {
                            @Override
                            public void done(Boolean success, Throwable throwable) {
                                if (success) {
                                    player.sendMessage(new ComponentBuilder("Whooooooooooosh!").color(ChatColor.LIGHT_PURPLE).create());
                                } else {
                                    // Pretend nothing happened for the player being sent
                                    sender.sendMessage(ChatColor.RED + "Unable to send player to server.");
                                }
                            }
                        });
            } else {
                sender.sendMessage(Messages.lazyColour(Messages.PLAYER_OFFLINE));
            }
        } else {
            sender.sendMessage(Messages.lazyColour(Messages.INVALID_ARGS));
        }
    }
}