package com.cryotron.skyspaceproject.networking.energyshield;

@SuppressWarnings("resource")
public class ClientEnergyShieldData {
    private static float currES;
    private static float recTimer;

    public static void set(float currES, float recTimer) {
    	ClientEnergyShieldData.currES = currES;
    	ClientEnergyShieldData.recTimer = recTimer;
    }

    public static float getcurrES() {
        return currES;
    }

    public static float getrecTimer() {
        return recTimer;
    }
}
