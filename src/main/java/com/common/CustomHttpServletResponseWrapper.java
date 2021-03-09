package com.common;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

public class CustomHttpServletResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream output;
    private ServletOutputStream out;
    private PrintWriter printWriter;

    public CustomHttpServletResponseWrapper(HttpServletResponse response) throws IOException{
        super(response);
        this.output = new ByteArrayOutputStream();
        this.out = new WrapperedOutputStream(output);
        this.printWriter = new PrintWriter(new OutputStreamWriter(output, this.getCharacterEncoding()));
    }

    @Override
    public ServletOutputStream getOutputStream() {
            return out;
    }

    @Override
    public PrintWriter getWriter() {
        return printWriter;
    }

    public byte[] getBytes() {
        if(null != printWriter) {
            printWriter.close();
            return output.toByteArray();
        }

        if(null != output) {
            try {
                output.flush();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return output.toByteArray();
    }

    private class WrapperedOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream bos = null;

        public WrapperedOutputStream(ByteArrayOutputStream stream) throws IOException {
            bos = stream;
        }

        @Override
        public void write(int b) throws IOException {
            bos.write(b);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }

}
