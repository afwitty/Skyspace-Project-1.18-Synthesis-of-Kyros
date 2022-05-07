/*    */ package software.bernie.shadowed.eliotlash.mclib.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Ternary
/*    */   implements IValue
/*    */ {
/*    */   public IValue condition;
/*    */   public IValue ifTrue;
/*    */   public IValue ifFalse;
/*    */   
/*    */   public Ternary(IValue condition, IValue ifTrue, IValue ifFalse) {
/* 17 */     this.condition = condition;
/* 18 */     this.ifTrue = ifTrue;
/* 19 */     this.ifFalse = ifFalse;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double get() {
/* 25 */     return (this.condition.get() != 0.0D) ? this.ifTrue.get() : this.ifFalse.get();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 31 */     return this.condition.toString() + " ? " + this.ifTrue.toString() + " : " + this.ifFalse.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mclib\math\Ternary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */