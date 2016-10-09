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
			this.setText(String.valueOf(worlds.getLevelData().Tech));
			this.setVisible(worlds.getLevelData().Tech>=1 || worlds.isDebug());
			break;
		case cout:
			worlds.getLevelData().Cout=worlds.getLevelData().Cout_orig-worlds.getLevelData().Cout_copperfiber-worlds.getLevelData().Cout_transmuter;
			this.setText(String.valueOf(worlds.getLevelData().Cout));
			if (worlds.getLevelData().Cout>0.25*worlds.getLevelData().Cout_orig)
				this.setColor(1f, 1f, 1f, 1f);
			else if  (worlds.getLevelData().Cout>0) 
				this.setColor(1f, 0.5f, 0.5f, 1f);
			else 
				this.setColor(1f, 0, 0, 1f);
			this.setVisible(worlds.getLevelData().Cout_orig>0 || worlds.isDebug());
			break;
		case research:
			this.setText(String.valueOf(worlds.ReadResearch()));
			this.setVisible((worlds.getLevelData().Tech>=1 && worlds.ReadResearch()>0) || worlds.isDebug());
			break;
		case cycle:
			this.setVisible(worlds.getWorld()>=1 || worlds.isDebug());
			if (showmaximum)
				this.setText(String.valueOf(worlds.getLevelData().Cycle)+"/"+String.valueOf(worlds.getLevelData().Maxcycle));	
			else
				this.setText(String.valueOf(worlds.getLevelData().Cycle));
			break;
		case temp:
			this.setVisible(worlds.getWorld()>=2 || worlds.isDebug());
			if (showmaximum)
				this.setText(String.valueOf((int)worlds.getLevelData().Temp)+"/"+String.valueOf(worlds.getLevelData().Maxtemp));	
			else
				this.setText(String.valueOf((int)worlds.getLevelData().Temp));
			break;
		case rayon:
			this.setVisible(worlds.getWorld()>=3 || worlds.isDebug());
			if (showmaximum)
				this.setText(String.valueOf((int)worlds.getLevelData().Rayon)+"/"+String.valueOf(worlds.getLevelData().Maxrayon));	
			else
				this.setText(String.valueOf((int)worlds.getLevelData().Rayon));
			break;
		case nrj:
			this.setVisible(worlds.getWorld()>=4 || worlds.isDebug());
			if (showmaximum)
				this.setText(String.valueOf((int)worlds.getLevelData().Nrj)+"/"+String.valueOf(worlds.getLevelData().Maxnrj));	
			else
				this.setText(String.valueOf((int)worlds.getLevelData().Nrj));
		break;
		}
		
	}
}
