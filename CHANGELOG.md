# Changelog

## 26.2.0

- Ported the mod to Minecraft Java 26.2 and Java 25.
- Updated Fabric Loader, Fabric API, Loom and Gradle.
- Removed the Kiwi dependency and replaced its configuration, registration and resource-condition features with native implementations.
- Added migration from the old YAML configuration to JSON.
- Fixed the inverted name-tag visibility check.
- Fixed foliage slowdown while `alwaysLeafWalking` was enabled.
- Preserved loaded values when the configuration file cannot be created.
- Ensured temporary collision state is restored if an intercepted call throws an exception.
- Removed excessive logging from entity movement processing.
- Updated datagen, tags and resource formats for Minecraft 26.2.
- Removed unnecessary binaries and development files from the source package.
- Pinned dependency versions and added checksum verification for the Gradle distribution.
- Preserved Fabric and Quilt support on both client and server.
