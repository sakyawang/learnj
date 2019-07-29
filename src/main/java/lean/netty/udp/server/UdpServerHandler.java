package lean.netty.udp.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-3-25 上午11:27
 */
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        ByteBuf buf = datagramPacket.content();
        String content = buf.toString(CharsetUtil.UTF_8);
        System.out.println(content);
       /* ByteBuf response = Unpooled.copiedBuffer("dasda", CharsetUtil.UTF_8);
        DatagramPacket responsePacket = new DatagramPacket(response, datagramPacket.sender());
        channelHandlerContext.writeAndFlush(responsePacket).sync();*/
    }
}
