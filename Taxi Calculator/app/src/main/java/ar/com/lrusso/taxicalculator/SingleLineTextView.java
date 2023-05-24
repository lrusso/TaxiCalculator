package ar.com.lrusso.taxicalculator;

import android.content.Context;
import android.text.Layout;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

public class SingleLineTextView extends TextView
	{
	public SingleLineTextView(Context context, AttributeSet attrs, int defStyle)
		{
		super(context, attrs, defStyle);
		setSingleLine();
		setEllipsize(TruncateAt.END);
		}

	public SingleLineTextView(Context context, AttributeSet attrs)
		{
		super(context, attrs);
		setSingleLine();
		setEllipsize(TruncateAt.END);
		}

	public SingleLineTextView(Context context)
		{
		super(context);
		setSingleLine();
		setEllipsize(TruncateAt.END);
		}

	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
		{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final Layout layout = getLayout();
		if (layout != null)
			{
			final int lineCount = layout.getLineCount();
			if (lineCount > 0)
				{
				final int ellipsisCount = layout.getEllipsisCount(lineCount - 1);
				if (ellipsisCount > 0)
					{
					//final float textSize = getTextSize();
					// textSize is already expressed in pixels
					//setTextSize(TypedValue.COMPLEX_UNIT_PX, (textSize - 1));
					super.onMeasure(widthMeasureSpec, heightMeasureSpec);
					}
				}
			}
		}
	}