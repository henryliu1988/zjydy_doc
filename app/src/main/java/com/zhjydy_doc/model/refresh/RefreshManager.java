package com.zhjydy_doc.model.refresh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/9 0009.
 */
public class RefreshManager {

    public static RefreshManager mInstance = new RefreshManager();


    public Map<Integer, ArrayList<RefreshListener>> mRefreshListeners = new HashMap<>();

    public RefreshManager() {

    }

    public static RefreshManager getInstance() {
        if (mInstance == null) {
            mInstance = new RefreshManager();
        }
        return mInstance;
    }


    public void addNewListener(int key, RefreshListener listener) {
        if (mRefreshListeners.containsKey(key)) {
            ArrayList<RefreshListener> listeners = mRefreshListeners.get(key);
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
        } else {
            ArrayList<RefreshListener> listeners = new ArrayList<>();
            listeners.add(listener);
            mRefreshListeners.put(key, listeners);
        }
    }

    public void removeListner(int key, RefreshListener listener) {
        if (mRefreshListeners.containsKey(key)) {
            ArrayList<RefreshListener> listeners = mRefreshListeners.get(key);
            if (listeners.contains(listener)) {
                listeners.remove(listener);
            }
        }
    }

    public void removeListner(RefreshListener listener) {
        for (Map.Entry entry : mRefreshListeners.entrySet()) {
            ArrayList<RefreshListener> listeners = (ArrayList<RefreshListener>) entry.getValue();
            if (listeners != null && listeners.contains(listener)) {
                listeners.remove(listener);
            }
        }
    }

    public void refreshData(int key) {
        ArrayList<RefreshListener> listeners = mRefreshListeners.get(key);
        if (listeners != null && listeners.size() > 0) {
            for (RefreshListener l : listeners) {
                if (l instanceof RefreshWithKey) {
                    ((RefreshWithKey) l).onRefreshWithKey(key);
                }
            }
        }
    }

    public void refreshData(int key, Object data) {
        ArrayList<RefreshListener> listeners = mRefreshListeners.get(key);
        if (listeners != null && listeners.size() > 0) {
            for (RefreshListener l : listeners) {
                if (l instanceof RefreshWithData) {
                    ((RefreshWithData) l).onRefreshWithData(key, data);
                }
            }
        }
    }
}
