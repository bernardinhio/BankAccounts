package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R;
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.data.SystemData;
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.AccountType;

public class MainActivity extends AppCompatActivity {

    private Animation animationClientContainer;
    private ConstraintLayout
            containerClientOnlyCheckingAccount,
            containerClientOnlySavingsAccount,
            containerClientBothCheckingAndSavingsAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupClientsContainersAnimation();
    }

    /**
     * when any of the 4 dashboard Activities are launched while the
     * parent activity MainActivity is partially visible, then the
     * life-cycle method "onPause()" is launched, the when the daughter
     * Activity is destroyed (of of the 4 Activities) then the "onResume()"
     * of the MainActivity is launched again.
     * So here is the place to use to get the data modified in the data
     * provider which is the static class SystemData
     */
    @Override
    protected void onResume() {
        super.onResume();
        switch (SystemData.Companion.getAccountModified().getType()){
            case "checking":
                startAnimationClientContainer(containerClientOnlyCheckingAccount);
                break;
            case "savings":
                startAnimationClientContainer(containerClientOnlySavingsAccount);
                break;
            case "checking_and_savings":
                startAnimationClientContainer(containerClientBothCheckingAndSavingsAccount);
                break;
            default:
                break;
        }
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

    public void setupClientsContainersAnimation(){
        containerClientOnlyCheckingAccount = findViewById(R.id.client_with_only_checking_account);
        containerClientOnlySavingsAccount = findViewById(R.id.client_with_only_savings_account);
        containerClientBothCheckingAndSavingsAccount = findViewById(R.id.client_with_both_savings_and_checking_account);

        animationClientContainer = AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.anim_notify_container_changed);
    }

    private void startAnimationClientContainer(ConstraintLayout container){
        container.startAnimation(animationClientContainer);
    }
}
