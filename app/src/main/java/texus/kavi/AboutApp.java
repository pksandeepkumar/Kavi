package texus.kavi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutApp extends AppCompatActivity {

    TextView tvSubmitAPoem;
    TextView tvShareApp;
    TextView tvYourFeedback;
    TextView tvLikeUs;
    CardView crdOtherApps;
    LinearLayout llOtherAppHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        init();
    }

    public void init() {

        tvSubmitAPoem   = (TextView) this.findViewById(R.id.tvSubmitAPoem);
        tvShareApp      = (TextView) this.findViewById(R.id.tvShareApp);
        tvYourFeedback  = (TextView) this.findViewById(R.id.tvYourFeedback);
        tvLikeUs        = (TextView) this.findViewById(R.id.tvLikeUs);
        crdOtherApps    = (CardView) this.findViewById(R.id.crdOtherApps);
        llOtherAppHolder = (LinearLayout) this.findViewById(R.id.llOtherAppHolder);

    }

    public void submitAPoem( View view ) {

    }

    public void shareApp( View view ) {

    }

    public void sendFeedback( View view ) {

    }

    public void likeUs( View view ) {

    }
}
