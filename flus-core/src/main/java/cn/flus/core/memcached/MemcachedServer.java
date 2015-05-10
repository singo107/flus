package cn.flus.core.memcached;

public class MemcachedServer {

    private String address;
    private int    port;
    private int    weight;

    public MemcachedServer(String address, int port, int weight) {
        this.address = address;
        this.port = port;
        this.weight = weight;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
