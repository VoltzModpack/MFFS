package resonantinduction.mechanical.mech

import codechicken.multipart.ControlKeyModifer
import io.netty.buffer.ByteBuf
import net.minecraft.block.material.Material
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraftforge.common.util.ForgeDirection
import resonant.content.spatial.block.SpatialTile
import resonant.engine.ResonantEngine
import resonant.lib.content.prefab.TRotatable
import resonant.lib.grid.node.TSpatialNodeProvider
import resonant.lib.network.ByteBufWrapper._
import resonant.lib.network.discriminator.PacketType
import resonant.lib.network.handle.{TPacketReceiver, TPacketSender}
import resonant.lib.transform.vector.Vector3
import resonantinduction.mechanical.mech.grid.NodeMechanical

import scala.collection.convert.wrapAll._

/** Prefab for resonantinduction.mechanical tiles
  *
  * @author Calclavia */
abstract class TileMechanical(material: Material) extends SpatialTile(material: Material) with TRotatable with TSpatialNodeProvider with TPacketSender with TPacketReceiver
{
  /** Node that handles most mechanical actions */
  private var _mechanicalNode: NodeMechanical = null

  def mechanicalNode = _mechanicalNode

  def mechanicalNode_=(newNode: NodeMechanical)
  {
    _mechanicalNode = newNode
    mechanicalNode.onVelocityChanged = () => sendPacket(1)
    nodes.removeAll(nodes.filter(_.isInstanceOf[NodeMechanical]))
    nodes.add(mechanicalNode)
  }

  /** External debug GUI */
  var frame: DebugFrameMechanical = null

  mechanicalNode = new NodeMechanical(this)

  override def update()
  {
    super.update()

    if (frame != null)
    {
      frame.update()

      if (!frame.isVisible)
      {
        frame.dispose()
        frame = null
      }
    }
  }

  override def setDirection(direction: ForgeDirection): Unit =
  {
    super.setDirection(direction)

    if (!world.isRemote)
      nodes.foreach(_.reconstruct())
  }

  override def use(player: EntityPlayer, side: Int, hit: Vector3): Boolean =
  {
    if (!world.isRemote)
    {
      println(mechanicalNode + " in " + mechanicalNode.grid)
      mechanicalNode.reconstruct()
    }

    //Debugging
    val itemStack: ItemStack = player.getHeldItem

    if (ResonantEngine.runningAsDev)
    {
      if (itemStack != null && !world.isRemote)
      {
        if (itemStack.getItem == Items.stick)
        {
          if (ControlKeyModifer.isControlDown(player))
          {
            if (frame == null)
            {
              frame = new DebugFrameMechanical(this)
              frame.showDebugFrame()
            }
            else
            {
              frame.closeDebugFrame()
              frame = null
            }
          }
        }
      }
    }
    return false
  }

  override def write(buf: ByteBuf, id: Int)
  {
    super.write(buf, id)

    id match
    {
      case 0 =>
      case 1 => buf <<< mechanicalNode.angularVelocity.toFloat
    }
  }

  override def read(buf: ByteBuf, id: Int, packetType: PacketType)
  {
    super.read(buf, id, packetType)

    id match
    {
      case 0 =>
      case 1 => mechanicalNode.angularVelocity = buf.readFloat()
    }
  }
}