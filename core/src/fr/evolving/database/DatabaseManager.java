package fr.evolving.database;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.utils.Array;

public class DatabaseManager {
	private static Base[] bases;
	private static String[] old;
	private static Array<Class<?>> backends;

	public Base getType(Base.datatype model) {
		return bases[model.ordinal()];
	}

	public Base user() {
		return bases[Base.datatype.userdata.ordinal()];
	}

	public Base stat() {
		return bases[Base.datatype.statdata.ordinal()];
	}

	public Base game() {
		return bases[Base.datatype.gamedata.ordinal()];
	}

	public boolean verifyall() {
		return (bases[0] != null && bases[1] != null && bases[2] != null);
	}

	public DatabaseManager() {
		bases = new Base[3];
		old = new String[3];
		backends = new Array<Class<?>>();
	}

	public void CloseAll() {
		for (int i = 0; i < 3; i++)
			if (bases[i] != null) {
				bases[i].Close();
				bases[i] = null;
			}
	}

	public String getOld(Base.datatype model) {
		return old[model.ordinal()];
	}

	public boolean Attach(Base.datatype model, String Url) {
		if (bases[model.ordinal()] != null || model == null || Url == null)
			return false;
		Base backend = getBackend(model, Url);
		if (backend != null) {
			bases[model.ordinal()] = backend;
			old[model.ordinal()] = Url;
			return true;
		} else {
			bases[model.ordinal()] = null;
			old[model.ordinal()] = null;
			return false;
		}
	}

	public Base getBackend(Base.datatype model, String Url) {
		String Type = Url.split(":")[0];
		Class[] cArg = new Class[2];
		cArg[0] = Base.datatype.class;
		cArg[1] = String.class;
		for (Class<?> classe : backends) {
			Base back;
			try {
				back = (Base) classe.newInstance();
				if (back.getPrefix().equals(Type)) {
					back = (Base) classe.getDeclaredConstructor(cArg)
							.newInstance(model, Url);
					return back;
				}
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public boolean isBackend(Base.datatype model, String Url) {
		String Type = Url.split(":")[0];
		for (Class<?> classe : backends) {
			Base back;
			try {
				back = (Base) classe.newInstance();
				if (back.getPrefix().equals(Type))
					return true;
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public void RegisterBackend(Class<?> classe) {
		backends.add(classe);
	}

}
