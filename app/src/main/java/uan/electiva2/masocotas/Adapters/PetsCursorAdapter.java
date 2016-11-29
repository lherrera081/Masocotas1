package uan.electiva2.masocotas.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import uan.electiva2.masocotas.dummy.ImageConverter;

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
        final ImageView avatarImage = (ImageView) view.findViewById(R.id.list_avatar);
        TextView descriptionText = (TextView) view.findViewById(R.id.tv_description);
        TextView birthDateText = (TextView) view.findViewById(R.id.tv_birth_date);

        // Obtiene y asigna los valores.
        String name = cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.NAME));
        String description = cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.DESCRIPTION));
        byte[] bitmapdata = cursor.getBlob(cursor.getColumnIndex(PetContract.PetEntry.PHOTO));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date age = dateFormat.parse(cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.BIRTH_DATE)));
            birthDateText.setText("Nacido el " + outputDateFormat.format(age));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        nameText.setText(name);
        if(bitmapdata !=null) {
            //Bitmap circularBitmap = getRoundedCornerBitmap(new  BitmapDrawable(view.getResources(), bitmapdata),true);
            Bitmap bitmap=BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
            Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, bitmap.getWidth()/2 );
            avatarImage.setImageBitmap(circularBitmap);

        }

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

    public static Bitmap getRoundedCornerBitmap(Drawable drawable, boolean square) {
        int width = 0;
        int height = 0;

        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap() ;

        if(square){
            if(bitmap.getWidth() < bitmap.getHeight()){
                width = bitmap.getWidth();
                height = bitmap.getWidth();
            } else {
                width = bitmap.getHeight();
                height = bitmap.getHeight();
            }
        } else {
            height = bitmap.getHeight();
            width = bitmap.getWidth();
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);
        final float roundPx = 90;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}


