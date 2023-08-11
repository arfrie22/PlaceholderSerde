package com.github.arfrie22.serde;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Serde extends PlaceholderExpansion {
    private final LegacyComponentSerializer legacyHexSerializer = LegacyComponentSerializer.builder().hexColors().useUnusualXRepeatedCharacterHexFormat().build();
    private final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.builder().build();
    private final PlainTextComponentSerializer plainTextSerializer = PlainTextComponentSerializer.builder().build();
    private final ANSIComponentSerializer ansiSerializer = ANSIComponentSerializer.builder().build();
    private final MiniMessage miniMessageSerializer = MiniMessage.builder()
            .tags(TagResolver.builder()
                    .resolvers(StandardTags.defaults())
                    .build()
            )
            .build();


    @Override
    public @NotNull String getIdentifier() {
        return "serde";
    }

    @Override
    public @NotNull String getAuthor() {
        return "arfrie22";
    }

    @Override
    public @NotNull String getVersion() {
        return "VERSION";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        String[] identifierParts = identifier.split("\\.");
        if (identifierParts.length < 3) {
            return null;
        }

        String message = PlaceholderAPI.setPlaceholders(player, "%" + String.join(".", Arrays.copyOfRange(identifierParts, 2, identifierParts.length)) + "%");

        Component component;
        switch (identifierParts[0]) {
            case "legacy" -> component = legacySerializer.deserialize(message);
            case "legacy-hex" -> component = legacyHexSerializer.deserialize(message);
            case "plaintext" -> component = plainTextSerializer.deserialize(message);
            case "minimessage" -> component = miniMessageSerializer.deserialize(message);
            default -> {
                return null;
            }
        }

        String convertedMessage;
        switch (identifierParts[1]) {
            case "legacy" -> convertedMessage = legacySerializer.serialize(component);
            case "legacy-hex" -> convertedMessage = legacyHexSerializer.serialize(component);
            case "plaintext" -> convertedMessage = plainTextSerializer.serialize(component);
            case "ansi" -> convertedMessage = ansiSerializer.serialize(component);
            case "minimessage" -> convertedMessage = miniMessageSerializer.serialize(component);
            default -> {
                return null;
            }
        }

        return convertedMessage;
    }
}
