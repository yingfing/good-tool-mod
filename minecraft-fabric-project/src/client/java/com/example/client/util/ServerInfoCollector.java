package com.example.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.entity.player.Player;

/**
 * 纯客户端获取服务器信息
 * 无需服务器端支持，直接从客户端连接中提取
 */
public class ServerInfoCollector {
	
	public static int getProtocolVersion() {
		Minecraft mc = Minecraft.getInstance();
		ClientPacketListener connection = mc.getConnection();
		if (connection != null) {
			return connection.getProtocolVersion();
		}
		return -1;
	}
	
	public static int getOnlinePlayerCount() {
		Minecraft mc = Minecraft.getInstance();
		if (mc.level != null && mc.level.players != null) {
			return mc.level.players.size();
		}
		return 0;
	}
	
	public static int getMaxPlayers() {
		Minecraft mc = Minecraft.getInstance();
		if (mc.level != null) {
			// 从客户端连接中获取最大玩家数
			ClientPacketListener connection = mc.getConnection();
			if (connection != null && connection.getLevelData() != null) {
				return connection.getLevelData().getMaxPlayers();
			}
		}
		return 20; // 默认值
	}
	
	public static int getClientViewDistance() {
		Minecraft mc = Minecraft.getInstance();
		if (mc.level != null) {
			return mc.level.getChunkSource().getViewDistance();
		}
		return 10; // 默认值
	}
	
	public static int getSimulationDistance() {
		Minecraft mc = Minecraft.getInstance();
		if (mc.level != null) {
			return mc.level.getChunkSource().getTickingRegionManager().ticks;
		}
		return 8; // 默认值
	}
	
	public static String getServerBrand() {
		Minecraft mc = Minecraft.getInstance();
		ClientPacketListener connection = mc.getConnection();
		if (connection != null) {
			// 获取客户端知道的服务器品牌
			String serverBrand = connection.getServerBrand();
			return serverBrand != null ? serverBrand : "Unknown";
		}
		return "Unknown";
	}
	
	public static long getGameTime() {
		Minecraft mc = Minecraft.getInstance();
		if (mc.level != null) {
			return mc.level.getGameTime();
		}
		return 0;
	}
	
	public static String getGameTimeFormatted() {
		long gameTime = getGameTime();
		long dayTime = gameTime % 24000;
		int hours = (int) ((dayTime + 6000) / 1000) % 24;
		int minutes = (int) ((dayTime % 1000) / 1000.0 * 60);
		return String.format("%02d:%02d", hours, minutes);
	}
	
	public static boolean isConnectedToServer() {
		Minecraft mc = Minecraft.getInstance();
		return mc.getConnection() != null && mc.level != null && !mc.isSingleplayer();
	}
}
