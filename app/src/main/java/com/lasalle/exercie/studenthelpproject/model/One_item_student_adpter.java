package com.lasalle.exercie.studenthelpproject.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lasalle.exercie.studenthelpproject.R;

import java.util.ArrayList;

public class One_item_student_adpter extends BaseAdapter {

private Context context;
private ArrayList<TutorialAssignment> listAssign;
TutorialAssignment tutoA;

    public One_item_student_adpter(Context context, ArrayList<TutorialAssignment> listAssign) {
        this.context = context;
        this.listAssign = listAssign;
    }

    @Override
    public int getCount() {
        return listAssign.size();
    }

    @Override
    public Object getItem(int i) {
        return listAssign.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View oneitem;
        TextView tvDate,tvDescription,tvTutor;
        LayoutInflater inflator =  LayoutInflater.from(context);
        oneitem = inflator.inflate(R.layout.one_item_student,viewGroup,false);
        tvDate = oneitem.findViewById(R.id.tvDate);
        tvDescription = oneitem.findViewById(R.id.tvDescription);
        tvTutor = oneitem.findViewById(R.id.tvTutor);

        tutoA =listAssign.get(i);
        tvDate.setText(tutoA.getTutorialDate());
        tvDescription.setText(tutoA.getTutorialDescription());
        tvTutor.setText(String.valueOf(tutoA.getTutorId()));


        return oneitem ;
    }
}
