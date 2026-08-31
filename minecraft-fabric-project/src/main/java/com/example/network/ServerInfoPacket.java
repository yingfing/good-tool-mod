package com.example.network;

import net.fabricmc.fabric.api.networking.v1.FabricPacketCodec;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import com.example.ExampleMod;

public class ServerInfoPacket implements CustomPacketPayload {
	public static final ResourceLocation ID = ExampleMod.id("server_info");
	
	// Server info fields
	public final int protocolVersion;
	public final int onlineCount;
	public final int maxCount;
	public final int serverViewDistance;
	public final int simDistance;
	public final String serverBrand;
	public final long serverTime;

	public ServerInfoPacket(int protocolVersion, int onlineCount, int maxCount, int serverViewDistance, 
			int simDistance, String serverBrand, long serverTime) {
		this.protocolVersion = protocolVersion;
		this.onlineCount = onlineCount;
		this.maxCount = maxCount;
		this.serverViewDistance = serverViewDistance;
		this.simDistance = simDistance;
		this.serverBrand = serverBrand;
		this.serverTime = serverTime;
	}

	public static final StreamCodec<FriendlyByteBuf, ServerInfoPacket> CODEC = 
		CustomPacketPayload.codec(ServerInfoPacket::write, ServerInfoPacket::read);

	private static void write(ServerInfoPacket packet, FriendlyByteBuf buf) {
		buf.writeInt(packet.protocolVersion);
		buf.writeInt(packet.onlineCount);
		buf.writeInt(packet.maxCount);
		buf.writeInt(packet.serverViewDistance);
		buf.writeInt(packet.simDistance);
		buf.writeUtf(packet.serverBrand);
		buf.writeLong(packet.serverTime);
	}

	private static ServerInfoPacket read(FriendlyByteBuf buf) {
		return new ServerInfoPacket(
			buf.readInt(),
			buf.readInt(),
			buf.readInt(),
			buf.readInt(),
			buf.readInt(),
			buf.readUtf(),
			buf.readLong()
		);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}

	/**
	 * 创建一个包含当前服务器信息的数据包
	 */
	public static ServerInfoPacket create(MinecraftServer server, ServerGamePacketListenerImpl handler) {
		try {
			int protocolVersion = handler.player.connection.getProtocolVersion();
			int onlineCount = server.getPlayerCount();
			int maxCount = server.getMaxPlayers();
			int serverViewDistance = server.getProperties().viewDistance;
			int simDistance = server.getProperties().simulationDistance;
			String serverBrand = server.getServerModName();
			long serverTime = server.overworld().getDayTime();

			return new ServerInfoPacket(protocolVersion, onlineCount, maxCount, 
				serverViewDistance, simDistance, serverBrand, serverTime);
		} catch (Exception e) {
			ExampleMod.LOGGER.warn("Failed to create server info packet", e);
			// 返回默认值
			return new ServerInfoPacket(340, 1, 20, 10, 8, "unknown", 0);
		}
	}
}
