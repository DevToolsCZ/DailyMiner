# DailyMiner
DailyMiner is open source plugin for Survival/Vanilla MineCraft servers

### About plugin and Configuration
Plugin can blocked mining for more active users.
More servers have problem with breaking blocks of sands in worlds, or with Ores.

For this reason there is DailyMiner For this reason there is plugin DailyMiner. You can set your custom blocks for blocking mining.
in configuration you can set your custom blocks what be can blocked.

Configuration is easy:

> PlaceHolders: [block] [time]
> [block] return name of block
> [time] return time of available for allow mining.

```yml
DailyMiner:
  prefix: '&f[&2DAILY_MINER&f] &7'
  blocked_mining: 'You are now blocked for mining [block] [time]'
  blocks:
    GOLD_ORE:
      max_to_break: 128
    IRON_ORE:
      max_to_break: 128
    COAL_ORE:
      max_to_break: 128
    LAPIS_ORE:
      max_to_break: 128
    DIAMOND_ORE:
      max_to_break: 128
    REDSTONE_ORE:
      max_to_break: 128
    EMERALD_ORE:
      max_to_break: 128
    NETHER_QUARTZ_ORE:
      max_to_break: 128
```

When player goal finished on max_to_break mining is blocked for him.
And Saved in player_cache.yml
> plugins/DayilMiner/cache/player_cache.yml

Time saved in TimeStamp on 20 minutes because this is one day in minecraft !
When 20 minutes expires player can break blocks.
