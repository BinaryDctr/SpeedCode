package me.binarydctr.speedcoding.warn;

import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class WarnCommand implements CommandExecutor {

    private WarnManager warnManager;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 2) {
            String warner = null;
            if(sender instanceof Player) {
                warner = sender.getName();
            } else {
                warner = "Console";
            }
            String warned = args[1];

            warnManager.warn(warner, warned, "t");
        }

        return false;
    }
}
