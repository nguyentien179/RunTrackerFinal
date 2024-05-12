package dev.tien.runnerz.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/runs")
public class RunController {
    private final RunRepository runRepository;

    public RunController(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @GetMapping("get-all")
    List<Run> getAllRuns(){
        return runRepository.getAllRuns();
    }
    @GetMapping("/{id}")
    Run findRunById(@PathVariable Integer id) {
        Optional<Run> run = runRepository.findById(id);
        if(run.isEmpty()) {
            throw new RunNotFoundException();
        }
        return run.get();
    }

    //create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    void createRun(@Valid @RequestBody Run run) {
        runRepository.addRun(run);
    }

    //update
    @PutMapping("/update/{id}")
    void updateRun(@Valid @RequestBody Run run, @PathVariable Integer id) {
        runRepository.updateRun(run, id);
    }
    //delete
    @DeleteMapping("/delete/{id}")
    void deleteRun(@PathVariable Integer id) {
        runRepository.deleteRun(id);
    }

}
