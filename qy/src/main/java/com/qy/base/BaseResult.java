package com.qy.base;

import java.io.Serializable;

/**
 * Created by qyang on 2017-11-3.
 */

public class BaseResult implements Serializable{
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
