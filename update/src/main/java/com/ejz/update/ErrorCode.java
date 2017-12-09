package com.ejz.update;

/**
 * Created by WORK on 2017/3/20.
 */

public enum ErrorCode implements SystemException.IErrorCode {
    SDCARD_UNMOUNTED(101),
    SDCARD_NO_SPACE(102),
    NETWORK_BLOCKED(201),
    NETWORK_TIMEOUT(202),
    NETWORK_IO(203),
    DOWNLOAD_INCOMPLETE(301),
    HTTP_STATUS(401);

    private final int number;

    private ErrorCode(int number) {
        this.number = number;
    }

    public int code() {
        return this.number;
    }
}

