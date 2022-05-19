package com.cryotron.skyspaceproject.gui.overlay;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import java.util.Random;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.capabilities.CapabilityList;
import com.cryotron.skyspaceproject.capabilities.energyshield.IEnergyShieldCapability;
import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredAttributes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

@Mod.EventBusSubscriber
public class EnergyShieldHandler {
	
	  private static final ResourceLocation ICON_HEXES = new ResourceLocation(Skyspace.ID, "textures/gui/mantleheartstempii.png");
	  private static final ResourceLocation ICON_VANILLA = GuiComponent.GUI_ICONS_LOCATION;
	
	  private final Minecraft mc = Minecraft.getInstance();

	  private int playerShield = 0;
	  private int lastPlayerShield = 0;
	  private long shieldUpdateCounter = 0;
	  private long lastSystemTime = 0;
	  private final Random rand = new Random();

	  int shield = 0;
	  
	  private int recharge;
	  
	  /**
	   * Draws a texture to the screen
	   * @param matrixStack  Matrix stack instance
	   * @param x            X position
	   * @param y            Y position
	   * @param textureX     Texture X
	   * @param textureY     Texture Y
	   * @param width        Width to draw
	   * @param height       Height to draw
	   */
	  private void blit(PoseStack matrixStack, int x, int y, int textureX, int textureY, int width, int height) {
	    Minecraft.getInstance().gui.blit(matrixStack, x, y, textureX, textureY, width, height);
	  }

