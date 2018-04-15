package github.com.coneey.carousellistpicker;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import org.jetbrains.annotations.NotNull;

import io.reactivex.subjects.Subject;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class CarouselAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final RecyclerView.Adapter adapter;
    private Animation clickAnimation = createAnimation();

    Function1<? super Integer, Unit> itemGetter;



    private Animation createAnimation() {
        ScaleAnimation scaleOut = new ScaleAnimation(1F, 0.8F, 1F, 0.8F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        scaleOut.setDuration(200);
        scaleOut.setRepeatMode(Animation.REVERSE);
        scaleOut.setRepeatCount(1);
        scaleOut.setInterpolator(new DecelerateInterpolator());
        return scaleOut;
    }

    public void setClickAnimation(Animation clickAnimation) {
        this.clickAnimation = clickAnimation;
    }

    public CarouselAdapter(@NotNull RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        adapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(parent, viewType);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        itemGetter.invoke((viewHolder.getAdapterPosition()));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                v.startAnimation(clickAnimation);
            }
        });

        return viewHolder;
    }

}
