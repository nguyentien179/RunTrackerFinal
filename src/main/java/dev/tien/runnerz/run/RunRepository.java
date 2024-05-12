package dev.tien.runnerz.run;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RunRepository {
    private static final Logger log = LoggerFactory.getLogger(RunRepository.class);
    private final JdbcClient jdbcClient;

    public RunRepository(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }
    public List<Run> getAllRuns(){
        return jdbcClient.sql("select * from run")
                .query(Run.class)
                .list();
    }
    public Optional<Run> findById(Integer id){
     return jdbcClient.sql("SELECT id, title, start_on, end_on,kilometers, location FROM Run WHERE id = :id")
             .param("id",id)
             .query(Run.class)
             .optional();
    }
    public void addRun(Run run){
        var updated = jdbcClient.sql("INSERT INTO Run(id,title,start_on, end_on,kilometers, location) values ( ?,?,?,?,?,?)")
                .params(List.of(run.id(),run.title(),run.startOn(),run.endOn(),run.kilometers(),run.location().toString()))
                .update();

        Assert.state(updated==1,"failed to create run");
    }

    public void updateRun(Run run, Integer id){
        var updated = jdbcClient.sql("update run set title = ?, start_on = ?, end_on = ?, kilometers = ?, location = ? where id = ?")
                .params(List.of(run.id(),run.title(),run.startOn(),run.endOn(),run.kilometers(),run.location().toString(), id))
                .update();
        Assert.state(updated==1,"failed to update run");
    }

    public void deleteRun(Integer id){
        var updated = jdbcClient.sql("delete from run where id = :id")
                .param("id", id)
                .update();
        Assert.state(updated==1,"failed to delete run");
    }

    public int count(){
        return jdbcClient.sql("select * from run").query().listOfRows().size();
    }
    public void saveAll(List<Run> runs){
        runs.stream().forEach(this::addRun);
    }
    public List<Run> findByLocation(String location){
        return jdbcClient.sql("select * from run where location = :location")
                .param("location", location)
                .query(Run.class)
                .list();
    }

}
