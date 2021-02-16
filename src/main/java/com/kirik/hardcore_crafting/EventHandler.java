package com.kirik.hardcore_crafting;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent e) {
        double random = Math.random();
        System.out.println(random);
        if (random < ConfigHandler.chance) {
            if (!e.player.world.isRemote) {
                for (int i = 0; i < e.crafting.getCount(); i++) {
                    EntityHusk husk = new EntityHusk(e.player.world);
                    husk.posX = e.player.posX;
                    husk.posY = e.player.posY;
                    husk.posZ = e.player.posZ;
                    husk.setChild(true);
                    husk.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
                    husk.addPotionEffect(new PotionEffect(MobEffects.SPEED, Integer.MAX_VALUE, 1, false, false));
                    husk.setAttackTarget(e.player);
                    husk.setHealth(2.5F);
                    husk.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(e.crafting.getItem(),1,e.crafting.getMetadata()));
                    husk.setHeldItem(EnumHand.OFF_HAND, new ItemStack(e.crafting.getItem(), 1,e.crafting.getMetadata()));
                    husk.setCustomNameTag(e.crafting.getDisplayName());
                    husk.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(e.crafting.getItem(), 1, e.crafting.getMetadata()));
                    husk.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(e.crafting.getItem(), 1, e.crafting.getMetadata()));
                    husk.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(e.crafting.getItem(),1,e.crafting.getMetadata()));
                    husk.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(e.crafting.getItem(),1,e.crafting.getMetadata()));
                    husk.setSilent(true);
                    husk.setDropItemsWhenDead(false);
                    husk.getEntityData().setBoolean("LIVING_ITEM", true);
                    e.player.world.spawnEntity(husk);
                }
                e.player.inventory.clearMatchingItems(e.crafting.getItem(), e.crafting.getMetadata(), e.crafting.getCount(), null);
            }
        }
    }
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        if (entity instanceof EntityHusk) {
            if (entity.getEntityData().getBoolean("LIVING_ITEM")) {
                EntityItem item = new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ);
                item.setItem(new ItemStack(entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem(), 1, entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getMetadata()));
                entity.world.spawnEntity(item);
                e.setCanceled(true);
            }
        }
    }
}
