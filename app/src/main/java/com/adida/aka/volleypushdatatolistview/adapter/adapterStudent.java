package com.adida.aka.volleypushdatatolistview.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adida.aka.volleypushdatatolistview.MainActivity;
import com.adida.aka.volleypushdatatolistview.R;
import com.adida.aka.volleypushdatatolistview.UpdateSV;
import com.adida.aka.volleypushdatatolistview.model.SinhVien;

import java.util.List;

/**
 * Created by Aka on 6/2/2017.
 */

public class adapterStudent extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<SinhVien> listStudent;

    public adapterStudent(MainActivity context, int layout, List<SinhVien> listStudent) {
        this.context = context;
        this.layout = layout;
        this.listStudent = listStudent;
    }

    @Override
    public int getCount() {
        return listStudent.size();
    }

    @Override
    public Object getItem(int i) {
        return listStudent.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        TextView txtTen, txtBirthday, txtPlace;
        ImageView imgEdit, imgDelete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        View row = view;
        if(row == null){
            holder = new ViewHolder();
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(layout, null);
            holder.txtTen      = (TextView) row.findViewById(R.id.name);
            holder.txtBirthday = (TextView) row.findViewById(R.id.birthday);
            holder.txtPlace    = (TextView) row.findViewById(R.id.born);
            holder.imgEdit     = (ImageView) row.findViewById(R.id.imgEdit);
            holder.imgDelete   = (ImageView) row.findViewById(R.id.imgDelete);

            row.setTag(holder);
        }else {
            holder = (ViewHolder) row.getTag();
        }

        final SinhVien sinhVien = listStudent.get(i);
        holder.txtTen.setText(sinhVien.getTen());
        holder.txtBirthday.setText(sinhVien.getNamSinh());
        holder.txtPlace.setText(sinhVien.getDiaChi());

        //listener update and delete
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateSV.class);
                intent.putExtra("dataSV", sinhVien);
                context.startActivity(intent);
                context.finish();
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete(sinhVien.getTen(), sinhVien.getId());
            }
        });
        return row;
    }

    private void confirmDelete(String name, final String id){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        dialogDelete.setMessage("Do you delete "+ "'"+ name + "'");
        dialogDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.deleteSV(id);
            }
        });

        dialogDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialogDelete.show();
    }
}
