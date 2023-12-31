package moe.inx.skys_the_limit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import moe.inx.skys_the_limit.block.SkyBlock;
import moe.inx.skys_the_limit.world.SkyFeature;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;

public class TheSkysTheLimit implements ModInitializer {
	public static final String MODID = "skys-the-limit";

	public static Identifier id(String id) {
		return new Identifier(MODID, id);
	}

	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		Registry.register(Registries.BLOCK, SkyBlock.ID, SkyBlock.INSTANCE);
		Registry.register(Registries.FEATURE, SkyFeature.ID, SkyFeature.INSTANCE);

		BiomeModifications.addFeature(
			BiomeSelectors.foundInOverworld(),
			GenerationStep.Feature.RAW_GENERATION,
			RegistryKey.of(RegistryKeys.PLACED_FEATURE, SkyFeature.ID)
		);
	}
}
