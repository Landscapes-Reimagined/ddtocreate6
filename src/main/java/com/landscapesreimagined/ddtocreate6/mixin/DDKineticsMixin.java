package com.landscapesreimagined.ddtocreate6.mixin;

import com.landscapesreimagined.ddtocreate6.replaced.DDStress;
import com.landscapesreimagined.ddtocreate6.util.mixin.StressValueProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uwu.lopyluna.create_dd.configs.DDConfigBase;
import uwu.lopyluna.create_dd.configs.server.DDKinetics;

@Mixin(DDKinetics.class)
public abstract class DDKineticsMixin extends DDConfigBase implements StressValueProvider {

    @Unique
    public DDStress ddtocreate6$stressValues;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void initMixin(CallbackInfo ci){
        this.ddtocreate6$stressValues = this.nested(1, DDStress::new, "Fine tune the kinetic stats of individual components");
    }

    @Unique
    public DDStress getDdtocreate6$stressValues() {
        return ddtocreate6$stressValues;
    }
}
