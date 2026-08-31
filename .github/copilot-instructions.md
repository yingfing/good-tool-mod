# AI Coding Agent Instructions for Fabric Mod Development

This is a Minecraft Fabric mod project targeting Minecraft 26.2 with Java 25. These instructions help AI agents understand the codebase structure and conventions.

## Project Overview

- **Type**: Minecraft Fabric modding framework
- **Target Minecraft**: 26.2
- **Java Version**: 25
- **Build Tool**: Gradle with Fabric Loom plugin
- **Licensing**: CC0-1.0

## Directory Structure

- `src/main/` - Server-side mod code
  - `java/com/example/` - Main mod entry point and server logic
  - `resources/` - Configuration and assets
    - `fabric.mod.json` - Mod metadata (id, version, entrypoints, dependencies)
    - `modid.mixins.json` - Server-side mixin configuration
    - `assets/modid/` - Asset files (textures, sounds, etc.)

- `src/client/` - Client-side mod code
  - `java/com/example/client/` - Client-specific logic and rendering
  - `resources/modid.client.mixins.json` - Client-side mixin configuration

- `src/main/java/com/example/mixin/` - Server-side bytecode modifications
- `src/client/java/com/example/client/mixin/` - Client-side bytecode modifications

## Build & Development

**Build the mod**:
```bash
./gradlew build
```
Outputs JAR to `build/libs/`

**IDE Setup**:
Follow [Fabric Documentation](https://docs.fabricmc.net/develop/getting-started/creating-a-project#setting-up) for your IDE (IntelliJ IDEA, Eclipse, etc.)

**Gradle Configuration**:
- Modify versions in `gradle.properties` (not gradle.properties files in subdirectories)
- `minecraft_version=26.2`
- `loader_version=0.19.3`
- `loom_version=1.17-SNAPSHOT`

## Key Concepts

### Mod Entrypoints
Defined in `fabric.mod.json`:
- `main` - Server-side initialization (implements `ModInitializer`)
- `client` - Client-side initialization (implements `ClientModInitializer`)

### Mixins & Bytecode Modification
- Used for injecting code into Minecraft classes without replacing them
- Defined in `modid.mixins.json` (server) and `modid.client.mixins.json` (client)
- Library: SpongePowered ASM (org.spongepowered.asm.mixin)
- Common decorators:
  - `@Mixin(TargetClass.class)` - Mark class as mixin for a target
  - `@Inject` - Inject code at a specific point in a method
  - `@At` - Specify injection point (e.g., "HEAD", "RETURN")

### Split Environment Source Sets
The mod uses Loom's `splitEnvironmentSourceSets()` to separate client and server code:
- Server code runs on both client and dedicated servers
- Client code only on the client
- Prevents class loading errors from client-only code on servers

## Conventions

1. **Mod ID**: Use `"modid"` consistently (defined in fabric.mod.json and mixin JSON files)
2. **Package Structure**: `com.example.*` for main code, `com.example.client.*` for client code, `com.example.mixin.*` for mixins
3. **Logger**: Use `LoggerFactory.getLogger(MOD_ID)` for logging (see ExampleMod.java)
4. **Identifiers**: Use `ExampleMod.id(String path)` utility to create namespaced identifiers
5. **Java Version**: Ensure compatibility with Java 25 when writing code

## CI/CD

GitHub Actions workflow (`.github/workflows/build.yml`):
- Runs on every push and pull request
- Validates Gradle wrapper
- Builds with JDK 25 on Ubuntu
- Uploads build artifacts

## Dependencies

Core dependencies (in `build.gradle`):
- `net.fabricmc:fabric-loader` - Mod loader
- `net.fabricmc.fabric-api:fabric-api` - Fabric API for common utilities
- `com.mojang:minecraft` - Minecraft source code (mapped by Loom)

## Common Tasks

**Adding a new block/item**: 
1. Create registries in the main mod class
2. Register in mod initialization
3. Create client-side rendering in client initializer if needed

**Adding event listeners**:
1. Use Fabric API events (e.g., `ServerLifecycleEvents`, `ClientTickEvents`)
2. Register in mod/client initializers

**Modifying Minecraft behavior**:
1. Create a mixin class targeting the Minecraft class/method
2. Use `@Inject` with appropriate `@At` point
3. Register in mixins JSON config
4. Test on both client and server

## Resources

- [Fabric Documentation](https://docs.fabricmc.net/)
- [Mixin Documentation](https://github.com/SpongePowered/Mixin/wiki)
- [Fabric API Javadocs](https://maven.fabricmc.net/docs/fabric-api/)
- Example mod: This repository serves as a template
