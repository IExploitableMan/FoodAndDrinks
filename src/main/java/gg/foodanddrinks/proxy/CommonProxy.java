package gg.foodanddrinks.proxy;

import gg.foodanddrinks.FoodAndDrinks;
import gg.foodanddrinks.Misc;
import gg.foodanddrinks.custom.item.ItemCocoa;
import lib.___exploit___.ModRegistry;
import lib.___exploit___.item.ItemContainedProduct;
import lib.___exploit___.item.ItemContainedProduct.Type;
import lib.___exploit___.item.ItemCustomPotion;
import net.minecraft.block.Block;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import static net.minecraft.block.Block.spawnAsEntity;

@Mod.EventBusSubscriber
public class CommonProxy {
    //Fields
    public static ModRegistry modreg;

    public static class ObjectHolders {
        @GameRegistry.ObjectHolder("foodanddrinks:cherry")
        public static ItemFood cherry;
        @GameRegistry.ObjectHolder("foodanddrinks:fried_eggs")
        public static ItemContainedProduct friedEggs;
        @GameRegistry.ObjectHolder("foodanddrinks:pear")
        public static ItemFood pear;
        @GameRegistry.ObjectHolder("foodanddrinks:uncooked_fried_eggs")
        public static ItemContainedProduct uncookedFriedEggs;
    }

    //Registry
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> eventRegistry = event.getRegistry();

        eventRegistry.registerAll(
                //Default
        );
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> eventRegistry = event.getRegistry();

        for (Item i : modreg.getAutoItemBlock()) {
            eventRegistry.register(i);
        }

        eventRegistry.registerAll(
                //Patch
                new ItemFood(2, 0.1F, false).setCreativeTab(CreativeTabs.MATERIALS).setRegistryName("minecraft:sugar").setUnlocalizedName("sugar"),

                //Default
                modreg.registerItem("apple_juice", new ItemContainedProduct(Type.DRINK, 7, 0.3F), true),
                modreg.registerItem("carrot_salad", new ItemContainedProduct(Type.FOOD, 11, 0.6F), true),
                modreg.registerItem("cherry", new ItemFood(2, 0.3F, false), true),
                modreg.registerItem("cherry_juice", new ItemContainedProduct(Type.DRINK, 5, 0.3F), true),
                modreg.registerItem("chocolate_bar", new ItemContainedProduct(Type.FOOD, 9, 0.6F).setContainer(Items.PAPER), true),
                modreg.registerItem("cocoa", new ItemCocoa(), true),
                modreg.registerItem("fried_eggs", new ItemContainedProduct(Type.FOOD, 5, 0.4F).setNoCraftContainer(), true),
                modreg.registerItem("fried_eggs_with_bacon", new ItemContainedProduct(Type.FOOD, 14, 0.8F), true),
                modreg.registerItem("fruit_salad", new ItemContainedProduct(Type.FOOD, 9, 0.3F), true),
                modreg.registerItem("golden_carrot_salad", new ItemContainedProduct(Type.FOOD, 14, 1.2F), true),
                modreg.registerItem("melon_juice", new ItemContainedProduct(Type.DRINK, 5, 0.3F), true),
                modreg.registerItem("pear", new ItemFood(3, 0.3F, false), true),
                modreg.registerItem("pear_juice", new ItemContainedProduct(Type.DRINK, 6, 0.3F), true),
                modreg.registerItem("sunflower_oil", new ItemContainedProduct(Type.DRINK).setBadFood(), true),
                modreg.registerItem("totem_of_undying_extract", new ItemCustomPotion(Misc.getEffect(Misc.Effect.TOTEM_EXTRACT)), true),
                modreg.registerItem("uncooked_fried_eggs", new ItemContainedProduct(Type.FOOD).setBadFood(), true)
        );
    }

    //Events
    @SubscribeEvent
    public static void harvestDropsEvent(BlockEvent.HarvestDropsEvent event) {
        IBlockState state = event.getState();
        Block block = state.getBlock();

        boolean leafRandom = event.getWorld().rand.nextInt(200) == 0;

        if (block == Blocks.LEAVES && leafRandom) {
            BlockPlanks.EnumType type = state.getValue(BlockOldLeaf.VARIANT);
            if (type == BlockPlanks.EnumType.BIRCH) {
                spawnAsEntity(event.getWorld(), event.getPos(), new ItemStack(ObjectHolders.cherry));
            }
        }
        if (block == Blocks.LEAVES2 && leafRandom) {
            BlockPlanks.EnumType type = state.getValue(BlockNewLeaf.VARIANT);
            if (type == BlockPlanks.EnumType.DARK_OAK) {
                spawnAsEntity(event.getWorld(), event.getPos(), new ItemStack(ObjectHolders.pear));
            }
        }
    }

    //Init
    public void preInit(FMLPreInitializationEvent event) {
        CreativeTabs tab = new CreativeTabs(FoodAndDrinks.ID) {
            @Override
            public ItemStack getTabIconItem() {
                return new ItemStack(ObjectHolders.pear);
            }
        };
        modreg = new ModRegistry(FoodAndDrinks.ID, tab);
    }

    public void init(FMLInitializationEvent event) {
        GameRegistry.addSmelting(ObjectHolders.uncookedFriedEggs, new ItemStack(ObjectHolders.friedEggs), 1);
    }

    public void postInit(FMLPostInitializationEvent event) {
    }
}
