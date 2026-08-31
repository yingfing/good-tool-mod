package com.example.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import com.example.ExampleMod;

public class ServerNetworking {
	
	public static void init() {
		// 注册服务器端的网络包处理
		// 当客户端请求服务器信息时，服务器会收到请求并发送响应
		ServerPlayNetworking.registerGlobalReceiver(ServerInfoPacket.ID, (packet, player, handler) -> {
			try {
				// 创建包含服务器信息的响应数据包
				ServerInfoPacket responsePacket = ServerInfoPacket.create(player.getServer(), handler);
				// 发送回客户端
				ServerPlayNetworking.send(player, responsePacket);
			} catch (Exception e) {
				ExampleMod.LOGGER.warn("Failed to handle server info request", e);
			}
		});
		
		ExampleMod.LOGGER.info("Server networking initialized");
	}
}
