package com.zhjydy_doc.model.refresh;

interface RefreshWithData extends RefreshListener {
    void onRefreshWithData(int key, Object data);
}