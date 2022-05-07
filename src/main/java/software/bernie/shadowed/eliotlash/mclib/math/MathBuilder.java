/*     */ package software.bernie.shadowed.eliotlash.mclib.math;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;

import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Abs;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Cos;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Exp;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Ln;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Mod;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Pow;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Sin;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Sqrt;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Clamp;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Max;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;
import software.bernie.shadowed.eliotlash.mclib.math.functions.rounding.Ceil;
import software.bernie.shadowed.eliotlash.mclib.math.functions.rounding.Floor;
import software.bernie.shadowed.eliotlash.mclib.math.functions.rounding.Round;
import software.bernie.shadowed.eliotlash.mclib.math.functions.rounding.Trunc;
import software.bernie.shadowed.eliotlash.mclib.math.functions.utility.Lerp;
import software.bernie.shadowed.eliotlash.mclib.math.functions.utility.LerpRotate;
import software.bernie.shadowed.eliotlash.mclib.math.functions.utility.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MathBuilder
/*     */ {
/*  50 */   public Map<String, Variable> variables = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public Map<String, Class<? extends Function>> functions = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public MathBuilder() {
/*  60 */     register(new Variable("PI", Math.PI));
/*  61 */     register(new Variable("E", Math.E));
/*     */ 
/*     */     
/*  64 */     this.functions.put("floor", Floor.class);
/*  65 */     this.functions.put("round", Round.class);
/*  66 */     this.functions.put("ceil", Ceil.class);
/*  67 */     this.functions.put("trunc", Trunc.class);
/*     */ 
/*     */     
/*  70 */     this.functions.put("clamp", Clamp.class);
/*  71 */     this.functions.put("max", Max.class);
/*  72 */     this.functions.put("min", Min.class);
/*     */ 
/*     */     
/*  75 */     this.functions.put("abs", Abs.class);
/*  76 */     this.functions.put("cos", Cos.class);
/*  77 */     this.functions.put("sin", Sin.class);
/*  78 */     this.functions.put("exp", Exp.class);
/*  79 */     this.functions.put("ln", Ln.class);
/*  80 */     this.functions.put("sqrt", Sqrt.class);
/*  81 */     this.functions.put("mod", Mod.class);
/*  82 */     this.functions.put("pow", Pow.class);
/*     */ 
/*     */     
/*  85 */     this.functions.put("lerp", Lerp.class);
/*  86 */     this.functions.put("lerprotate", LerpRotate.class);
/*  87 */     this.functions.put("random", Random.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(Variable variable) {
/*  95 */     this.variables.put(variable.getName(), variable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IValue parse(String expression) throws Exception {
/* 104 */     return parseSymbols(breakdownChars(breakdown(expression)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] breakdown(String expression) throws Exception {
/* 113 */     if (!expression.matches("^[\\w\\d\\s_+-/*%^&|<>=!?:.,()]+$"))
/*     */     {
/* 115 */       throw new Exception("Given expression '" + expression + "' contains illegal characters!");
/*     */     }
/*     */ 
/*     */     
/* 119 */     expression = expression.replaceAll("\\s+", "");
/*     */     
/* 121 */     String[] chars = expression.split("(?!^)");
/*     */     
/* 123 */     int left = 0;
/* 124 */     int right = 0;
/*     */     
/* 126 */     for (String s : chars) {
/*     */       
/* 128 */       if (s.equals("(")) {
/*     */         
/* 130 */         left++;
/*     */       }
/* 132 */       else if (s.equals(")")) {
/*     */         
/* 134 */         right++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 139 */     if (left != right)
/*     */     {
/* 141 */       throw new Exception("Given expression '" + expression + "' has more uneven amount of parenthesis, there are " + left + " open and " + right + " closed!");
/*     */     }
/*     */     
/* 144 */     return chars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Object> breakdownChars(String[] chars) {
/* 152 */     List<Object> symbols = new ArrayList();
/* 153 */     String buffer = "";
/* 154 */     int len = chars.length;
/*     */     
/* 156 */     for (int i = 0; i < len; i++) {
/*     */       
/* 158 */       String s = chars[i];
/* 159 */       boolean longOperator = (i > 0 && isOperator(chars[i - 1] + s));
/*     */       
/* 161 */       if (isOperator(s) || longOperator || s.equals(",")) {
/*     */ 
/*     */ 
/*     */         
/* 165 */         if (s.equals("-")) {
/*     */           
/* 167 */           int size = symbols.size();
/*     */           
/* 169 */           boolean isFirst = (size == 0 && buffer.isEmpty());
/* 170 */           boolean isOperatorBehind = (size > 0 && (isOperator(symbols.get(size - 1)) || symbols.get(size - 1).equals(",")) && buffer.isEmpty());
/*     */           
/* 172 */           if (isFirst || isOperatorBehind) {
/*     */             
/* 174 */             buffer = buffer + s;
/*     */             
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */         
/* 180 */         if (longOperator) {
/*     */           
/* 182 */           s = chars[i - 1] + s;
/* 183 */           buffer = buffer.substring(0, buffer.length() - 1);
/*     */         } 
/*     */ 
/*     */         
/* 187 */         if (!buffer.isEmpty()) {
/*     */           
/* 189 */           symbols.add(buffer);
/* 190 */           buffer = "";
/*     */         } 
/*     */         
/* 193 */         symbols.add(s); continue;
/*     */       } 
/* 195 */       if (s.equals("(")) {
/*     */ 
/*     */         
/* 198 */         if (!buffer.isEmpty()) {
/*     */           
/* 200 */           symbols.add(buffer);
/* 201 */           buffer = "";
/*     */         } 
/*     */         
/* 204 */         int counter = 1;
/*     */         
/* 206 */         for (int j = i + 1; j < len; j++)
/*     */         {
/* 208 */           String c = chars[j];
/*     */           
/* 210 */           if (c.equals("(")) {
/*     */             
/* 212 */             counter++;
/*     */           }
/* 214 */           else if (c.equals(")")) {
/*     */             
/* 216 */             counter--;
/*     */           } 
/*     */           
/* 219 */           if (counter == 0) {
/*     */             
/* 221 */             symbols.add(breakdownChars(buffer.split("(?!^)")));
/*     */             
/* 223 */             i = j;
/* 224 */             buffer = "";
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 230 */           buffer = buffer + c;
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 237 */         buffer = buffer + s;
/*     */       } 
/*     */       continue;
/*     */     } 
/* 241 */     if (!buffer.isEmpty())
/*     */     {
/* 243 */       symbols.add(buffer);
/*     */     }
/*     */     
/* 246 */     return symbols;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IValue parseSymbols(List<Object> symbols) throws Exception {
/* 263 */     IValue ternary = tryTernary(symbols);
/*     */     
/* 265 */     if (ternary != null)
/*     */     {
/* 267 */       return ternary;
/*     */     }
/*     */     
/* 270 */     int size = symbols.size();
/*     */ 
/*     */     
/* 273 */     if (size == 1)
/*     */     {
/* 275 */       return valueFromObject(symbols.get(0));
/*     */     }
/*     */ 
/*     */     
/* 279 */     if (size == 2) {
/*     */       
/* 281 */       Object first = symbols.get(0);
/* 282 */       Object second = symbols.get(1);
/*     */       
/* 284 */       if ((isVariable(first) || first.equals("-")) && second instanceof List)
/*     */       {
/* 286 */         return createFunction((String)first, (List<Object>)second);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 291 */     int lastOp = seekLastOperator(symbols);
/* 292 */     int op = lastOp;
/*     */     
/* 294 */     while (op != -1) {
/*     */       
/* 296 */       int leftOp = seekLastOperator(symbols, op - 1);
/*     */       
/* 298 */       if (leftOp != -1) {
/*     */         
/* 300 */         Operation left = operationForOperator((String)symbols.get(leftOp));
/* 301 */         Operation right = operationForOperator((String)symbols.get(op));
/*     */         
/* 303 */         if (right.value > left.value) {
/*     */           
/* 305 */           IValue leftValue = parseSymbols(symbols.subList(0, leftOp));
/* 306 */           IValue rightValue = parseSymbols(symbols.subList(leftOp + 1, size));
/*     */           
/* 308 */           return new Operator(left, leftValue, rightValue);
/*     */         } 
/* 310 */         if (left.value > right.value) {
/*     */           
/* 312 */           Operation initial = operationForOperator((String)symbols.get(lastOp));
/*     */           
/* 314 */           if (initial.value < left.value) {
/*     */             
/* 316 */             IValue iValue1 = parseSymbols(symbols.subList(0, lastOp));
/* 317 */             IValue iValue2 = parseSymbols(symbols.subList(lastOp + 1, size));
/*     */             
/* 319 */             return new Operator(initial, iValue1, iValue2);
/*     */           } 
/*     */           
/* 322 */           IValue leftValue = parseSymbols(symbols.subList(0, op));
/* 323 */           IValue rightValue = parseSymbols(symbols.subList(op + 1, size));
/*     */           
/* 325 */           return new Operator(right, leftValue, rightValue);
/*     */         } 
/*     */       } 
/*     */       
/* 329 */       op = leftOp;
/*     */     } 
/*     */     
/* 332 */     Operation operation = operationForOperator((String)symbols.get(lastOp));
/*     */     
/* 334 */     return new Operator(operation, parseSymbols(symbols.subList(0, lastOp)), parseSymbols(symbols.subList(lastOp + 1, size)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int seekLastOperator(List<Object> symbols) {
/* 339 */     return seekLastOperator(symbols, symbols.size() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int seekLastOperator(List<Object> symbols, int offset) {
/* 347 */     for (int i = offset; i >= 0; i--) {
/*     */       
/* 349 */       Object o = symbols.get(i);
/*     */       
/* 351 */       if (isOperator(o))
/*     */       {
/* 353 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 357 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int seekFirstOperator(List<Object> symbols) {
/* 362 */     return seekFirstOperator(symbols, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int seekFirstOperator(List<Object> symbols, int offset) {
/* 370 */     for (int i = offset, size = symbols.size(); i < size; i++) {
/*     */       
/* 372 */       Object o = symbols.get(i);
/*     */       
/* 374 */       if (isOperator(o))
/*     */       {
/* 376 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 380 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IValue tryTernary(List<Object> symbols) throws Exception {
/* 392 */     int question = -1;
/* 393 */     int questions = 0;
/* 394 */     int colon = -1;
/* 395 */     int colons = 0;
/* 396 */     int size = symbols.size();
/*     */     
/* 398 */     for (int i = 0; i < size; i++) {
/*     */       
/* 400 */       Object object = symbols.get(i);
/*     */       
/* 402 */       if (object instanceof String)
/*     */       {
/* 404 */         if (object.equals("?")) {
/*     */           
/* 406 */           if (question == -1)
/*     */           {
/* 408 */             question = i;
/*     */           }
/*     */           
/* 411 */           questions++;
/*     */         }
/* 413 */         else if (object.equals(":")) {
/*     */           
/* 415 */           if (colons + 1 == questions && colon == -1)
/*     */           {
/* 417 */             colon = i;
/*     */           }
/*     */           
/* 420 */           colons++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 425 */     if (questions == colons && question > 0 && question + 1 < colon && colon < size - 1)
/*     */     {
/* 427 */       return new Ternary(
/* 428 */           parseSymbols(symbols.subList(0, question)), 
/* 429 */           parseSymbols(symbols.subList(question + 1, colon)), 
/* 430 */           parseSymbols(symbols.subList(colon + 1, size)));
/*     */     }
/*     */ 
/*     */     
/* 434 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IValue createFunction(String first, List<Object> args) throws Exception {
/* 451 */     if (first.equals("!"))
/*     */     {
/* 453 */       return new Negate(parseSymbols(args));
/*     */     }
/*     */     
/* 456 */     if (first.startsWith("!") && first.length() > 1)
/*     */     {
/* 458 */       return new Negate(createFunction(first.substring(1), args));
/*     */     }
/*     */ 
/*     */     
/* 462 */     if (first.equals("-"))
/*     */     {
/* 464 */       return new Negative(parseSymbols(args));
/*     */     }
/*     */     
/* 467 */     if (first.startsWith("-") && first.length() > 1)
/*     */     {
/* 469 */       return new Negative(createFunction(first.substring(1), args));
/*     */     }
/*     */     
/* 472 */     if (!this.functions.containsKey(first))
/*     */     {
/* 474 */       throw new Exception("Function '" + first + "' couldn't be found!");
/*     */     }
/*     */     
/* 477 */     List<IValue> values = new ArrayList<>();
/* 478 */     List<Object> buffer = new ArrayList();
/*     */     
/* 480 */     for (Object o : args) {
/*     */       
/* 482 */       if (o.equals(",")) {
/*     */         
/* 484 */         values.add(parseSymbols(buffer));
/* 485 */         buffer.clear();
/*     */         
/*     */         continue;
/*     */       } 
/* 489 */       buffer.add(o);
/*     */     } 
/*     */ 
/*     */     
/* 493 */     if (!buffer.isEmpty())
/*     */     {
/* 495 */       values.add(parseSymbols(buffer));
/*     */     }
/*     */     
/* 498 */     Class<? extends Function> function = this.functions.get(first);
/* 499 */     Constructor<? extends Function> ctor = function.getConstructor(new Class[] { IValue[].class, String.class });
/* 500 */     Function func = ctor.newInstance(new Object[] { values.toArray(new IValue[values.size()]), first });
/*     */     
/* 502 */     return (IValue)func;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IValue valueFromObject(Object object) throws Exception {
/* 515 */     if (object instanceof String) {
/*     */       
/* 517 */       String symbol = (String)object;
/*     */ 
/*     */       
/* 520 */       if (symbol.startsWith("!"))
/*     */       {
/* 522 */         return new Negate(valueFromObject(symbol.substring(1)));
/*     */       }
/*     */       
/* 525 */       if (isDecimal(symbol))
/*     */       {
/* 527 */         return new Constant(Double.parseDouble(symbol));
/*     */       }
/* 529 */       if (isVariable(symbol))
/*     */       {
/*     */         
/* 532 */         if (symbol.startsWith("-"))
/*     */         {
/* 534 */           symbol = symbol.substring(1);
/* 535 */           Variable value = getVariable(symbol);
/*     */           
/* 537 */           if (value != null)
/*     */           {
/* 539 */             return new Negative(value);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 544 */           IValue value = getVariable(symbol);
/*     */ 
/*     */           
/* 547 */           if (value != null)
/*     */           {
/* 549 */             return value;
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 554 */     } else if (object instanceof List) {
/*     */       
/* 556 */       return new Group(parseSymbols((List<Object>)object));
/*     */     } 
/*     */     
/* 559 */     throw new Exception("Given object couldn't be converted to value! " + object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Variable getVariable(String name) {
/* 567 */     return this.variables.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Operation operationForOperator(String op) throws Exception {
/* 575 */     for (Operation operation : Operation.values()) {
/*     */       
/* 577 */       if (operation.sign.equals(op))
/*     */       {
/* 579 */         return operation;
/*     */       }
/*     */     } 
/*     */     
/* 583 */     throw new Exception("There is no such operator '" + op + "'!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVariable(Object o) {
/* 591 */     return (o instanceof String && !isDecimal((String)o) && !isOperator((String)o));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isOperator(Object o) {
/* 596 */     return (o instanceof String && isOperator((String)o));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isOperator(String s) {
/* 604 */     return (Operation.OPERATORS.contains(s) || s.equals("?") || s.equals(":"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isDecimal(String s) {
/* 613 */     return s.matches("^-?\\d+(\\.\\d+)?$");
/*     */   }
/*     */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mclib\math\MathBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */