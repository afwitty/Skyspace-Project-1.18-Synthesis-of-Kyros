/*     */ package software.bernie.shadowed.eliotlash.mclib.utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Interpolations
/*     */ {
/*     */   public static float lerp(float a, float b, float position) {
/*  20 */     return a + (b - a) * position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float lerpYaw(float a, float b, float position) {
/*  32 */     a = MathHelper.wrapDegrees(a);
/*  33 */     b = MathHelper.wrapDegrees(b);
/*     */     
/*  35 */     return lerp(a, normalizeYaw(a, b), position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double cubicHermite(double y0, double y1, double y2, double y3, double x) {
/*  50 */     double a = -0.5D * y0 + 1.5D * y1 - 1.5D * y2 + 0.5D * y3;
/*  51 */     double b = y0 - 2.5D * y1 + 2.0D * y2 - 0.5D * y3;
/*  52 */     double c = -0.5D * y0 + 0.5D * y2;
/*     */     
/*  54 */     return ((a * x + b) * x + c) * x + y1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double cubicHermiteYaw(float y0, float y1, float y2, float y3, float position) {
/*  62 */     y0 = MathHelper.wrapDegrees(y0);
/*  63 */     y1 = MathHelper.wrapDegrees(y1);
/*  64 */     y2 = MathHelper.wrapDegrees(y2);
/*  65 */     y3 = MathHelper.wrapDegrees(y3);
/*     */     
/*  67 */     y1 = normalizeYaw(y0, y1);
/*  68 */     y2 = normalizeYaw(y1, y2);
/*  69 */     y3 = normalizeYaw(y2, y3);
/*     */     
/*  71 */     return cubicHermite(y0, y1, y2, y3, position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float cubic(float y0, float y1, float y2, float y3, float x) {
/*  85 */     float a = y3 - y2 - y0 + y1;
/*  86 */     float b = y0 - y1 - a;
/*  87 */     float c = y2 - y0;
/*     */     
/*  89 */     return ((a * x + b) * x + c) * x + y1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float cubicYaw(float y0, float y1, float y2, float y3, float position) {
/*  97 */     y0 = MathHelper.wrapDegrees(y0);
/*  98 */     y1 = MathHelper.wrapDegrees(y1);
/*  99 */     y2 = MathHelper.wrapDegrees(y2);
/* 100 */     y3 = MathHelper.wrapDegrees(y3);
/*     */     
/* 102 */     y1 = normalizeYaw(y0, y1);
/* 103 */     y2 = normalizeYaw(y1, y2);
/* 104 */     y3 = normalizeYaw(y2, y3);
/*     */     
/* 106 */     return cubic(y0, y1, y2, y3, position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float bezierX(float x1, float x2, float t, float epsilon) {
/* 120 */     float x = t;
/* 121 */     float init = bezier(0.0F, x1, x2, 1.0F, t);
/* 122 */     float factor = Math.copySign(0.1F, t - init);
/*     */     
/* 124 */     while (Math.abs(t - init) > epsilon) {
/*     */       
/* 126 */       float oldFactor = factor;
/*     */       
/* 128 */       x += factor;
/* 129 */       init = bezier(0.0F, x1, x2, 1.0F, x);
/*     */       
/* 131 */       if (Math.copySign(factor, t - init) != oldFactor)
/*     */       {
/* 133 */         factor *= -0.25F;
/*     */       }
/*     */     } 
/*     */     
/* 137 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float bezierX(float x1, float x2, float t) {
/* 146 */     return bezierX(x1, x2, t, 5.0E-4F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float bezier(float x1, float x2, float x3, float x4, float t) {
/* 160 */     float t1 = lerp(x1, x2, t);
/* 161 */     float t2 = lerp(x2, x3, t);
/* 162 */     float t3 = lerp(x3, x4, t);
/* 163 */     float t4 = lerp(t1, t2, t);
/* 164 */     float t5 = lerp(t2, t3, t);
/*     */     
/* 166 */     return lerp(t4, t5, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float normalizeYaw(float a, float b) {
/* 175 */     float diff = a - b;
/*     */     
/* 177 */     if (diff > 180.0F || diff < -180.0F) {
/*     */       
/* 179 */       diff = Math.copySign(360.0F - Math.abs(diff), diff);
/*     */       
/* 181 */       return a + diff;
/*     */     } 
/*     */     
/* 184 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float envelope(float x, float duration, float fades) {
/* 194 */     return envelope(x, 0.0F, fades, duration - fades, duration);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float envelope(float x, float lowIn, float lowOut, float highIn, float highOut) {
/* 204 */     if (x < lowIn || x > highOut) return 0.0F; 
/* 205 */     if (x < lowOut) return (x - lowIn) / (lowOut - lowIn); 
/* 206 */     if (x > highIn) return 1.0F - (x - highIn) / (highOut - highIn);
/*     */     
/* 208 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double lerp(double a, double b, double position) {
/* 218 */     return a + (b - a) * position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double lerpYaw(double a, double b, double position) {
/* 230 */     a = MathHelper.wrapDegrees(a);
/* 231 */     b = MathHelper.wrapDegrees(b);
/*     */     
/* 233 */     return lerp(a, normalizeYaw(a, b), position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double cubic(double y0, double y1, double y2, double y3, double x) {
/* 247 */     double a = y3 - y2 - y0 + y1;
/* 248 */     double b = y0 - y1 - a;
/* 249 */     double c = y2 - y0;
/*     */     
/* 251 */     return ((a * x + b) * x + c) * x + y1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double cubicYaw(double y0, double y1, double y2, double y3, double position) {
/* 259 */     y0 = MathHelper.wrapDegrees(y0);
/* 260 */     y1 = MathHelper.wrapDegrees(y1);
/* 261 */     y2 = MathHelper.wrapDegrees(y2);
/* 262 */     y3 = MathHelper.wrapDegrees(y3);
/*     */     
/* 264 */     y1 = normalizeYaw(y0, y1);
/* 265 */     y2 = normalizeYaw(y1, y2);
/* 266 */     y3 = normalizeYaw(y2, y3);
/*     */     
/* 268 */     return cubic(y0, y1, y2, y3, position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double bezierX(double x1, double x2, double t, double epsilon) {
/* 282 */     double x = t;
/* 283 */     double init = bezier(0.0D, x1, x2, 1.0D, t);
/* 284 */     double factor = Math.copySign(0.10000000149011612D, t - init);
/*     */     
/* 286 */     while (Math.abs(t - init) > epsilon) {
/*     */       
/* 288 */       double oldFactor = factor;
/*     */       
/* 290 */       x += factor;
/* 291 */       init = bezier(0.0D, x1, x2, 1.0D, x);
/*     */       
/* 293 */       if (Math.copySign(factor, t - init) != oldFactor)
/*     */       {
/* 295 */         factor *= -0.25D;
/*     */       }
/*     */     } 
/*     */     
/* 299 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double bezierX(double x1, double x2, float t) {
/* 308 */     return bezierX(x1, x2, t, 5.000000237487257E-4D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double bezier(double x1, double x2, double x3, double x4, double t) {
/* 322 */     double t1 = lerp(x1, x2, t);
/* 323 */     double t2 = lerp(x2, x3, t);
/* 324 */     double t3 = lerp(x3, x4, t);
/* 325 */     double t4 = lerp(t1, t2, t);
/* 326 */     double t5 = lerp(t2, t3, t);
/*     */     
/* 328 */     return lerp(t4, t5, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double normalizeYaw(double a, double b) {
/* 337 */     double diff = a - b;
/*     */     
/* 339 */     if (diff > 180.0D || diff < -180.0D) {
/*     */       
/* 341 */       diff = Math.copySign(360.0D - Math.abs(diff), diff);
/*     */       
/* 343 */       return a + diff;
/*     */     } 
/*     */     
/* 346 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double envelope(double x, double duration, double fades) {
/* 356 */     return envelope(x, 0.0D, fades, duration - fades, duration);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double envelope(double x, double lowIn, double lowOut, double highIn, double highOut) {
/* 366 */     if (x < lowIn || x > highOut) return 0.0D; 
/* 367 */     if (x < lowOut) return (x - lowIn) / (lowOut - lowIn); 
/* 368 */     if (x > highIn) return 1.0D - (x - highIn) / (highOut - highIn);
/*     */     
/* 370 */     return 1.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mcli\\utils\Interpolations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */