package com.venmo.view;

import android.graphics.RectF;
import android.view.View;

public final class ArrowAlignmentHelper {

    public static float calculateArrowMidPoint(TooltipView view, RectF rectF) {
        int offset = view.getAlignmentOffset();
        float middle = 0f;

        switch (view.getArrowAlignment()) {
            case START:
                middle = offset == 0 ? rectF.width() / 4 : offset;
                break;
            case CENTER:
                middle = rectF.width() / 2;
                if (offset > 0)
                    throw new IllegalArgumentException(
                            "Offsets are not support when the tooltip arrow is anchored in the middle of the view.");
                break;
            case END:
                middle = rectF.width();
                middle -= (offset == 0 ? rectF.width() / 4 : offset);
                break;
            case ANCHORED_VIEW:
                int halfArrow = view.getArrowWidth()/2;
                middle = halfArrow;
                View anchoredView = view.getAnchorView();

                if (anchoredView == null && view.getAnchoredViewId() != View.NO_ID) {
                    anchoredView = ((View) view.getParent()).findViewById(view.getAnchoredViewId());
                }

                if(anchoredView != null) {
                    int[] anchorLoc = new int[2];
                    int[] viewLoc = new int[2];
                    anchoredView.getLocationOnScreen(anchorLoc);
                    view.getLocationOnScreen(viewLoc);

                    // middle of arrow = absolute middle of anchored view - absolute left of tooltip
                    middle = anchorLoc[0] + (anchoredView.getWidth() / 2) - viewLoc[0];


                    if(middle < halfArrow){
                        middle = halfArrow;
                    }
                    if(middle > rectF.right - halfArrow){
                        middle = rectF.right - halfArrow;
                    }
                }
                break;
        }
        return middle;
    }
}
