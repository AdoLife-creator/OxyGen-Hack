package com.oxygenclient.module.render;

import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

public class ESP extends Module {
    public ESP() {
        super("ESP", "See players through walls", Category.RENDER);
    }
}
