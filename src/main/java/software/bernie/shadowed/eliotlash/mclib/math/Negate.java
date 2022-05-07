/*    */ package software.bernie.shadowed.eliotlash.mclib.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Negate
/*    */   implements IValue
/*    */ {
/*    */   public IValue value;
/*    */   
/*    */   public Negate(IValue value) {
/* 14 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double get() {
/* 20 */     return (this.value.get() == 0.0D) ? 1.0D : 0.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 26 */     return "!" + this.value.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mclib\math\Negate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */