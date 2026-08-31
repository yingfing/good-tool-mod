package com.example.client;

import net.fabricmc.api.ClientModInitializer;

public class ExampleModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This is a pure client-side mod
		// No server-side networking needed
	}
}
