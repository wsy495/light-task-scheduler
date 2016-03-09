package com.lts.queue.mysql.support;

import com.lts.biz.logger.domain.JobLogPo;
import com.lts.biz.logger.domain.LogType;
import com.lts.core.cluster.NodeType;
import com.lts.core.constant.Level;
import com.lts.core.domain.TaskTrackerJobResult;
import com.lts.core.json.JSON;
import com.lts.core.json.TypeReference;
import com.lts.queue.domain.JobFeedbackPo;
import com.lts.queue.domain.JobPo;
import com.lts.queue.domain.NodeGroupPo;
import com.lts.store.jdbc.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Robert HG (254963746@qq.com) on 5/31/15.
 */
public class RshHolder {

    public static final ResultSetHandler<JobPo> JOB_PO_RSH = new ResultSetHandler<JobPo>() {
        @Override
        public JobPo handle(ResultSet rs) throws SQLException {
            if (!rs.next()) {
                return null;
            }
            return getJobPo(rs);
        }
    };

    public static final ResultSetHandler<List<JobPo>> JOB_PO_LIST_RSH = new ResultSetHandler<List<JobPo>>() {
        @Override
        public List<JobPo> handle(ResultSet rs) throws SQLException {
            List<JobPo> jobPos = new ArrayList<JobPo>();
            while (rs.next()) {
                jobPos.add(getJobPo(rs));
            }
            return jobPos;
        }
    };

    private static JobPo getJobPo(ResultSet rs) throws SQLException {
        JobPo jobPo = new JobPo();
        jobPo.setJobId(rs.getString("job_id"));
        jobPo.setPriority(rs.getInt("priority"));
        jobPo.setRetryTimes(rs.getInt("retry_times"));
        jobPo.setTaskId(rs.getString("task_id"));
        jobPo.setGmtCreated(rs.getLong("gmt_created"));
        jobPo.setGmtModified(rs.getLong("gmt_modified"));
        jobPo.setSubmitNodeGroup(rs.getString("submit_node_group"));
        jobPo.setTaskTrackerNodeGroup(rs.getString("task_tracker_node_group"));
        jobPo.setExtParams(JSON.parse(rs.getString("ext_params"), new TypeReference<HashMap<String, String>>() {
        }));
        jobPo.setIsRunning(rs.getBoolean("is_running"));
        jobPo.setTaskTrackerIdentity(rs.getString("task_tracker_identity"));
        jobPo.setCronExpression(rs.getString("cron_expression"));
        jobPo.setNeedFeedback(rs.getBoolean("need_feedback"));
        jobPo.setTriggerTime(rs.getLong("trigger_time"));
        return jobPo;
    }

    public static final ResultSetHandler<List<JobFeedbackPo>> JOB_FEED_BACK_LIST_RSH = new ResultSetHandler<List<JobFeedbackPo>>() {
        @Override
        public List<JobFeedbackPo> handle(ResultSet rs) throws SQLException {
            List<JobFeedbackPo> jobFeedbackPos = new ArrayList<JobFeedbackPo>();
            while (rs.next()) {
                JobFeedbackPo jobFeedbackPo = new JobFeedbackPo();
                jobFeedbackPo.setId(rs.getString("id"));
                jobFeedbackPo.setTaskTrackerJobResult(JSON.parse(rs.getString("job_result"), new TypeReference<TaskTrackerJobResult>() {
                }));
                jobFeedbackPo.setGmtCreated(rs.getLong("gmt_created"));
                jobFeedbackPos.add(jobFeedbackPo);
            }
            return jobFeedbackPos;
        }
    };

    public static final ResultSetHandler<List<NodeGroupPo>> NODE_GROUP_LIST_RSH = new ResultSetHandler<List<NodeGroupPo>>() {
        @Override
        public List<NodeGroupPo> handle(ResultSet rs) throws SQLException {
            List<NodeGroupPo> list = new ArrayList<NodeGroupPo>();
            while (rs.next()) {
                NodeGroupPo nodeGroupPo = new NodeGroupPo();
                nodeGroupPo.setNodeType(NodeType.valueOf(rs.getString("node_type")));
                nodeGroupPo.setName(rs.getString("name"));
                nodeGroupPo.setGmtCreated(rs.getLong("gmt_created"));
                list.add(nodeGroupPo);
            }
            return list;
        }
    };

    public static final ResultSetHandler<List<JobLogPo>> JOB_LOGGER_LIST_RSH = new ResultSetHandler<List<JobLogPo>>() {
        @Override
        public List<JobLogPo> handle(ResultSet rs) throws SQLException {
            List<JobLogPo> result = new ArrayList<JobLogPo>();
            while (rs.next()) {
                JobLogPo jobLogPo = new JobLogPo();
                jobLogPo.setLogTime(rs.getLong("log_time"));
                jobLogPo.setGmtCreated(rs.getLong("gmt_created"));
                jobLogPo.setLogType(LogType.valueOf(rs.getString("log_type")));
                jobLogPo.setSuccess(rs.getBoolean("success"));
                jobLogPo.setMsg(rs.getString("msg"));
                jobLogPo.setTaskTrackerIdentity(rs.getString("task_tracker_identity"));
                jobLogPo.setLevel(Level.valueOf(rs.getString("level")));
                jobLogPo.setTaskId(rs.getString("task_id"));
                jobLogPo.setJobId(rs.getString("job_id"));
                jobLogPo.setPriority(rs.getInt("priority"));
                jobLogPo.setSubmitNodeGroup(rs.getString("submit_node_group"));
                jobLogPo.setTaskTrackerNodeGroup(rs.getString("task_tracker_node_group"));
                jobLogPo.setExtParams(JSON.parse(rs.getString("ext_params"), new TypeReference<Map<String, String>>() {
                }));
                jobLogPo.setNeedFeedback(rs.getBoolean("need_feedback"));
                jobLogPo.setCronExpression(rs.getString("cron_expression"));
                jobLogPo.setTriggerTime(rs.getLong("trigger_time"));
                jobLogPo.setRetryTimes(rs.getInt("retry_times"));
                result.add(jobLogPo);
            }
            return result;
        }
    };
}


