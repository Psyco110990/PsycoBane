// • ▌ ▄ ·.  ▄▄▄·  ▄▄ • ▪   ▄▄· ▄▄▄▄·  ▄▄▄·  ▐▄▄▄  ▄▄▄ .
// ·██ ▐███▪▐█ ▀█ ▐█ ▀ ▪██ ▐█ ▌▪▐█ ▀█▪▐█ ▀█ •█▌ ▐█▐▌·
// ▐█ ▌▐▌▐█·▄█▀▀█ ▄█ ▀█▄▐█·██ ▄▄▐█▀▀█▄▄█▀▀█ ▐█▐ ▐▌▐▀▀▀
// ██ ██▌▐█▌▐█ ▪▐▌▐█▄▪▐█▐█▌▐███▌██▄▪▐█▐█ ▪▐▌██▐ █▌▐█▄▄▌
// ▀▀  █▪▀▀▀ ▀  ▀ ·▀▀▀▀ ▀▀▀·▀▀▀ ·▀▀▀▀  ▀  ▀ ▀▀  █▪ ▀▀▀
//      Magicbane Emulator Project © 2013 - 2022
//                www.magicbane.com


package engine.net.client.msg.chat;


import engine.net.AbstractConnection;
import engine.net.ByteBufferReader;
import engine.net.ByteBufferWriter;
import engine.net.client.Protocol;
import engine.objects.AbstractCharacter;
import engine.objects.AbstractWorldObject;
import engine.server.world.WorldServer;

public class ChatShoutMsg extends AbstractChatMsg {

    /**
     * This is the general purpose constructor.
     */
    public ChatShoutMsg(AbstractWorldObject source, String message) {
        super(Protocol.CHATSHOUT, source, message);
    }

    /**
     * This constructor is used by NetMsgFactory. It attempts to deserialize the
     * ByteBuffer into a message. If a BufferUnderflow occurs (based on reading
     * past the limit) then this constructor Throws that Exception to the
     * caller.
     */
    public ChatShoutMsg(AbstractConnection origin, ByteBufferReader reader) {
        super(Protocol.CHATSHOUT, origin, reader);
    }

    /**
     * Copy constructor
     */
    public ChatShoutMsg(ChatShoutMsg msg) {
        super(msg);
    }

    /**
     * Deserializes the subclass specific items from the supplied ByteBufferReader.
     */
    @Override
    protected void _deserialize(ByteBufferReader reader) {
        this.sourceType = reader.getInt();
        this.sourceID = reader.getInt();
        this.unknown01 = reader.getInt();

        this.message = reader.getString();
        this.sourceName = reader.getString();

        this.unknown02 = reader.getInt();
    }

    /**
     * Serializes the subclass specific items to the supplied ByteBufferWriter.
     */
    @Override
    protected void _serialize(ByteBufferWriter writer) {
        writer.putInt(this.sourceType);
        writer.putInt(this.sourceID);
        writer.putInt(this.unknown01);
        writer.putString(this.message);
        if (this.source == null) {
            // TODO log error here
            writer.putString("");
            writer.putInt(0);
        } else {
            writer.putString(((AbstractCharacter) this.source).getFirstName());
            writer.putInt(WorldServer.worldMapID);
        }
    }
}
