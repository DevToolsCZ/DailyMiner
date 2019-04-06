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
package org.encryptsl.dailyminer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.encryptsl.dailyminer.config.PlayerCache;
import org.encryptsl.dailyminer.listener.BlockBreakEventClass;

public final class DailyMiner extends JavaPlugin {

    private static FileConfiguration configuration;
    private static PlayerCache playerCache;
    private PluginManager manager = getServer().getPluginManager();

    @Override
    public void onEnable() {
        this.configuration = getConfig();
        playerCache = new PlayerCache();
        playerCache.createCache();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.registerListener();
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }

    private void registerListener() {
        System.out.println("Register/Listeners status ok....");
        manager.registerEvents(new BlockBreakEventClass(this), this);
    }

    public PlayerCache getPlayerCache() {
        return playerCache;
    }

}
