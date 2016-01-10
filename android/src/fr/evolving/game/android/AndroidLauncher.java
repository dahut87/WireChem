package fr.evolving.game.android;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import fr.evolving.game.main;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;
		//cfg.hideStatusBar = true; //set to true by default
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			getWindow().getDecorView().setSystemUiVisibility(View.STATUS_BAR_VISIBLE);
			getWindow().getDecorView().setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
		}
		initialize(new main(), config);
	}
}
