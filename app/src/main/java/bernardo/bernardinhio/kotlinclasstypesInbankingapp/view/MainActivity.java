package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R;
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.data.SystemData;

public class MainActivity extends AppCompatActivity {

    private Animation animationClientContainer;
    private ConstraintLayout
            containerClientOnlyCheckingAccount,
            containerClientOnlySavingsAccount,
            containerClientBothCheckingAndSavingsAccount;

    private TextView ownerInfoOnlySavings, ownerInfoOnlyChecking,
            ownerInfoBothSavingsAndChecking, accountInfoOnlySavings,
            accountInfoOnlyChecking, accountInfoBothSavingsAndChecking,
            consoleMessages;

    private Button buttonActionOnlySavings,
            buttonActionOnlyChecking,
            buttonActionBothSavingsAndChecking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();
        setupClientsContainersAnimation();
    }

    private void initializeView(){
        ownerInfoOnlySavings = findViewById(R.id.owner_only_savings);
        ownerInfoOnlyChecking = findViewById(R.id.owner_only_checking);
        ownerInfoBothSavingsAndChecking = findViewById(R.id.owner_both_savings_and_checking);
        accountInfoOnlySavings = findViewById(R.id.account_only_savings);
        accountInfoOnlyChecking = findViewById(R.id.account_only_checking);
        accountInfoBothSavingsAndChecking = findViewById(R.id.account_both_savings_and_checking);
        consoleMessages = findViewById(R.id.tv_result);
        buttonActionOnlySavings = findViewById(R.id.bt_dashboard_savings);
        buttonActionOnlyChecking = findViewById(R.id.bt_dashboard_checking);
        buttonActionBothSavingsAndChecking = findViewById(R.id.bt_dashboard_savings_and_checking);
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
                updateUiCheckingOnlyAccount();
                startAnimationClientContainer(containerClientOnlyCheckingAccount);
                break;
            case "savings":
                updateUiSavingsOnlyAccount();
                startAnimationClientContainer(containerClientOnlySavingsAccount);
                break;
            case "checking_and_savings":
                updateUiBothCheckingAndSavingsAccount();
                startAnimationClientContainer(containerClientBothCheckingAndSavingsAccount);
                break;
            default:
                break;
        }
    }

    private void updateUiCheckingOnlyAccount() {
        ownerInfoOnlyChecking.setVisibility(View.VISIBLE);
        accountInfoOnlyChecking.setVisibility(View.VISIBLE);
        buttonActionOnlyChecking.setVisibility(View.VISIBLE);
        ownerInfoOnlyChecking.setText("Owner: " + SystemData.Companion.getOwnerOnlyCheckingAccount().getFirstName() + " " + SystemData.Companion.getOwnerOnlyCheckingAccount().getLastName());
        accountInfoOnlyChecking.setText("Checking Balance: " + SystemData.Companion.getAccountOnlyChecking().getCheckingBalance());
        consoleMessages.setText(consoleMessages.getText()+ "\nBank created Checking only account");
    }

    private void updateUiSavingsOnlyAccount() {
        ownerInfoOnlySavings.setVisibility(View.VISIBLE);
        accountInfoOnlySavings.setVisibility(View.VISIBLE);
        buttonActionOnlySavings.setVisibility(View.VISIBLE);
        ownerInfoOnlySavings.setText("Owner: " + SystemData.Companion.getOwnerOnlySavingsAccount().getFirstName() + " " + SystemData.Companion.getOwnerOnlySavingsAccount().getLastName());
        accountInfoOnlySavings.setText("Savings Balance: " + SystemData.Companion.getAccountOnlySavings().getSavingsBalance());
        consoleMessages.setText(consoleMessages.getText()+ "\nBank created Savings only account");
    }

    private void updateUiBothCheckingAndSavingsAccount() {
        ownerInfoBothSavingsAndChecking.setVisibility(View.VISIBLE);
        accountInfoBothSavingsAndChecking.setVisibility(View.VISIBLE);
        buttonActionBothSavingsAndChecking.setVisibility(View.VISIBLE);
        ownerInfoBothSavingsAndChecking.setText("Owner: " + SystemData.Companion.getOwnerBothCheckingAndSavingsAccount().getFirstName() + " " + SystemData.Companion.getOwnerBothCheckingAndSavingsAccount().getLastName());
        accountInfoBothSavingsAndChecking.setText("Checking Balance: " + SystemData.Companion.getAccountBothCheckingAndSavings().getCheckingBalance() + "\n" + "Savings Balance: " + SystemData.Companion.getAccountBothCheckingAndSavings().getSavingsBalance());
        consoleMessages.setText(consoleMessages.getText()+ "\nBank created both Checking & Savings account");
    }


    public void openDashboardBank(View view) {
        startActivity(new Intent(this, DashboardActivityBank.class));
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
