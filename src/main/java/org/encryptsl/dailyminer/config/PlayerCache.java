/**
 *
 * @author EncryptSL
 * @organization EternityCas
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
package org.encryptsl.dailyminer.config;

import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PlayerCache {

    private File file;
    private FileConfiguration configuration;

    public void createCache() {
        file = new File("plugins/DailyMiner/cache/player_cache.yml");
        configuration = YamlConfiguration.loadConfiguration(file);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }


    public void setCancelMining(Player player, Block block) {
        configuration = YamlConfiguration.loadConfiguration(file);
        long toset = System.currentTimeMillis()+1200000;
        configuration.set("PlayerCache."+player.getName()+"."+block.getType()+".mclimit_complete", toset);
        configuration.set("PlayerCache."+player.getName()+"."+block.getType()+".count_breaks", 0);
        saveCache();
    }

    public boolean getAllowMining(Player player, Block block) {
        long current = System.currentTimeMillis();
        long millis = getConfig().getLong("PlayerCache."+player.getName()+"."+block.getType()+".mclimit_complete");
        return current > millis;
    }

    public String getTime(long millis) {
        long sec = millis/1000;
        long min = 0;
        while (sec >60) {
            sec-=60;
            min++;
        }
        long h = 0;
        while (min >60) {
            min-=60;
            h++;
        }
        return h+":"+min+":"+sec;
    }

    public FileConfiguration getConfig() {
        return configuration;
    }

    public void saveCache() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadCache() {
        configuration = YamlConfiguration.loadConfiguration(file);
    }

}
