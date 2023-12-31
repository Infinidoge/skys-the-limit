package moe.inx.skys_the_limit.world;

import static moe.inx.skys_the_limit.TheSkysTheLimit.id;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import moe.inx.skys_the_limit.block.SkyBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class SkyFeature extends Feature<SkyFeatureConfig> {
    public static final Identifier ID = id("sky_feature");
    public static final SkyFeature INSTANCE = new SkyFeature(SkyFeatureConfig.CODEC);
    public static final SkyFeatureConfig CONFIG = new SkyFeatureConfig(300, SkyBlock.ID);
    public static final ConfiguredFeature<SkyFeatureConfig, SkyFeature> CONFIGURED = new ConfiguredFeature<>(
        INSTANCE,
        CONFIG
    );

    public SkyFeature(Codec<SkyFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeatureContext<SkyFeatureConfig> context) {
        SkyFeatureConfig config = context.getConfig();
        int height = config.height();
        Identifier blockId = config.blockId();

        BlockState blockState = Registries.BLOCK.get(blockId).getDefaultState();
        if (blockState == null)
            throw new IllegalStateException(blockId + " could not be parsed to a valid block identifier!");

        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        origin = origin.up(height - origin.getY());

        BlockPos testPos;
        boolean success = false;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                testPos = origin.add(x, 0, z);
                if (world.getBlockState(testPos).isOf(Blocks.AIR)) {
                    world.setBlockState(testPos, blockState, 0x10);
                    success = true;
                }
            }
        }
        return success;
    }
}

record SkyFeatureConfig(int height, Identifier blockId) implements FeatureConfig {
    public static final Codec<SkyFeatureConfig> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codecs.POSITIVE_INT.fieldOf("height").forGetter(SkyFeatureConfig::height),
            Identifier.CODEC.fieldOf("blockID").forGetter(SkyFeatureConfig::blockId)
        )
            .apply(instance, SkyFeatureConfig::new)
    );
}
