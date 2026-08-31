package com.example.client.network;

import com.example.network.ServerInfoPacket;

public class ClientServerInfoData {
	private static ServerInfoPacket lastServerInfo = null;

	public static void setLastServerInfo(ServerInfoPacket packet) {
		lastServerInfo = packet;
	}

	public static ServerInfoPacket getLastServerInfo() {
		return lastServerInfo;
	}

	public static boolean hasServerInfo() {
		return lastServerInfo != null;
	}

	public static void clear() {
		lastServerInfo = null;
	}
}
