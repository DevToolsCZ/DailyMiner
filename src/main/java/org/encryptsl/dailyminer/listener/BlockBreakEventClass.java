/**
 *
 * @author EncryptSL
 * @organization EternityCast
 * @web: http://encryptsl.cekuj.net
 * License
 * Copyright (c) <2019> <EternityCast>
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package org.encryptsl.dailyminer.listener;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.encryptsl.dailyminer.DailyMiner;

import java.util.Map;
import java.util.WeakHashMap;

public class BlockBreakEventClass implements Listener {

    private DailyMiner dailyMiner;
    private Map<Player, Integer> cache_blocks = new WeakHashMap<>();
    public BlockBreakEventClass(DailyMiner dailyMiner) {
        this.dailyMiner = dailyMiner;
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        int cache_blocks = this.cache_blocks.getOrDefault(player, 0) + 1;
        if(dailyMiner.getConfig().getString("DailyMiner.blocks." + block.getType()) != null) {
            if(!dailyMiner.getPlayerCache().getAllowMining(player, block)) {
                long current = System.currentTimeMillis();
                long release = dailyMiner.getPlayerCache().getConfig().getLong("PlayerCache."+player.getName()+"."+block.getType()+".mclimit_complete");
                long millis = release - current;
                String message_replace = dailyMiner.getConfig().getString("DailyMiner.prefix") +  dailyMiner.getConfig().getString("DailyMiner.blocked_mining")
                        .replace("[block]", block.getType().toString())
                        .replace("[player]", player.getName())
                        .replace("[time]", dailyMiner.getPlayerCache().getTime(millis));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message_replace));
                event.setCancelled(true);
            } else {
                if (dailyMiner.getConfig().getInt("DailyMiner.blocks." + block.getType() + ".max_to_break") ==
                        dailyMiner.getPlayerCache().getConfig().getInt("PlayerCache." + player.getName() + "." + block.getType() + ".count_breaks")) {
                    event.setCancelled(true);
                        dailyMiner.getPlayerCache().setCancelMining(player, block);
                } else {
                    this.cache_blocks.put(player, cache_blocks);
                    dailyMiner.getPlayerCache().getConfig().set("PlayerCache." + player.getName() + "." + block.getType() + ".count_breaks", cache_blocks);
                    dailyMiner.getPlayerCache().saveCache();
                }
            }
        }
    }

}
