package com.xwszt.backend.web.controller;

import com.xwszt.backend.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 登录模块
 *
 * @author xwszt
 */
@RestController
@RequestMapping(("/cluster"))
public class LoginController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/allkeys")
    public Set<String> allKeys() {
        return demoService.keys();
    }

    @RequestMapping("/get/{key}")
    public String get(@PathVariable("key") String key) {
        return "key : " + key + ", value : " + demoService.get(key);
    }

    /**
     * 添加节点
     *
     * @param ip
     * @param port
     * @return
     */
    @RequestMapping("/meet/{ip}/{port}")
    public String meet(@PathVariable("ip") String ip, @PathVariable("port") Integer port) {
        demoService.meet(ip, port);
        return "OK";
    }

    /**
     * 删除节点
     *
     * @param ip
     * @param port
     * @return
     */
    @RequestMapping("/forget/{ip}/{port}")
    public String forget(@PathVariable("ip") String ip, @PathVariable("port") Integer port) {
        demoService.forget(ip, port);
        return "OK";
    }

    /**
     * Shutdown节点
     *
     * @param ip
     * @param port
     * @return
     */
    @RequestMapping("/shutdown/{ip}/{port}")
    public String shutdown(@PathVariable("ip") String ip, @PathVariable("port") Integer port) {
        try {
            demoService.shutdown(ip, port);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "OK";
    }

    /**
     * 监测所有节点链接情况
     *
     * @return
     */
    @RequestMapping("/checkNodes")
    public String checkNodes() {
        demoService.checkNodes();
        return "OK";
    }
}
