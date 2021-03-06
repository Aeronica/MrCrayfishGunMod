package com.mrcrayfish.guns.object;

import com.google.gson.annotations.SerializedName;
import com.mrcrayfish.guns.client.render.HeldAnimation;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Author: MrCrayfish
 */
public enum GripType
{
    @SerializedName("one_handed")
    ONE_HANDED(new HeldAnimation()
    {
        @Override
        public void applyPlayerModelRotation(ModelPlayer model, float aimProgress)
        {
            copyModelAngles(model.bipedHead, model.bipedRightArm);
            model.bipedRightArm.rotateAngleX += Math.toRadians(-70F);
        }
    }, true),
    @SerializedName("two_handed")
    TWO_HANDED(new HeldAnimation()
    {
        @Override
        public void applyPlayerModelRotation(ModelPlayer model, float aimProgress)
        {
            copyModelAngles(model.bipedHead, model.bipedRightArm);
            copyModelAngles(model.bipedHead, model.bipedLeftArm);
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-55F + aimProgress * -30F);
            model.bipedRightArm.rotateAngleY = (float) Math.toRadians(-45F + aimProgress * -20F);
            model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-42F + aimProgress * -48F);
            model.bipedLeftArm.rotateAngleY = (float) Math.toRadians(-15F + aimProgress * 5F);
        }

        @Override
        public void applyPlayerPreRender(EntityPlayer player, float aimProgress)
        {
            player.prevRenderYawOffset = player.prevRotationYaw + 25F + aimProgress * 20F;
            player.renderYawOffset = player.rotationYaw + 25F + aimProgress * 20F;
        }

        @Override
        public void applyHeldItemTransforms(float aimProgress)
        {
            GlStateManager.translate(0, 0, 0.05);
            float invertRealProgress = 1.0F - aimProgress;
            GlStateManager.rotate(25F * invertRealProgress, 0, 0, 1);
            GlStateManager.rotate(30F * invertRealProgress + aimProgress * -20F, 0, 1, 0);
            GlStateManager.rotate(25F * invertRealProgress + aimProgress * 5F, 1, 0, 0);
        }
    }, false),
    @SerializedName("chain_gun")
    CHAIN_GUN(new HeldAnimation()
    {
        @Override
        public void applyPlayerModelRotation(ModelPlayer model, float aimProgress)
        {
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-15F);
            model.bipedRightArm.rotateAngleY = (float) Math.toRadians(-45F);
            model.bipedRightArm.rotateAngleZ = (float) Math.toRadians(0F);

            model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-60F);
            model.bipedLeftArm.rotateAngleY = (float) Math.toRadians(15F);
            model.bipedLeftArm.rotateAngleZ = (float) Math.toRadians(0F);
        }

        @Override
        public void applyPlayerPreRender(EntityPlayer player, float aimProgress)
        {
            player.prevRenderYawOffset = player.prevRotationYaw + 45F;
            player.renderYawOffset = player.rotationYaw + 45F;
        }
    }, false);

    private final HeldAnimation heldAnimation;
    private final boolean renderOffhand;

    GripType(HeldAnimation heldAnimation, boolean renderOffhand)
    {
        this.heldAnimation = heldAnimation;
        this.renderOffhand = renderOffhand;
    }

    public HeldAnimation getHeldAnimation()
    {
        return heldAnimation;
    }

    public boolean canRenderOffhand()
    {
        return renderOffhand;
    }

    @SideOnly(Side.CLIENT)
    private static void copyModelAngles(ModelRenderer source, ModelRenderer dest)
    {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
    }
}
