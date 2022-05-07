/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions.utility;
import software.bernie.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
import software.bernie.shadowed.eliotlash.mclib.utils.Interpolations;
/*    */ 
/*    */ public class LerpRotate
/*    */   extends Function
/*    */ {
/*    */   public LerpRotate(IValue[] values, String name) throws Exception {
/* 11 */     super(values, name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredArguments() {
/* 17 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double get() {
/* 23 */     return Interpolations.lerpYaw(getArg(0), getArg(1), getArg(2));
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mclib\math\function\\utility\LerpRotate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */