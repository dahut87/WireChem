package fr.evolving.assets;

import com.badlogic.gdx.utils.Array;

import fr.evolving.automata.Grid;
import fr.evolving.automata.Level;

public class InitWorlds {
	public static Array<Level> go() {
		Level[] thelevels = new Level[45];

		thelevels[0] = new Level(
				0,
				0,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"e0", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 38f, 740f,
				-1, 0, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999, 99999,
				99999, "", false, new int[][] { { 0, 1 }, { 0, 8 } });

		thelevels[1] = new Level(
				0,
				1,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"e0", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 38f, 260f,
				-1, 0, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999, 99999,
				99999, "", false, new int[][] { { 0, 2 } });

		thelevels[2] = new Level(
				0,
				2,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"e0", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 180f,
				460f, -1, 15, new Grid(10, 3), 0, 0, 0, 0, 99999, 99999, 99999,
				99999, "", false, new int[][] { { 0, 3 } });

		thelevels[3] = new Level(
				0,
				3,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"e+", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0 }, 380f,
				550f, 0, 16, new Grid(30, 20), 0, 0, 0, 0, 99999, 99999, 99999,
				99999, "", false, new int[][] { { 0, 4 } });

		thelevels[4] = new Level(
				0,
				4,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"E-", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 590f,
				550f, 0, 16, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999, 99999,
				99999, "", false, new int[][] { { 0, 5 } });

		thelevels[5] = new Level(
				0,
				5,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"e0", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 590f,
				220f, 1, 136, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { { 0, 6 } });

		thelevels[6] = new Level(
				0,
				6,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"eX", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0 }, 790f,
				220f, 2, 230, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { { 0, 7 } });

		thelevels[7] = new Level(
				0,
				7,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"p", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 }, 950f,
				400f, 2, 370, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { { 0, 8 } });

		thelevels[8] = new Level(
				0,
				8,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"H", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 }, 1050f,
				740f, 3, 750, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", true, new int[][] { { 1, 0 }, { 1, 2 } });

		thelevels[9] = new Level(
				1,
				0,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"D", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 }, 30f, 700f,
				4, 1100, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999, 99999,
				99999, "", false, new int[][] { { 1, 1 } });

		thelevels[10] = new Level(
				1,
				1,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"T", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 1 }, 420f,
				750f, 4, 1000, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {} });

		thelevels[11] = new Level(
				1,
				2,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"He", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 2 }, 30f, 350f,
				4, 1200, new Grid(20, 20), 0, 0, 0, 0, 700, 99999, 99999,
				99999, "", false, new int[][] { { 1, 3 }, { 1, 5 } });

		thelevels[12] = new Level(
				1,
				3,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"He", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 2 }, 180f,
				230f, 4, 1300, new Grid(20, 20), 0, 0, 0, 0, 800, 99999, 99999,
				99999, "", false, new int[][] { { 1, 4 } });

		thelevels[13] = new Level(
				1,
				4,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"He", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 6, 2 }, 420f,
				230f, 5, 1500, new Grid(20, 20), 0, 0, 0, 0, 1800, 99999,
				99999, 99999, "", false, new int[][] { {} });

		thelevels[14] = new Level(
				1,
				5,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Li", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0, 3, 3 }, 300f,
				490f, 4, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { { 1, 6 } });

		thelevels[15] = new Level(
				1,
				6,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Be", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 5, 4 }, 550f,
				490f, 5, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { { 1, 7 } });

		thelevels[16] = new Level(
				1,
				7,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"B", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 3, 0, 0, 6, 5 }, 800f,
				570f, 5, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { { 1, 8 } });

		thelevels[17] = new Level(
				1,
				8,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"C", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 4, 0, 0, 6, 6 }, 1000f,
				750f, 6, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", true, new int[][] { {2,8} });
		
		
		
		thelevels[18] = new Level(
				2,
				0,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"N", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 5, 0, 0, 7, 7 }, 550f,
				750f, 6, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {2,1} });
		
		thelevels[19] = new Level(
				2,
				1,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"N", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 5, 0, 0, 8, 7 }, 800f,
				620f, 6, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {2,2} });
		
		thelevels[20] = new Level(
				2,
				2,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"O", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 6, 0, 0, 8, 8 }, 1050f,
				470f, 6, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {2,3} });
		
		thelevels[21] = new Level(
				2,
				3,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"O", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 6, 0, 0, 9, 8 }, 800f,
				350f, 7, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {2,4} });
		
		thelevels[22] = new Level(
				2,
				4,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"F", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 7, 1, 0, 10, 9 }, 550f,
				200f, 7, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {2,5} });
		
		thelevels[23] = new Level(
				2,
				5,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Ne", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 0, 0, 10, 10 }, 300f,
				350f, 7, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {2,6},{2,8} });
		
		thelevels[24] = new Level(
				2,
				6,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Ne", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 0, 0, 11, 10 }, 70f,
				470f, 7, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {2,7} });
		
		thelevels[25] = new Level(
				2,
				7,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Ne", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 0, 0, 12, 10 }, 300f,
				620f, 8, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {2,8} });
				
		thelevels[26] = new Level(
				2,
				8,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Na", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 1, 0, 12, 11 }, 550f,
				480f, 8, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", true, new int[][] { {} });
		
		
		
		thelevels[27] = new Level(
				3,
				0,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Mg", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 2, 0, 12, 12 }, 280f,
				330f, 8, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {3,1},{3,8} });
		thelevels[28] = new Level(
				3,
				1,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Mg", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 2, 0, 13, 12 }, 580f,
				330f, 8, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {3,2} });
		thelevels[29] = new Level(
				3,
				2,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Mg", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 2, 0, 14, 12 }, 880f,
				330f, 8, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {3,3} });
		thelevels[30] = new Level(
				3,
				3,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Al", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 3, 0, 14, 13 }, 1050f,
				550f, 9, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {3,4} });
		thelevels[31] = new Level(
				3,
				4,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Si", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 4, 0, 14, 14 }, 880f,
				750f, 9, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {3,5} });
		thelevels[32] = new Level(
				3,
				5,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Si", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 4, 0, 15, 14 }, 580f,
				750f, 9, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {3,6} });
		thelevels[33] = new Level(
				3,
				6,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Si", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 4, 0, 16, 14 }, 280f,
				750f, 9	, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {3,7} });	
		thelevels[34] = new Level(
				3,
				7,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Si", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 4, 0, 18, 14 }, 80f,
				550f, 10	, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", true, new int[][] { {} });
		thelevels[35] = new Level(
				3,
				8,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"O", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 6, 0, 0, 10, 8 }, 580f,
				550f, 8	, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {3,3} });

		
		thelevels[36] = new Level(
				4,
				0,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"P", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 5, 0, 16, 15 }, 80f,
				500f, 10	, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {4,1},{4,2},{4,3} });
		thelevels[37] = new Level(
				4,
				1,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"S", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 6, 0, 16, 16 }, 320f,
				750f, 10	, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {4,5} });
		thelevels[38] = new Level(
				4,
				2,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"S", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 6, 0, 17, 16 }, 320f,
				500f, 10	, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {4,4} });
		thelevels[39] = new Level(
				4,
				3,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"S", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 6, 0, 18, 16 }, 320f,
				250f, 10	, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {4,4} });
		thelevels[40] = new Level(
				4,
				4,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"S", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 6, 0, 20, 16 }, 550f,
				380f, 11	, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {4,6} });
		thelevels[41] = new Level(
				4,
				5,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Cl", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 7, 0, 18, 17 }, 550f,
				750f, 11	, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {4,6} });
		thelevels[42] = new Level(
				4,
				6,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Cl", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 7, 0, 20, 17 }, 780f,
				580f, 11	, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { {4,7},{4,8} });
		thelevels[43] = new Level(
				4,
				7,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Ar", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 8, 0, 18, 18 }, 1000f,
				750f, 12	, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", true, new int[][] { {} });
		thelevels[44] = new Level(
				4,
				8,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Ar", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 8, 8, 0, 20, 18 }, 1000f,
				380f, 12	, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", true, new int[][] { {} });
		
		
		return new Array<Level>(thelevels);
	}

}
