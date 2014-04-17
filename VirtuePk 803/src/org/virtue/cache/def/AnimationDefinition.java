package org.virtue.cache.def;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.virtue.Launcher;
import org.virtue.network.protocol.packet.RS3PacketReader;

/**
 * @author Virtue Development Team 2014 (c).
 * @since Apr 16, 2014
 */
public class AnimationDefinition {

    public static final int ANIMATION_DEFINITIONS_INDEX = 20;
    
	private static ConcurrentHashMap<Integer, AnimationDefinition> animDefs = new ConcurrentHashMap<Integer, AnimationDefinition>();
	private static int animationCount = -1;

 	public boolean aBool7066;
    //AnimationDefinitionLoader loader;//aClass556_7067
    public int[][] handledSounds;//anIntArrayArray7068
    public int[] anIntArray7069;
    public int animationID;//anInt7070
    public int[] anIntArray7071;
    public int duration = 0;//anInt7072
    //public AnimationConfig config;//aClass543_7073
    public int[] frameDurations;//anIntArray7074
    public int anInt7075 = -1;
    public int anInt7076 = 5;
    public int anInt7077 = -1;
    public int anInt7078;
    public int anInt7079;
    public int itemInHand = -1;//anInt7080
    public int anInt7081;
    public static boolean aBool7082 = false;
    public boolean aBool7083;
    public HashMap<Integer, Object> paramaters;
    public int[] anIntArray7085;
    public int anInt7086;
    public int[] soundMinDelay;//anIntArray7087
    public int anInt7088;
    public int[] soundMaxDelay;//anIntArray7089



	public static final AnimationDefinition forId(int id) {
		try {
			if (animationCount < 0) {
				animationCount = getSize();
			}
			if (id >= animationCount) {
				return null;
			}
			AnimationDefinition defs = animDefs.get(id);
			if (defs != null) {
				return defs;
			}
			defs = new AnimationDefinition(id);
			animDefs.put(id, defs);
			return defs;
		} catch (Throwable t) {
			return null;
		}
	}

	public static int getSize() throws IOException {
		int lastArchiveId = Launcher.getCache().getFileCount(ANIMATION_DEFINITIONS_INDEX);//Cache.getStore().getIndexes()[19].getLastArchiveId();
		return (lastArchiveId * 256 + Launcher.getCache().getContainerCount(ANIMATION_DEFINITIONS_INDEX, lastArchiveId-1));//Cache.getStore().getIndexes()[19].getValidFilesCount(lastArchiveId));
	}
	
	public AnimationDefinition (int id) {
		this.animationID = id;
		loadAnimationDefinition();
	}
    
