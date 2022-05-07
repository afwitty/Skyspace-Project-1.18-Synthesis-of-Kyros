/*    */ package software.bernie.shadowed.eliotlash.molang.expressions;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;

import software.bernie.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.shadowed.eliotlash.molang.MolangParser;
/*    */ 
/*    */ 
/*    */ public class MolangValue
/*    */   extends MolangExpression
/*    */ {
/*    */   public IValue value;
/*    */   public boolean returns;
/*    */   
/*    */   public MolangValue(MolangParser context, IValue value) {
/* 16 */     super(context);
/*    */     
/* 18 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public MolangExpression addReturn() {
/* 23 */     this.returns = true;
/*    */     
/* 25 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double get() {
/* 31 */     return this.value.get();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 37 */     return (this.returns ? "return " : "") + this.value.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonElement toJson() {
/* 43 */     if (this.value instanceof software.bernie.shadowed.eliotlash.mclib.math.Constant)
/*    */     {
/* 45 */       return (JsonElement)new JsonPrimitive(Double.valueOf(this.value.get()));
/*    */     }
/*    */     
/* 48 */     return super.toJson();
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\molang\expressions\MolangValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */