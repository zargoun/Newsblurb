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
    
    protected Animation anAnimation;
    protected OpenListener    anOpenListener;
    protected CloseListener   aCloseListener;
    protected Listener theListener;

    protected boolean isPressed = false;

// TODO why for weird constructor setup?
    public Sidebar(Context context) { //** for constructors, I honestly don't know why they are done like this
        this(context, null); //one calls the other, but then can only pass null data, I assume something later
    } //needs an AttributeSet argument

    public Sidebar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    

    @Override
    public void onFinishInflate() { //** sets views on inflate, throws errors if the view is not defined
        super.onFinishInflate();
        theSidebar = findViewById(R.id.animation_layout_sidebar);
        theContent = findViewById(R.id.animation_layout_content);

        if (theSidebar == null) {
            throw new NullPointerException("no view id = animation_sidebar");
        }

        if (theContent == null) {
            throw new NullPointerException("no view id = animation_content");
        }

        anOpenListener = new OpenListener(theSidebar, theContent); //listeners for the sidebar
        aCloseListener = new CloseListener(theSidebar, theContent);
    }
    
    public void setListener(Listener l) {
        theListener = l; //sets the last listener for the sidebar
    }

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) { //**
		int sidebarLeft = l;
        if (!placeLeft) {
            sidebarLeft = r - sidebarWidth; //places on the right
        }
        theSidebar.layout(sidebarLeft, 0, sidebarLeft + sidebarWidth, 0 + theSidebar.getMeasuredHeight()); //places on the right

        if (opened) {
            if (placeLeft) { //places the sidebar when it has been opened to be shown on the left
                theContent.layout(l + sidebarWidth, 0, r + sidebarWidth, b);
            } else  { //same but right
                theContent.layout(l - sidebarWidth, 0, r - sidebarWidth, b);
            }
        } else {
            theContent.layout(l, 0, r, b);
        }	
    }
	
	@Override
    public void onMeasure(int w, int h) { //** This and the next method are for setting the size of the views
        super.onMeasure(w, h); 
        super.measureChildren(w, h);
        sidebarWidth = theSidebar.getMeasuredWidth(); //sets width
    }

    @Override
    protected void measureChild(View child, int parentWidth, int parentHeight) { //**
        if (child == theSidebar) {
            int mode = MeasureSpec.getMode(parentWidth);
            int width = (int)(getMeasuredWidth() * 0.9); //can be up to 90%
            super.measureChild(child, MeasureSpec.makeMeasureSpec(width, mode), parentHeight); //makes a measure specification which sets limits for the sidebar
        } else {
            super.measureChild(child, parentWidth, parentHeight);
        }
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
		public void onAnimationEnd(Animation animation) { //**
			iContent.clearAnimation(); //** cancels animations
            opened = !opened; //** switches opened boolean to record state of sidebar
            requestLayout(); //** invalidates the view so changes are shown
            if (theListener != null) {
                theListener.onSidebarOpened();
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
	
	
	
    class CloseListener implements Animation.AnimationListener { //** mostly the opposite/same as OpenListner
    	View iSidebar;
        View iContent;
        
        CloseListener(View sidebar, View content) {
            iSidebar = sidebar;
            iContent = content;
        }
        
		@Override
		public void onAnimationEnd(Animation animation) { 
			iContent.clearAnimation();
            iSidebar.setVisibility(View.INVISIBLE); //** hides the sidebar when it is closed
            opened = !opened;
            requestLayout();
            if (theListener != null) {
                theListener.onSidebarClosed();
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
