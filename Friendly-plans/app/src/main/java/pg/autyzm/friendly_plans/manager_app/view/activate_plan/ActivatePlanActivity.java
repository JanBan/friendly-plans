package pg.autyzm.friendly_plans.manager_app.view.activate_plan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import javax.inject.Inject;

import database.entities.PlanTemplate;
import database.repository.PlanTemplateRepository;
import pg.autyzm.friendly_plans.App;
import pg.autyzm.friendly_plans.R;
import pg.autyzm.friendly_plans.view.child_settings.ActivePlanRecyclerViewAdapter;

public class ActivatePlanActivity extends AppCompatActivity {

    @Inject
    PlanTemplateRepository planTemplateRepository;

    ActivatePlanRecyclerViewAdapter planListAdapter;

    ActivatePlanRecyclerViewAdapter.PlanItemClickListener  planItemClickListener =
            new ActivatePlanRecyclerViewAdapter.PlanItemClickListener(){

                @Override
                public void onPlanItemClick(int position) {
                    planListAdapter.setSelectedPlanPosition(position);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getAppComponent().inject(this);
        setContentView(R.layout.activate_plan);
        setUpViews();
    }

    private void setUpViews(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_activate_plan_plan_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        planListAdapter = new ActivatePlanRecyclerViewAdapter(planItemClickListener);
        recyclerView.setAdapter(planListAdapter);
        planListAdapter.setPlanItems(planTemplateRepository.getAll());

        Button activateButton = (Button) findViewById(R.id.id_btn_activate_plan);
        activateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activatePlan();
            }
        });
    }

    private void activatePlan(){
        PlanTemplate selectedPlan = planListAdapter.getSelectedPlan();

        if (selectedPlan != null) {
            planTemplateRepository.setAllInactive();
            planTemplateRepository.setIsActive(selectedPlan, true);
        }
    }
}
