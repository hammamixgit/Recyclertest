package recorder.appss.cool.recyclertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_templete);
        Configtemplete configtemplete = Configtemplete.values()[0];
        configtemplete.startActivity(this);
    }


}
