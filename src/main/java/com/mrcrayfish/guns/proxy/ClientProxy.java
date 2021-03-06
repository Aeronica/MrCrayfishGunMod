package com.mrcrayfish.guns.proxy;

import com.mrcrayfish.guns.client.KeyBinds;
import com.mrcrayfish.guns.client.event.RenderEvents;
import com.mrcrayfish.guns.client.event.AimHandler;
import com.mrcrayfish.guns.client.render.entity.RenderProjectile;
import com.mrcrayfish.guns.entity.EntityProjectile;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit() 
	{
		super.preInit();

		MinecraftForge.EVENT_BUS.register(new RenderEvents());
		MinecraftForge.EVENT_BUS.register(new AimHandler());
		RenderingRegistry.registerEntityRenderingHandler(EntityProjectile.class, RenderProjectile::new);
		KeyBinds.register();
	}
}
