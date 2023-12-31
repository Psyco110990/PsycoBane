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

public class LootWindowRequestMsg extends ClientNetMsg {

    private int sourceType;
    private int sourceID;
    private int targetType;
    private int targetID;

    /**
     * This is the general purpose constructor.
     */
    public LootWindowRequestMsg(int sourceType, int sourceID, int targetType, int targetID) {
        super(Protocol.REQUESTCONTENTS);
        this.sourceType = sourceType;
        this.sourceID = sourceID;
        this.targetType = targetType;
        this.targetID = targetID;
    }

    /**
     * This constructor is used by NetMsgFactory. It attempts to deserialize the
     * ByteBuffer into a message. If a BufferUnderflow occurs (based on reading
     * past the limit) then this constructor Throws that Exception to the
     * caller.
     */
    public LootWindowRequestMsg(AbstractConnection origin, ByteBufferReader reader) {
        super(Protocol.REQUESTCONTENTS, origin, reader);
    }

    /**
     * Serializes the subclass specific items to the supplied NetMsgWriter.
     */
    @Override
    protected void _serialize(ByteBufferWriter writer) {
        writer.putInt(this.sourceType);
        writer.putInt(this.sourceID);
        writer.putInt(this.targetType);
        writer.putInt(this.targetID);
    }

    /**
     * Deserializes the subclass specific items from the supplied NetMsgReader.
     */
    @Override
    protected void _deserialize(ByteBufferReader reader) {
        this.sourceType = reader.getInt();
        this.sourceID = reader.getInt();
        this.targetType = reader.getInt();
        this.targetID = reader.getInt();
    }

    /**
     * @return the sourceType
     */
    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int value) {
        this.sourceType = value;
    }

    /**
     * @return the sourceID
     */
    public int getSourceID() {
        return sourceID;
    }

    public void setSourceID(int value) {
        this.sourceID = value;
    }

    /**
     * @return the targetType
     */
    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int value) {
        this.targetType = value;
    }

    /**
     * @return the targetID
     */
    public int getTargetID() {
        return targetID;
    }

    public void setTargetID(int value) {
        this.targetID = value;
    }

}
