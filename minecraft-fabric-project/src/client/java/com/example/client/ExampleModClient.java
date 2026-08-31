package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import com.example.client.network.ClientNetworking;

public class ExampleModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Initialize client networking for server info display
		ClientNetworking.init();
	}
}
