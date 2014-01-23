package resonantinduction.mechanical.fluid.pump;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import resonantinduction.core.Reference;
import resonantinduction.core.prefab.block.BlockRIRotatable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGrate extends BlockRIRotatable
{
	private Icon drainIcon;
	private Icon fillIcon;

	public BlockGrate()
	{
		super("grate");
		rotationMask = 0b111111;
	}

	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		return new TileGrate();
	}

	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		this.drainIcon = iconRegister.registerIcon(Reference.PREFIX + "grate_drain");
		this.fillIcon = iconRegister.registerIcon(Reference.PREFIX + "grate_fill");
		super.registerIcons(iconRegister);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public Icon getIcon(int side, int metadata)
	{
		if (side == metadata)
		{
			return blockIcon;
		}

		return drainIcon;
	}

	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		ForgeDirection dir = ForgeDirection.getOrientation(side);

		if (entity instanceof TileGrate)
		{
			if (dir == ((TileGrate) entity).getDirection())
			{
				return blockIcon;
			}
		}

		return ((TileGrate) entity).canDrain() ? drainIcon : fillIcon;
	}

	@Override
	public boolean onSneakUseWrench(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			TileEntity tile = world.getBlockTileEntity(x, y, z);

			if (tile instanceof TileGrate)
			{
				((TileGrate) tile).canDrain = !((TileGrate) tile).canDrain;
				entityPlayer.sendChatToPlayer(ChatMessageComponent.createFromText("Drain fluid mode: " + ((TileGrate) tile).canDrain()));
			}

			return true;
		}

		return true;
	}
}
