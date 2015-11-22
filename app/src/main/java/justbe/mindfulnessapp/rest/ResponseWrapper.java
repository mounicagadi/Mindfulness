package justbe.mindfulnessapp.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import justbe.mindfulnessapp.rest.DataError;

/**
 * Created by eddiehurtig on 11/19/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseWrapper <T> {
    private T[] objects;
    private APIMeta meta;

    private String error_message;
    private String traceback;
    private DataError error;

    public T[] getObjects() {
        return objects;
    }

    public void setObjects(T[] objects) {
        this.objects = objects;
    }

    public APIMeta getMeta() { return meta; }

    public void setMeta(APIMeta meta) {
        this.meta = meta;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getTraceback() {
        return traceback;
    }

    public void setTraceback(String traceback) {
        this.traceback = traceback;
    }

    public Boolean isOK() {
        return (this.error == null && this.error_message == null);
    }

    public String getErrorMessage() {
        if (this.error != null) {
            return this.error.getMessage();
        } else if (this.error_message != null) {
            return this.error_message;
        } else {
            return null;
        }
    }

    public void setError(DataError error) {
        this.error = error;
    }

    public DataError getError() {
        return this.error;
    }

    public class APIMeta {
        private int limit;
        private String next;
        private int offset;
        private String previous;
        private int total_count;

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public String getPrevious() {
            return previous;
        }

        public void setPrevious(String previous) {
            this.previous = previous;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }
    }
}