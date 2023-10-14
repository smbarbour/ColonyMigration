package tk.imaginescape.colonymigration;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class MigrateCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("migratecolony")
                        .then(
                                Commands.argument("colony", IntegerArgumentType.integer(1))
                                        .executes(command -> {
                                            return updateItem(command.getSource(), IntegerArgumentType.getInteger(command, "colony"));
                                        })));
    }

    private static int updateItem(CommandSourceStack source, int colony) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        ItemStack inHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (!inHand.isEmpty() && inHand.hasTag()) {
            CompoundTag tag = inHand.getTag();
            if (tag.contains("colony")) {
                tag.put("colony", IntTag.valueOf(colony));
                inHand.setTag(tag);
                source.sendSuccess(Component.literal("Updated colony"), false);
                return 1;
            }
        }
        source.sendFailure(Component.literal("Item is not linked to a colony!"));
        return 0;
    }
}
