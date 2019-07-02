package pg.autyzm.friendly_plans.manager_app.view.activate_plan;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import database.entities.PlanTemplate;
import pg.autyzm.friendly_plans.R;

public class ActivatePlanRecyclerViewAdapter extends
        RecyclerView.Adapter<ActivatePlanRecyclerViewAdapter.ActivatePlanListViewHolder> {

    private List<PlanTemplate> planItemList;
    private PlanItemClickListener planItemClickListener;
    private Integer selectedPlanPosition;

    ActivatePlanRecyclerViewAdapter(PlanItemClickListener childItemClickListener) {
        this.planItemClickListener = childItemClickListener;
        this.planItemList = Collections.emptyList();
    }

    @Override
    public ActivatePlanRecyclerViewAdapter.ActivatePlanListViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_active_plan, parent, false);
        return new ActivatePlanRecyclerViewAdapter.ActivatePlanListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivatePlanRecyclerViewAdapter.ActivatePlanListViewHolder holder,
                                 int position) {
        if (planItemList != null && !planItemList.isEmpty()) {
            PlanTemplate planItem = planItemList.get(position);
            holder.planName.setText(planItem.getName());
        }
        if (isPositionActive(position)) {
            holder.itemView.setBackgroundColor(Color.parseColor("#cccccc"));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return planItemList != null && planItemList.size() != 0 ? planItemList.size() : 0;
    }

    public boolean isPositionActive(int position) {
        return (selectedPlanPosition != null && selectedPlanPosition == position) || (
                selectedPlanPosition == null && planItemList.get(position).getIsActive());
    }

    public PlanTemplate getSelectedPlan(){
        if (selectedPlanPosition != null)
            return planItemList.get(selectedPlanPosition);
        else
            return null;
    }

    void setPlanItems(List<PlanTemplate> planItemList) {
        this.planItemList = planItemList;
        notifyDataSetChanged();
    }

    void setSelectedPlanPosition(int selectedChildPosition) {
        this.selectedPlanPosition = selectedChildPosition;
        notifyDataSetChanged();
    }

    interface PlanItemClickListener {
        void onPlanItemClick(int position);
    }


    class ActivatePlanListViewHolder extends RecyclerView.ViewHolder {
        TextView planName;

        ActivatePlanListViewHolder(View itemView) {
            super(itemView);
            this.planName = (TextView) itemView
                    .findViewById(R.id.id_tv_plan_name);
            itemView.setOnClickListener(selectItemListener);
        }

        View.OnClickListener selectItemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planItemClickListener.onPlanItemClick(getAdapterPosition());
            }
        };
    }
}
