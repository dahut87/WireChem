package fr.evolving.assets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.evolving.automata.Cell;
import fr.evolving.automata.Grid;
import fr.evolving.automata.Level;

public class SaveObject {
	
	private static String DB_DRIVER = "";
	private static String DB_CONNECTION = "";
	private static String DB_USER = "";
	private static String DB_PASSWORD = "";

    public SaveObject(String DRIVER, String CONNECTION, String USER, String PASSWORD) {
       	DB_DRIVER=DRIVER;
    	DB_CONNECTION=CONNECTION;
    	DB_USER=USER;
    	DB_PASSWORD=PASSWORD;
	}

    public void saveObject(Object javaObject) throws Exception
    {
        try{
        Connection conn = getDBConnection();
        PreparedStatement ps=null;
        String sql=null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(javaObject);
        oos.flush();
        oos.close();
        bos.close();
        byte[] data = bos.toByteArray();
        sql="insert into worlds (javaObject) values(?)";
        ps=conn.prepareStatement(sql);
        ps.setObject(1, data);
        ps.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public Object getObject(Object javaObject) throws Exception
    {
        Connection conn=getDBConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        Object mc =null;
        String sql="select * from worlds where id=1";
        ps=conn.prepareStatement(sql);
        rs=ps.executeQuery();
        if(rs.next())
        {
            ByteArrayInputStream bais;
            ObjectInputStream ins;
            try {
            bais = new ByteArrayInputStream(rs.getBytes("javaObject"));
            ins = new ObjectInputStream(bais);
            mc = ins.readObject();
            ins.close();
            }
            catch (Exception e) {
            e.printStackTrace();
            }
        }
        return mc;
    }
    
    private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		}
		catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION,DB_USER,DB_PASSWORD);
			return dbConnection;
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

    public Level[] initObject()
    {
    	Level[] thelevels=new Level[9];
    	
    	thelevels[0]=new Level("Introduction",
    	"Prise en main de l'interface de WireChem{#169} et amener l'électron neutre sur le senseur.",
    	"e0",
    	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	38f,
    	450f,
    	-1,
    	0,
    	new Grid(20,20),
    	0,
    	0,
    	0,
    	0,
    	99999,
    	99999,
    	99999,
    	99999,
    	"",
    	false,
    	new int[][]{{0, 1}, {0, 8}});
    	return thelevels;
    }

}
