/*    */ package software.bernie.shadowed.eliotlash.molang.expressions;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;

import software.bernie.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.shadowed.eliotlash.mclib.math.Operation;
import software.bernie.shadowed.eliotlash.molang.MolangParser;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MolangExpression
/*    */   implements IValue
/*    */ {
/*    */   public MolangParser context;
/*    */   
/*    */   public static boolean isZero(MolangExpression expression) {
/* 18 */     return isConstant(expression, 0.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isOne(MolangExpression expression) {
/* 23 */     return isConstant(expression, 1.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isConstant(MolangExpression expression, double x) {
/* 28 */     if (expression instanceof MolangValue) {
/*    */       
/* 30 */       MolangValue value = (MolangValue)expression;
/*    */       
/* 32 */       return (value.value instanceof software.bernie.shadowed.eliotlash.mclib.math.Constant && Operation.equals(value.value.get(), x));
/*    */     } 
/*    */     
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isExpressionConstant(MolangExpression expression) {
/* 40 */     if (expression instanceof MolangValue) {
/*    */       
/* 42 */       MolangValue value = (MolangValue)expression;
/*    */       
/* 44 */       return value.value instanceof software.bernie.shadowed.eliotlash.mclib.math.Constant;
/*    */     } 
/*    */     
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public MolangExpression(MolangParser context) {
/* 52 */     this.context = context;
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement toJson() {
/* 57 */     return (JsonElement)new JsonPrimitive(toString());
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\molang\expressions\MolangExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */