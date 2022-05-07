/*    */ package software.bernie.shadowed.eliotlash.mclib.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Operator
/*    */   implements IValue
/*    */ {
/*    */   public Operation operation;
/*    */   public IValue a;
/*    */   public IValue b;
/*    */   
/*    */   public Operator(Operation op, IValue a, IValue b) {
/* 17 */     this.operation = op;
/* 18 */     this.a = a;
/* 19 */     this.b = b;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double get() {
/* 25 */     return this.operation.calculate(this.a.get(), this.b.get());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 31 */     return this.a.toString() + " " + this.operation.sign + " " + this.b.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mclib\math\Operator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */