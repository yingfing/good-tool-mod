package com.example.client.config;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class ModConfigScreen extends Screen {
	private final Screen parent;

	public ModConfigScreen(Screen parent) {
		super(Component.literal("Example Mod Config"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		// Add configuration buttons/options here
		this.addRenderableWidget(Button.builder(Component.literal("Done"), (button) -> {
			this.minecraft.setScreen(this.parent);
		}).bounds(this.width / 2 - 100, this.height - 29, 200, 20).build());
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
		guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xffffff);
		guiGraphics.drawString(this.font, Component.literal("Mod Configuration Settings"), 20, 50, 0xcccccc);
		
		super.render(guiGraphics, mouseX, mouseY, partialTick);
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}
}
