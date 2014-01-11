package resonantinduction.archaic.engineering;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import resonantinduction.core.prefab.block.BlockRI;
import universalelectricity.api.vector.Vector2;
import universalelectricity.api.vector.Vector3;
import calclavia.lib.utility.InventoryUtility;
import codechicken.multipart.ControlKeyModifer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * A world-based crafting table.
 * 
 * TODO: Filter support, inventory seek support.
 * 
 * @author Calclavia
 */
public class BlockEngineeringTable extends BlockRI
{
	@SideOnly(Side.CLIENT)
	private Icon workbenchIconTop;
	@SideOnly(Side.CLIENT)
	private Icon workbenchIconFront;

	public BlockEngineeringTable()
	{
		super("engineeringTable");
		setTextureName("crafting_table");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int hitSide, float hitX, float hitY, float hitZ)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);

		if (te instanceof TileEngineeringTable)
		{
			TileEngineeringTable tile = (TileEngineeringTable) te;

			if (hitSide == 1)
			{
				if (!world.isRemote)
				{

					ItemStack current = player.inventory.getCurrentItem();

					Vector2 hitVector = new Vector2(hitX, hitZ);
					double regionLength = 1d / 3d;

					/**
					 * Crafting Matrix
					 */
					matrix:
					for (int j = 0; j < 3; j++)
					{
						for (int k = 0; k < 3; k++)
						{
							Vector2 check = new Vector2(j, k).scale(regionLength);

							if (check.distance(hitVector) < regionLength)
							{
								int slotID = j * 3 + k;
								ItemStack checkStack = tile.craftingMatrix[slotID];

								if (checkStack != null)
								{
									InventoryUtility.dropItemStack(world, new Vector3(player), checkStack, 0);
									tile.craftingMatrix[slotID] = null;
								}
								else if (current != null)
								{
									if (ControlKeyModifer.isControlDown(player))
									{
										tile.craftingMatrix[slotID] = current.splitStack(1);
									}
									else
									{
										tile.craftingMatrix[slotID] = current;
										current = null;
									}

									if (current == null || current.stackSize <= 0)
									{
										player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
									}
								}

								break matrix;
							}
						}
					}

					tile.onInventoryChanged();

				}

				return true;
			}
			else if (hitSide != 0)
			{
				ItemStack output = tile.getStackInSlot(9);

				if (output != null)
				{
					InventoryUtility.dropItemStack(world, new Vector3(player), output, 0);
					tile.onPickUpFromSlot(player, 9, output);
					tile.setInventorySlotContents(9, null);
				}
			}
		}

		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? this.workbenchIconTop : (par1 == 0 ? Block.planks.getBlockTextureFromSide(par1) : (par1 != 2 && par1 != 4 ? this.blockIcon : this.workbenchIconFront));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon(this.getTextureName() + "_side");
		this.workbenchIconTop = par1IconRegister.registerIcon(this.getTextureName() + "_top");
		this.workbenchIconFront = par1IconRegister.registerIcon(this.getTextureName() + "_front");
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEngineeringTable();
	}
}
