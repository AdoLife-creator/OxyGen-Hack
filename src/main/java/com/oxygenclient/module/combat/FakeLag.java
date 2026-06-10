package com.oxygenclient.module.combat;

import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.network.packet.Packet;

public class FakeLag extends Module {
    private final Queue<Packet<?>> packetQueue = new ConcurrentLinkedQueue<>();
    private int lagDuration = 200;
    private long lastFlush = 0;

    public FakeLag() {
        super("FakeLag", "Sahte lag yarat - sen vurursun ama onlar vuramaz", Category.COMBAT);
    }

    @Override
    public void onTick() {
        long now = System.currentTimeMillis();
        
        if (now - lastFlush >= lagDuration) {
            // Birikmiş packetleri gönder
            flushPackets();
            lastFlush = now;
        }
    }

    public void queuePacket(Packet<?> packet) {
        packetQueue.add(packet);
    }

    private void flushPackets() {
        if (mc.getNetworkHandler() == null) return;
        
        Packet<?> packet;
        while ((packet = packetQueue.poll()) != null) {
            mc.getNetworkHandler().sendPacket(packet);
        }
    }

    @Override
    public void onDisable() {
        flushPackets();
    }

    private static net.minecraft.client.MinecraftClient mc() {
        return net.minecraft.client.MinecraftClient.getInstance();
    }
}
