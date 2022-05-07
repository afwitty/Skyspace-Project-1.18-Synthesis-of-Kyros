/*    */ package software.bernie.shadowed.eliotlash.mclib.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Group
/*    */   implements IValue
/*    */ {
/*    */   private IValue value;
/*    */   
/*    */   public Group(IValue value) {
/* 15 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double get() {
/* 21 */     return this.value.get();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 27 */     return "(" + this.value.toString() + ")";
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mclib\math\Group.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */