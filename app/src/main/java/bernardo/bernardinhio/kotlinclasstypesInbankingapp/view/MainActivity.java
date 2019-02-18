package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openDashboardSavingsAccount(View view) {
        startActivity(new Intent(this, DashboardActivityClientSavingsAccount.class));
    }

    public void openDashboardCheckingAccount(View view) {
        startActivity(new Intent(this, DashboardActivityClientCheckingAccount.class));
    }

    public void openDashboardSavingsAndCheckingAccount(View view) {
        startActivity(new Intent(this, DashboardActivityClientSavingsAndCheckingAccount.class));
    }

    public void openDashboardBank(View view) {
        startActivity(new Intent(this, DashboardActivityBank.class));
    }
}
