package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import snownee.passablefoliage.CoreModule;
import snownee.passablefoliage.PassableFoliageBlock;

@Mixin(value = Blocks.class, priority = 500)
public class BlocksMixin {

	@Unique
	private static boolean err;

	@Inject(method = "rebuildCache", at = @At("HEAD"))
	private static void pfoliage_rebuildCache(CallbackInfo ci) {
		for (Block block : BuiltInRegistries.BLOCK) {
			try {
				((PassableFoliageBlock) block).pfoliage$setPassable(block.builtInRegistryHolder().is(CoreModule.PASSABLES));
			} catch (Throwable e) {
				if (!err) {
					System.err.println(e);
					err = true;
				}
				((PassableFoliageBlock) block).pfoliage$setPassable(false);
			}
		}
	}

}