	public void loadAnimationDefinition() {
		try {
			byte[] data = Launcher.getCache().read(ANIMATION_DEFINITIONS_INDEX, getArchiveId(), getFileId()).array();//Cache.getStore().getIndexes()[19].getFile(getArchiveId(), getFileId());
			if (data == null) {
				return;
			}
			read(new RS3PacketReader(data));
			postDecodeEvent();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
    void read(RS3PacketReader buffer) {
		for (;;) {
		    int opcode = buffer.getUnsignedByte();
		    if (opcode == 0) {
			break;
	            }
		    decode(buffer, opcode);
		}
    }
    
    void decode(RS3PacketReader buffer, int opcode) {
		if (opcode == 1) {
		    int length = buffer.getUnsignedShort();
		    frameDurations = new int[length];
		    for (int index = 0; index < length; index++) {
		    	frameDurations[index] = buffer.getUnsignedShort();
		    }
		    anIntArray7069 = new int[length];
		    for (int index = 0; index < length; index++) {
		    	anIntArray7069[index] = buffer.getUnsignedShort();
		    }
		    for (int index = 0; index < length; index++) {
		    	anIntArray7069[index] = (buffer.getUnsignedShort() << 16) + anIntArray7069[index];
		    }
		} else if (opcode == 2) {
		    anInt7075 = buffer.getUnsignedShort();
		} else if (opcode == 5) {
		    anInt7076 = buffer.getUnsignedByte();
		} else if (6 == opcode) {
		    anInt7077 = buffer.getUnsignedShort();
		} else if (7 == opcode) {
		    itemInHand = buffer.getUnsignedShort();
		} else if (opcode == 8) {
		    anInt7079 = buffer.getUnsignedByte();
		} else if (opcode == 9) {
		    anInt7088 = buffer.getUnsignedByte();
		} else if (opcode == 10) {
		    anInt7081 = buffer.getUnsignedByte();
		} else if (11 == opcode) {
		    anInt7078 = buffer.getUnsignedByte();
		} else if (12 == opcode) {
		    int length = buffer.getUnsignedByte();
		    anIntArray7071 = new int[length];
		    for (int index = 0; index < length; index++) {
		    	anIntArray7071[index] = buffer.getUnsignedShort();
		    }
		    for (int index = 0; index < length; index++) {
		    	anIntArray7071[index] = (buffer.getUnsignedShort() << 16) + anIntArray7071[index];
		    }
		} else if (13 == opcode) {
		    int length = buffer.getUnsignedShort();
		    handledSounds = new int[length][];
		    for (int index = 0; index < length; index++) {
		    	int values = buffer.getUnsignedByte();
				if (values > 0) {
				    handledSounds[index] = new int[values];
				    handledSounds[index][0] = buffer.getTriByte();
				    for (int valueIdx = 1; valueIdx < values; valueIdx++) {
				    	handledSounds[index][valueIdx] = buffer.getUnsignedShort();
				    }
				}
		    }
		} else if (14 == opcode) {
		    aBool7083 = true;
		} else if (opcode == 15) {
		    aBool7066 = true;
		} else if (16 != opcode && 18 != opcode) {
		    if (19 == opcode) {
				if (null == anIntArray7085) {
				    anIntArray7085 = new int[handledSounds.length];
				    for (int index = 0; index < handledSounds.length; index++) {
				    	anIntArray7085[index] = 255;
				    }
				}
				anIntArray7085[buffer.getUnsignedByte()] = buffer.getUnsignedByte();
		    } else if (opcode == 20) {
				if (soundMaxDelay == null || null == soundMinDelay) {
				    soundMaxDelay = new int[handledSounds.length];
				    soundMinDelay = new int[handledSounds.length];
				    for (int index = 0; index < handledSounds.length; index++) {
						soundMaxDelay[index] = 256;
						soundMinDelay[index] = 256;
				    }
				}
				int index = buffer.getUnsignedByte();
				soundMaxDelay[index] = buffer.getUnsignedShort();
				soundMinDelay[index] = buffer.getUnsignedShort();
		    } else if (opcode == 22) {
		    	anInt7086 = buffer.getUnsignedByte();
		    } else if (opcode == 23) {
		    	buffer.getUnsignedShort();
		    } else if (opcode == 24) {
				int configID = buffer.getUnsignedShort();
				//config = loader.animationConfigLoader.getConfig(configID, -1946742207);
		    } else if (249 == opcode) {
				int length = buffer.getUnsignedByte();
				if (paramaters == null) {
				    //int i_18_ = Class480.method11001(length, (byte) 47);
				    paramaters = new HashMap<Integer, Object>(length);
				}
				for (int index = 0; index < length; index++) {
				    boolean stringInstance = buffer.getUnsignedByte() == 1;
				    int key = buffer.getTriByte();
				    Object value;
				    if (stringInstance) {
				    	value = buffer.getString();
				    } else {
				    	value = new Integer(buffer.getInt());
				    }
				    paramaters.put(key, value);
				}
		    }
		}
    }
    
    void postDecodeEvent() {
		/*if (2119332939 * anInt7088 == -1) {
		    if (config != null
			&& config.aBoolArray7014 != null)
			anInt7088 = -296129850;
		    else
			anInt7088 = 0;
		}
		if (anInt7081 * 377701637 == -1) {
		    if (null != config && config.aBoolArray7014 != null) {
		    	anInt7081 = 680492954;
		    } else {
		    	anInt7081 = 0;
		    }
		}*/
		if (frameDurations != null) {
		    duration = 0;
		    for (int i_21_ = 0; i_21_ < frameDurations.length; i_21_++) {
		    	duration += frameDurations[i_21_] * -184347003;
		    }
		}
    }

	public int getGFXID() {
		if (paramaters.get(2920) == null)
			return -1;
		return (int) paramaters.get(2920);
	}

	/**
	 * Gets the duration of this animation in milliseconds.
	 * 
	 * @return The duration.
	 */
	public int getDuration() {
		/*if (anIntArray7189 == null) {
			return 0;
		}
		int time = 0;
		for (int i : anIntArray7189) {
			time += i;
		}*/
		return (int) ((duration * 30d) * 0.6);
	}

	/**
	 * Gets the duration of this animation in (600ms) ticks.
	 * 
	 * @return The duration in ticks.
	 */
	public int getDurationTicks() {
		return getDuration() / 600;
	}

	public int getArchiveId() {
		return animationID >>> 7;
	}

	public int getFileId() {
		return 0x7f & animationID;
	}
}
