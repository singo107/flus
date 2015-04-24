package cn.flus.account.web.config;

import java.util.List;

import org.springframework.stereotype.Service;

@Service("unprotectedConfig")
public class UnprotectedConfig {

    private List<RequestPattern> unprotected;

    public List<RequestPattern> getUnprotected() {
        return unprotected;
    }

    public void setUnprotected(List<RequestPattern> unprotected) {
        this.unprotected = unprotected;
    }

}
