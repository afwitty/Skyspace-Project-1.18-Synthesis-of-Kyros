/*     */ package software.bernie.shadowed.eliotlash.molang;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonPrimitive;

import software.bernie.shadowed.eliotlash.mclib.math.Constant;
import software.bernie.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.shadowed.eliotlash.mclib.math.MathBuilder;
import software.bernie.shadowed.eliotlash.mclib.math.Variable;
import software.bernie.shadowed.eliotlash.molang.expressions.MolangAssignment;
import software.bernie.shadowed.eliotlash.molang.expressions.MolangExpression;
import software.bernie.shadowed.eliotlash.molang.expressions.MolangMultiStatement;
import software.bernie.shadowed.eliotlash.molang.expressions.MolangValue;
import software.bernie.shadowed.eliotlash.molang.functions.CosDegrees;
import software.bernie.shadowed.eliotlash.molang.functions.SinDegrees;

/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MolangParser
/*     */   extends MathBuilder
/*     */ {
/*  28 */   public static final MolangExpression ZERO = (MolangExpression)new MolangValue(null, (IValue)new Constant(0.0D));
/*  29 */   public static final MolangExpression ONE = (MolangExpression)new MolangValue(null, (IValue)new Constant(1.0D));
/*     */ 
/*     */   
/*     */   public static final String RETURN = "return ";
/*     */ 
/*     */   
/*     */   private MolangMultiStatement currentStatement;
/*     */ 
/*     */   
/*     */   public MolangParser() {
/*  39 */     this.functions.put("cos", CosDegrees.class);
/*  40 */     this.functions.put("sin", SinDegrees.class);
/*     */ 
/*     */     
/*  43 */     remap("abs", "math.abs");
/*  44 */     remap("ceil", "math.ceil");
/*  45 */     remap("clamp", "math.clamp");
/*  46 */     remap("cos", "math.cos");
/*  47 */     remap("exp", "math.exp");
/*  48 */     remap("floor", "math.floor");
/*  49 */     remap("lerp", "math.lerp");
/*  50 */     remap("lerprotate", "math.lerprotate");
/*  51 */     remap("ln", "math.ln");
/*  52 */     remap("max", "math.max");
/*  53 */     remap("min", "math.min");
/*  54 */     remap("mod", "math.mod");
/*  55 */     remap("pow", "math.pow");
/*  56 */     remap("random", "math.random");
/*  57 */     remap("round", "math.round");
/*  58 */     remap("sin", "math.sin");
/*  59 */     remap("sqrt", "math.sqrt");
/*  60 */     remap("trunc", "math.trunc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remap(String old, String newName) {
/*  68 */     this.functions.put(newName, this.functions.remove(old));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(String name, double value) {
/*  73 */     Variable variable = getVariable(name);
/*     */     
/*  75 */     if (variable != null)
/*     */     {
/*  77 */       variable.set(value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Variable getVariable(String name) {
/*  87 */     Variable variable = (this.currentStatement == null) ? null : (Variable)this.currentStatement.locals.get(name);
/*     */     
/*  89 */     if (variable == null)
/*     */     {
/*  91 */       variable = super.getVariable(name);
/*     */     }
/*     */     
/*  94 */     if (variable == null) {
/*     */       
/*  96 */       variable = new Variable(name, 0.0D);
/*     */       
/*  98 */       register(variable);
/*     */     } 
/*     */     
/* 101 */     return variable;
/*     */   }
/*     */ 
/*     */   
/*     */   public MolangExpression parseJson(JsonElement element) throws MolangException {
/* 106 */     if (element.isJsonPrimitive()) {
/*     */       
/* 108 */       JsonPrimitive primitive = element.getAsJsonPrimitive();
/*     */       
/* 110 */       if (primitive.isString()) {
/*     */         
/*     */         try {
/*     */           
/* 114 */           return (MolangExpression)new MolangValue(this, (IValue)new Constant(Float.parseFloat(primitive.getAsString())));
/*     */         }
/* 116 */         catch (Exception exception) {
/*     */ 
/*     */           
/* 119 */           return parseExpression(primitive.getAsString());
/*     */         } 
/*     */       }
/*     */       
/* 123 */       return (MolangExpression)new MolangValue(this, (IValue)new Constant(primitive.getAsDouble()));
/*     */     } 
/*     */ 
/*     */     
/* 127 */     return ZERO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MolangExpression parseExpression(String expression) throws MolangException {
/* 135 */     List<String> lines = new ArrayList<>();
/*     */     
/* 137 */     for (String split : expression.toLowerCase().trim().split(";")) {
/*     */       
/* 139 */       if (!split.trim().isEmpty())
/*     */       {
/* 141 */         lines.add(split);
/*     */       }
/*     */     } 
/*     */     
/* 145 */     if (lines.size() == 0)
/*     */     {
/* 147 */       throw new MolangException("Molang expression cannot be blank!");
/*     */     }
/*     */     
/* 150 */     MolangMultiStatement result = new MolangMultiStatement(this);
/*     */     
/* 152 */     this.currentStatement = result;
/*     */ 
/*     */     
/*     */     try {
/* 156 */       for (String line : lines)
/*     */       {
/* 158 */         result.expressions.add(parseOneLine(line));
/*     */       }
/*     */     }
/* 161 */     catch (Exception e) {
/*     */       
/* 163 */       this.currentStatement = null;
/*     */       
/* 165 */       throw e;
/*     */     } 
/*     */     
/* 168 */     this.currentStatement = null;
/*     */     
/* 170 */     return (MolangExpression)result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MolangExpression parseOneLine(String expression) throws MolangException {
/* 178 */     expression = expression.trim();
/*     */     
/* 180 */     if (expression.startsWith("return ")) {
/*     */       
/*     */       try {
/*     */         
/* 184 */         return (new MolangValue(this, parse(expression.substring("return ".length())))).addReturn();
/*     */       }
/* 186 */       catch (Exception e) {
/*     */         
/* 188 */         throw new MolangException("Couldn't parse return '" + expression + "' expression!");
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 194 */       List<Object> symbols = breakdownChars(breakdown(expression));
/*     */ 
/*     */       
/* 197 */       if (symbols.size() >= 3 && symbols.get(0) instanceof String && isVariable(symbols.get(0)) && symbols.get(1).equals("=")) {
/*     */         
/* 199 */         String name = (String)symbols.get(0);
/* 200 */         symbols = symbols.subList(2, symbols.size());
/*     */         
/* 202 */         Variable variable = null;
/*     */         
/* 204 */         if (!this.variables.containsKey(name) && !this.currentStatement.locals.containsKey(name)) {
/*     */           
/* 206 */           variable = new Variable(name, 0.0D);
/* 207 */           this.currentStatement.locals.put(name, variable);
/*     */         }
/*     */         else {
/*     */           
/* 211 */           variable = getVariable(name);
/*     */         } 
/*     */         
/* 214 */         return (MolangExpression)new MolangAssignment(this, variable, parseSymbolsMolang(symbols));
/*     */       } 
/*     */       
/* 217 */       return (MolangExpression)new MolangValue(this, parseSymbolsMolang(symbols));
/*     */     }
/* 219 */     catch (Exception e) {
/*     */       
/* 221 */       throw new MolangException("Couldn't parse '" + expression + "' expression!");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IValue parseSymbolsMolang(List<Object> symbols) throws MolangException {
/*     */     try {
/* 232 */       return parseSymbols(symbols);
/*     */     }
/* 234 */     catch (Exception e) {
/*     */       
/* 236 */       e.printStackTrace();
/*     */       
/* 238 */       throw new MolangException("Couldn't parse an expression!");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isOperator(String s) {
/* 249 */     return (super.isOperator(s) || s.equals("="));
/*     */   }
/*     */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\molang\MolangParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */