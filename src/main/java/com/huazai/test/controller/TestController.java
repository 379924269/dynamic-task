package com.huazai.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huazai.test.entity.ScheduleSetting;
import com.huazai.test.mapper.ScheduleSettingMapper;
import com.huazai.test.task.CronTaskRegistrar;
import com.huazai.test.task.SchedulingRunnable;
import com.huazai.test.util.CommonResult;
import com.huazai.test.vo.IdVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.jsqlparser.statement.select.Offset;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Api(value = "TestController", tags = "动态定时任务API")
@RestController
@RequestMapping("/dynamicTask")
public class TestController {

    @Resource
    private CronTaskRegistrar cronTaskRegistrar;
    @Resource
    private ScheduleSettingMapper scheduleSettingMapper;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "查看所有定时任务")
    public CommonResult<List<ScheduleSetting>> find(@ApiParam(name = "remark", value = "模糊查询字段：备注") @RequestParam(required = false, name = "remark") String remark
            , @ApiParam(name = "jobStatus", value = "状态(1正常 0暂停)") @RequestParam(required = false, name = "jobStatus") Integer jobStatus
    ) {
        final QueryWrapper<ScheduleSetting> queryWrapper = new QueryWrapper<>();
        if (remark != null) {
            queryWrapper.lambda().like(ScheduleSetting::getRemark, remark);
        }
        if (jobStatus != null) {
            queryWrapper.lambda().like(ScheduleSetting::getJobStatus, jobStatus);
        }
        return CommonResult.success(scheduleSettingMapper.selectList(queryWrapper));
    }

    @RequestMapping(value = "/{jobId}", method = RequestMethod.PUT)
    @ApiOperation(value = "通过jobId修改定时任务", notes = "除jobId外，最少传一个修改字段")
    public CommonResult<Object> update(@ApiParam(name = "jobId", value = "任务ID", required = true) @PathVariable(name = "jobId") Integer jobId
            , @ApiParam(name = "beanName", value = "bean名称") @RequestParam(required = false, name = "beanName") String beanName
            , @ApiParam(name = "methodName", value = "方法名称") @RequestParam(required = false, name = "methodName") String methodName
            , @ApiParam(name = "methodParams", value = "方法参数") @RequestParam(required = false, name = "methodParams") String methodParams
            , @ApiParam(name = "cronExpression", value = "cron表达式") @RequestParam(required = false, name = "cronExpression") String cronExpression
            , @ApiParam(name = "remark", value = "备注") @RequestParam(required = false, name = "remark") String remark
            , @ApiParam(name = "jobStatus", value = "状态(1正常 0暂停)") @RequestParam(required = false, name = "jobStatus") Integer jobStatus
    ) {
        ScheduleSetting scheduleSetting = new ScheduleSetting(jobId, beanName, methodName, methodParams, cronExpression
                , jobStatus, remark, null, LocalDateTime.now());
        scheduleSettingMapper.updateById(scheduleSetting);

        // 修改成功,则先删除任务器中的任务,并重新添加
        SchedulingRunnable task1 = new SchedulingRunnable(beanName, methodName, methodParams);
        cronTaskRegistrar.removeCronTask(task1);
        if (jobStatus.equals(1)) {// 如果修改后的任务状态是1就加入任务器
            SchedulingRunnable task = new SchedulingRunnable(beanName, methodName, methodParams);
            cronTaskRegistrar.addCronTask(task, cronExpression);
        }
        return CommonResult.success();
    }

    @RequestMapping(value = "/{jobId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "通过jobId删除定时任务")
    public CommonResult<Object> delete(@ApiParam(name = "jobId", value = "任务ID", required = true) @PathVariable(name = "jobId") Integer jobId
    ) {
        final ScheduleSetting scheduleSetting = scheduleSettingMapper.selectById(jobId);
        scheduleSettingMapper.deleteById(jobId);

        SchedulingRunnable task = new SchedulingRunnable(scheduleSetting.getBeanName(), scheduleSetting.getMethodName()
                , scheduleSetting.getMethodParams());
        cronTaskRegistrar.removeCronTask(task);
        return CommonResult.success();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "添加定时任务")
    public CommonResult<IdVo> add(@ApiParam(name = "beanName", value = "bean名称") @RequestParam(required = false, name = "beanName") String beanName
            , @ApiParam(name = "methodName", value = "方法名称") @RequestParam(required = false, name = "methodName") String methodName
            , @ApiParam(name = "methodParams", value = "方法参数") @RequestParam(required = false, name = "methodParams") String methodParams
            , @ApiParam(name = "cronExpression", value = "cron表达式") @RequestParam(required = false, name = "cronExpression") String cronExpression
            , @ApiParam(name = "remark", value = "备注") @RequestParam(required = false, name = "remark") String remark
            , @ApiParam(name = "jobStatus", value = "状态(1正常 0暂停)") @RequestParam(required = false, name = "jobStatus") Integer jobStatus
    ) {
        ScheduleSetting scheduleSetting = new ScheduleSetting(beanName, methodName, methodParams
                , cronExpression, jobStatus, remark, LocalDateTime.now(), null);
        scheduleSettingMapper.insert(scheduleSetting);
        if (jobStatus.equals(1)) {// 添加成功,并且状态是1，直接放入任务器
            SchedulingRunnable task = new SchedulingRunnable(beanName, methodName, methodParams);
            cronTaskRegistrar.addCronTask(task, cronExpression);
        }
        return CommonResult.success(new IdVo(scheduleSetting.getJobId()));
    }

    @RequestMapping(value = "/startOrStopJob/{jobId}/{jobStatus}", method = RequestMethod.POST)
    @ApiOperation(value = "启动或停止定时任务")
    public CommonResult<Object> startOrStopJob(@ApiParam(name = "jobId", value = "任务ID") @PathVariable(required = false, name = "jobId") Integer jobId
            , @ApiParam(name = "jobStatus", value = "状态(1正常 0暂停)") @PathVariable(required = false, name = "jobStatus") Integer jobStatus
    ) {
        ScheduleSetting scheduleSetting = new ScheduleSetting();
        scheduleSetting.setJobId(jobId);
        scheduleSetting.setJobStatus(jobStatus);

        scheduleSettingMapper.updateById(scheduleSetting);

        final ScheduleSetting dbJob = scheduleSettingMapper.selectById(jobId);
        SchedulingRunnable task = new SchedulingRunnable(dbJob.getBeanName(), dbJob.getMethodName(), dbJob.getMethodParams());
        if (jobStatus.equals(1)) {
            cronTaskRegistrar.addCronTask(task, dbJob.getCronExpression());
        } else {
            // 否则清除任务
            cronTaskRegistrar.removeCronTask(task);
        }
        return CommonResult.success();
    }

}
