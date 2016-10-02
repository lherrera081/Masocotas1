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
import uan.electiva2.masocotas.R;

/**
 * Created by lucho on 29/9/2016.
 */
public class PetsCursorAdapter extends CursorAdapter {
    public PetsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_pet, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Referencias UI.
        TextView nameText = (TextView) view.findViewById(R.id.tv_name);
        final ImageView avatarImage = (ImageView) view.findViewById(R.id.iv_avatar);
        TextView descriptionText = (TextView) view.findViewById(R.id.tv_description);
        TextView birthDateText = (TextView) view.findViewById(R.id.tv_birth_date);

        // Obtiene y asigna los valores.
        String name = cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.NAME));
        String description = cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.DESCRIPTION));
        byte[] photo = cursor.getBlob(cursor.getColumnIndex(PetContract.PetEntry.PHOTO));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date age = dateFormat.parse(cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.BIRTH_DATE)));
            birthDateText.setText("Nacido el " + outputDateFormat.format(age));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        nameText.setText(name);
//        Glide
//                .with(context)
//                .load(Uri.parse("file:///android_asset/" + avatarUri))
//                .asBitmap()
//                .error(R.drawable.ic_account_circle)
//                .centerCrop()
//                .into(new BitmapImageViewTarget(avatarImage) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        RoundedBitmapDrawable drawable
//                                = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                        drawable.setCircular(true);
//                        avatarImage.setImageDrawable(drawable);
//                    }
//                });

    }
}
