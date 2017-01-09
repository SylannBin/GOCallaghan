package romnat.tpmap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class OcallaghanDescriptionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocallaghan_description);
    }

    public void link (View view) {

        Intent linkIntent = new Intent (Intent.ACTION_VIEW, Uri.parse("http://ocallaghan-grenoble.com/"));
        startActivity(linkIntent);
    }
}
