package recorder.appss.cool.utils;

import android.content.Context;

import com.bumptech.glide.load.MultiTransformation;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by yassin baccour on 07/11/2017.
 */

public class FileUtils {

    private Context mContext;

    public FileUtils(Context context) {
        this.mContext = context;
    }

    public MultiTransformation getMultiTransformation() {
        return new MultiTransformation(
                new BlurTransformation(1),
                new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
    }
}
