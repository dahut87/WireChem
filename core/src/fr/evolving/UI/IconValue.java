package fr.evolving.UI;

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import fr.evolving.automata.Worlds;

public class IconValue extends ImageTextButton{
	
	public enum Icon{tech,cout,research,cycle,temp,rayon,nrj};
	Icon icon;
	boolean showmaximum;
	Worlds worlds;
	
	public IconValue(Icon icon,Worlds worlds, Skin skin) {
		super("", skin,icon.toString()+"2");
		this.icon=icon;
		this.worlds=worlds;
	}
	
	public void SetShowMaximum(boolean value)
	{
		showmaximum=value;
	}
	
	@Override
	public void act(float delta)
	{
		switch(this.icon) {
		case tech:
			this.setText(String.valueOf(worlds.getInformations().Tech));
			this.setVisible(worlds.getInformations().Tech>=1);
			break;
		case cout:
			this.setText(String.valueOf(worlds.getInformations().Cout));
			this.setVisible(worlds.getInformations().Cout>0);
			break;
		case research:
			this.setText(String.valueOf(worlds.ReadResearch()));
			this.setVisible(worlds.ReadResearch()>0);
			break;
		case cycle:
			this.setVisible(worlds.getWorld()>=1);
			if (showmaximum)
				this.setText(String.valueOf(worlds.getInformations().Cycle)+"/"+String.valueOf(worlds.getInformations().Maxcycle));	
			else
				this.setText(String.valueOf(worlds.getInformations().Cycle));
			break;
		case temp:
			this.setVisible(worlds.getWorld()>=2);
			if (showmaximum)
				this.setText(String.valueOf(worlds.getInformations().Temp)+"/"+String.valueOf(worlds.getInformations().Maxtemp));	
			else
				this.setText(String.valueOf(worlds.getInformations().Temp));
			break;
		case rayon:
			this.setVisible(worlds.getWorld()>=3);
			if (showmaximum)
				this.setText(String.valueOf(worlds.getInformations().Rayon)+"/"+String.valueOf(worlds.getInformations().Maxrayon));	
			else
				this.setText(String.valueOf(worlds.getInformations().Rayon));
			break;
		case nrj:
			this.setVisible(worlds.getWorld()>=4);
			if (showmaximum)
				this.setText(String.valueOf(worlds.getInformations().Nrj)+"/"+String.valueOf(worlds.getInformations().Maxnrj));	
			else
				this.setText(String.valueOf(worlds.getInformations().Nrj));
		break;
		}
		
	}
}
