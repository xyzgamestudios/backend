package com.xwszt.backend.web.controller;

import com.xwszt.backend.service.DemoService;
import com.xwszt.backend.web.vo.CommandStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

    @RequestMapping("/info/{section}")
    public Object info(@PathVariable("section") String section) {
        Properties info = demoService.info(section);
        if ("commandstats".equalsIgnoreCase(section)) {
            Properties properties = new Properties();
            Set<Object> keySet = info.keySet();
            List<CommandStats> statsList = new ArrayList<>(keySet.size());
            Iterator<Object> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                Object key = iterator.next();

                CommandStats stats = new CommandStats();
                String[] keySplit = key.toString().split(".cmdstat_");
                stats.setNode(keySplit[0]);
                stats.setCmdName(keySplit[1]);

                String value = (String)info.get(key);
                String[] valueSplit = value.split(",");
                stats.setCount(valueSplit[0].split("=")[1]);
                stats.setTotalCpuUseTime(valueSplit[1].split("=")[1]);
                stats.setPerCpuUseTime(valueSplit[2].split("=")[1]);
                statsList.add(stats);
            }
            return statsList;
        } else {
            return info;
        }
    }

    @RequestMapping("/benchmark")
    public String benchmark() {
        return demoService.benchmark();
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
