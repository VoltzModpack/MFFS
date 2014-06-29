package mffs.item.module;

import mffs.base.ItemMFFS;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import resonant.api.mffs.IFieldInteraction;
import resonant.api.mffs.IProjector;
import resonant.api.mffs.modules.IModule;
import resonant.lib.utility.LanguageUtility;
import universalelectricity.api.UnitDisplay;
import universalelectricity.core.transform.vector.Vector3;

import java.util.List;
import java.util.Set;

public class ItemModule extends ItemMFFS implements IModule
{
	private float fortronCost = 0.5f;

	public ItemModule(int id, String name)
	{
		super(id, name);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
	{
		info.add(LanguageUtility.getLocal("info.item.fortron") + " " + new UnitDisplay(UnitDisplay.Unit.LITER, getFortronCost(1) * 20) + "/s");
		super.addInformation(itemStack, player, info, b);
	}

	@Override
	public void onPreCalculate(IFieldInteraction projector, Set<Vector3> position)
	{

	}

	@Override
	public void onCalculate(IFieldInteraction projector, Set<Vector3> position)
	{
	}

	@Override
	public boolean onProject(IProjector projector, Set<Vector3> fields)
	{
		return false;
	}

	@Override
	public int onProject(IProjector projector, Vector3 position)
	{
		return 0;
	}

	@Override
	public boolean onCollideWithForceField(World world, int x, int y, int z, Entity entity, ItemStack moduleStack)
	{
		return true;
	}

	public ItemModule setCost(float cost)
	{
		this.fortronCost = cost;
		return this;
	}

	@Override
	public ItemModule setMaxStackSize(int par1)
	{
		super.setMaxStackSize(par1);
		return this;
	}

	@Override
	public float getFortronCost(float amplifier)
	{
		return this.fortronCost;
	}

	@Override
	public boolean onDestroy(IProjector projector, Set<Vector3> field)
	{
		return false;
	}

	@Override
	public boolean requireTicks(ItemStack moduleStack)
	{
		return false;
	}
}