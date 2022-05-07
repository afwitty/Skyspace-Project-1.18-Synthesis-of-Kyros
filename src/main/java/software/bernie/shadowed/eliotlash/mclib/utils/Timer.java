/*    */ package software.bernie.shadowed.eliotlash.mclib.utils;
/*    */ 
/*    */ 
/*    */ public class Timer
/*    */ {
/*    */   public boolean enabled;
/*    */   public long time;
/*    */   public long duration;
/*    */   
/*    */   public Timer(long duration) {
/* 11 */     this.duration = duration;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getRemaining() {
/* 16 */     return this.time - System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */   
/*    */   public void mark() {
/* 21 */     mark(this.duration);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mark(long duration) {
/* 26 */     this.enabled = true;
/* 27 */     this.time = System.currentTimeMillis() + duration;
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 32 */     this.enabled = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkReset() {
/* 37 */     boolean enabled = check();
/*    */     
/* 39 */     if (enabled)
/*    */     {
/* 41 */       reset();
/*    */     }
/*    */     
/* 44 */     return enabled;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean check() {
/* 49 */     return (this.enabled && isTime());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTime() {
/* 54 */     return (System.currentTimeMillis() >= this.time);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkRepeat() {
/* 59 */     if (!this.enabled)
/*    */     {
/* 61 */       mark();
/*    */     }
/*    */     
/* 64 */     return checkReset();
/*    */   }
/*    */ }


/* Location:              C:\Users\Aleksei Witty\Twitch\Minecraft\Instances\mantle test II\mods\AoA3-1.18.2-3.6-Public Alpha 4.jar!\software\bernie\aoa3\shadowed\eliotlash\mcli\\utils\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */