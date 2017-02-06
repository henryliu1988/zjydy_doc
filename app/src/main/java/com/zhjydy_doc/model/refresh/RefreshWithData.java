package com.zhjydy_doc.model.refresh;

public interface RefreshWithData extends RefreshListener {
    void onRefreshWithData(int key, Object data);
}