package com.example.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import com.example.client.util.ServerInfoCollector;

/**
 * 纯客户端服务器信息显示屏幕
 * 无需网络通信，直接从客户端获取信息
 */
public class ServerInfoScreen extends Screen {
	private final Screen parent;
	private static final int ENTRY_HEIGHT = 25;
	private static final int SIDEBAR_WIDTH = 200;

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
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

		// Title
		guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 10, 0xffffff);

		if (!ServerInfoCollector.isConnectedToServer()) {
			guiGraphics.drawCenteredString(this.font, Component.literal("Not connected to a multiplayer server"), 
				this.width / 2, this.height / 2, 0xff5555);
		} else {
			int yPos = 40;
			int leftMargin = 20;

			// Protocol Version
			drawInfoLine(guiGraphics, leftMargin, yPos, "Protocol Version:", 
				String.valueOf(ServerInfoCollector.getProtocolVersion()));
			yPos += ENTRY_HEIGHT;

			// Online Players
			drawInfoLine(guiGraphics, leftMargin, yPos, "Online Players:", 
				String.format("%d / %d", ServerInfoCollector.getOnlinePlayerCount(), 
					ServerInfoCollector.getMaxPlayers()));
			yPos += ENTRY_HEIGHT;

			// Client View Distance
			drawInfoLine(guiGraphics, leftMargin, yPos, "View Distance:", 
				String.format("%d chunks", ServerInfoCollector.getClientViewDistance()));
			yPos += ENTRY_HEIGHT;

			// Simulation Distance
			drawInfoLine(guiGraphics, leftMargin, yPos, "Simulation Distance:", 
				String.format("%d chunks", ServerInfoCollector.getSimulationDistance()));
			yPos += ENTRY_HEIGHT;

			// Server Brand
			drawInfoLine(guiGraphics, leftMargin, yPos, "Server Brand:", 
				ServerInfoCollector.getServerBrand());
			yPos += ENTRY_HEIGHT;

			// Game Time
			drawInfoLine(guiGraphics, leftMargin, yPos, "Server Time:", 
				ServerInfoCollector.getGameTimeFormatted());
			yPos += ENTRY_HEIGHT;

			// Environment
			drawInfoLine(guiGraphics, leftMargin, yPos, "Environment:", "Multiplayer Server");
			yPos += ENTRY_HEIGHT;
			
			// Info text
			guiGraphics.drawString(this.font, Component.literal("§7Press ESC to close"), 
				leftMargin, this.height - 50, 0x888888);
			guiGraphics.drawString(this.font, Component.literal("§8(Client-side mod - No server support needed)"), 
				leftMargin, this.height - 40, 0x666666);
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
