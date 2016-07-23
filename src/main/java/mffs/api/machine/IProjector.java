package mffs.api.machine;

import com.builtbroken.mc.lib.transform.vector.Pos;
import mffs.api.fortron.FrequencyGridRegistry;
import net.minecraft.inventory.IInventory;

import java.util.List;

/**
 * Also extends IDisableable, IFortronFrequency
 *
 * @author Calclavia
 */
public abstract interface IProjector extends IInventory, IFieldMatrix, FrequencyGridRegistry.IBlockFrequency
{
	/**
	 * Projects the force field.
	 */
	public void projectField();

	/**
	 * Destroys the force field.
	 */
	public void destroyField();

	/**
	 * @return The speed in which a force field is constructed.
	 */
	public int getProjectionSpeed();

	/**
	 * @return The amount of ticks this projector has existed in the world.
	 */
	public long getTicks();

	/**
	 * DO NOT modify this list. Read-only.
	 *
	 * @return The actual force field block coordinates in the world.
	 */
	public List<Pos> getForceFields();

}