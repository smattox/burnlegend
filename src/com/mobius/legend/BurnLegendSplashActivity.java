package com.mobius.legend;

import com.mobius.legend.technique.TechniqueReader;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BurnLegendSplashActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        
        new TechniqueReader().readTechniques(this);
        
        TextView versionInfo = (TextView) this.findViewById(R.id.version);
        try {
            String app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            versionInfo.setText(app_ver);
        }
        catch (NameNotFoundException e) {
        	versionInfo.setVisibility(View.GONE);
        }

        
        Button proceedButton = (Button)this.findViewById(R.id.proceed);
        proceedButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
				BurnLegendSplashActivity.this.startActivity(new Intent(BurnLegendSplashActivity.this,
						BurnLegendMainMenuActivity.class));
			}
        });
    }
}