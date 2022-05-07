/*    */ package software.bernie.shadowed.eliotlash.mclib.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MathHelper
/*    */ {
/*    */   public static float wrapDegrees(float value) {
/*  9 */     value %= 360.0F;
/*    */     
/* 11 */     if (value >= 180.0F)
/*    */     {
/* 13 */       value -= 360.0F;
/*    */     }
/*    */     
/* 16 */     if (value < -180.0F)
/*    */     {
/* 18 */       value += 360.0F;
/*    */     }
/*    */     
/* 21 */     return value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static double wrapDegrees(double value) {
/* 29 */     value %= 360.0D;
/*    */     
/* 31 */     if (value >= 180.0D)
/*    */     {
/* 33 */       value -= 360.0D;
/*    */     }
/*    */     
/* 36 */     if (value < -180.0D)
/*    */     {
/* 38 */       value += 360.0D;
/*    */     }
/*    */     
/* 41 */     return value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int wrapDegrees(int angle) {
/* 49 */     angle %= 360;
/*    */     
/* 51 */     if (angle >= 180)
/*    */     {
/* 53 */       angle -= 360;
/*    */     }
/*    */     
/* 56 */     if (angle < -180)
/*    */     {
/* 58 */       angle += 360;
/*    */     }
/*    */     
/* 61 */     return angle;
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mcli\\utils\MathHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */