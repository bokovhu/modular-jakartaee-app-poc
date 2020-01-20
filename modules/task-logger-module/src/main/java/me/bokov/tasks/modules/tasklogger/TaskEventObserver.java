package me.bokov.tasks.modules.tasklogger;

import lombok.extern.slf4j.Slf4j;
import me.bokov.tasks.core.model.event.TaskEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
@Slf4j
public class TaskEventObserver {

    public void onTaskEvent (@Observes TaskEvent event) {

        log.info (
                "A task event occured! {}, {}, {}",
                event.getEventTimestamp (),
                event.getAction (),
                event.getTaskId ()
        );

    }

}
