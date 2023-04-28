package snownee.passablefoliage.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.block.Block;
import snownee.passablefoliage.PassableFoliageBlock;

@Mixin(Block.class)
public class BlockMixin implements PassableFoliageBlock {

	private boolean pfoliage$passable;

	@Override
	public void pfoliage$setPassable(boolean passable) {
		pfoliage$passable = passable;
	}

	@Override
	public boolean pfoliage$isPassable() {
		return pfoliage$passable;
	}
}
