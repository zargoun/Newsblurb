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
    protected boolean placeLeft = true; //** obviously for basically every variable because, I still need variables
    protected boolean opened;
    protected View theSidebar;
    protected View theContent;
    protected int sidebarWidth = 150; //default value which is rewritten later
    
    protected Animation mAnimation;
    protected OpenListener    mOpenListener;
    protected CloseListener   mCloseListener;
    protected Listener mListener;

    protected boolean mPressed = false;


	public Sidebar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		
	}
	//here be dragons, and classes to implement
	class OpenListener implements Animation.AnimationListener {

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
	}
	
    class CloseListener implements Animation.AnimationListener {

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}

	}
    
    public interface Listener {
        public void onSidebarOpened();
        public void onSidebarClosed();
        public boolean onContentTouchedWhenOpening();
    }


}
