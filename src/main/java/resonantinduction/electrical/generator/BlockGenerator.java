package resonantinduction.electrical.generator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import resonantinduction.core.Reference;
import resonantinduction.core.prefab.block.BlockRIRotatable;
import resonantinduction.core.render.RIBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGenerator extends BlockRIRotatable
{
	public BlockGenerator()
	{
		super("generator");
		setTextureName(Reference.PREFIX + "material_steel");
	}

	@Override
	public boolean onUseWrench(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof TileGenerator)
		{
			if (!world.isRemote)
			{
				((TileGenerator) tileEntity).isInversed = !((TileGenerator) tileEntity).isInversed;
				entityPlayer.addChatMessage("Generator now producing " + (((TileGenerator) tileEntity).isInversed ? "mechanical" : "electrical") + " energy.");
			}

			return true;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileGenerator();
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderType()
	{
		return RIBlockRenderingHandler.ID;
	}
}
