package com.finallion.graveyard.mixin;


import com.finallion.graveyard.init.TGStructures;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.LakeFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LakeFeature.class)
public class LakeFeatureMixin {


    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    private void generateNoLakes(FeatureContext<LakeFeature.Config> context, CallbackInfoReturnable<Boolean> info) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        ChunkSectionPos chunkPos = ChunkSectionPos.from(blockPos);
        for (StructureFeature<?> structure : TGStructures.structures) {
            if (structureWorldAccess.getStructures(chunkPos, structure).stream().findAny().isPresent()) {
                info.setReturnValue(false);
            }
        }
    }
}
