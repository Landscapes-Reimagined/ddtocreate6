package com.landscapesreimagined.ddtocreate6.mixin;

import com.landscapesreimagined.ddtocreate6.replaced.DDStress;
import com.landscapesreimagined.ddtocreate6.util.mixin.StressValueProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import uwu.lopyluna.create_dd.configs.DDConfigBase;
import uwu.lopyluna.create_dd.configs.server.DDKinetics;

@Mixin(DDKinetics.class)
public abstract class DDKineticsMixin extends DDConfigBase implements StressValueProvider {

    @Unique
    public final DDStress ddtocreate6$stressValues = this.nested(1, DDStress::new, "Fine tune the kinetic stats of individual components");

    @Unique
    public DDStress getDdtocreate6$stressValues() {
        return ddtocreate6$stressValues;
    }
}
