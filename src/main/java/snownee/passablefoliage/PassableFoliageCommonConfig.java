package snownee.passablefoliage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.Strictness;

import net.fabricmc.loader.api.FabricLoader;

public final class PassableFoliageCommonConfig {
	private static final Gson GSON = new GsonBuilder()
			.setStrictness(Strictness.STRICT)
			.setPrettyPrinting()
			.disableHtmlEscaping()
			.create();
	private static final long MAX_CONFIG_BYTES = 64 * 1024;
	private static final String CONFIG_FILE = "passablefoliage.json";
	private static final String LEGACY_CONFIG_FILE = "passablefoliage-common.yaml";

	public static float fallDamageMultiplier = 0.5F;
	public static int fallDamageThreshold = 20;
	public static float speedMultiplierHorizontal = 0.9F;
	public static float speedMultiplierVertical = 0.9F;
	public static boolean modifyPathFinding = true;
	public static boolean playerOnly;
	public static boolean alwaysNotViewBlocking = true;
	public static boolean alwaysLeafWalking;
	public static boolean headHitter;
	public static boolean soundsPlayerOnly;
	public static float soundVolume = 1F;
	public static boolean leafWalkerEnabled = true;

	private PassableFoliageCommonConfig() {
	}

	public static synchronized void load() {
		Path configDirectory = FabricLoader.getInstance().getConfigDir();
		Path configPath = configDirectory.resolve(CONFIG_FILE);
		Path legacyPath = configDirectory.resolve(LEGACY_CONFIG_FILE);
		ConfigValues values = new ConfigValues();
		boolean shouldWrite = false;

		try {
			Files.createDirectories(configDirectory);
			if (Files.isRegularFile(configPath)) {
				values = readJson(configPath);
			} else {
				shouldWrite = true;
				if (Files.isRegularFile(legacyPath) && migrateLegacyYaml(legacyPath, values)) {
					PassableFoliage.LOGGER.info("Migrated legacy configuration from {} to {}", legacyPath, configPath);
				}
			}
			apply(values);
		} catch (IOException | JsonParseException | IllegalArgumentException exception) {
			apply(new ConfigValues());
			PassableFoliage.LOGGER.error(
					"Failed to load {}. Safe defaults will be used and the existing file will not be overwritten.",
					configPath,
					exception);
			return;
		}

		if (shouldWrite) {
			try {
				writeJsonAtomically(configPath, snapshot());
			} catch (IOException exception) {
				PassableFoliage.LOGGER.warn(
						"Loaded safe configuration values, but failed to write {}.",
						configPath,
						exception);
			}
		}
	}

	private static ConfigValues readJson(Path path) throws IOException {
		validateSize(path);
		String json = Files.readString(path, StandardCharsets.UTF_8);
		ConfigValues values = GSON.fromJson(json, ConfigValues.class);
		if (values == null) {
			throw new JsonParseException("Configuration root must be a JSON object");
		}
		if (values.sounds == null) {
			values.sounds = new SoundValues();
		}
		return values;
	}

	private static boolean migrateLegacyYaml(Path path, ConfigValues values) throws IOException {
		validateSize(path);
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		String section = "";
		boolean foundKnownValue = false;

		for (String originalLine : lines) {
			String line = originalLine.stripTrailing();
			String trimmed = line.stripLeading();
			if (trimmed.isEmpty() || trimmed.startsWith("#") || trimmed.equals("---")) {
				continue;
			}

			int separator = trimmed.indexOf(':');
			if (separator <= 0) {
				continue;
			}
			String key = trimmed.substring(0, separator).trim();
			String value = trimmed.substring(separator + 1).trim();
			int indentation = line.length() - trimmed.length();

			if (value.isEmpty()) {
				section = indentation == 0 ? key : section + "." + key;
				continue;
			}

			String pathKey = indentation > 0 && !section.isEmpty() ? section + "." + key : key;
			foundKnownValue |= applyLegacyValue(values, pathKey, value);
		}
		return foundKnownValue;
	}

	private static boolean applyLegacyValue(ConfigValues values, String key, String value) {
		try {
			return switch (key) {
				case "fallDamageMultiplier" -> setFloat(value, number -> values.fallDamageMultiplier = number);
				case "fallDamageThreshold" -> setInt(value, number -> values.fallDamageThreshold = number);
				case "speedMultiplierHorizontal" -> setFloat(value, number -> values.speedMultiplierHorizontal = number);
				case "speedMultiplierVertical" -> setFloat(value, number -> values.speedMultiplierVertical = number);
				case "modifyPathFinding" -> setBoolean(value, enabled -> values.modifyPathFinding = enabled);
				case "playerOnly" -> setBoolean(value, enabled -> values.playerOnly = enabled);
				case "alwaysNotViewBlocking" -> setBoolean(value, enabled -> values.alwaysNotViewBlocking = enabled);
				case "alwaysLeafWalking" -> setBoolean(value, enabled -> values.alwaysLeafWalking = enabled);
				case "headHitter" -> setBoolean(value, enabled -> values.headHitter = enabled);
				case "sounds.playerOnly" -> setBoolean(value, enabled -> values.sounds.playerOnly = enabled);
				case "sounds.volume" -> setFloat(value, number -> values.sounds.volume = number);
				case "modules.enchantment" -> setBoolean(value, enabled -> values.leafWalkerEnabled = enabled);
				default -> false;
			};
		} catch (NumberFormatException exception) {
			PassableFoliage.LOGGER.warn("Ignoring invalid legacy configuration value for {}", key);
			return false;
		}
	}

