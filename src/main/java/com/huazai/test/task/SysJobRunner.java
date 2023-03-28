package com.huazai.test.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huazai.test.entity.ScheduleSetting;
import com.huazai.test.mapper.ScheduleSettingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * spring boot项目启动完成后，加载数据库里状态为正常的定时任务
 */
@Slf4j
@Service
public class SysJobRunner implements CommandLineRunner {
    @Resource
    private CronTaskRegistrar cronTaskRegistrar;
    @Resource
    private ScheduleSettingMapper scheduleSettingMapper;

    @Override
    public void run(String... args) {
        // 初始加载数据库里状态为正常的定时任务
        List<ScheduleSetting> jobList = scheduleSettingMapper.selectList(new QueryWrapper<ScheduleSetting>()
                .eq("job_status", 1));
        if (!jobList.isEmpty()) {
            for (ScheduleSetting job : jobList) {
                SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
                cronTaskRegistrar.addCronTask(task, job.getCronExpression());
            }
            log.info("定时任务已加载完毕...");
        }
    }
}
