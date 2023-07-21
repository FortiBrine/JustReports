package me.fortibrine.justreports.utils;

import me.fortibrine.justreports.JustReports;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.COLOR_CHAR;

public class MessageManager {

    private static FileConfiguration config;

    public static void init(JustReports plugin) {
        MessageManager.config = plugin.getConfig();
    }

    public static String getStringFromConfig(String path) {
        return supportMessagesJSON(supportColorsHEX(config.getString(path))).replace("&", "§");
    }

    public static List<String> getStringListFromConfig(String path) {
        List<String> stringList = config.getStringList(path);

        stringList.replaceAll(s -> supportMessagesJSON(supportColorsHEX(s)).replace("&", "§"));
        return stringList;
    }

    public static String supportColorsHEX(String nameMessage) {

        String sversion;

        try {
            sversion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            return nameMessage;
        }

        if (Integer.parseInt(sversion.split("\\_")[1]) < 16) {
            return nameMessage;
        }

        final Pattern hexColorsPattern = Pattern.compile("&#([a-f0-9]{6})");
        final Matcher matcherPattern = hexColorsPattern.matcher(nameMessage);
        StringBuffer buffer = new StringBuffer(nameMessage.length() + 4 * 8);
        while (matcherPattern.find())
        {
            String group = matcherPattern.group(1);
            matcherPattern.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcherPattern.appendTail(buffer).toString();
    }

    public static String supportMessagesJSON(String nameMessage) {

        if (nameMessage.startsWith("[json]")) {
            return new TextComponent(ComponentSerializer.parse(nameMessage.substring(5))).toString();
        }
        return nameMessage;
    }

}
