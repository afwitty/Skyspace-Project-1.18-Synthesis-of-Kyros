/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions.rounding;
import software.bernie.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
/*    */ 
/*    */ public class Trunc
/*    */   extends Function
/*    */ {
/*    */   public Trunc(IValue[] values, String name) throws Exception {
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
/* 22 */     double value = getArg(0);
/*    */     
/* 24 */     return (value < 0.0D) ? Math.ceil(value) : Math.floor(value);
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mclib\math\functions\rounding\Trunc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */