// • ▌ ▄ ·.  ▄▄▄·  ▄▄ • ▪   ▄▄· ▄▄▄▄·  ▄▄▄·  ▐▄▄▄  ▄▄▄ .
// ·██ ▐███▪▐█ ▀█ ▐█ ▀ ▪██ ▐█ ▌▪▐█ ▀█▪▐█ ▀█ •█▌ ▐█▐▌·
// ▐█ ▌▐▌▐█·▄█▀▀█ ▄█ ▀█▄▐█·██ ▄▄▐█▀▀█▄▄█▀▀█ ▐█▐ ▐▌▐▀▀▀
// ██ ██▌▐█▌▐█ ▪▐▌▐█▄▪▐█▐█▌▐███▌██▄▪▐█▐█ ▪▐▌██▐ █▌▐█▄▄▌
// ▀▀  █▪▀▀▀ ▀  ▀ ·▀▀▀▀ ▀▀▀·▀▀▀ ·▀▀▀▀  ▀  ▀ ▀▀  █▪ ▀▀▀
//      Magicbane Emulator Project © 2013 - 2022
//                www.magicbane.com


package engine.net.client.msg;


import engine.net.AbstractConnection;
import engine.net.ByteBufferReader;
import engine.net.ByteBufferWriter;
import engine.net.client.Protocol;
import engine.objects.AbstractWorldObject;

public class ApplyEffectMsg extends ClientNetMsg {

    protected int numTrains;
    protected int effectID;
    protected int sourceType;
    protected int sourceID;
    protected int targetType;
    protected int targetID;

    protected int unknown02;
    protected int unknown03;
    protected int duration;
    protected int unknown05;
    protected byte unknown06;

    protected int powerUsedID;

    protected String powerUsedName;
    private int effectSourceType = 0;
    private int effectSourceID = 0;

    /**
     * This is the general purpose constructor.
     */
    public ApplyEffectMsg() {
        super(Protocol.POWERACTION);
        this.numTrains = 0;
        this.effectID = 0;
        this.sourceType = 0;
        this.sourceID = 0;
        this.targetType = 0;
        this.targetID = 0;

        this.unknown02 = 0;
        this.unknown03 = 0;
        this.duration = 0;
        this.unknown05 = 0;
        this.unknown06 = (byte) 0;

        this.powerUsedID = 0;
        this.powerUsedName = "";
    }

    /**
     * This is the general purpose constructor.
     */
    public ApplyEffectMsg(AbstractWorldObject source, AbstractWorldObject target, int numTrains, int effectID, int duration,
                          int powerUsedID, String powerUsedName) {
        super(Protocol.POWERACTION);
        this.numTrains = numTrains;
        this.effectID = effectID;

        if (source != null) {
            this.sourceType = source.getObjectType().ordinal();
            this.sourceID = source.getObjectUUID();
        } else {
            this.sourceType = 0;
            this.sourceID = 0;
        }

        if (target != null) {
            this.targetType = target.getObjectType().ordinal();
            this.targetID = target.getObjectUUID();
        } else {
            this.targetType = 0;
            this.targetID = 0;
        }
        this.unknown02 = 0;
        this.unknown03 = 0;
        this.duration = duration;
        this.unknown05 = 0;
        this.unknown06 = (byte) 0;

        this.powerUsedID = powerUsedID;
        this.powerUsedName = powerUsedName;
    }

    /**
     * This constructor is used by NetMsgFactory. It attempts to deserialize the
     * ByteBuffer into a message. If a BufferUnderflow occurs (based on reading
     * past the limit) then this constructor Throws that Exception to the
     * caller.
     */
    public ApplyEffectMsg(AbstractConnection origin, ByteBufferReader reader) {
        super(Protocol.POWERACTION, origin, reader);
    }

    /**
     * Serializes the subclass specific items to the supplied NetMsgWriter.
     */
    @Override
    protected void _serialize(ByteBufferWriter writer) {
        writer.putInt(this.numTrains);
        writer.putInt(this.effectID);

        writer.putInt(this.sourceType);
        writer.putInt(this.sourceID);
        writer.putInt(this.targetType);
        writer.putInt(this.targetID);

        writer.putInt(this.unknown02);
        writer.putInt(this.unknown03);
        writer.putInt(this.duration);
        writer.putInt(this.unknown05);
        writer.put(this.unknown06);

        if (this.unknown06 == (byte) 1) {
            writer.putInt(this.effectSourceType);
            writer.putInt(this.effectSourceID);
        } else {
            writer.putInt(this.powerUsedID);
        }

        writer.putString(this.powerUsedName);
    }

    /**
     * Deserializes the subclass specific items from the supplied NetMsgReader.
     */
    @Override
    protected void _deserialize(ByteBufferReader reader) {
        this.numTrains = reader.getInt();
        this.effectID = reader.getInt();

        this.sourceType = reader.getInt();
        this.sourceID = reader.getInt();
        this.targetType = reader.getInt();
        this.targetID = reader.getInt();

        this.unknown02 = reader.getInt();
        this.unknown03 = reader.getInt();
        this.duration = reader.getInt();
        this.unknown05 = reader.getInt();
        this.unknown06 = reader.get();

        this.powerUsedID = reader.getInt();

        this.powerUsedName = reader.getString();
    }

    public int getNumTrains() {
        return this.numTrains;
    }

    public void setNumTrains(int value) {
        this.numTrains = value;
    }

    public int getEffectID() {
        return this.effectID;
    }

    public void setEffectID(int value) {
        this.effectID = value;
    }

    public int getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(int value) {
        this.sourceType = value;
    }

    public int getSourceID() {
        return this.sourceID;
    }

    public void setSourceID(int value) {
        this.sourceID = value;
    }

    public int getTargetType() {
        return this.targetType;
    }

    public void setTargetType(int value) {
        this.targetType = value;
    }

    public int getTargetID() {
        return this.targetID;
    }

    public void setTargetID(int value) {
        this.targetID = value;
    }

    public int getUnknown02() {
        return this.unknown02;
    }

    public void setUnknown02(int value) {
        this.unknown02 = value;
    }

    public int getUnknown03() {
        return this.unknown03;
    }

    public void setUnknown03(int value) {
        this.unknown03 = value;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int value) {
        this.duration = value;
    }

    public int getUnknown05() {
        return this.unknown05;
    }

    public void setUnknown05(int value) {
        this.unknown05 = value;
    }

    public byte getUnknown06() {
        return this.unknown06;
    }

    public void setUnknown06(byte value) {
        this.unknown06 = value;
    }

    public void setPowerUsedID(int value) {
        this.powerUsedID = value;
    }

    public void setPowerUsedName(String value) {
        this.powerUsedName = value;
    }

    public void setEffectSourceType(int effectSourceType) {
        this.effectSourceType = effectSourceType;
    }

    public void setEffectSourceID(int effectSourceID) {
        this.effectSourceID = effectSourceID;
    }
}
