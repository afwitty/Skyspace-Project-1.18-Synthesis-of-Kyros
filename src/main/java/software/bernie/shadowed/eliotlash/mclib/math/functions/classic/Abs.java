/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions.classic;
import software.bernie.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Abs
/*    */   extends Function
/*    */ {
/*    */   public Abs(IValue[] values, String name) throws Exception {
/* 13 */     super(values, name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredArguments() {
/* 19 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double get() {
/* 25 */     return Math.abs(getArg(0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mclib\math\functions\classic\Abs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */