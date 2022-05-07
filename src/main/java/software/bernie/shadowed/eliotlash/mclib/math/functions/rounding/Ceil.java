/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions.rounding;
import software.bernie.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
/*    */ 
/*    */ public class Ceil
/*    */   extends Function
/*    */ {
/*    */   public Ceil(IValue[] values, String name) throws Exception {
/* 10 */     super(values, name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredArguments() {
/* 16 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double get() {
/* 22 */     return Math.ceil(getArg(0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mclib\math\functions\rounding\Ceil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */