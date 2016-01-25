package fr.evolving.assets;

import com.badlogic.gdx.utils.Array;

import fr.evolving.automata.Grid;
import fr.evolving.automata.Level;

public class InitWorlds {
	public static Array<Level> go() {
		Level[] thelevels = new Level[35];

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
				4, 1200, new Grid(20, 20), 0, 0, 0, 0, 800, 99999, 99999,
				99999, "", false, new int[][] { { 1, 3 }, { 1, 5 } });

		thelevels[12] = new Level(
				1,
				3,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"He", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 2 }, 180f,
				230f, 4, 1300, new Grid(20, 20), 0, 0, 0, 0, 700, 99999, 99999,
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
				new int[] { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 }, 300f,
				490f, 4, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { { 1, 6 } });

		thelevels[15] = new Level(
				1,
				6,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"C", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 }, 550f,
				490f, 4, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { { 1, 7 } });

		thelevels[16] = new Level(
				1,
				7,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"O", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1 }, 800f,
				570f, 4, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", false, new int[][] { { 1, 8 } });

		thelevels[17] = new Level(
				1,
				8,
				(int) (Math.random() * Integer.MAX_VALUE),
				"",
				"",
				"Ne", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1 }, 1000f,
				750f, 6, 1200, new Grid(20, 20), 0, 0, 0, 0, 99999, 99999,
				99999, 99999, "", true, new int[][] { {} });

		thelevels[18] = new Level(2, 1,
				(int) (Math.random() * Integer.MAX_VALUE), "test",
				"C'est un test.", "e0", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0 }, new int[] { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
						0, 0, 0 }, 38f, 740f, -1, 0, new Grid(20, 20), 0, 0, 0,
				0, 99999, 99999, 99999, 99999, "", false,
				new int[][] { { 2, 2 } });

		thelevels[19] = new Level(2, 2,
				(int) (Math.random() * Integer.MAX_VALUE), "test",
				"C'est un test.", "e0", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0 }, new int[] { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
						0, 0, 0 }, 238f, 340f, -1, 0, new Grid(20, 20), 0, 0,
				0, 0, 99999, 99999, 99999, 99999, "", false, new int[][] {});

		thelevels[20] = new Level(3, 1,
				(int) (Math.random() * Integer.MAX_VALUE), "test",
				"C'est un test.", "e0", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0 }, new int[] { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
						0, 0, 0 }, 38f, 740f, -1, 0, new Grid(20, 20), 0, 0, 0,
				0, 99999, 99999, 99999, 99999, "", false,
				new int[][] { { 3, 2 } });

		;

		thelevels[21] = new Level(3, 2,
				(int) (Math.random() * Integer.MAX_VALUE), "test",
				"C'est un test.", "e0", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0 }, new int[] { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
						0, 0, 0 }, 438f, 540f, -1, 0, new Grid(20, 20), 0, 0,
				0, 0, 99999, 99999, 99999, 99999, "", false, new int[][] {});

		thelevels[22] = new Level(4, 1,
				(int) (Math.random() * Integer.MAX_VALUE), "test",
				"C'est un test.", "e0", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0 }, new int[] { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
						0, 0, 0 }, 38f, 740f, -1, 0, new Grid(20, 20), 0, 0, 0,
				0, 99999, 99999, 99999, 99999, "", false,
				new int[][] { { 4, 2 } });

		;

		thelevels[23] = new Level(4, 2,
				(int) (Math.random() * Integer.MAX_VALUE), "test",
				"C'est un test.", "e0", new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0 }, new int[] { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
						0, 0, 0 }, 238f, 640f, -1, 0, new Grid(20, 20), 0, 0,
				0, 0, 99999, 99999, 99999, 99999, "", false, new int[][] {});

		return new Array<Level>(thelevels);
	}

}