	  @SubscribeEvent(priority = EventPriority.LOW)
	  public void renderShieldbar(RenderGameOverlayEvent.PreLayer event) {
		/*
		 * ENERGY_SHIELD_SECTION
		 */
		  
	    if (event.isCanceled() || event.getOverlay() != ForgeIngameGui.PLAYER_HEALTH_ELEMENT) {
	      return;
	    }
	    // ensure its visible
	    if (!(mc.gui instanceof ForgeIngameGui gui) || mc.options.hideGui || !gui.shouldDrawSurvivalElements()) {
	      return;
	    }
	    Entity renderViewEnity = this.mc.getCameraEntity();
	    if (!(renderViewEnity instanceof Player player)) {
	      return;
	    }
	    gui.setupOverlayRenderState(true, false);

	    this.mc.getProfiler().push("energy_shield");

	    // extra setup stuff from us
	    int left_height = gui.left_height;
	    int width = this.mc.getWindow().getGuiScaledWidth();
	    int height = this.mc.getWindow().getGuiScaledHeight();
	    int updateCounter = this.mc.gui.getGuiTicks();

	    // start default forge/mc rendering
	    // changes are indicated by comment

	    LazyOptional<IEnergyShieldCapability> es = player.getCapability(CapabilityList.ENERGY_SHIELD);		

	    
		es.ifPresent(cap -> {
			shield = Mth.ceil(cap.getEnergyShield());
		});
	    
	    boolean highlight = this.shieldUpdateCounter > (long) updateCounter && (this.shieldUpdateCounter - (long) updateCounter) / 3L % 2L == 1L;

	    if (shield < this.playerShield && player.invulnerableTime > 0) {
	      this.lastSystemTime = Util.getMillis();
	      this.shieldUpdateCounter = (updateCounter + 20);
	    }
	    else if (shield > this.playerShield && player.invulnerableTime > 0) {
	      this.lastSystemTime = Util.getMillis();
	      this.shieldUpdateCounter = (updateCounter + 10);
	    }

	    if (Util.getMillis() - this.lastSystemTime > 1000L) {
	      this.playerShield = shield;
	      this.lastPlayerShield = shield;
	      this.lastSystemTime = Util.getMillis();
	    }

	    this.playerShield = shield;
	    int healthLast = this.lastPlayerShield;

	    AttributeInstance attrMaxShield = player.getAttribute(RegisteredAttributes.MAX_ENERGY_SHIELD.get());
	    float shieldMax = attrMaxShield == null ? 0 : (float) attrMaxShield.getValue();

	    // CHANGE: simulate 10 hearts max if there's more, so vanilla only renders one row max
	    shieldMax = Math.min(shieldMax, 20f);
	    shield = Math.min(shield, 20);

	    int healthRows = Mth.ceil((shieldMax) / 2.0F / 10.0F);
	    int rowHeight = Math.max(10 - (healthRows - 2), 3);

	    this.rand.setSeed(updateCounter * 312871L);

	    int left = width / 2 - 91;
	    int top = height - left_height;
	    // change: these are unused below, unneeded? should these adjust the Forge variable?
	    //left_height += (healthRows * rowHeight);
	    //if (rowHeight != 10) left_height += 10 - rowHeight;

	    // Worry about Shield Sustain Later.
//	    this.regen = -1;
//	    if (player.hasEffect(MobEffects.REGENERATION)) {
//	      this.regen = updateCounter % 25;
//	    }

	    assert this.mc.level != null;
	    final int TOP = 9 * (this.mc.level.getLevelData().isHardcore() ? 5 : 0);
	    final int BACKGROUND = (highlight ? 25 : 16);
	    int MARGIN = 16;
	    if      (player.hasEffect(MobEffects.POISON)) MARGIN += 36;
	    else if (player.hasEffect(MobEffects.WITHER)) MARGIN += 72;

	    PoseStack matrixStack = event.getMatrixStack();
	    for (int i = Mth.ceil((shieldMax) / 2.0F) - 1; i >= 0; --i) {
	      int row = Mth.ceil((float) (i + 1) / 10.0F) - 1;
	      int x = left + i % 10 * 8;
	      int y = top - row * rowHeight;

	      if (shield <= 4) y += this.rand.nextInt(2);
	      //if (i == this.regen) y -= 2;

	      this.blit(matrixStack, x, y, BACKGROUND, TOP, 9, 9);

	      if (highlight) {
	        if (i * 2 + 1 < healthLast) {
	          this.blit(matrixStack, x, y, MARGIN + 54, TOP, 9, 9); //6
	        }
	        else if (i * 2 + 1 == healthLast) {
	          this.blit(matrixStack, x, y, MARGIN + 63, TOP, 9, 9); //7
	        }
	      }
	      else {
	        if (i * 2 + 1 < shield) {
	          this.blit(matrixStack, x, y, MARGIN + 36, TOP, 9, 9); //4
	        }
	        else if (i * 2 + 1 == shield) {
	          this.blit(matrixStack, x, y, MARGIN + 45, TOP, 9, 9); //5
	        }
	      }
	    }

	    this.renderShields(matrixStack, left, top, player);
	    //this.renderExtraAbsorption(matrixStack, left, top - rowHeight, player);

	    RenderSystem.setShaderTexture(0, ICON_VANILLA);
	    gui.left_height += 10;

	    event.setCanceled(true);
	    RenderSystem.disableBlend();
	    this.mc.getProfiler().pop();
	    MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.PostLayer(matrixStack, event, ForgeIngameGui.PLAYER_HEALTH_ELEMENT));
	  }

	  /**
	   * Renders the health above 10 hearts
	   * @param matrixStack  Matrix stack instance
	   * @param xBasePos     Health bar top corner
	   * @param yBasePos     Health bar top corner
	   * @param player       Player instance
	   */
	  private void renderShields(PoseStack matrixStack, int xBasePos, int yBasePos, Player player) {
	    //int potionOffset = this.getPotionOffset(player);

	    // Extra hearts
	    RenderSystem.setShaderTexture(0, ICON_HEXES);
	    int hp = Mth.ceil(player.getHealth());
	    this.renderCustomHearts(matrixStack, xBasePos, yBasePos, hp, false);
	  }
	  
	  /**
	   * Shared logic to render custom hearts
	   * @param matrixStack  Matrix stack instance
	   * @param xBasePos     Health bar top corner
	   * @param yBasePos     Health bar top corner
	   * @param potionOffset Offset from the potion effect
	   * @param count        Number to render
	   * @param absorb       If true, render absorption hearts
	   */
	  private void renderCustomHearts(PoseStack matrixStack, int xBasePos, int yBasePos, int count, boolean absorb) {
	    int regenOffset = absorb ? 10 : 0;
	    for (int iter = 0; iter < count / 20; iter++) {
	      int renderHearts = (count - 20 * (iter + 1)) / 2;
	      int heartIndex = iter % 11;
	      if (renderHearts > 10) {
	        renderHearts = 10;
	      }
	      // Worry about Shield Sustain later.
//	      for (int i = 0; i < renderHearts; i++) {
//	        int y = this.getYRegenOffset(i, regenOffset);
//	        if (absorb) {
//	          this.blit(matrixStack, xBasePos + 8 * i, yBasePos + y, 0, 54, 9, 9);
//	        }
//	        this.blit(matrixStack, xBasePos + 8 * i, yBasePos + y, 18 * heartIndex, potionOffset, 9, 9);
//	      }
//	      if (count % 2 == 1 && renderHearts < 10) {
//	        int y = this.getYRegenOffset(renderHearts, regenOffset);
//	        if (absorb) {
//	          this.blit(matrixStack, xBasePos + 8 * renderHearts, yBasePos + y, 0, 54, 9, 9);
//	        }
//	        this.blit(matrixStack, xBasePos + 8 * renderHearts, yBasePos + y, 9 + 18 * heartIndex, potionOffset, 9, 9);
//	      }
	    }
	  }
}
