package fr.evolving.database;

public class SqlBase extends Base {

	public SqlBase(datatype model, String param) {
		super(model, param);
	}

	public SqlBase() {
	}

	public String getprefix() {
		return "mysql";
	}

	public static boolean isHandling(datatype base) {
		if (base == datatype.statdata)
			return false;
		else
			return true;
	}
}