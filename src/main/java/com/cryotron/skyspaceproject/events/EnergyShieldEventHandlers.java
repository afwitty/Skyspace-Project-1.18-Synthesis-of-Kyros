package com.cryotron.skyspaceproject.events;

import java.util.List;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.capabilities.CapabilityList;
import com.cryotron.skyspaceproject.capabilities.energyshield.IEnergyShieldCapability;
import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredAttributes;
import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredEntities;
import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredItems;
import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredParticles;
import com.cryotron.skyspaceproject.items.armor.SkyspaceEnergyShieldItem;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagContainer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = Skyspace.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class EnergyShieldEventHandlers {
	
	//private static int rechargeTime = 0;
	private static float ENERGY_SHIELD_VALUE ;
	private static float ENERGY_SHIELD_RECHARGE_TIMER ;
	
	static float currES, maxES, rechargeCounter, rechargeTime; //Energy Recharge
	
	private static int delayPeriod = 140;
	private static int COUNTER = 0; // 40 Server Ticks = 1 Second
	
    static Random rand = new Random();
    
    /*
     * EquipEnergyShieldArmorEvent - Fires this event whenever a living entity changes their equipment.
     * 		 While any living entity can do this, this event is fired from players most. Mainly firing from any armor that uses Energy Shield to add Maximum Energy Shield Modifier.
     */
    
	@SubscribeEvent
	public static void EquipEnergyShieldArmorEvent (LivingEquipmentChangeEvent event) {
		
		ItemStack equipped = event.getTo();
		ItemStack unequipped = event.getFrom();

		EquipmentSlot slot = event.getSlot();
		LivingEntity le = event.getEntityLiving();
		UUID uuid = UUID.fromString("934cc13f-d185-40ef-9440-b2b2cbbcdee5");
		event.getSlot().getIndex();
		
		//Skyspace.LOGGER.info("Slot Index Fired: " + event.getSlot().getIndex());
		
		SkyspaceEnergyShieldItem ssESitem;
		AttributeModifier am;

		if  ( unequipped.getItem() instanceof SkyspaceEnergyShieldItem && ( event.getSlot().getIndex() != 0 )) {
			if (unequipped.getItem() == RegisteredItems.ENERGY_SHIELD_TEST_ARMOR.get()) {			
				Skyspace.LOGGER.info("Energy Shield Test Armor Unequipped! Unequipped Item: " + unequipped.getItem());
				le.getAttribute(RegisteredAttributes.MAX_ENERGY_SHIELD.get()).removeModifier(uuid);
			}
		}
		
		if (equipped.getItem() instanceof SkyspaceEnergyShieldItem && ( event.getSlot().getIndex() != 0 ) ) {
				ssESitem = (SkyspaceEnergyShieldItem) equipped.getItem();
				am = new AttributeModifier(uuid, "energy_shield_modifier", ssESitem.getEnergyShield(), Operation.ADDITION);
				if (equipped.getItem() == RegisteredItems.ENERGY_SHIELD_TEST_ARMOR.get()) {	
					Skyspace.LOGGER.info("Energy Shield Test Armor Equipped! Equipped Item: " + equipped.getItem());
					if ( !(le.getAttribute(RegisteredAttributes.MAX_ENERGY_SHIELD.get()).hasModifier(am))) {
						le.getAttribute(RegisteredAttributes.MAX_ENERGY_SHIELD.get()).addPermanentModifier(am);
					}
				}
			
		}

	}
	
	/*
	 * TooltipEnergyShieldArmorEvent - Fire this event whenever a player hovers their cursor over a specific item, namely any items that grants Energy Shield instead of armor.
	 */
	
  @SubscribeEvent
  public static void TooltipEnergyShieldArmorEvent ( ItemTooltipEvent event ) {

	  ItemStack item = event.getItemStack();
	  TooltipFlag pIsAdvanced = event.getFlags();
	  List<Component> list = event.getToolTip();
	  Player pPlayer = event.getPlayer();
	  EquipmentSlot slot = item.getEquipmentSlot();
	  
	  if ( item.getItem() == RegisteredItems.ENERGY_SHIELD_TEST_ARMOR.get() && (item.getItem() instanceof SkyspaceEnergyShieldItem)) {
		  SkyspaceEnergyShieldItem ssESitem = (SkyspaceEnergyShieldItem) item.getItem();
          list.add(new TextComponent("+" + ssESitem.getEnergyShield() + " Energy Shield").withStyle(ChatFormatting.DARK_AQUA));
	  }
  }
  
  /*
   * RechargeEnergyShieldEvent - Fires this event each (approximate) second and counts how long a living entity has not taken damage for X seconds.
   * 		Afterwards, recover their Energy Shield at a rate of Y Energy Shield per second until either the living entity takes damage or have their Current Energy Shield is greater or equal to their Maximum Energy Shield. 
   * 		X = 4 for Players; 7 for Monsters. Y = Maximum Energy Shield / 3 for Players; Maximum Energy Shield / 7 for Monsters.
   * 		X value can be modified by "Faster start of Energy Shield recharge" or in this case ENERGY_SHIELD_RECHARGE attribute. 
   */

  @SubscribeEvent
  public static void RechargeEnergyShieldEvent ( LivingUpdateEvent event ) {

	  LivingEntity entity = event.getEntityLiving();
	  LazyOptional<IEnergyShieldCapability> es = entity.getCapability(CapabilityList.ENERGY_SHIELD);		
	  	
		//Skyspace.LOGGER.info("=====TICK INFO=====");
		//Skyspace.LOGGER.info("Entity: " + entity.getName().toString());
	  
	  // Set Currents
		es.ifPresent(cap -> {
			currES = cap.getEnergyShield(); // Get current Energy Shield value from an entity.
			rechargeCounter = cap.getEnergyShieldRechargeTimer(); // Get Energy Shield Recharge Timer from an entity..
		});
		
		// Set Max
		if (entity.getAttributes().hasAttribute(RegisteredAttributes.MAX_ENERGY_SHIELD.get())) {
			maxES = (float) entity.getAttributeValue(RegisteredAttributes.MAX_ENERGY_SHIELD.get());	// Get Maximum Energy Shield value from an entity. 
		}
		
		if (entity.getAttributes().hasAttribute(RegisteredAttributes.ENERGY_SHIELD_RECHARGE.get())) {
			rechargeTime = (float) entity.getAttributeValue(RegisteredAttributes.ENERGY_SHIELD_RECHARGE.get()); 
		}

		if ( rechargeCounter < rechargeTime ) {
			rechargeCounter =  rechargeCounter + (1/20.0f);
			es.ifPresent(cap -> {
				cap.setEnergyShieldRechargeTimer(rechargeCounter); // Get Energy Shield Recharge Timer from an entity..
			});
		}
		
		if (rechargeCounter >= rechargeTime) {
			currES += (maxES)/60;
			if (currES >= maxES) {
				currES = maxES;
			}
			es.ifPresent(cap -> {
				cap.setEnergyShield(currES);
			});
		}
  }
  
	/*
	 * EnergyShieldFunctionEvent - Fires this event whenever a living entity takes damage with Energy Shield. This is the Energy Shield's Main Function.
	 */
	
	@SubscribeEvent
	public static void EnergyShieldFunctionEvent(LivingDamageEvent event)  {
		LivingEntity entity = event.getEntityLiving();		
		float dammie = event.getAmount();
		Level worldin = event.getEntity().level;
		ServerLevel sworldin = (ServerLevel) event.getEntity().level;
		int randNum = rand.nextInt(2);
		BlockPos blockpos = event.getEntity().blockPosition();
		String sourcestring = event.getSource().toString();
		double px = blockpos.getX() + rand.nextFloat();
		double py = blockpos.getY() + rand.nextFloat() + 1;
		double pz = blockpos.getZ() + rand.nextFloat();
		double vx = (rand.nextFloat() - 0.5) / 2.;
		double vy = (rand.nextFloat() - 0.5) / 2.;
		double vz = (rand.nextFloat() - 0.5) / 2.;
//		DamageSource source = new DamageSource("EntityDamage");
		LazyOptional<IEnergyShieldCapability> es = entity.getCapability(CapabilityList.ENERGY_SHIELD);		
				
		es.ifPresent(cap -> {
			ENERGY_SHIELD_VALUE = cap.getEnergyShield();
			cap.setEnergyShieldRechargeTimer(0);
		});

		if (entity.getCapability(CapabilityList.ENERGY_SHIELD).isPresent()) {			
			if ( ENERGY_SHIELD_VALUE > 0.00f ) {
				if ( ENERGY_SHIELD_VALUE - dammie > 0 ) {				
					es.ifPresent(cap -> {
						cap.setEnergyShield((float) (ENERGY_SHIELD_VALUE - dammie));					
						cap.setEnergyShieldRechargeTimer(0.0f);
					});									
					event.setAmount(0);
					event.getSource();				
					if (sourcestring.contains("EntityDamageSource")) {
						sworldin.sendParticles(RegisteredParticles.ENERGY_SHIELD_DAMAGE_INDICATOR.get(), px, py, pz, (int) dammie, vx, vy, vz, 0);
					}			
				}
				if ( ENERGY_SHIELD_VALUE - dammie <= 0.00f ) {
					es.ifPresent(cap -> {
						cap.setEnergyShield(0);						
						cap.setEnergyShieldRechargeTimer(0.0f);
					});						
					event.setAmount((float) (dammie - ENERGY_SHIELD_VALUE));
					entity.setHealth((float) (entity.getHealth() - (dammie - ENERGY_SHIELD_VALUE)));
				}				
				ENERGY_SHIELD_VALUE = ENERGY_SHIELD_VALUE - dammie;
				if (ENERGY_SHIELD_VALUE <= 0) {
					ENERGY_SHIELD_VALUE = 0.0f;
				}
			}			
		}		
	}

  /*
   * SpawnEvent - Fires this event if a living entity spawns with their Maximum Energy Shield Attribute set and also set their Current Energy Shield Capability equal to its Maximum. 
   */
  
	@SubscribeEvent
	public static void SpawnEvent(EntityJoinWorldEvent event)  {
		Entity en = event.getEntity();

		if (en instanceof LivingEntity) {
			LivingEntity len = (LivingEntity) en;
			
			if ((len.getAttribute(RegisteredAttributes.MAX_ENERGY_SHIELD.get()) != null) && (len.getAttributeValue(RegisteredAttributes.MAX_ENERGY_SHIELD.get()) != 0.0)) {
		
				if (len.getCapability(CapabilityList.ENERGY_SHIELD).isPresent()) {								
					len.getCapability(CapabilityList.ENERGY_SHIELD).ifPresent(cap -> {
						cap.setEnergyShield(cap.getEnergyShield());						
						cap.setEnergyShieldRechargeTimer(cap.getEnergyShieldRechargeTimer());					
					});
				}			
			}
		}			
	}	
  

  
  
}

