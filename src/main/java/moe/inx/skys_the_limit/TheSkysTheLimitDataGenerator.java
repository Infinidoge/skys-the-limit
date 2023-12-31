package moe.inx.skys_the_limit;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import moe.inx.skys_the_limit.world.SkyFeature;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.HolderLookup.Provider;
import net.minecraft.registry.HolderLookup.RegistryLookup;
import net.minecraft.registry.HolderProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistrySetBuilder;
import net.minecraft.world.gen.BootstrapContext;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

public class TheSkysTheLimitDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(WorldgenProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(RegistryKeys.CONFIGURED_FEATURE, WorldgenProvider::bootstrapConfigured);
		registryBuilder.add(RegistryKeys.PLACED_FEATURE, WorldgenProvider::bootstrapPlaced);
	}
}

final class WorldgenProvider extends FabricDynamicRegistryProvider {
	public WorldgenProvider(FabricDataOutput output, CompletableFuture<Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public String getName() {
		return TheSkysTheLimit.MODID;
	}

	public static final RegistryKey<ConfiguredFeature<?, ?>> SKY_CEILING_CONFIGURED = RegistryKey
		.of(RegistryKeys.CONFIGURED_FEATURE, SkyFeature.ID);
	public static final RegistryKey<PlacedFeature> SKY_CEILING_PLACED = RegistryKey
		.of(RegistryKeys.PLACED_FEATURE, SkyFeature.ID);

	public static void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		context.register(SKY_CEILING_CONFIGURED, SkyFeature.CONFIGURED);
	}

	public static void bootstrapPlaced(BootstrapContext<PlacedFeature> context) {
		HolderProvider<ConfiguredFeature<?, ?>> provider = context.lookup(RegistryKeys.CONFIGURED_FEATURE);
		context.register(
			SKY_CEILING_PLACED,
			new PlacedFeature(provider.getHolderOrThrow(SKY_CEILING_CONFIGURED), List.of())
		);
	}

	@Override
	protected void configure(Provider registries, Entries entries) {
		final RegistryLookup<ConfiguredFeature<?, ?>> registryConfigured = registries
			.getLookupOrThrow(RegistryKeys.CONFIGURED_FEATURE);
		final RegistryLookup<PlacedFeature> registryPlaced = registries
			.getLookupOrThrow(RegistryKeys.PLACED_FEATURE);

		entries.add(
			SKY_CEILING_CONFIGURED,
			registryConfigured.getHolderOrThrow(SKY_CEILING_CONFIGURED).value()
		);

		entries.add(SKY_CEILING_PLACED, registryPlaced.getHolderOrThrow(SKY_CEILING_PLACED).value());
	}
}
