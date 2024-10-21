package snownee.passablefoliage;

import com.mojang.serialization.MapCodec;

import net.neoforged.neoforge.common.conditions.ICondition;

public class AlwaysLeafWalkingCondition implements ICondition {
	public static final MapCodec<AlwaysLeafWalkingCondition> CODEC = MapCodec.unit(new AlwaysLeafWalkingCondition());

	@Override
	public MapCodec<? extends ICondition> codec() {
		return CODEC;
	}

	@Override
	public boolean test(IContext context) {
		return PassableFoliageCommonConfig.alwaysLeafWalking;
	}
}
