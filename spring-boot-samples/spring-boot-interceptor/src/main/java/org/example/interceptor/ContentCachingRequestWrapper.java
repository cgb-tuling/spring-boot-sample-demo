package org.example.interceptor;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

@Deprecated
public class ContentCachingRequestWrapper extends HttpServletRequestWrapper {

    private byte[] body;

    private BufferedReader reader;

    //private ServletInputStream inputStream;

    //原子变量，用来区分首次读取还是非首次
    private AtomicBoolean isFirst = new AtomicBoolean(true);

    public ContentCachingRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        loadBody(request);
    }

    private void loadBody(HttpServletRequest request) throws IOException {
        body = IOUtils.toByteArray(request.getInputStream());
        //inputStream = new RequestCachingInputStream(body);
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (isFirst.get()) {
            //首次读取直接调父类的方法，这一次执行完之后 缓存流中有数据了
            //后续读取就读缓存流里的。
            isFirst.set(false);
            return super.getInputStream();
        }
//        if (inputStream != null) {
//            return inputStream;
//        }
        return new RequestCachingInputStream(body);
    }

//    @Override
//    public BufferedReader getReader() throws IOException {
//        if (reader == null) {
//            reader = new BufferedReader(new InputStreamReader(inputStream, getCharacterEncoding()));
//        }
//        return reader;
//    }

    private static class RequestCachingInputStream extends ServletInputStream {

        private final ByteArrayInputStream inputStream;
        private boolean finished = false;

        public RequestCachingInputStream(byte[] bytes) {
            inputStream = new ByteArrayInputStream(bytes);
        }

        @Override
        public int read() throws IOException {
            int read = inputStream.read();
            if (read == -1) {
                this.finished = true;
            }
            return read;
        }

        @Override
        public boolean isFinished() {
//            return inputStream.available() == 0;
            return finished;
        }

        @Override
        public int available() throws IOException {
            return this.inputStream.available();
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.inputStream.close();
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readlistener) {
            throw new UnsupportedOperationException();
        }

    }

}