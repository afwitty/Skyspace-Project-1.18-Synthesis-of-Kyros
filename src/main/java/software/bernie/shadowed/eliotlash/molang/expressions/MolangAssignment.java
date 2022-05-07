/*    */ package software.bernie.shadowed.eliotlash.molang.expressions;
import software.bernie.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.shadowed.eliotlash.mclib.math.Variable;
import software.bernie.shadowed.eliotlash.molang.MolangParser;
/*    */ 
/*    */ public class MolangAssignment
/*    */   extends MolangExpression
/*    */ {
/*    */   public Variable variable;
/*    */   public IValue expression;
/*    */   
/*    */   public MolangAssignment(MolangParser context, Variable variable, IValue expression) {
/* 14 */     super(context);
/*    */     
/* 16 */     this.variable = variable;
/* 17 */     this.expression = expression;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double get() {
/* 23 */     double value = this.expression.get();
/*    */     
/* 25 */     this.variable.set(value);
/*    */     
/* 27 */     return value;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 33 */     return this.variable.getName() + " = " + this.expression.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\molang\expressions\MolangAssignment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */