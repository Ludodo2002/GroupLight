package fr.redcraft;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static void hoverMessage(ComponentBuilder builder, List<String> lines) {
        HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_TEXT, lines.stream().map((Text::new)).collect(Collectors.toList()));
        builder.event(event);
    }
}
