package com.example.nknchecker;

public class NknNode {

    public static final int DEFAULT_JSON_RPC_PORT = 30003;

    public static final String TAG = NknNode.class.getSimpleName();
    public static final String FIELD_ADDR = "addr";
    public static final String FIELD_CURR_TIME_STAMP = "currTimeStamp";
    public static final String FIELD_HEIGHT = "height";
    public static final String FIELD_ID = "id";
    public static final String FIELD_JSON_RPC_PORT = "jsonRpcPort";
    public static final String FIELD_PROPOSAL_SUBMITTED = "proposalSubmitted";
    public static final String FIELD_PROTOCOL_VERSION = "protocolVersion";
    public static final String FIELD_PUBLIC_KEY = "publicKey";
    public static final String FIELD_RELAY_MESSAGE_COUNT = "relayMessageCount";
    public static final String FIELD_SYNC_STATE = "syncState";
    public static final String FIELD_UPTIME = "uptime";
    public static final String FIELD_VERSION = "version";
    public static final String FIELD_WEBSOCKET_PORT = "websocketPort";
    // extra field
    public static final String FIELD_RESULT = "result";
    public static final String FIELD_JSONRPC = "jsonrpc";
    public static final String FIELD_ERROR = "error";

    /* syncState possible values */
    public static final String SYNC_STATE_PERSIST_FINISHED = "PERSIST_FINISHED";
    public static final String SYNC_STATE_OFFLINE = "OFFLINE";

    // given
    private String name;
    private String hostService;
    private String ipAddress;
    // nkn response
    private String addr;
    private long currTimeStamp;
    private long height;
    private String id;
    private int jsonRpcPort;
    private int proposalSubmitted;
    private int protocolVersion;
    private String publicKey;
    private int relayMessageCount;
    private String syncState;
    private long uptime;
    private String version;
    private int websocketPort;
    // error
    private String error;

    public NknNode(String name, String hostService, String ipAddress) {
        this.name = name;
        this.hostService = hostService;
        this.ipAddress = ipAddress;
    }

    public String getName() {
        return name;
    }

    public String getHostService() {
        return hostService;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public long getCurrTimeStamp() {
        return currTimeStamp;
    }

    public void setCurrTimeStamp(long currTimeStamp) {
        this.currTimeStamp = currTimeStamp;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getJsonRpcPort() {
        return jsonRpcPort;
    }

    public void setJsonRpcPort(int jsonRpcPort) {
        this.jsonRpcPort = jsonRpcPort;
    }

    public int getProposalSubmitted() {
        return proposalSubmitted;
    }

    public void setProposalSubmitted(int proposalSubmitted) {
        this.proposalSubmitted = proposalSubmitted;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public int getRelayMessageCount() {
        return relayMessageCount;
    }

    public void setRelayMessageCount(int relayMessageCount) {
        this.relayMessageCount = relayMessageCount;
    }

    public String getSyncState() {
        return syncState;
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    public long getUptime() {
        return uptime;
    }

    public void setUptime(long uptime) {
        this.uptime = uptime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getWebsocketPort() {
        return websocketPort;
    }

    public void setWebsocketPort(int websocketPort) {
        this.websocketPort = websocketPort;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void resetNodePartially() {
        addr = "";
        currTimeStamp = 0;
        height = 0;
        id = "";
        jsonRpcPort = 0;
        proposalSubmitted = 0;
        protocolVersion = 0;
        publicKey = "";
        relayMessageCount = 0;
        syncState = "";
        uptime = 0;
        version = "";
        websocketPort = 0;
        error = "";
    }

    public void resetNode() {
        name = "";
        hostService = "";
        ipAddress = "";
        resetNodePartially();
    }

    public static String getNodeEndpoint(String ipAddress, int jsonRpcPort) {
        return "http://" + ipAddress + ":" + jsonRpcPort;
    }

    public static String getNodeEndpoint(String ipAddress) {
        return getNodeEndpoint(ipAddress, DEFAULT_JSON_RPC_PORT);
    }

    public String getNodeEndpoint() {
        if (jsonRpcPort == 0) {
            return getNodeEndpoint(ipAddress);
        } else {
            return getNodeEndpoint(ipAddress, jsonRpcPort);
        }
    }
}
