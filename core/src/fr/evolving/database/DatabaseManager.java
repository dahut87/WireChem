package fr.evolving.database;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.utils.Array;

public class DatabaseManager {
	private static Base[] bases;
	private static Array<Class<?>> backends;
	
	public Base user(){
		return bases[Base.datatype.userdata.ordinal()];
	}
	
	public Base stat(){
		return bases[Base.datatype.statdata.ordinal()];
	}
	
	public Base game(){
		return bases[Base.datatype.gamedata.ordinal()];
	}
	
	public DatabaseManager(){
		bases=new Base[3];
		backends=new Array<Class<?>>();
	}
	
	public void CloseAll() {
		for(Base base:bases)
			base.Close();
	}
	
	public boolean Attach(Base.datatype model, String Url) {
		if (bases[model.ordinal()]!=null)
			return false;
		Base backend=getBackend(model,Url);
		if (backend!=null) {
			bases[model.ordinal()]=backend;
			return true;
		}
		else
			return false;
	}

	public Base getBackend(Base.datatype model, String Url) {
		String Type=Url.split(":")[0];
		Class[] cArg = new Class[2]; 
		cArg[0] = Base.datatype.class; 
		cArg[1] = String.class; 
		for(Class<?> classe:backends) {
				Base back;
				try {
					back = (Base) classe.getDeclaredConstructor(cArg).newInstance(model,Url);
					if (back.getprefix().equals(Type))
						return back;
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
		
	}
	
	public void RegisterBackend(Class<?> classe) {
		backends.add(classe);
	}

}
