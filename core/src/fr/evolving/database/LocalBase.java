package fr.evolving.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

import javax.xml.bind.DatatypeConverter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.DatabaseFactory;
import com.badlogic.gdx.sql.SQLiteGdxException;
import com.badlogic.gdx.utils.Array;

import fr.evolving.automata.Grid;
import fr.evolving.automata.Level;
import fr.evolving.automata.Transmuter;
import fr.evolving.database.Base.datatype;

public class LocalBase extends Base {
	private static Database dbHandler;
	private String databasename = "base.db";
    private String creation;
    
    //Contructeur de la base de donnée
    
    public LocalBase(datatype model,String param) {
    	super(model,param);
    	String[] params=param.split(":");
    	if (params.length>1)
    		databasename=params[1];
    	if (dbHandler!=null)
    		Gdx.app.log("Local", "Reprise de la base '"+databasename+"', table:"+model.toString());
    	else
    	{
    		Gdx.app.log("Local", "Utilisation de la base '"+databasename+"', table:"+model.toString());
    		dbHandler = DatabaseFactory.getNewDatabase(databasename,1, "", null);
    		dbHandler.setupDatabase();

    		try {
    			dbHandler.openOrCreateDatabase();
    		}
    		catch (SQLiteGdxException e) {
    			e.printStackTrace();
    		}
    	}
    	try {
    		if (model==datatype.statdata)
    			creation = "create table if not exists stat (id integer)";
    		else if (model==datatype.userdata) {
    			dbHandler.execSQL("CREATE TABLE if not exists locks(date DATETIME DEFAULT CURRENT_TIMESTAMP, level INTEGER NOT NULL, user INTEGER NOT NULL, PRIMARY KEY(level,user));");
    			dbHandler.execSQL("CREATE TABLE if not exists grids(date DATETIME DEFAULT CURRENT_TIMESTAMP, level INTEGER NOT NULL, user INTEGER NOT NULL, object TEXT, PRIMARY KEY(level,user,date));");
    			dbHandler.execSQL("CREATE TABLE if not exists transmuters(date DATETIME DEFAULT CURRENT_TIMESTAMP, user INTEGER NOT NULL, object TEXT, PRIMARY KEY(user));");
       			dbHandler.execSQL("CREATE TABLE if not exists research(date DATETIME DEFAULT CURRENT_TIMESTAMP, user INTEGER NOT NULL, value INT, PRIMARY KEY(user));");
       		 
    		}
    		else
    			dbHandler.execSQL("CREATE TABLE if not exists worlds(date DATETIME DEFAULT CURRENT_TIMESTAMP, desc TEXT NOT NULL, object TEXT, PRIMARY KEY(desc));");
    	} catch (SQLiteGdxException e) {
    		e.printStackTrace();
    	}
    }
    
    //Gestion model type gamedata

    public Array<String> getworlds() {
    	DatabaseCursor cursor=null;
    	try {
    		cursor = dbHandler.rawQuery("select desc,date from worlds;");
    	} catch (SQLiteGdxException e) {
    		return null;
    	}
    	Array<String> returnvalue=new Array<String>();
    	while (cursor.next())
    		returnvalue.add(cursor.getString(0));
    	return returnvalue;
    }

