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
		View iSidebar;
        View iContent;
        
        OpenListener(View sidebar, View content) {
            iSidebar = sidebar; //** sets local variables to those passed in
            iContent = content;
        }
		@Override
		// TODO figure this block out
		public void onAnimationEnd(Animation animation) { //**
			iContent.clearAnimation();
            opened = !opened;
            requestLayout();
            if (mListener != null) {
                mListener.onSidebarOpened();
            }			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		} //do nothing

		@Override
		public void onAnimationStart(Animation animation) {
            iSidebar.setVisibility(View.VISIBLE); //** sets the sidebar to be visible			
		}
	}
	
	
	
    class CloseListener implements Animation.AnimationListener {
    	View iSidebar;
        View iContent;
        
        CloseListener(View sidebar, View content) {
            iSidebar = sidebar;
            iContent = content;
        }
        
		@Override
		public void onAnimationEnd(Animation animation) {
			iContent.clearAnimation();
            iSidebar.setVisibility(View.INVISIBLE); //hides the sidebar when it is closed
            opened = !opened;
            requestLayout();
            if (mListener != null) {
                mListener.onSidebarClosed();
            }			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		} //do nothing

		@Override
		public void onAnimationStart(Animation animation) {
		} //do nothing

	}
    
    public interface Listener {
        public void onSidebarOpened();
        public void onSidebarClosed();
        public boolean onContentTouchedWhenOpening();
    }


}
