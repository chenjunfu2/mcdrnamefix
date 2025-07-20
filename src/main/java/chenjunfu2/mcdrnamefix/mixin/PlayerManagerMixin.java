package chenjunfu2.mcdrnamefix.mixin;

import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.function.Predicate;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin
{
	@ModifyArgs
	(
		method = "broadcast(Lnet/minecraft/network/message/SignedMessage;Ljava/util/function/Predicate;Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/network/message/MessageType$Parameters;)V",
		at = @At
		(
				value = "INVOKE",
				target = "Lnet/minecraft/server/MinecraftServer;logChatMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageType$Parameters;Ljava/lang/String;)V"
		)
	)
	
	private void paramsModify(Args args, SignedMessage message, Predicate<ServerPlayerEntity> shouldSendFiltered, @Nullable ServerPlayerEntity sender, MessageType.Parameters params)
	{
		if(sender != null)
		{
			MessageType.Parameters newParam = MessageType.params(MessageType.CHAT, sender.getWorld().getRegistryManager(), sender.getName());
			args.set(1,newParam);
		}
	}
}