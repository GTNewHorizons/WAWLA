package net.darkhax.wawla;

import java.util.Arrays;

import net.darkhax.wawla.commands.CommandPrint;
import net.darkhax.wawla.handler.IMCHandler;
import net.darkhax.wawla.proxy.ProxyCommon;
import net.darkhax.wawla.util.Config;
import net.darkhax.wawla.util.Reference;
import net.minecraft.command.ServerCommandManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Reference.MODID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:Waila")
public class Wawla {
    
    @SidedProxy(serverSide = Reference.SERVER, clientSide = Reference.CLIENT)
    public static ProxyCommon proxy;
    
    @Mod.Instance(Reference.MODID)
    public static Wawla instance;
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
    
        setModMeta(event.getModMetadata());
        new Config(event.getSuggestedConfigurationFile());
        proxy.registerSidedEvents();
        proxy.registerSidedModules();
        proxy.registerSidedPlugins();
    }
    
    @EventHandler
    public void messageRecieved (FMLInterModComms.IMCEvent event) {
    
        for (IMCMessage message : event.getMessages())
            IMCHandler.readMeassage(message);
    }
    
    @EventHandler
    public void onServerStarting (FMLServerStartingEvent event) {
    
        ServerCommandManager manager = (ServerCommandManager) event.getServer().getCommandManager();
        manager.registerCommand(new CommandPrint());
    }
    
    /**
     * Method to set information about the mod. This is an in game alternative to the mcmod.info file and is primarily used by the list of mods in the main menu added by Forge Mod Loader.
     * 
     * @param meta: The ModMetadata for the mod. This can be obtained from the preInit event.
     */
    void setModMeta (ModMetadata meta) {
    
        meta.authorList = Arrays.asList("Darkhax", "Lclc98");
        meta.credits = "Coded by Darkhax and Lclc98, based on prior work done by ChickenBones and ProfMobius";
        meta.description = "This mod aims to add more ingame documentation for both vanilla and a large variety of mods.";
        meta.autogenerated = false;
    }
}