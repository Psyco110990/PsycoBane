// • ▌ ▄ ·.  ▄▄▄·  ▄▄ • ▪   ▄▄· ▄▄▄▄·  ▄▄▄·  ▐▄▄▄  ▄▄▄ .
// ·██ ▐███▪▐█ ▀█ ▐█ ▀ ▪██ ▐█ ▌▪▐█ ▀█▪▐█ ▀█ •█▌ ▐█▐▌·
// ▐█ ▌▐▌▐█·▄█▀▀█ ▄█ ▀█▄▐█·██ ▄▄▐█▀▀█▄▄█▀▀█ ▐█▐ ▐▌▐▀▀▀
// ██ ██▌▐█▌▐█ ▪▐▌▐█▄▪▐█▐█▌▐███▌██▄▪▐█▐█ ▪▐▌██▐ █▌▐█▄▄▌
// ▀▀  █▪▀▀▀ ▀  ▀ ·▀▀▀▀ ▀▀▀·▀▀▀ ·▀▀▀▀  ▀  ▀ ▀▀  █▪ ▀▀▀
//      Magicbane Emulator Project © 2013 - 2022
//                www.magicbane.com


package engine.net.client.msg.guild;


import engine.net.AbstractConnection;
import engine.net.ByteBufferReader;
import engine.net.ByteBufferWriter;
import engine.net.client.Protocol;
import engine.net.client.msg.ClientNetMsg;

public class SwearInMsg extends ClientNetMsg {

    private int targetType;
    private int targetID;
    private int unknown01;
    private String message;

    /**
     * This is the general purpose constructor.
     */
    public SwearInMsg() {
        super(Protocol.ACTIVATEPLEDGE);
    }

    /**
     * This constructor is used by NetMsgFactory. It attempts to deserialize the ByteBuffer into a message. If a BufferUnderflow occurs (based on reading past the limit) then this constructor Throws that Exception to the caller.
     */
    public SwearInMsg(AbstractConnection origin, ByteBufferReader reader) {
        super(Protocol.ACTIVATEPLEDGE, origin, reader);
    }

    /**
     * Serializes the subclass specific items to the supplied ByteBufferWriter.
     */
    @Override
    protected void _serialize(ByteBufferWriter writer) {
        writer.putInt(this.targetType);
        writer.putInt(this.targetID);
        writer.putInt(this.unknown01);
        writer.putString(this.message);
    }

    /**
     * Deserializes the subclass specific items from the supplied ByteBufferReader.
     */
    @Override
    protected void _deserialize(ByteBufferReader reader) {
        this.targetType = reader.getInt();
        this.targetID = reader.getInt();
        this.unknown01 = reader.getInt();
        this.message = reader.getString();
    }

    /**
     * @return the targetType
     */
    public int getTargetType() {
        return targetType;
    }

    /**
     * @param targetType the targetType to set
     */
    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    /**
     * @return the targetID
     */
    public int getTargetID() {
        return targetID;
    }

    /**
     * @param targetID the targetID to set
     */
    public void setTargetID(int targetID) {
        this.targetID = targetID;
    }

    /**
     * @return the unknown01
     */
    public int getUnknown01() {
        return unknown01;
    }

    /**
     * @param unknown01 the unknown01 to set
     */
    public void setUnknown01(int unknown01) {
        this.unknown01 = unknown01;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
