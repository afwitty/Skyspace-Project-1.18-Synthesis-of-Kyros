/*     */ package software.bernie.shadowed.eliotlash.mclib.math;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ public enum Operation
/*     */ {
/*  17 */   ADD("+", 1)
/*     */   {
/*     */     
/*     */     public double calculate(double a, double b)
/*     */     {
/*  22 */       return a + b;
/*     */     }
/*     */   },
/*  25 */   SUB("-", 1)
/*     */   {
/*     */     
/*     */     public double calculate(double a, double b)
/*     */     {
/*  30 */       return a - b;
/*     */     }
/*     */   },
/*  33 */   MUL("*", 2)
/*     */   {
/*     */     
/*     */     public double calculate(double a, double b)
/*     */     {
/*  38 */       return a * b;
/*     */     }
/*     */   },
/*  41 */   DIV("/", 2)
/*     */   {
/*     */ 
/*     */     
/*     */     public double calculate(double a, double b)
/*     */     {
/*  47 */       return a / ((b == 0.0D) ? 1.0D : b);
/*     */     }
/*     */   },
/*  50 */   MOD("%", 2)
/*     */   {
/*     */     
/*     */     public double calculate(double a, double b)
/*     */     {
/*  55 */       return a % b;
/*     */     }
/*     */   },
/*  58 */   POW("^", 3)
/*     */   {
/*     */     
/*     */     public double calculate(double a, double b)
/*     */     {
/*  63 */       return Math.pow(a, b);
/*     */     }
/*     */   },
/*  66 */   AND("&&", 5)
/*     */   {
/*     */     
/*     */     public double calculate(double a, double b)
/*     */     {
/*  71 */       return (a != 0.0D && b != 0.0D) ? 1.0D : 0.0D;
/*     */     }
/*     */   },
/*  74 */   OR("||", 5)
/*     */   {
/*     */     
/*     */     public double calculate(double a, double b)
/*     */     {
/*  79 */       return (a != 0.0D || b != 0.0D) ? 1.0D : 0.0D;
/*     */     }
/*     */   },
/*  82 */   LESS("<", 5)
/*     */   {
/*     */     
/*     */     public double calculate(double a, double b)
/*     */     {
/*  87 */       return (a < b) ? 1.0D : 0.0D;
/*     */     }
/*     */   },
/*  90 */   LESS_THAN("<=", 5)
/*     */   {
/*     */     
/*     */     public double calculate(double a, double b)
/*     */     {
/*  95 */       return (a <= b) ? 1.0D : 0.0D;
/*     */     }
/*     */   },
/*  98 */   GREATER_THAN(">=", 5)
/*     */   {
/*     */     
/*     */     public double calculate(double a, double b)
/*     */     {
/* 103 */       return (a >= b) ? 1.0D : 0.0D;
/*     */     }
/*     */   },
/* 106 */   GREATER(">", 5)
/*     */   {
/*     */     
/*     */     public double calculate(double a, double b)
/*     */     {
/* 111 */       return (a > b) ? 1.0D : 0.0D;
/*     */     }
/*     */   },
/* 114 */   EQUALS("==", 5)
/*     */   {
/*     */     
/*     */     public double calculate(double a, double b)
/*     */     {
/* 119 */       return equals(a, b) ? 1.0D : 0.0D;
/*     */     }
/*     */   },
/* 122 */   NOT_EQUALS("!=", 5)
/*     */   {
/*     */     
/*     */     public double calculate(double a, double b)
/*     */     {
/* 127 */       return !equals(a, b) ? 1.0D : 0.0D;
/*     */     } };
/*     */   

/*     */ 
/*     */   
/*     */   public static final Set<String> OPERATORS;

/*     */   static {
/* 131 */     OPERATORS = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     for (Operation op : values())
/*     */     {
/* 142 */       OPERATORS.add(op.sign);
/*     */     }
/*     */   }

/*     */   
/*     */   public final String sign;
/*     */   
/*     */   public final int value;
/*     */ 
/*     */   
/*     */   public static boolean equals(double a, double b) {
/*     */     return (Math.abs(a - b) < 1.0E-5D);
/*     */   }
/*     */   
/*     */   Operation(String sign, int value) {
/* 159 */     this.sign = sign;
/* 160 */     this.value = value;
/*     */   }
/*     */   
/*     */   public abstract double calculate(double paramDouble1, double paramDouble2);
/*     */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mclib\math\Operation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */