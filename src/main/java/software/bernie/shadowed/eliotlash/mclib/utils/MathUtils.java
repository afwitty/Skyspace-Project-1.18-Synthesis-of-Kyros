/*    */ package software.bernie.shadowed.eliotlash.mclib.utils;
/*    */ 
/*    */ 
/*    */ public class MathUtils
/*    */ {
/*    */   public static int clamp(int x, int min, int max) {
/*  7 */     return (x < min) ? min : ((x > max) ? max : x);
/*    */   }
/*    */ 
/*    */   
/*    */   public static float clamp(float x, float min, float max) {
/* 12 */     return (x < min) ? min : ((x > max) ? max : x);
/*    */   }
/*    */ 
/*    */   
/*    */   public static double clamp(double x, double min, double max) {
/* 17 */     return (x < min) ? min : ((x > max) ? max : x);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int cycler(int x, int min, int max) {
/* 22 */     return (x < min) ? max : ((x > max) ? min : x);
/*    */   }
/*    */ 
/*    */   
/*    */   public static float cycler(float x, float min, float max) {
/* 27 */     return (x < min) ? max : ((x > max) ? min : x);
/*    */   }
/*    */ 
/*    */   
/*    */   public static double cycler(double x, double min, double max) {
/* 32 */     return (x < min) ? max : ((x > max) ? min : x);
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mcli\\utils\MathUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */