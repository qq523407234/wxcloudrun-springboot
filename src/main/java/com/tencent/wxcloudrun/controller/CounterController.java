package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.service.CounterService;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * counter控制器
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class CounterController {

    @Resource
    private CounterService counterService;

    /**
     * 获取当前计数
     * @return API response json
     */
    @GetMapping("/count")
    ApiResponse get() {
        log.info("/api/count get request");
        Optional<Counter> counter = counterService.getCounter(1);
        Integer count = 0;
        if (counter.isPresent()) {
            count = counter.get().getCount();
        }

        return ApiResponse.ok(count);
    }

    /**
     * 更新计数，自增或者清零
     * @param request {@link CounterRequest}
     * @return API response json
     */
    @PostMapping("/count")
    ApiResponse create(@RequestBody CounterRequest request) {
        log.info("/api/count post request, action: {}", request.getAction());

        Optional<Counter> curCounter = counterService.getCounter(1);
        if ("inc".equals(request.getAction())) {
            Integer count = 1;
            if (curCounter.isPresent()) {
                count += curCounter.get().getCount();
            }
            Counter counter = new Counter();
            counter.setId(1);
            counter.setCount(count);
            counterService.upsertCount(counter);
            return ApiResponse.ok(count);
        } else if ("clear".equals(request.getAction())) {
            if (!curCounter.isPresent()) {
                return ApiResponse.ok(0);
            }
            counterService.clearCount(1);
            return ApiResponse.ok(0);
        } else {
            return ApiResponse.error("参数action错误");
        }
    }

}