public class TrafficControllerSimple implements TrafficController {
   private TrafficRegistrar registrar;
   private boolean bridgeOccupied = false;

   public TrafficControllerSimple(TrafficRegistrar var1) {
      this.registrar = var1;
   }

   public synchronized void enterRight(Vehicle var1) {
      while(this.bridgeOccupied) {
         try {
            this.wait();
         } catch (InterruptedException var3) {
            Thread.currentThread().interrupt();
         }
      }

      this.bridgeOccupied = true;
      this.registrar.registerRight(var1);
   }

   public synchronized void enterLeft(Vehicle var1) {
      while(this.bridgeOccupied) {
         try {
            this.wait();
         } catch (InterruptedException var3) {
            Thread.currentThread().interrupt();
         }
      }

      this.bridgeOccupied = true;
      this.registrar.registerLeft(var1);
   }

   public synchronized void leaveLeft(Vehicle var1) {
      this.bridgeOccupied = false;
      this.registrar.deregisterLeft(var1);
      this.notifyAll();
   }

   public synchronized void leaveRight(Vehicle var1) {
      this.bridgeOccupied = false;
      this.registrar.deregisterRight(var1);
      this.notifyAll();
   }
}

