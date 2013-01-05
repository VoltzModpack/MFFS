package liquidmechanics.common.handlers;

import liquidmechanics.api.helpers.PipeColor;
import net.minecraftforge.liquids.LiquidStack;

public class LiquidData
{
    private boolean isAGas;
    private int defaultPressure;
    private LiquidStack sampleStack;
    private String name;
    private PipeColor color;

    public LiquidData(String name, LiquidStack stack,PipeColor color, boolean gas, int dPressure)
    {
        this.sampleStack = stack;
        this.isAGas = gas;
        this.defaultPressure = dPressure;
        this.name = name;
        this.color = color;
    }

    public String getName()
    {
        if (name != null || !name.equalsIgnoreCase("")) { return name; }
        return "unknown";
    }
    public int getPressure()
    {
        return defaultPressure;
    }
    public LiquidStack getStack()
    {
        if (sampleStack != null) { return sampleStack; }
        return new LiquidStack(0,1);
    }
    public boolean getCanFloat()
    {
        return isAGas;
    }
    public PipeColor getColor()
    {
        if (color != null) { return color; }
        return PipeColor.NONE;
    }
    
}
