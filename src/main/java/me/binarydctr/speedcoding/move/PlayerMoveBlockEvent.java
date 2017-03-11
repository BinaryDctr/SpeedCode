package me.binarydctr.speedcoding.move;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class PlayerMoveBlockEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Location location;
    private boolean cancelled;

    public PlayerMoveBlockEvent(Player player, Location location) {
        this.player = player;
        this.location = location;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
