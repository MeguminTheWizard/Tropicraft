package net.tropicraft.block;

import net.minecraft.block.material.Material;
import net.tropicraft.info.TCNames;
import net.tropicraft.registry.TCCreativeTabRegistry;

public class BlockTropicraftPlank extends BlockTropicraftMulti {

	public BlockTropicraftPlank(String[] names) {
		super(names, Material.wood);
		this.setBlockTextureName(TCNames.plank);
		this.disableStats();
		this.setHardness(2.0F);
		this.setStepSound(soundTypeWood);
		this.setCreativeTab(TCCreativeTabRegistry.tabBlock);
	}

}
