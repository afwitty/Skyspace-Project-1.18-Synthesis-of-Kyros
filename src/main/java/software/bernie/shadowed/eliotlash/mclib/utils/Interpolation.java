/*     */ package software.bernie.shadowed.eliotlash.mclib.utils;
/*     */ 
/*     */ public enum Interpolation
/*     */ {
/*   5 */   LINEAR("linear")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  10 */       return Interpolations.lerp(a, b, x);
/*     */     }
/*     */   },
/*  13 */   QUAD_IN("quad_in")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  18 */       return a + (b - a) * x * x;
/*     */     }
/*     */   },
/*  21 */   QUAD_OUT("quad_out")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  26 */       return a - (b - a) * x * (x - 2.0F);
/*     */     }
/*     */   },
/*  29 */   QUAD_INOUT("quad_inout")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  34 */       x *= 2.0F;
/*     */       
/*  36 */       if (x < 1.0F) return a + (b - a) / 2.0F * x * x;
/*     */       
/*  38 */       x--;
/*     */       
/*  40 */       return a - (b - a) / 2.0F * (x * (x - 2.0F) - 1.0F);
/*     */     }
/*     */   },
/*  43 */   CUBIC_IN("cubic_in")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  48 */       return a + (b - a) * x * x * x;
/*     */     }
/*     */   },
/*  51 */   CUBIC_OUT("cubic_out")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  56 */       x--;
/*  57 */       return a + (b - a) * (x * x * x + 1.0F);
/*     */     }
/*     */   },
/*  60 */   CUBIC_INOUT("cubic_inout")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  65 */       x *= 2.0F;
/*     */       
/*  67 */       if (x < 1.0F) return a + (b - a) / 2.0F * x * x * x;
/*     */       
/*  69 */       x -= 2.0F;
/*     */       
/*  71 */       return a + (b - a) / 2.0F * (x * x * x + 2.0F);
/*     */     }
/*     */   },
/*  74 */   EXP_IN("exp_in")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  79 */       return a + (b - a) * (float)Math.pow(2.0D, (10.0F * (x - 1.0F)));
/*     */     }
/*     */   },
/*  82 */   EXP_OUT("exp_out")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  87 */       return a + (b - a) * (float)(-Math.pow(2.0D, (-10.0F * x)) + 1.0D);
/*     */     }
/*     */   },
/*  90 */   EXP_INOUT("exp_inout")
/*     */   {
/*     */     
/*     */     public float interpolate(float a, float b, float x)
/*     */     {
/*  95 */       if (x == 0.0F) return a; 
/*  96 */       if (x == 1.0F) return b;
/*     */       
/*  98 */       x *= 2.0F;
/*     */       
/* 100 */       if (x < 1.0F) return a + (b - a) / 2.0F * (float)Math.pow(2.0D, (10.0F * (x - 1.0F)));
/*     */       
/* 102 */       x--;
/*     */       
/* 104 */       return a + (b - a) / 2.0F * (float)(-Math.pow(2.0D, (-10.0F * x)) + 2.0D);
/*     */     }
/*     */   };
/*     */ 
/*     */   
/*     */   public final String key;
/*     */   
/*     */   Interpolation(String key) {
/* 112 */     this.key = key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 119 */     return "mclib.interpolations." + this.key;
/*     */   }
/*     */   
/*     */   public abstract float interpolate(float paramFloat1, float paramFloat2, float paramFloat3);
/*     */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mcli\\utils\Interpolation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */