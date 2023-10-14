package tk.imaginescape.colonymigration;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(ColonyMigration.MODID)
public class ColonyMigration {
    public static final String MODID = "colonymigration";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ColonyMigration() {
        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
    }

    public void registerCommands(RegisterCommandsEvent registerEvent) {
        MigrateCommand.register(registerEvent.getDispatcher());
        LOGGER.info("Command registered");
    }
}
