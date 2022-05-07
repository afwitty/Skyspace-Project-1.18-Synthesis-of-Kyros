/*    */ package software.bernie.shadowed.eliotlash.molang.expressions;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.StringJoiner;

import software.bernie.shadowed.eliotlash.mclib.math.Variable;
import software.bernie.shadowed.eliotlash.molang.MolangParser;
/*    */ 
/*    */ public class MolangMultiStatement
/*    */   extends MolangExpression
/*    */ {
/* 14 */   public List<MolangExpression> expressions = new ArrayList<>();
/* 15 */   public Map<String, Variable> locals = new HashMap<>();
/*    */ 
/*    */   
/*    */   public MolangMultiStatement(MolangParser context) {
/* 19 */     super(context);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double get() {
/* 25 */     double value = 0.0D;
/*    */     
/* 27 */     for (MolangExpression expression : this.expressions)
/*    */     {
/* 29 */       value = expression.get();
/*    */     }
/*    */     
/* 32 */     return value;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 38 */     StringJoiner builder = new StringJoiner("; ");
/*    */     
/* 40 */     for (MolangExpression expression : this.expressions) {
/*    */       
/* 42 */       builder.add(expression.toString());
/*    */       
/* 44 */       if (expression instanceof MolangValue && ((MolangValue)expression).returns) {
/*    */         break;
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 50 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\molang\expressions\MolangMultiStatement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */