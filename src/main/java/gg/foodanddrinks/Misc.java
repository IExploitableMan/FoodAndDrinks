package gg.foodanddrinks;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class Misc {
    public static PotionEffect getEffect(Effect id) {

        if (id == Effect.TOTEM_EXTRACT) {
            return new PotionEffect(MobEffects.HEALTH_BOOST, Integer.MAX_VALUE, 9, false, false);
        }
        return null;
    }

    private static void sandbox() {
    }

    public enum Effect {
        TOTEM_EXTRACT
    }
}
