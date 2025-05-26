package fr.mirage.api.spigot.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PlayerUtils {

//    public void sendActionBar(String str) {
//        String colored = ChatColor.translateAlternateColorCodes('&', str);
//        IChatBaseComponent chat = new ChatComponentText(colored);
//        PacketPlayOutChat packet = new PacketPlayOutChat(chat, (byte) 2);
//        sendPacket(packet);
//    }
//
//    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
//        if (title != null) {
//            title = ChatColor.translateAlternateColorCodes('&', title);
//            IChatBaseComponent titleComponent = new ChatComponentText(title);
//            PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleComponent);
//            sendPacket(titlePacket);
//        }
//
//        if (subtitle != null) {
//            subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
//            IChatBaseComponent subtitleComponent = new ChatComponentText(subtitle);
//            PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleComponent);
//            sendPacket(subtitlePacket);
//        }
//
//        PacketPlayOutTitle timingPacket = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
//        sendPacket(timingPacket);
//    }
//
//    public void sendTitle(String title) {
//        sendTitle(title, null, 10, 70, 20);
//    }
//
//    public void clearTitle() {
//        PacketPlayOutTitle clearPacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.CLEAR, null);
//        sendPacket(clearPacket);
//    }
//
//    public void resetTitle() {
//        PacketPlayOutTitle resetPacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET, null);
//        sendPacket(resetPacket);
//    }
//
//    private void sendPacket(Packet<?> packet) {
//        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
//    }

}