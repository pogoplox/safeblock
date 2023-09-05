package turniplabs.examplemod.mixin.mixins;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFire;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import turniplabs.examplemod.NitroBlockMod;
import turniplabs.halplibe.helper.BlockBuilder;

@Mixin(value = EntityLiving.class, remap = false)
public abstract class ExampleMixin extends Entity {
    @Shadow protected String entityType;

    public ExampleMixin(World world) {
        super(world);
    }

    @Redirect(
            method = "moveEntityWithHeading",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/entity/EntityLiving;moveRelative(FFF)V",
                    ordinal = -1
            )
    )
    private void test(EntityLiving instance, float f, float f1, float f2) {
        float speed = f2;

        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && entityType == "humanoid") {
            speed *= 2;
        }

        float f3 = MathHelper.sqrt_float(f * f + f1 * f1);
        if (f3 < 0.01f) {
            return;
        }
        if (f3 < 1.0f) {
            f3 = 1.0f;
        }
        f3 = speed / f3;
        float sinYaw = MathHelper.sin(this.yRot * 3.141593f / 180.0f);
        float cosYaw = MathHelper.cos(this.yRot * 3.141593f / 180.0f);
        this.xd += (double)((f *= f3) * cosYaw - (f1 *= f3) * sinYaw);
        this.zd += (double)(f1 * cosYaw + f * sinYaw);
    }
}