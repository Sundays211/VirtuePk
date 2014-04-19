package org.virtue.game.logic.node.entity.player.identity;

import org.jboss.netty.channel.Channel;
import org.virtue.game.core.AttributeSet;
import org.virtue.game.logic.node.entity.region.Tile;
import org.virtue.network.session.Session;
import org.virtue.utility.DisplayMode;

/**
 * @author Taylor
 * @date Jan 15, 2014
 */
public class Account extends AttributeSet {
	
	/**
	 * Represents this account's username.
	 */
	private final Username username;
	
	/**
	 * Represents this account's password.
	 */
	private final Password password;
	
	/**
	 * Represents the rank.
	 */
	private Rank rank = Rank.ADMINISTRATOR;
	
	/**
	 * Represents the bound channel on this account.
	 */
	private final Channel channel;
	
	/**
	 * Represents the bound session.
	 */
	private Session session;
	
	/**
	 * Represents the display mode.
	 */
	private DisplayMode displayMode;
	
	/**
	 * Represents the key for the ongoing client session.
	 */
	private final long clientSessionKey;
	
	/**
	 * Represents the key for the ongoing server session.
	 */
	private final long serverSessionKey;
	
	private Email email;
	
	private Age age;
	
	private DateOfBirth dateofbirth;
	
	private Tile tile;
	
	/**
	 * Constructs a new {@code Account.java}.
	 * @param username The username.
	 * @param password The password.
	 */
	public Account(Username username, Password password, Channel channel, DisplayMode displayMode, long clientSessionKey, long serverSessionKey) {
		this.username = username;
		this.password = password;
		this.channel = channel;
		this.displayMode = displayMode;
		this.clientSessionKey = clientSessionKey;
		this.serverSessionKey = serverSessionKey;
	}
	
	/**
	 * Constructs a new {@code Account.java}.
	 * @param username The username.
	 * @param password The password.
	 * @param dateOfBirth2 
	 */
	public Account(Username username, Password password, Rank rank, Email email, Age age, DateOfBirth dateofbirth, Tile tile, Channel channel, DisplayMode displayMode, long clientSessionKey, long serverSessionKey) {
		this.username = username;
		this.password = password;
		this.rank = rank;
		this.email = email;
		this.age = age;
		this.dateofbirth = dateofbirth;
		this.tile = tile;
		this.channel = channel;
		this.displayMode = displayMode;
		this.clientSessionKey = clientSessionKey;
		this.serverSessionKey = serverSessionKey;
	}

	/**
	 * @return the username
	 */
	public Username getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public Password getPassword() {
		return password;
	}

	/**
	 * @return the channel
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * @return the displayMode
	 */
	public DisplayMode getDisplayMode() {
		return displayMode;
	}

	/**
	 * @param displayMode the displayMode to set
	 */
	public void setDisplayMode(DisplayMode displayMode) {
		this.displayMode = displayMode;
	}

	/**
	 * @return the clientSessionKey
	 */
	public long getClientSessionKey() {
		return clientSessionKey;
	}

	/**
	 * @return the serverSessionKey
	 */
	public long getServerSessionKey() {
		return serverSessionKey;
	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * @return the rank
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
	public Email getEmail() {
		return email;
	}
	
	public Age getAge() {
		return age;
	}
	
	public DateOfBirth getDateOfBirth() {
		return dateofbirth;
	}
	
	public Tile getTile() {
		return tile;
	}
	
}
