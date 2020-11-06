package snownee.passablefoliage.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class LeafWalkerEnchantment extends Enchantment {

    public LeafWalkerEnchantment() {
        super(Rarity.COMMON, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[] { EquipmentSlotType.FEET });
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public boolean canGenerateInLoot() {
        return false;
    }
}
