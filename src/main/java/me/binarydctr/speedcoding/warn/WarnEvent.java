package me.binarydctr.speedcoding.warn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class WarnEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private String warner;
    private String warned;
    private String inform;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
