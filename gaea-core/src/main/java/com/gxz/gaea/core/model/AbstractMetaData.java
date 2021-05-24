package com.gxz.gaea.core.model;


import lombok.Data;
import java.util.Set;

/**
 * 基础会话实体类
 *
 * @author gongxuanzhang
 */
@Data
public abstract class AbstractMetaData  {
    /**
     * 探针标识 记录是哪个探针生成的txt
     */
    protected String source;
    /**
     * 数据采集时间
     */
    protected Long captureTime;
    /**
     * 数据会话结束
     */
    protected Long endTime;
    /**
     * 用户标识
     */
    protected String userId;
    /**
     * 服务端标识
     */
    protected String serverId;
    /**
     * 协议名
     */
    protected String proName;
    /**
     * 内外层五元组相关
     */
    protected Integer protocol;
    protected String clientMac;
    protected String serverMac;
    protected String clientIp;
    protected String serverIp;
    protected Integer clientPort;
    protected Integer serverPort;
    protected String clientIpOuter;
    protected String serverIpOuter;
    protected Integer clientPortOuter;
    protected Integer serverPortOuter;
    protected Integer protocolOuter;
    /**
     * 是否重点目标
     */
    protected Boolean important;
    /**
     * 在伪造协议时上下行字节
     */
    protected String malformedUpPayload;
    protected String malformedDownPayload;
    /**
     * SIM卡唯一识别码
     */
    protected String imsi;
    /**
     * 移动终端唯一识别码
     */
    protected String imei;
    /**
     * 手机号
     */
    protected String msisdn;
    /**
     * dataType数据类型字段
     * session：0：未知proName 1：已知proName
     * dns：-1：伪造 0：请求 1：应答
     * ssl：-1：伪造 0：正常
     * http：-1：伪造 1：正常
     * ssh：-1：伪造 1：正常
     */
    protected Integer dataType;
    /**
     * 组名
     */
    protected String groupName;
    /**
     * 重点目标标识
     */
    protected String targetName;
    /**
     * 流量相关
     */
    protected Long upPkt;
    protected Long upByte;
    protected Long downPkt;
    protected Long downByte;
    /**
     * SYN标识（TCP协议会话建联标识）
     */
    protected Boolean syn;
    /**
     * FIN标识（TCP协议会话结束标识）
     */
    protected Boolean fin;
    /**
     * 该会话是否境外会话
     */
    protected Boolean foreign;

    /**
     * 该会话是否加密
     */
    protected Boolean encryption;

    /**
     * 会话标签
     */
    protected Set<String> caseTags;

    /**
     * 会话是否完整
     */
    protected Boolean completeSession;

}
