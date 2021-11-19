package com.silent.base;

import com.silent.constant.Constant;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zhaoc
 * @date 2021/11/19 9:54
 */

@Slf4j
public class IoDemo {

    public static void main(String[] args) throws IOException {
        nio();
    }



    public static void bio() throws IOException {
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(Constant.HOST, Constant.PORT));
        int idx = 0;
        while (true) {
            //阻塞方法
            final Socket socket = ss.accept();
            new Thread(() -> {
                handle(socket);
            },"线程[" + idx++ + "]" ).start();
        }
    }




    static void handle(Socket socket) {
        byte[] bytes = new byte[1024];
        try {
            String serverMsg = "  server sss[ 线程："+ Thread.currentThread().getName() +"]";
            socket.getInputStream().read(bytes, 0, bytes.length);
            log.info("读到消息：{}", new String(bytes));
            //阻塞方法
            socket.getOutputStream().write(serverMsg.getBytes());
            socket.getOutputStream().flush();
        } catch (Exception e) {
            log.error("handle error", e);
        }
    }


    public static void nio() throws IOException {
        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(Constant.HOST, Constant.PORT));
        System.out.println(" NIO server started ... ");
        ss.configureBlocking(false);

        /*
         * 阻塞模式：在调用accept方法后，将阻塞知道有新的socket连接时返回SocketChannel对象，代表新建立的套接字通道。
         * 非阻塞模式：在调用accept方法后，如果无连接建立，则返回null；如果有连接，则返回SocketChannel。
         * */
        final SocketChannel socket = ss.accept();

        handle(socket);
//        int idx = 0;
//        while (true) {
//            //阻塞方法
//            final SocketChannel socket = ss.accept();
//            new Thread(() -> {
//                handle(socket);
//            },"线程[" + idx++ + "]" ).start();
//        }
    }


    static void handle(SocketChannel socket) {
        try {
            socket.configureBlocking(false);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socket.read(byteBuffer);
            byteBuffer.flip();
            System.out.println("请求：" + new String(byteBuffer.array()));
            String resp = "服务器响应";
            byteBuffer.get(resp.getBytes());
            socket.write(byteBuffer);
        } catch (IOException e) {
            log.error("handle error", e);
        }
    }

    public static void multiplexing() throws IOException {
        //管道型ServerSocket
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(Constant.HOST, Constant.PORT));
        //设置非阻塞
        ssc.configureBlocking(false);
        System.out.println(" NIO single server started, listening on :" + ssc.getLocalAddress());
        Selector selector = Selector.open();
        //在建立好的管道上，注册关心的事件 就绪
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while(it.hasNext()) {
                SelectionKey key = it.next();
                //处理的事件，必须删除
                it.remove();
                handle(key);
            }
        }
    }


    private static void handle(SelectionKey key) throws IOException {
        if(key.isAcceptable()) {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel sc = ssc.accept();
            //设置非阻塞
            sc.configureBlocking(false);
            //在建立好的管道上，注册关心的事件 可读
            sc.register(key.selector(), SelectionKey.OP_READ );
            //flip
        } else if (key.isReadable()) {
            SocketChannel sc = null;
            sc = (SocketChannel)key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(512);
            buffer.clear();
            int len = sc.read(buffer);
            if(len != -1) {
                System.out.println("[" +Thread.currentThread().getName()+"] recv :"+ new String(buffer.array(), 0, len));
            }
            ByteBuffer bufferToWrite = ByteBuffer.wrap("HelloClient".getBytes());
            sc.write(bufferToWrite);
        }
    }


    public static void async() throws IOException {
        final AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open()
                .bind(new InetSocketAddress(Constant.HOST, Constant.PORT));
        serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(final AsynchronousSocketChannel client, Object attachment) {
                serverChannel.accept(null, this);
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        attachment.flip();
                        //业务逻辑
                        client.write(ByteBuffer.wrap("HelloClient".getBytes()));
                    }
                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        //失败处理
                        System.out.println(exc.getMessage());
                    }
                });
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                //失败处理
                exc.printStackTrace();
            }
        });
        while (true) {
            //不while true main方法一瞬间结束
        }
    }



}
