/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions;
import software.bernie.shadowed.eliotlash.mclib.math.IValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Function
/*    */   implements IValue
/*    */ {
/*    */   protected IValue[] args;
/*    */   protected String name;
/*    */   
/*    */   public Function(IValue[] values, String name) throws Exception {
/* 18 */     if (values.length < getRequiredArguments()) {
/*    */       
/* 20 */       String message = String.format("Function '%s' requires at least %s arguments. %s are given!", new Object[] { getName(), Integer.valueOf(getRequiredArguments()), Integer.valueOf(values.length) });
/*    */       
/* 22 */       throw new Exception(message);
/*    */     } 
/*    */     
/* 25 */     this.args = values;
/* 26 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getArg(int index) {
/* 34 */     if (index < 0 || index >= this.args.length)
/*    */     {
/* 36 */       return 0.0D;
/*    */     }
/*    */     
/* 39 */     return this.args[index].get();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 45 */     String args = "";
/*    */     
/* 47 */     for (int i = 0; i < this.args.length; i++) {
/*    */       
/* 49 */       args = args + this.args[i].toString();
/*    */       
/* 51 */       if (i < this.args.length - 1)
/*    */       {
/* 53 */         args = args + ", ";
/*    */       }
/*    */     } 
/*    */     
/* 57 */     return getName() + "(" + args + ")";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 65 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredArguments() {
/* 73 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mclib\math\functions\Function.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */