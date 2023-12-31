package moe.inx.skys_the_limit;

import moe.inx.skys_the_limit.block.SkyBlock;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class TheSkysTheLimitClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(SkyBlock.INSTANCE, RenderLayer.getTranslucent());
	}
}
