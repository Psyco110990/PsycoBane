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
import engine.objects.Realm;
import engine.server.world.WorldServer;


public class WorldRealmMsg extends ClientNetMsg {


    /**
     * This is the general purpose constructor.
     */
    public WorldRealmMsg() {
        super(Protocol.REALMDATA);
    }

    /**
     * This constructor is used by NetMsgFactory. It attempts to deserialize the
     * ByteBuffer into a message. If a BufferUnderflow occurs (based on reading
     * past the limit) then this constructor Throws that Exception to the
     * caller.
     */
    public WorldRealmMsg(AbstractConnection origin, ByteBufferReader reader) {
        super(Protocol.REALMDATA, origin, reader);
    }

    @Override
    protected int getPowerOfTwoBufferSize() {
        return (14); // 2^14 == 16384
    }

    /**
     * Serializes the subclass specific items to the supplied NetMsgWriter.
     */

    @Override
    protected void _serialize(ByteBufferWriter writer) {

        int realmCount;
        int realmID;
        Realm serverRealm;


        realmCount = Realm._realms.size();

        writer.putInt(realmCount);

        for (Realm realm : Realm._realms.values())
            realm.serializeForClientMsg(writer);

        writer.putInt(0x0);
        writer.putInt(WorldServer.worldRealmMap);

    }

    /**
     * Deserializes the subclass specific items from the supplied NetMsgReader.
     */
    @Override
    protected void _deserialize(ByteBufferReader reader) {
        // TODO Implement Deserialization
    }
}
