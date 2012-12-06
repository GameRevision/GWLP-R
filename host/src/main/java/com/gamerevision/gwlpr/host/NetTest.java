
package com.gamerevision.gwlpr.host;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetTest implements Runnable
{
  
    @Override
    public void run()
    {
        try {
            Selector selector = Selector.open();

            ServerSocketChannel ssChannel1 = ServerSocketChannel.open();
            ssChannel1.configureBlocking(false);
            ssChannel1.socket().bind(new InetSocketAddress(7112));

            ssChannel1.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
              selector.select();
              Iterator it = selector.selectedKeys().iterator();
              while (it.hasNext()) {
                SelectionKey selKey = (SelectionKey) it.next();
                it.remove();

                if (selKey.isAcceptable()) {
                  ServerSocketChannel ssChannel = (ServerSocketChannel) selKey.channel();
                  SocketChannel sc = ssChannel.accept();
                  ByteBuffer bb =ByteBuffer.allocate(100);
                  sc.read(bb);
          
                }
              }
            }
        } catch (IOException ex) {
            Logger.getLogger(NetTest.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
}