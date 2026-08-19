# Passable Foliage Forked

Mod version: **26.2.0**  
Minecraft Java: **26.2**

## About

Passable Foliage allows players and entities to move through leaves and any other blocks included in the `passablefoliage:passables` tag. Movement through foliage still has configurable effects such as reduced speed, sounds and fall-damage reduction. The mod also adjusts pathfinding and provides the Leaf Walker enchantment.

This project is a fork of [Passable Foliage](https://github.com/Snownee/PassableFoliage), originally created by Snownee and released under the MIT License.

## Requirements

| Component | Version |
| --- | --- |
| Minecraft Java | 26.2 |
| Java | 25 or newer |
| Fabric Loader | 0.19.3 or newer |
| Fabric API | 0.158.0 or newer for Minecraft 26.2 |
| Quilt Loader | Supported through Fabric mod compatibility |
| Environment | Client and server |

The same JAR can be used with Fabric and Quilt. Fabric API is required in both cases.

NeoForge is not included in this source tree. The original project supplied for this update was the Fabric/Quilt branch.

## Features

- Removes collision from leaves and blocks included in the mod tag.
- Can restrict passable foliage to players only.
- Configurable horizontal and vertical movement speed inside foliage.
- Reduces and limits fall damage when landing in leaves.
- Plays configurable impact and movement sounds.
- Allows pathfinding entities to recognize foliage as an open path.
- Includes the Leaf Walker enchantment for walking on top of foliage.
- Includes the `headHitter` option, which preserves collision when approaching foliage from below.
- Hides an entity's name tag only while the entity is fully inside foliage.

## Changes in 26.2.0

- Updated from Minecraft 26.1.2 to Minecraft Java 26.2.
- Updated to Fabric Loader 0.19.3, Fabric API 0.158.0, Loom 1.17.19, Gradle 9.7.0 and Java 25.
- Removed the Kiwi dependency because no Minecraft 26.2-compatible release was available.
- Replaced Kiwi registration and resource conditions with native Fabric and vanilla APIs.
- Replaced the old YAML configuration with `config/passablefoliage.json`.
- Added automatic migration of known values from `config/passablefoliage-common.yaml`. The old file is not deleted.
- Added strict parsing, type and range validation, and a 64 KiB configuration size limit.
- Added atomic configuration writes to reduce the risk of partially written files.
- Fixed the inverted name-tag visibility check.
- Fixed foliage slowdown still being applied while `alwaysLeafWalking` was enabled.
- Preserved loaded configuration values when the configuration file cannot be created.
- Protected temporary collision suppression with `try/finally` blocks.
- Removed an `INFO` log from the entity movement hot path.
- Removed unnecessary access to Minecraft internals and the Access Widener.
- Updated resource formats, datagen and tag APIs for Minecraft 26.2.
- Replaced dynamic build-plugin versions with exact versions.
- Enabled reproducible archives and added checksum verification to the Gradle Wrapper.
- Removed development leftovers and binaries that were not required to build the mod.

## Configuration

The `config/passablefoliage.json` file is created the first time the game starts with the mod installed.

| Option | Default | Range or purpose |
| --- | ---: | --- |
| `fallDamageMultiplier` | `0.5` | From 0 to 1 |
| `fallDamageThreshold` | `20` | From 5 to 255 blocks |
| `speedMultiplierHorizontal` | `0.9` | From 0 to 1 |
| `speedMultiplierVertical` | `0.9` | From 0 to 1 |
| `modifyPathFinding` | `true` | Adjusts entity pathfinding |
| `playerOnly` | `false` | Restricts passable foliage to players |
| `alwaysNotViewBlocking` | `true` | Prevents foliage from blocking vision |
| `alwaysLeafWalking` | `false` | Applies the Leaf Walker effect to all entities |
| `headHitter` | `false` | Preserves collision when approaching from below |
| `sounds.playerOnly` | `false` | Restricts foliage sounds to players |
| `sounds.volume` | `1.0` | From 0 to 10 |
| `leafWalkerEnabled` | `true` | Enables the enchantment and its recipe |

Out-of-range values are clamped. If the file is invalid, the mod keeps it unchanged, reports the problem in the log and uses safe defaults for that session.

## Building

Detailed instructions for Windows 10 and Visual Studio Code are available in [BUILDING_WINDOWS.md](BUILDING_WINDOWS.md).

From PowerShell in the project directory:

```powershell
java -version
.\gradlew.bat clean build
```

The main JAR is written to:

```text
build\libs\PassableFoliage-mc26.2-Fabric-26.2.0.jar
```

Development tasks:

```powershell
.\gradlew.bat runClient
.\gradlew.bat runServer
.\gradlew.bat runDatagen
```

## License

MIT. See [LICENSE](LICENSE).
