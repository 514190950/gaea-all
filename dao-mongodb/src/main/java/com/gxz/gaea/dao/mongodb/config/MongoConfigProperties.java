package com.gxz.gaea.dao.mongodb.config;


import com.mongodb.Block;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.connection.ClusterSettings;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gongxuanzhang
 */
@Data
public class MongoConfigProperties {

    protected List<String> host;
    protected int port;
    protected String user;
    protected String password;
    protected String database;

    final private Block<ClusterSettings.Builder> block = settings -> settings.hosts(getServerAddress());

    public List<ServerAddress> getServerAddress() {
        List<ServerAddress> address = new ArrayList<>();
        for (String hostAddress : host) {
            address.add(new ServerAddress(hostAddress, port));
        }
        return address;
    }

    public MongoCredential getMongoCredential() {
        return MongoCredential.createCredential(user, database, password.toCharArray());
    }


}
