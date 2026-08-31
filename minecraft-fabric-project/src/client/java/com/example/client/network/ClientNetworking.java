package com.example.client.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import com.example.ExampleMod;
import com.example.network.ServerInfoPacket;

public class ClientNetworking {
	
	public static void init() {
		// Register client-side packet handler for server info responses
		ClientPlayNetworking.registerGlobalReceiver(ServerInfoPacket.ID, (packet, player, handler) -> {
			// Store packet data for display in screen
			ClientServerInfoData.setLastServerInfo(packet);
			ExampleMod.LOGGER.info("Received server info: {} players, view distance: {}", 
				packet.onlineCount, packet.serverViewDistance);
		});
		
		ExampleMod.LOGGER.info("Client networking initialized");
	}

	/**
	 * 向服务器请求服务器信息
	 * 这会发送一个空包给服务器，服务器会响应包含详细信息的包
	 */
	public static void requestServerInfo() {
		try {
			if (ClientPlayNetworking.canSend(ServerInfoPacket.ID)) {
				// 发送一个空包作为请求
				FriendlyByteBuf buf = PacketByteBufs.create();
				ClientPlayNetworking.send(ServerInfoPacket.ID, buf);
				ExampleMod.LOGGER.debug("Sent server info request");
			} else {
				ExampleMod.LOGGER.warn("Cannot send server info request - server doesn't support this mod");
			}
		} catch (Exception e) {
			ExampleMod.LOGGER.warn("Failed to request server info", e);
		}
	}
}
