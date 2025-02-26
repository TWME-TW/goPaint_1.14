/*
 * goPaint is designed to simplify painting inside of Minecraft.
 * Copyright (C) Arcaniax-Development
 * Copyright (C) Arcaniax team and contributors
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
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.arcaniax.gopaint.command;

import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Handler implements Listener, CommandExecutor {

    public static GoPaintPlugin plugin;

    public Handler(GoPaintPlugin main) {
        plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gopaint") || cmd.getName().equalsIgnoreCase("gp")) {
            if (!(sender instanceof Player)) {
                return false;
            }
            Player p = (Player) sender;
            PlayerBrush pb = GoPaintPlugin.getBrushManager().getPlayerBrush(p);
            String prefix = GoPaintPlugin.getSettings().getPrefix();
            if (!p.hasPermission("gopaint.use")) {
                p.sendMessage(prefix + "§c您缺乏 gopaint.use 的權限。");
                return true;
            }
            if (args.length == 0) {
                if (p.hasPermission("gopaint.admin")) {
                    p.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo§7|§creload");
                    return true;
                }
                p.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo");
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("size")) {
                    p.sendMessage(prefix + "§c/gp size [數字]");
                    return true;
                } else if (args[0].equalsIgnoreCase("toggle")) {
                    if (pb.isEnabled()) {
                        pb.toggleEnabled();
                        p.sendMessage(prefix + "§c筆刷已被禁用。");
                    } else {
                        pb.toggleEnabled();
                        p.sendMessage(prefix + "§a筆刷已啟用。");
                    }
                    return true;
                } else if ((args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) && p.hasPermission(
                        "gopaint.admin")) {
                    GoPaintPlugin.reload();
                    p.sendMessage(prefix + "§a已重新載入。");
                    return true;
                } else if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("i")) {

                    p.spigot().sendMessage(new ComponentBuilder("goPaint> ").color(ChatColor.AQUA)
                            .append("Created by: ").color(ChatColor.GOLD)
                            .append("Arcaniax").color(ChatColor.YELLOW).create());


                    p.spigot().sendMessage(new ComponentBuilder("goPaint> ").color(ChatColor.AQUA)
                            .append("Links: ").color(ChatColor.GOLD)
                            .append("Twitter").color(ChatColor.DARK_AQUA).color(ChatColor.UNDERLINE)
                            .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://twitter.com/Arcaniax")).append("     ")
                            .append("Spigot").color(ChatColor.YELLOW).color(ChatColor.UNDERLINE)
                            .event(new ClickEvent(
                                    ClickEvent.Action.OPEN_URL,
                                    "https://www.spigotmc.org/resources/authors/arcaniax.47444/"
                            )).create());
                    return true;
                }
                if (p.hasPermission("gopaint.admin")) {
                    p.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo§7|§creload");
                    return true;
                }
                p.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo");
                return true;
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("size") || args[0].equalsIgnoreCase("s")) {
                    try {
                        int sizeAmount = Integer.parseInt(args[1]);
                        pb.setBrushSize(sizeAmount);
                        p.sendMessage(prefix + "§6大小設置為: §e" + pb.getBrushSize());
                        return true;
                    } catch (Exception e) {
                        p.sendMessage(prefix + "§c/gb size [數字]");
                        return true;
                    }
                }
                if (p.hasPermission("gopaint.admin")) {
                    p.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo§7|§creload");

                    return true;
                }
                p.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo");
                return true;
            }
        }
        return false;
    }

}
