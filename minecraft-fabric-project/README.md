# Minecraft 1.21.11 Fabric Mod

This is a Fabric mod project for Minecraft 1.21.11 configured with:
- **Minecraft Version**: 1.21.11
- **Fabric Loader**: 0.16.0+
- **Java Version**: 25
- **Loom Version**: 1.17-SNAPSHOT

## Build the mod

```bash
cd minecraft-fabric-project
./gradlew build
```

The built JAR will be in `build/libs/`.

## Project Structure

- `src/main/` - Server-side mod code
- `src/client/` - Client-side mod code
- `src/main/resources/` - Mod metadata and server mixins
- `src/client/resources/` - Client mixins

## IDE Setup

Follow [Fabric Documentation](https://docs.fabricmc.net/develop/getting-started/creating-a-project#setting-up) for your IDE setup.

## License

This template is available under the CC0 license.
