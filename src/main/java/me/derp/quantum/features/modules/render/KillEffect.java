package me.derp.quantum.features.modules.render;

import java.util.ArrayList;
import me.derp.quantum.features.modules.Module;
import me.derp.quantum.features.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class KillEffect
extends Module {
    public /* synthetic */ Setting<Integer> timeActive;
    public /* synthetic */ Setting<Boolean> totemPop;
    public /* synthetic */ Setting<Boolean> mobs;
    public /* synthetic */ Setting<Boolean> sound;
    public /* synthetic */ Setting<Boolean> fire;
    public /* synthetic */ Setting<Boolean> water;
    public /* synthetic */ Setting<Boolean> firework;
    public /* synthetic */ Setting<Boolean> totemPopSound;
    /* synthetic */ ArrayList<EntityPlayer> playersDead;
    public /* synthetic */ Setting<Integer> numberSound;
    public /* synthetic */ Setting<Boolean> all;
    public /* synthetic */ Setting<Boolean> lightning;
    public /* synthetic */ Setting<Boolean> thunder;
    public /* synthetic */ Setting<Integer> numbersThunder;
    final /* synthetic */ Object sync;
    public /* synthetic */ Setting<Boolean> smoke;
    public /* synthetic */ Setting<Boolean> players;
    public /* synthetic */ Setting<Boolean> animals;
    public /* synthetic */ Setting<Boolean> LOLBADD;

    @Override
    public void onEnable() {
        this.playersDead.clear();
    }

    @Override
    public void onUpdate() {
        if (KillEffect.mc.world == null) {
            this.playersDead.clear();
            return;
        }
        KillEffect.mc.world.playerEntities.forEach(entityPlayer -> {
            if (this.playersDead.contains(entityPlayer)) {
                if (entityPlayer.getHealth() > 0.0f) {
                    this.playersDead.remove(entityPlayer);
                }
            } else if (entityPlayer.getHealth() == 0.0f) {
                int n;
                if (this.thunder.getValue().booleanValue()) {
                    for (n = 0; n < this.numbersThunder.getValue(); ++n) {
                        KillEffect.mc.world.spawnEntity((Entity)new EntityLightningBolt((World)KillEffect.mc.world, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, true));
                    }
                }
                if (this.sound.getValue().booleanValue()) {
                    for (n = 0; n < this.numberSound.getValue(); ++n) {
                        KillEffect.mc.player.playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 0.5f, 1.0f);
                    }
                }
                this.playersDead.add((EntityPlayer)entityPlayer);
            }
        });
    }

    public KillEffect() {
        super("KillEffect", "When you kill something it spawns shit.", Module.Category.RENDER, true, false, false);
        this.thunder = this.register(new Setting<Boolean>("Thunder", true));
        this.numbersThunder = this.register(new Setting<Integer>("Number Thunder", 1, 1, 10));
        this.sound = this.register(new Setting<Boolean>("Sound", true));
        this.numberSound = this.register(new Setting<Integer>("Number Sound", 1, 1, 10));
        this.LOLBADD = this.register(new Setting<Boolean>("_______________________________", false));
        this.timeActive = this.register(new Setting<Integer>("TimeActive", 20, 0, 50));
        this.lightning = this.register(new Setting<Boolean>("lightning", true));
        this.totemPop = this.register(new Setting<Boolean>("TotemPop", true));
        this.totemPopSound = this.register(new Setting<Boolean>("TotemPopSound", false));
        this.firework = this.register(new Setting<Boolean>("FireWork", false));
        this.fire = this.register(new Setting<Boolean>("Fire", false));
        this.water = this.register(new Setting<Boolean>("Water", false));
        this.smoke = this.register(new Setting<Boolean>("Smoke", false));
        this.players = this.register(new Setting<Boolean>("Players", true));
        this.animals = this.register(new Setting<Boolean>("Animals", true));
        this.mobs = this.register(new Setting<Boolean>("Mobs", true));
        this.all = this.register(new Setting<Boolean>("All", true));
        this.playersDead = new ArrayList();
        this.sync = new Object();
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent livingDeathEvent) {
        if (livingDeathEvent.getEntity() == KillEffect.mc.player) {
            return;
        }
        if (this.shouldRenderParticle(livingDeathEvent.getEntity())) {
            if (this.lightning.getValue().booleanValue()) {
                KillEffect.mc.world.addEntityToWorld(-999, (Entity)new EntityLightningBolt((World)KillEffect.mc.world, livingDeathEvent.getEntity().posX, livingDeathEvent.getEntity().posY, livingDeathEvent.getEntity().posZ, true));
            }
            if (this.totemPop.getValue().booleanValue()) {
                this.totemPop(livingDeathEvent.getEntity());
            }
            if (this.firework.getValue().booleanValue()) {
                KillEffect.mc.effectRenderer.emitParticleAtEntity(livingDeathEvent.getEntity(), EnumParticleTypes.FIREWORKS_SPARK, this.timeActive.getValue().intValue());
            }
            if (this.fire.getValue().booleanValue()) {
                KillEffect.mc.effectRenderer.emitParticleAtEntity(livingDeathEvent.getEntity(), EnumParticleTypes.FLAME, this.timeActive.getValue().intValue());
                KillEffect.mc.effectRenderer.emitParticleAtEntity(livingDeathEvent.getEntity(), EnumParticleTypes.DRIP_LAVA, this.timeActive.getValue().intValue());
            }
            if (this.water.getValue().booleanValue()) {
                KillEffect.mc.effectRenderer.emitParticleAtEntity(livingDeathEvent.getEntity(), EnumParticleTypes.WATER_BUBBLE, this.timeActive.getValue().intValue());
                KillEffect.mc.effectRenderer.emitParticleAtEntity(livingDeathEvent.getEntity(), EnumParticleTypes.WATER_DROP, this.timeActive.getValue().intValue());
            }
            if (this.smoke.getValue().booleanValue()) {
                KillEffect.mc.effectRenderer.emitParticleAtEntity(livingDeathEvent.getEntity(), EnumParticleTypes.SMOKE_NORMAL, this.timeActive.getValue().intValue());
            }
        }
    }

    public boolean shouldRenderParticle(Entity entity) {
        return entity != KillEffect.mc.player && (this.all.getValue() != false || entity instanceof EntityPlayer && this.players.getValue() != false || entity instanceof EntityMob || entity instanceof EntitySlime && this.mobs.getValue() != false || entity instanceof EntityAnimal && this.animals.getValue() != false);
    }

    public void totemPop(Entity entity) {
        KillEffect.mc.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.TOTEM, this.timeActive.getValue().intValue());
        if (this.totemPopSound.getValue().booleanValue()) {
            KillEffect.mc.world.playSound(entity.posX, entity.posY, entity.posZ, SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0f, 1.0f, false);
        }
    }
}

