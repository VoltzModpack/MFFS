package com.builtbroken.mffs.production;

import com.builtbroken.jlib.data.science.units.UnitDisplay;
import com.builtbroken.mc.core.Engine;
import com.builtbroken.mc.core.network.packet.PacketTile;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.builtbroken.mffs.base.GuiMFFS;
import com.builtbroken.mffs.base.TilePacketType;
import com.builtbroken.mffs.render.button.GuiTransferModeButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class GuiFortronCapacitor extends GuiMFFS<TileFortronCapacitor>
{
    public GuiFortronCapacitor(EntityPlayer player, TileFortronCapacitor tile)
    {
        super(new ContainerFortronCapacitor(player, tile), tile);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.buttonList.add(new GuiTransferModeButton(1, this.width / 2 - 30, this.height / 2 - 30, this, this.tile));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        fontRendererObj.drawString(tile.getInventoryName(), this.xSize / 2 - fontRendererObj.getStringWidth(tile.getInventoryName()) / 2, 6, 4210752);
        GL11.glPushMatrix();
        GL11.glRotatef(-90, 0, 0, 1);
        drawTextWithTooltip("upgrade", -95, 140, x, y);
        GL11.glPopMatrix();
        drawTextWithTooltip("linkedDevice", "%1: " + tile.getDeviceCount(), 8, 20, x, y);
        drawTextWithTooltip("transmissionRate", "%1: " + new UnitDisplay(UnitDisplay.Unit.LITER, tile.getTransmissionRate() * 20).symbol() + "/s", 8, 32, x, y);
        drawTextWithTooltip("range", "%1: " + tile.getTransmissionRange(), 8, 44, x, y);
        drawTextWithTooltip("input", ChatFormatting.DARK_GREEN + "%1", 12, 62, x, y);
        drawTextWithTooltip("output", ChatFormatting.RED + "%1", 92, 62, x, y);
        drawFortronText(x, y);
        super.drawGuiContainerForegroundLayer(x, y);
    }


    @Override
    protected void actionPerformed(GuiButton guiButton)
    {
        super.actionPerformed(guiButton);

        if (guiButton.id == 1)
        {
            Engine.instance.packetHandler.sendToAll(new PacketTile(this.tile, TilePacketType.toggleMoe.ordinal()));
        }
    }
}