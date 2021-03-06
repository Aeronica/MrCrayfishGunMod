package com.mrcrayfish.guns.network.message;

import com.mrcrayfish.guns.event.CommonEvents;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageAim implements IMessage, IMessageHandler<MessageAim, IMessage>
{
	private boolean aiming;

	public MessageAim()
	{
	}

	public MessageAim(boolean aiming)
	{
		this.aiming = aiming;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(aiming);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.aiming = buf.readBoolean();
	}

	@Override
	public IMessage onMessage(MessageAim message, MessageContext ctx)
	{
		EntityPlayer player = ctx.getServerHandler().player;
		player.getDataManager().set(CommonEvents.AIMING, message.aiming);
		return null;
	}
}
