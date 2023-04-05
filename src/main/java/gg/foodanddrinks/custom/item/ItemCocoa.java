package gg.foodanddrinks.custom.item;

import lib.___exploit___.item.ItemContainedProduct;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemCocoa extends ItemContainedProduct {
    public ItemCocoa() {
        super(ItemContainedProduct.Type.DRINK, 7, 0.6F);
        setAlwaysUsable();
    }

    @Override
    public void onUsed(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        entityLiving.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
    }
}
