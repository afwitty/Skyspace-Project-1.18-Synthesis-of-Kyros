/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions.classic;
import software.bernie.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
/*    */ 
/*    */ public class Mod
/*    */   extends Function
/*    */ {
/*    */   public Mod(IValue[] values, String name) throws Exception {
/* 10 */     super(values, name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredArguments() {
/* 16 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double get() {
/* 22 */     return getArg(0) % getArg(1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mclib\math\functions\classic\Mod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */