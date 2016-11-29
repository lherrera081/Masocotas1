package uan.electiva2.masocotas.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uan.electiva2.masocotas.Contracts.PetContract;
import uan.electiva2.masocotas.Contracts.VaccineContract;
import uan.electiva2.masocotas.R;
import uan.electiva2.masocotas.entities.Vaccine;


/**
 * Created by lucho on 4/10/2016.
 */
public class VaccinesCursorAdapter extends CursorAdapter {
    public VaccinesCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_vaccine, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Referencias UI.
        TextView nameText = (TextView) view.findViewById(R.id.tv_name);
        TextView ageInWeeksText = (TextView) view.findViewById(R.id.tv_age_in_weeks);
        TextView missingWeeks = (TextView) view.findViewById(R.id.tv_missing_weeks);

        // Obtiene y asigna los valores.
        String name = cursor.getString(cursor.getColumnIndex(VaccineContract.VaccineEntry.NAME));
        String description = cursor.getString(cursor.getColumnIndex(VaccineContract.VaccineEntry.DESCRIPTION));
        nameText.setText(name);
        ageInWeeksText.setText("Vacunar a las X semanas");
        missingWeeks.setText("Faltan X semanas para la vacuna");
    }
}