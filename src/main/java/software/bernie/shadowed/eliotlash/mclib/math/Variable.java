/*    */ package software.bernie.shadowed.eliotlash.mclib.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Variable
/*    */   implements IValue
/*    */ {
/*    */   private String name;
/*    */   private double value;
/*    */   
/*    */   public Variable(String name, double value) {
/* 20 */     this.name = name;
/* 21 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void set(double value) {
/* 29 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double get() {
/* 35 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 40 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 46 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mclib\math\Variable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */