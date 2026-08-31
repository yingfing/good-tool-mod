package com.example.client.mixin.screen;

import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.example.client.screen.ServerInfoScreen;

@Mixin(PauseScreen.class)
public class PauseScreenMixin {
	
	@Inject(method = "init", at = @At("TAIL"))
	private void addServerInfoButton(CallbackInfo ci) {
		PauseScreen screen = (PauseScreen) (Object) this;
		Minecraft mc = Minecraft.getInstance();

		// Only add button if player is on a server (not singleplayer)
		// hasSingleplayerServer() returns true for singleplayer worlds
		// We want the button only on multiplayer servers
		if (!mc.hasSingleplayerServer() && mc.getConnection() != null) {
			// Add "Server Information" button
			int buttonWidth = 204;
			int buttonHeight = 20;
			int spacing = 24;
			
			// Position it below the main pause buttons
			// The default layout has buttons starting at height/4 + 48
			int buttonX = (screen.width / 2) - (buttonWidth / 2);
			int buttonY = (screen.height / 4) + 96 + spacing;

			Button serverInfoButton = Button.builder(Component.literal("Server Information"), (button) -> {
				// Open server info screen - it will auto-request server info on init
				mc.setScreen(new ServerInfoScreen(screen));
			})
			.bounds(buttonX, buttonY, buttonWidth, buttonHeight)
			.build();

			screen.addRenderableWidget(serverInfoButton);
		}
	}
}
