package com.example.carcare1;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterCars extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Car> cars;
    private OnItemClickListener mItemClickListener;

    public AdapterCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public void updateList(ArrayList<Car> cars) {
        this.cars = cars;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            final Car car = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.note_LBL_title.setText(car.toString());
        }
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    private Car getItem(int position) {
        return cars.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView note_LBL_title;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.note_LBL_title = itemView.findViewById(R.id.list_cars_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), getItem(getAdapterPosition()));
                }
            });
        }
    }

    public int moveToMainPage(){
        return 0 ;
    }
    public void removeAt(int position) {
        cars.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cars.size());
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Car cars);
    }
}