    public Array<Level> getworld(String description) {
    	DatabaseCursor cursor=null;
    	try {
    		cursor = dbHandler.rawQuery("select object from worlds where desc='"+description+"';");
    	} catch (SQLiteGdxException e) {
    		return null;
    	}
    	Level[] mc=null;
    	if (cursor.next())
    	try {
    		byte[] bytes = DatatypeConverter.parseBase64Binary(cursor.getString(0));
    		ByteArrayInputStream  bais = new ByteArrayInputStream(bytes);
    		ObjectInputStream ins = new ObjectInputStream(bais);
    		mc=(Level[]) ins.readObject();
    		ins.close();
        	return new Array<Level>(mc);
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
	
	public boolean deleteworld(String description) {
    	try {
    		dbHandler.rawQuery("delete from worlds where desc='"+description+"';");
    	} catch (SQLiteGdxException e) {
    		return false;
    	}
    	return true;
    }

    public boolean setworld(Array<Level> world, String description) {
    	String encoded = "";
    	try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(world.toArray());
            oos.flush();
            oos.close();
            bos.close();
            byte[] bytes = bos.toByteArray();
            encoded = DatatypeConverter.printBase64Binary(bytes);
    		dbHandler.rawQuery("replace into worlds (desc,object) values ('"+description+"','"+encoded+"');");
    	} catch (Exception e) {
				return false;
    	}
    	return true;
    }	
	
	//Gestion de données type userdata
	
	public boolean getlevellock(int user,int level) {
		DatabaseCursor cursor=null;
    	try {
    		cursor=dbHandler.rawQuery("select user from locks where user="+user+" and level="+level+";");
    	} catch (SQLiteGdxException e) {
    		return false;
    	}
    	if (cursor.next())
    		return true;
    	else return false;
    }
	
	public boolean setlevelunlock(int user,int level){
		try {
			dbHandler.rawQuery("insert into locks (user,level) values ("+user+","+level+");");
		} catch (SQLiteGdxException e) {
				return false;
		}
		return true;
	}
	
	public Array<Transmuter> getTransmuters(int user){
    	DatabaseCursor cursor=null;
    	try {
    		cursor = dbHandler.rawQuery("select object from transmuters where user="+user+";");
    	} catch (SQLiteGdxException e) {
    		return null;
    	}
    	Transmuter[] mc=null;
    	if (cursor.next())
    	try {
    		byte[] bytes = DatatypeConverter.parseBase64Binary(cursor.getString(0));
    		ByteArrayInputStream  bais = new ByteArrayInputStream(bytes);
    		ObjectInputStream ins = new ObjectInputStream(bais);
    		mc=(Transmuter[]) ins.readObject();
    		ins.close();
        	return new Array<Transmuter>(mc);
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
	
	public boolean setTransmuters(int user,Array<Transmuter> transmuters){
    	String encoded = "";
    	try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(transmuters.toArray());
            oos.flush();
            oos.close();
            bos.close();
            byte[] bytes = bos.toByteArray();
            encoded = DatatypeConverter.printBase64Binary(bytes);
    		dbHandler.rawQuery("replace into transmuters (user,object) values ("+user+",'"+encoded+"');");
    	} catch (Exception e) {
				return false;
    	}
    	return true;
	}
	
	
	public int getResearchpoint(int user){
		DatabaseCursor cursor=null;
		try {
			cursor = dbHandler.rawQuery("select value from research where user="+user+";");
		} catch (SQLiteGdxException e) {
			return 0;
		}
		if (cursor.next())
			return  cursor.getInt(0);
		else
			return 0;
	}
	
	public boolean setResearchpoint(int user, int point){
    	try {
    		dbHandler.rawQuery("replace into research (user,value) values ("+user+","+point+");");
    	} catch (Exception e) {
				return false;
    	}
    	return true;
	}
	
	public Grid getGrid(int user,int level,int place){
	   	DatabaseCursor cursor=null;
    	try {
    		cursor = dbHandler.rawQuery("select object from grids where user="+user+" and level="+level+" order by date desc limit "+place+",1;");
    	} catch (SQLiteGdxException e) {
    		return null;
    	}
    	Grid mc=null;
    	if (cursor.next())
    	try {
    		byte[] bytes = DatatypeConverter.parseBase64Binary(cursor.getString(0));
    		ByteArrayInputStream  bais = new ByteArrayInputStream(bytes);
    		ObjectInputStream ins = new ObjectInputStream(bais);
    		mc=(Grid) ins.readObject();
    		ins.close();
        	return mc;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
	
	public boolean setGrid(int user,int level, Grid data){
    	String encoded = "";
    	try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(data);
            oos.flush();
            oos.close();
            bos.close();
            byte[] bytes = bos.toByteArray();
            encoded = DatatypeConverter.printBase64Binary(bytes);
    		dbHandler.rawQuery("insert into grids (user,level,object) values ("+user+","+level+",'"+encoded+"');");
    	} catch (Exception e) {
				return false;
    	}
    	return true;
	}

	public Array<String> getGrids(int user, int level){
    	DatabaseCursor cursor=null;
    	try {
    		cursor = dbHandler.rawQuery("select date from grids order by date desc;");
    	} catch (SQLiteGdxException e) {
    		return null;
    	}
    	Array<String> returnvalue=new Array<String>();
    	while (cursor.next())
    		returnvalue.add(cursor.getString(0));
    	return returnvalue;
    }
	
	//Gestion type Stat
	
	
	//Commun
	
	public boolean Eraseall(datatype base){
    	try {
    			dbHandler.execSQL("drop table if exists stat;");
    			dbHandler.execSQL("drop table if exists locks;");
    			dbHandler.execSQL("drop table if exists grids;");
    			dbHandler.execSQL("drop table if exists global;");
    			dbHandler.execSQL("drop table if exists worlds;");
    			return true;
    	} catch (SQLiteGdxException e1) {
		return false;
	}
	}
	
	public void Close() {
		try {
			dbHandler.closeDatabase();
		} catch (SQLiteGdxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getprefix() {
		return "local";
	}
	
    public static boolean isHandling(datatype base){
		return true;
	}

}
