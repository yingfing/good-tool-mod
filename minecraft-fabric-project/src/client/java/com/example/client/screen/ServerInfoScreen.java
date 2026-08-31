package com.example.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import com.example.client.network.ClientServerInfoData;
import com.example.client.network.ClientNetworking;
import com.example.network.ServerInfoPacket;

public class ServerInfoScreen extends Screen {
	private final Screen parent;
	private static final int ENTRY_HEIGHT = 25;
	private static final int SIDEBAR_WIDTH = 200;
	private boolean infoRequested = false;

	public ServerInfoScreen(Screen parent) {
		super(Component.literal("Server Information"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		// Back button
		this.addRenderableWidget(Button.builder(Component.literal("Back"), (button) -> {
			this.minecraft.setScreen(this.parent);
		}).bounds(this.width - SIDEBAR_WIDTH + 10, this.height - 30, SIDEBAR_WIDTH - 20, 20).build());
		
		// Clear old data and request fresh server info on screen initialization
		if (!infoRequested) {
			ClientServerInfoData.clear(); // Clear old data
			ClientNetworking.requestServerInfo();
			infoRequested = true;
		}
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

		// Title
		guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 10, 0xffffff);

		ServerInfoPacket info = ClientServerInfoData.getLastServerInfo();

		if (info == null) {
			guiGraphics.drawCenteredString(this.font, Component.literal("Loading server information..."), 
				this.width / 2, this.height / 2, 0xcccccc);
		} else {
			int yPos = 40;
			int leftMargin = 20;

			// Protocol Version
			drawInfoLine(guiGraphics, leftMargin, yPos, "Protocol Version:", String.valueOf(info.protocolVersion));
			yPos += ENTRY_HEIGHT;

			// Online Players
			drawInfoLine(guiGraphics, leftMargin, yPos, "Online Players:", 
				String.format("%d / %d", info.onlineCount, info.maxCount));
			yPos += ENTRY_HEIGHT;

			// Server View Distance
			drawInfoLine(guiGraphics, leftMargin, yPos, "Server View Distance:", 
				String.format("%d chunks", info.serverViewDistance));
			yPos += ENTRY_HEIGHT;

			// Simulation Distance
			drawInfoLine(guiGraphics, leftMargin, yPos, "Simulation Distance:", 
				String.format("%d chunks", info.simDistance));
			yPos += ENTRY_HEIGHT;

			// Server Brand
			drawInfoLine(guiGraphics, leftMargin, yPos, "Server Brand:", info.serverBrand);
			yPos += ENTRY_HEIGHT;

			// Day Time
			long dayTime = info.serverTime % 24000;
			int hours = (int) ((dayTime + 6000) / 1000) % 24;
			int minutes = (int) ((dayTime % 1000) / 1000.0 * 60);
			drawInfoLine(guiGraphics, leftMargin, yPos, "Server Time:", 
				String.format("%02d:%02d", hours, minutes));
			yPos += ENTRY_HEIGHT;

			// Environment
			drawInfoLine(guiGraphics, leftMargin, yPos, "Environment:", "Multiplayer Server");
			yPos += ENTRY_HEIGHT;
			
			// Extra info
			guiGraphics.drawString(this.font, Component.literal("§7Press ESC to close"), 
				leftMargin, this.height - 50, 0x888888);
		}

		super.render(guiGraphics, mouseX, mouseY, partialTick);
	}

	private void drawInfoLine(GuiGraphics guiGraphics, int x, int y, String label, String value) {
		guiGraphics.drawString(this.font, Component.literal(label), x, y, 0xaaaaaa);
		guiGraphics.drawString(this.font, Component.literal(value), x + 150, y, 0xffffff);
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(this.parent);
	}
}
