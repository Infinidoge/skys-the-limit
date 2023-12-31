package moe.inx.skys_the_limit.block;

import static moe.inx.skys_the_limit.TheSkysTheLimit.id;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class SkyBlock extends Block {
	public static final Identifier ID = id("sky_block");
	public static final Block INSTANCE = new SkyBlock(
		FabricBlockSettings.create()
			.nonOpaque() // Transparent block
			.strength(3.0f, 1200f) // Takes a little while to break, and blast resistant
			.dropsNothing()
			.solid()
			.withoutDustParticles()
			.allowsSpawning(Blocks::nonSpawnable)
			.pistonBehavior(PistonBehavior.BLOCK)
	);

	public SkyBlock(AbstractBlock.Settings settings) {
		super(settings);
	}

	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 1.0F;
	}
}
