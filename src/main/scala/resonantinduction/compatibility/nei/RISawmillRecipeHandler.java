package resonantinduction.compatibility.nei;

import resonant.lib.utility.LanguageUtility;
import resonantinduction.core.RecipeType;

public class RISawmillRecipeHandler extends RITemplateRecipeHandler
{

	@Override
	public String getRecipeName()
	{
		return LanguageUtility.getLocal("resonantinduction.machine.sawmill");
	}

	@Override
	public RecipeType getMachine()
	{
		return RecipeType.SAWMILL;
	}
}
