package com.zhjydy_doc.view.zjview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/9/13.
 */
public class MapTextView extends TextView
{
    private Map<String, String> map = new HashMap<>();

    public MapTextView(Context context)
    {
        super(context);
    }

    public MapTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setMap(String id, String value)
    {
        map.put("id", id);
        map.put("value", value);
        this.setText(value);
        invalidate();
    }

    public void clear()
    {
        map.clear();
        this.setText("");
    }

    public String getTextValue()
    {
        return map.get("value");
    }

    public String getTextId()
    {
        return map.get("id");
    }
}