	private static boolean setFloat(String value, FloatSetter setter) {
		setter.set(Float.parseFloat(value));
		return true;
	}

	private static boolean setInt(String value, IntSetter setter) {
		setter.set(Integer.parseInt(value));
		return true;
	}

	private static boolean setBoolean(String value, BooleanSetter setter) {
		if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
			throw new IllegalArgumentException("Expected a boolean value");
		}
		setter.set(Boolean.parseBoolean(value));
		return true;
	}

	private static void apply(ConfigValues values) {
		fallDamageMultiplier = clampFinite(values.fallDamageMultiplier, 0F, 1F, 0.5F);
		fallDamageThreshold = Math.clamp(values.fallDamageThreshold, 5, 255);
		speedMultiplierHorizontal = clampFinite(values.speedMultiplierHorizontal, 0F, 1F, 0.9F);
		speedMultiplierVertical = clampFinite(values.speedMultiplierVertical, 0F, 1F, 0.9F);
		modifyPathFinding = values.modifyPathFinding;
		playerOnly = values.playerOnly;
		alwaysNotViewBlocking = values.alwaysNotViewBlocking;
		alwaysLeafWalking = values.alwaysLeafWalking;
		headHitter = values.headHitter;
		soundsPlayerOnly = values.sounds.playerOnly;
		soundVolume = clampFinite(values.sounds.volume, 0F, 10F, 1F);
		leafWalkerEnabled = values.leafWalkerEnabled;
	}

	private static float clampFinite(float value, float minimum, float maximum, float fallback) {
		return Float.isFinite(value) ? Math.clamp(value, minimum, maximum) : fallback;
	}

	private static ConfigValues snapshot() {
		ConfigValues values = new ConfigValues();
		values.fallDamageMultiplier = fallDamageMultiplier;
		values.fallDamageThreshold = fallDamageThreshold;
		values.speedMultiplierHorizontal = speedMultiplierHorizontal;
		values.speedMultiplierVertical = speedMultiplierVertical;
		values.modifyPathFinding = modifyPathFinding;
		values.playerOnly = playerOnly;
		values.alwaysNotViewBlocking = alwaysNotViewBlocking;
		values.alwaysLeafWalking = alwaysLeafWalking;
		values.headHitter = headHitter;
		values.sounds.playerOnly = soundsPlayerOnly;
		values.sounds.volume = soundVolume;
		values.leafWalkerEnabled = leafWalkerEnabled;
		return values;
	}

	private static void writeJsonAtomically(Path path, ConfigValues values) throws IOException {
		Path temporary = Files.createTempFile(path.getParent(), "passablefoliage-", ".tmp");
		try {
			Files.writeString(temporary, GSON.toJson(values) + System.lineSeparator(), StandardCharsets.UTF_8);
			try {
				Files.move(temporary, path, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
			} catch (AtomicMoveNotSupportedException ignored) {
				Files.move(temporary, path, StandardCopyOption.REPLACE_EXISTING);
			}
		} finally {
			Files.deleteIfExists(temporary);
		}
	}

	private static void validateSize(Path path) throws IOException {
		if (Files.size(path) > MAX_CONFIG_BYTES) {
			throw new IOException("Configuration exceeds the 64 KiB size limit: " + path);
		}
	}

	@FunctionalInterface
	private interface FloatSetter {
		void set(float value);
	}

	@FunctionalInterface
	private interface IntSetter {
		void set(int value);
	}

	@FunctionalInterface
	private interface BooleanSetter {
		void set(boolean value);
	}

	private static final class ConfigValues {
		private float fallDamageMultiplier = 0.5F;
		private int fallDamageThreshold = 20;
		private float speedMultiplierHorizontal = 0.9F;
		private float speedMultiplierVertical = 0.9F;
		private boolean modifyPathFinding = true;
		private boolean playerOnly;
		private boolean alwaysNotViewBlocking = true;
		private boolean alwaysLeafWalking;
		private boolean headHitter;
		private SoundValues sounds = new SoundValues();
		private boolean leafWalkerEnabled = true;
	}

	private static final class SoundValues {
		private boolean playerOnly;
		private float volume = 1F;
	}
}
