/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions.utility;
import software.bernie.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
/*    */ 
/*    */ public class Random
/*    */   extends Function
/*    */ {
/*    */   public java.util.Random random;
/*    */   
/*    */   public Random(IValue[] values, String name) throws Exception {
/* 12 */     super(values, name);
/*    */     
/* 14 */     this.random = new java.util.Random();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double get() {
/* 20 */     double random = 0.0D;
/*    */     
/* 22 */     if (this.args.length >= 3) {
/*    */       
/* 24 */       this.random.setSeed((long)getArg(2));
/* 25 */       random = this.random.nextDouble();
/*    */     }
/*    */     else {
/*    */       
/* 29 */       random = Math.random();
/*    */     } 
/*    */     
/* 32 */     if (this.args.length >= 2) {
/*    */       
/* 34 */       double a = getArg(0);
/* 35 */       double b = getArg(1);
/*    */       
/* 37 */       double min = Math.min(a, b);
/* 38 */       double max = Math.max(a, b);
/*    */       
/* 40 */       random = random * (max - min) + min;
/*    */     }
/* 42 */     else if (this.args.length >= 1) {
/*    */       
/* 44 */       random *= getArg(0);
/*    */     } 
/*    */     
/* 47 */     return random;
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mclib\math\function\\utility\Random.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */