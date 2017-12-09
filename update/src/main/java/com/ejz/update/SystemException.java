package com.ejz.update;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SystemException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private SystemException.IErrorCode errorCode;
    private final Map<String, Object> properties = new TreeMap();

    public static SystemException wrap(Throwable exception, SystemException.IErrorCode errorCode) {
        if(exception instanceof SystemException) {
            SystemException se = (SystemException)exception;
            return errorCode != null && errorCode != se.getErrorCode()?new SystemException(exception.getMessage(), exception, errorCode):se;
        } else {
            return new SystemException(exception.getMessage(), exception, errorCode);
        }
    }

    public static SystemException wrap(Throwable exception) {
        return wrap(exception, (SystemException.IErrorCode)null);
    }

    public SystemException(SystemException.IErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public SystemException(String message, SystemException.IErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public SystemException(Throwable cause, SystemException.IErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public SystemException(String message, Throwable cause, SystemException.IErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public SystemException.IErrorCode getErrorCode() {
        return this.errorCode;
    }

    public SystemException setErrorCode(SystemException.IErrorCode errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

//    public <T> T get(String name) {
//        return this.properties.get(name);
//    }

    public SystemException set(String name, Object value) {
        this.properties.put(name, value);
        return this;
    }

    public void printStackTrace(PrintStream s) {
        synchronized(s) {
            this.printStackTrace(new PrintWriter(s));
        }
    }

    public void printStackTrace(PrintWriter s) {
        synchronized(s) {
            s.println(this);
            s.println("\t-------------------------------");
            if(this.errorCode != null) {
                s.println("\t" + this.errorCode + ":" + this.errorCode.getClass().getName());
            }

            Iterator trace = this.properties.keySet().iterator();

            while(trace.hasNext()) {
                String ourCause = (String)trace.next();
                s.println("\t" + ourCause + "=[" + this.properties.get(ourCause) + "]");
            }

            s.println("\t-------------------------------");
            StackTraceElement[] var8 = this.getStackTrace();

            for(int var9 = 0; var9 < var8.length; ++var9) {
                s.println("\tat " + var8[var9]);
            }

            Throwable var7 = this.getCause();
            if(var7 != null) {
                var7.printStackTrace(s);
            }

            s.flush();
        }
    }

    public interface IErrorCode {
        int code();
    }
}