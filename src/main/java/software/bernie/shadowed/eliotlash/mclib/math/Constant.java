/*    */ package software.bernie.shadowed.eliotlash.mclib.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Constant
/*    */   implements IValue
/*    */ {
/*    */   private double value;
/*    */   
/*    */   public Constant(double value) {
/* 14 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double get() {
/* 20 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(double value) {
/* 25 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 31 */     return String.valueOf(this.value);
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mclib\math\Constant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */