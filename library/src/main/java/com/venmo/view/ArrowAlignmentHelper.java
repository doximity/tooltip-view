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
                middle = rectF.width() / 2;
                View anchoredView = view.getAnchorView();

                if (anchoredView == null && view.getAnchoredViewId() != View.NO_ID) {
                    anchoredView = ((View) view.getParent()).findViewById(view.getAnchoredViewId());
                }

                if(anchoredView != null) {
                    int[] loc = new int[2];
                    anchoredView.getLocationOnScreen(loc);
                    middle += loc[0] + anchoredView.getWidth() / 2 - view.getX() - view.getWidth() / 2;
                }
                break;
        }
        return middle;
    }
}
