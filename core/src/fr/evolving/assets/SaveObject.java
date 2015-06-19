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

    public static Level[] initObject()
    {
    	Level[] thelevels=new Level[35];
    	
    	thelevels[0]=new Level(
    	0,
    	0,
    	"Introduction",
    	"Prise en main de l'interface de WireChem{#169} et amener l'électron neutre sur le senseur.",
    	"e0",
    	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	new int[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	38f,
    	740f,
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
    	
	thelevels[1]=new Level(
	0,
	1,
	"Trajectoires",
	"Comprendre les trajectoires empruntées par les électrons afin de mieux appréhender la conception de systèmes.",
	"e0",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	38f,
	260f,
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
	new int[][]{{0, 2}});
	
	thelevels[2]=new Level(
	0,
	2,
	"Pistes",
	"Utiliser des pistes afin de réaliser un circuit qui permet l'arrivée d'un électron neutre sur le senseur.",
	"e0",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	180f,
	460f,
	-1,
	15,
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
	new int[][]{{0, 3}});
	
	
	thelevels[3]=new Level(
	0,
	3,
	"Positiveur",
	"Comprendre le fonctionnement de l'élément positiveur et générer 8 électrons positifs sur le senseur.",
	"e+",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0},
	380f,
	550f,
	0,
	16,
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
	new int[][]{{0, 4}});	
	
	thelevels[4]=new Level(
	0,
	4,
	"Super-électrons",
	"Générer 2 super-électrons negatifs par colision et les amener sur le senseur.",
	"E-",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	590f,
	550f,
	0,
	16,
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
	new int[][]{{0, 5}});		
	
	thelevels[5]=new Level(
	0,
	5,
	"Activation",
	"L'objectif est de générer 6 électrons neutres et de découvrir les liaisons de fibres par lesquelles transitent les photons. Certains modifieurs nécessitent désormais l'activation par des photons.",
	"e0",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	590f,
	220f,
	1,
	136,
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
	new int[][]{{0, 6}});

	thelevels[6]=new Level(
	0,
	6,
	"Fibres",
	"A vous de dessiner votre infrastructure à base de fibres et de pistes mais aussi de modifieur activable afin de faire parvenir des éléctrons positifs et neutres sur les senseurs.",
	"eX",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0},
	790f,
	220f,
	2,
	230,
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
	new int[][]{{0, 7}});		

	
	thelevels[7]=new Level(
	0,
	7,
	"Protons",
	"Générer deux protons sur le senseur en utilisant un réacteur et un super-positron. Le réacteur est un élément qui nécessite l'activation.",
	"p",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
	950f,
	400f,
	2,
	370,
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
	new int[][]{{0, 8}});		
	
	thelevels[8]=new Level(
	0,
	8,
	"Hydrogène",
	"Générer le premier atome complet : l'hydrogène avec un proton et un électron négatif sur la couche K.",
	"H",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
	1050f,
	740f,
	3,
	750,
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
	true,
	new int[][]{{1, 0},{1, 2}});		
	
	thelevels[9]=new Level(
	1,
	0,
	"Deutérium",
	"Générer le Deutérium, un isostope de l'hydrogène, celui-ci comporte un neutron en plus du proton et de l'électron négatif sur la couche K.",
	"D",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1},
	30f,
	700f,
	4,
	1100,
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
	new int[][]{{1, 1}});	
	
	thelevels[10]=new Level(
	1,
	1,
	"Tritium",
	"Générer le second isostope de l'hydrogène : cet atome plus lourd que le Deutérium comporte alors deux neutrons, un proton ainsi qu'un électron négatif sur la couche K.",
	"T",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 1},
	420f,
	750f,
	4,
	1000,
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
	new int[][]{{}});	
	
	thelevels[11]=new Level(
	1,
	2,
	"Hélium-4",
	"L'isotope le plus courant de l'Hélium dans l'atmosphère terrestre est l'Hélium-4. Générer le en associant deux protons, deux neutrons et deux électrons négatifs sur la couche K.",
	"He",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 2},
	30f,
	350f,
	4,
	1200,
	new Grid(20,20),
	0,
	0,
	0,
	0,
	800,
	99999,
	99999,
	99999,
	"",
	false,
	new int[][]{{1, 3},{1, 5}});	
	
	thelevels[12]=new Level(
	1,
	3,
	"Hélium-3",
	"Générer le seul autre isotope stable de l'hélium, ce dernier n'étant présent qu'à l'état de traces sur Terre. Cet isotope comporte deux protons,un neutron et deux électrons négatifs sur la couche K.",
	"He",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 2},
	180f,
	230f,
	4,
	1300,
	new Grid(20,20),
	0,
	0,
	0,
	0,
	700,
	99999,
	99999,
	99999,
	"",
	false,
	new int[][]{{1, 4}});	
	
	thelevels[13]=new Level(
	1,
	4,
	"Hélium-8",
	"l'Hélium-8 est le plus étudié des isotopes lourds de l'Hélium avec l'Hélium-6. Cet isostope comporte deux protons, six neutrons et deux électrons négatifs sur la couche K.",
	"He",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 6, 2},
	420f,
	230f,
	5,
	1500,
	new Grid(20,20),
	0,
	0,
	0,
	0,
	1800,
	99999,
	99999,
	99999,
	"",
	false,
	new int[][]{{}});	
	
	thelevels[14]=new Level(
	1,
	5,
	"Lithium",
	"Générer un isostope de l'hydrogène qui comporte un proton et un neutron ainsi qu'un électron négatif sur la couche K.",
	"Li",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1},
	300f,
	490f,
	4,
	1200,
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
	new int[][]{{1, 6}});	
	
	thelevels[15]=new Level(
	1,
	6,
	"Carbone",
	"Générer un isostope de l'hydrogène qui comporte un proton et un neutron ainsi qu'un électron négatif sur la couche K.",
	"C",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1},
	550f,
	490f,
	4,
	1200,
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
	new int[][]{{1, 7}});	
	
	thelevels[16]=new Level(
	1,
	7,
	"Oxygène",
	"Générer un isostope de l'hydrogène qui comporte un proton et un neutron ainsi qu'un électron négatif sur la couche K.",
	"O",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1},
	800f,
	570f,
	4,
	1200,
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
	new int[][]{{1, 8}});	
	
	thelevels[17]=new Level(
	1,
	8,
	"Néon",
	"Générer le premier atome complet : l'hydrogène avec un proton et un électron négatif sur la couche K.",
	"Ne",
	new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	new int[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1},
	1000f,
	750f,
	6,
	1200,
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
	true,
	new int[][]{{}});	
	
	
	
	
	return thelevels;
}


}
