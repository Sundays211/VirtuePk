package org.virtue.network.protocol.codec.login;



import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.virtue.network.protocol.messages.GameLoginData;
import org.virtue.network.protocol.messages.LobbyLoginData;
import org.virtue.network.protocol.messages.LoginConfigData;
import org.virtue.network.protocol.messages.LoginResponse;
import org.virtue.utility.Packet;
import org.virtue.utility.PacketBuf;


/**
 * @author Virtue Development Team 2014 (c).
 * @since Apr 5, 2014
 */
public class LoginDataEncoder extends OneToOneEncoder implements ChannelHandler {

	@Override
	protected Object encode(ChannelHandlerContext arg0, Channel arg1, Object msg) throws Exception {
		
		if (msg instanceof LobbyLoginData) {
			return encodeLobbyLogin((LobbyLoginData) msg);
		} else if (msg instanceof GameLoginData) {
			return encodeGameLogin((GameLoginData) msg);
		} else if (msg instanceof LoginConfigData) {
			return encodeLoginConfig((LoginConfigData) msg);
		} else {
			return msg;
		}
	}

	/**
	 * @param msg
	 * @return
	 */
	private Object encodeLoginConfig(LoginConfigData config) {
		PacketBuf buf = new PacketBuf(LoginResponse.SUCCESS);
		buf.put(config.getUnknownBoolean() ? 1 : 0);
		int[] configVals = config.getConfigData();
		for (int i=0;i<configVals.length;i++) {
			if (configVals[i] != 0) {
				buf.putShort(i);
				buf.putInt(configVals[i]);
			}
		}
		Packet p = buf.toPacket();
		return new LoginResponse(LoginResponse.SUCCESS, p.getPayload(), p.getLength(), true);
	}

	/**
	 * @param msg
	 * @return
	 */
	private Object encodeGameLogin(GameLoginData data) {
		System.out.println("Sending game login...");
		PacketBuf buf = new PacketBuf(LoginResponse.SUCCESS);
		buf.put(data.rights);
		buf.put(0);//Byte
		buf.put(0);//Boolean
		buf.put(0);//Boolean
		buf.put(0);//Boolean
		buf.put(0);//Boolean
		buf.putShort(data.playerIndex);
		buf.put(data.isMember ? 1 : 0);
		buf.putTriByte(0);
		buf.put(0);//Booelan
		buf.putString("");//data.displayName
		Packet p = buf.toPacket();
		return new LoginResponse(LoginResponse.SUCCESS, p.getPayload(), p.getLength());
	}

	/**
	 * @param msg
	 * @return
	 */
	private Object encodeLobbyLogin(LobbyLoginData data) {
		PacketBuf buf = new PacketBuf(LoginResponse.SUCCESS);
		buf.put(data.rights);//rights (0)
		buf.put(0);//Something between 5 and 9 (0)
		buf.put(0);//Debug boolean? (0)
		buf.putTriByte(8388608);// (-128, 0, 0)
		buf.put(0);//Byte (0)
		buf.put(0);//Boolean (0)
		buf.put(0);//Boolean (0)
		buf.putLong(data.membershipEndDate); //members subscription end (end of 2014, 1420073999999L)
		buf.put5ByteInteger(125050283515445249L);//5-byte integer
		buf.put(0x1); //0x1 - if members, 0x2 - subscription (1)
		buf.putInt(0); //(0, 0, 0, 0)
		buf.putInt(400000); //recovery questions set date
		buf.putShort(data.recoveryQuestionsSetDay); //recovery questions set day
		buf.putShort(data.messageCount); //Unread message count
		buf.putShort(data.lastLoggedInDay);//TODO: Replace this with the line below, once the lastLoggedIn method is implemented
		//buf.writeShort(player.getLastLoggedIn() == 0 ? 0 : (int)(((player.getLastLoggedIn() - 1014786000000L) / 86400000) + 1));//last logged in date (17, 17)

		buf.putInt(0); //ip part
		buf.put(data.emailStatus); //(3)email status (0 - no email, 1 - pending parental confirmation, 2 - pending confirmation, 3 - registered)
		buf.putShort(53791);//
		buf.putShort(4427);//
		buf.put(0);//0
		buf.putJagString(data.displayName);
		buf.put(10);//A byte
		buf.putInt(37396180);//An int
		buf.putShort(data.defaultWorldNodeID); //default world id (should be generated by country ids like we found in client today)
		buf.putJagString(data.defaultWorldEndpoint);//Default world connection endpoint
		Packet p = buf.toPacket();
		return new LoginResponse(LoginResponse.SUCCESS, p.getPayload(), p.getLength());
	}
	
	

}
