package uk.co.ipodling.newsblurb;

import uk.co.ipodling.newsblurb.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class Sidebar extends ViewGroup{
	// adapted from walkingice's example here: https://github.com/walkingice/gui-sliding-sidebar/wiki
	// pulls will be marked **, I will note own work and implementation notes to show understanding
	
    public final static int DURATION = 500; //** used as duration for sidebar animation

	public Sidebar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		
	}


}
