package top.karpvp.function.autorestart;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements CommandExecutor {

    int time = 1800;

    @Override
    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getConsoleSender().sendMessage("§c自动重启已载入");
        saveDefaultConfig();
        reloadConfig();
        time = getConfig().getInt("time");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (time <= 600) {
                    if (time == 600) {
                        getServer().broadcastMessage("§8---------------------");
                        getServer().broadcastMessage("§7§l服务器将在§f§l10§7§l分钟后重启");
                        getServer().broadcastMessage("§8---------------------");
                    } else if (time == 300) {
                        getServer().broadcastMessage("§8---------------------");
                        getServer().broadcastMessage("§7§l服务器将在§f§l5§7§l分钟后重启");
                        getServer().broadcastMessage("§8---------------------");
                    } else if (time == 180) {
                        getServer().broadcastMessage("§8---------------------");
                        getServer().broadcastMessage("§7§l服务器将在§f§l3§7§l分钟后重启");
                        getServer().broadcastMessage("§8---------------------");
                    } else if (time == 60) {
                        getServer().broadcastMessage("§8---------------------");
                        getServer().broadcastMessage("§7§l服务器将在§f§l1§7§l分钟后重启");
                        getServer().broadcastMessage("§8§l* §7§l重启完成大概需要§f§l1§f§l分钟");
                        getServer().broadcastMessage("§8---------------------");
                    } else if (time == 30) {
                        getServer().broadcastMessage("§8---------------------");
                        getServer().broadcastMessage("§7§l服务器将在§f§l30§7§l秒后重启");
                        getServer().broadcastMessage("§8§l* §7§l重启完成大概需要§f§l1§f§l分钟");
                        getServer().broadcastMessage("§8---------------------");
                    } else if (time == 10) {
                        getServer().broadcastMessage("§6§lK§e§lar §7> §7§l服务器将在§f§l10§7§l秒后重启");
                    } else if (time == 5) {
                        getServer().broadcastMessage("§6§lK§e§lar §7> §7§l服务器将在§f§l5§7§l秒后重启");
                    } else if (time == 4) {
                        getServer().broadcastMessage("§6§lK§e§lar §7> §7§l服务器将在§f§l4§7§l秒后重启");
                    } else if (time == 3) {
                        getServer().broadcastMessage("§6§lK§e§lar §7> §7§l服务器将在§f§l3§7§l秒后重启");
                    } else if (time == 2) {
                        getServer().broadcastMessage("§6§lK§e§lar §7> §7§l服务器将在§f§l2§7§l秒后重启");
                    } else if (time == 1) {
                        getServer().broadcastMessage("§6§lK§e§lar §7> §7§l服务器将在§f§l1§7§l秒后重启");
                    } else if (time == 0) {
                        for (Player p : getServer().getOnlinePlayers()) {
                            quitServer(p);
                        }
                    } else if (time == -3) {
                        getServer().shutdown();
                    }
                }
                time--;
            }
        }.runTaskTimer(this, 0L, 20L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rstart")) {
            if (args.length == 0) {
                if(time > 11){
                    getServer().broadcastMessage("§6§lK§e§lar §7> §c自动重启已提前");
                }
                time = 11;
                return true;
            } else if (args.length == 1) {
                try {
                    int btime = time;
                    time = Integer.parseInt(args[0]);
                    if(time < 60){
                        if(btime < 300){
                            getServer().broadcastMessage("§6§lK§e§lar §7> §c自动重启已推迟到§f§l"+time+"s");
                        }else{
                            sender.sendMessage("§6§lK§e§lar §7> 成功将重启时间设置为§f§l"+time+"s");
                        }
                    }else if(time >=60 && time < 3600){
                        if(btime < 300){
                            getServer().broadcastMessage("§6§lK§e§lar §7> §c自动重启已推迟到§f§l"+(time/60)+"min "+(time%60)+"s");
                        }else{
                            sender.sendMessage("§6§lK§e§lar §7> 成功将重启时间设置为§f§l"+(time/60)+"min "+(time%60)+"s");
                        }
                    }else if(time >3600){
                        int hour = time / 3600;
                        int min = (time - (3600 * hour)) / 60;
                        int s = (time - (3600 * hour)) % 60;
                        if(btime < 300){
                            getServer().broadcastMessage("§6§lK§e§lar §7> §c自动重启已推迟到§f§l"+hour+"h "+min+"min "+s+"s");
                        }else{
                            sender.sendMessage("§6§lK§e§lar §7> 成功将重启时间设置为§f§l"+hour+"h "+min+"min "+s+"s");
                        }
                    }
                    return true;
                } catch (NumberFormatException ex) {
                    sender.sendMessage("§c必须是数字");
                    return true;
                }
            }
            sender.sendMessage("§c参数错误!");
        }
        if (cmd.getName().equalsIgnoreCase("delay")) {
            time += 300;
            if(time < 600){
                getServer().broadcastMessage("§6§lK§e§lar §7> §c自动重启已延迟5分钟");
            }else{
                sender.sendMessage("§6§lK§e§lar §7> §c自动重启已延迟5分钟");
            }
            return true;
        }
        return true;
    }

    public void quitServer(Player p) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(getConfig().getString("Server"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        p.sendPluginMessage(this, "BungeeCord", b.toByteArray());
    }

    @Override
    public void onDisable() {
        if(time < 300){
            getServer().broadcastMessage("§6§lK§e§lar §7> §c自动重启已推迟");
        }
        getServer().getConsoleSender().sendMessage("§c自动重启已卸载");
    }

}
