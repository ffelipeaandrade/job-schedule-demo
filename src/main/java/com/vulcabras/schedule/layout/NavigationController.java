package com.vulcabras.schedule.layout;

import com.vulcabras.schedule.repository.entity.JobExecutionEntity;
import com.vulcabras.schedule.domain.JobRequest;
import com.vulcabras.schedule.domain.JobScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NavigationController {

    @Autowired
    private JobScheduleService jobSchedulingService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("jobList", jobSchedulingService.listAllJobs());
        return Routes.HOME;
    }

    @GetMapping("/newJob")
    public String addNewJob(Model model) {
        JobRequest jobRequest = new JobRequest();
        model.addAttribute("job", jobRequest);
        return Routes.NEW_JOB;
    }

    @GetMapping("/executionJob")
    public String listErrorsJobs(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "size", required = false, defaultValue = "10") int size, Model model) {

        Page<JobExecutionEntity> postPage = jobSchedulingService.findAllPeageable(PageRequest.of(pageNumber - 1, size));
        model.addAttribute("executions", new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size)));
        return Routes.EXECUTION_JOB;
    }
    @GetMapping("/deleteExectuions")
    public String listErrorsJobs(Model model) {
        model.addAttribute("executions", new Paged<>(Page.empty(), Paging.of(0, 0, 0)));
        jobSchedulingService.removeAllExecutions();
        return Routes.EXECUTION_JOB;
    }

    @GetMapping("/delete/{jobId}")
    public String deleteThroughId(@PathVariable(value = "jobId") String jobId) throws Exception {
        jobSchedulingService.deleteJob(jobId);
        return Routes.HOME_REDIRECT;

    }
    @PostMapping("/save")
    public String saveJob(@ModelAttribute("job") JobRequest jobRequest, Model model) {
        try {
            jobSchedulingService.scheduleNewJob(jobRequest);
        } catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return Routes.NEW_JOB;
        }
        return Routes.HOME_REDIRECT;
    }


}
