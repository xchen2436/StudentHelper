package com.lasalle.exercie.studenthelpproject.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lasalle.exercie.studenthelpproject.R;

import java.util.ArrayList;

public class One_item_tutor_adpater extends BaseAdapter {
    private Context context;
    private ArrayList<TutorAppointment> listAssign;
    TutorAppointment tutorAppointment;

    public One_item_tutor_adpater(Context context, ArrayList<TutorAppointment> listAssign) {
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
        TextView tvDate,tvDescription,tvStudent;
        LayoutInflater inflator =  LayoutInflater.from(context);
        oneitem = inflator.inflate(R.layout.one_item_tutor,viewGroup,false);
        tvDate = oneitem.findViewById(R.id.tvDate);
        tvDescription = oneitem.findViewById(R.id.tvDescription);
        tvStudent = oneitem.findViewById(R.id.tvStudent);

        tutorAppointment =listAssign.get(i);
        tvDate.setText(tutorAppointment.getTutorialDate());
        tvDescription.setText(tutorAppointment.getTutorialDescription());
        tvStudent.setText(String.valueOf(tutorAppointment.getStudentId()));


        return oneitem ;
    }
}
