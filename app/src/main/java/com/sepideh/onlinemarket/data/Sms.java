
package com.sepideh.onlinemarket.data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sms {

    @SerializedName("return")
    @Expose
    private Return _return;
    @SerializedName("entries")
    @Expose
    private List<Entry> entries = null;

    public Return getReturn() {
        return _return;
    }

    public void setReturn(Return _return) {
        this._return = _return;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

}
