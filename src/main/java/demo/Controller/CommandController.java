package demo.Controller;

import demo.BusinessLogic.CommandLogic;
import demo.Exception.BadRequestException;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"/ambient-intelligence/commands"})

public class CommandController {

    private CommandLogic commandLogic;

    public CommandController(CommandLogic commandLogic) {
        this.commandLogic = commandLogic;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ObjectBoundary[] invokeACommand(@RequestBody CommandBoundary command) throws BadRequestException {
        return this.commandLogic.invokeCommand(command).toArray(new ObjectBoundary[0]);
    }

}
