package demo.BusinessLogic;

import demo.Controller.CommandBoundary;
import demo.Repository.CommandEntity;
import demo.Controller.CommandIDBoundary;
import org.springframework.stereotype.*;

@Component
public class CommandConverter {

    public CommandEntity toEntity(CommandBoundary command) {
        CommandEntity commandEntity = new CommandEntity();
        if (command.getCommandId() != null) {
            commandEntity.setCommandID(command.getCommandId().getCommandID());
            commandEntity.setSystemID(command.getCommandId().getSystemID());
        } else {
            commandEntity.setCommand(null);
        }
        if (command.getCommand() != null) {
            commandEntity.setCommand(command.getCommand());
        } else {
            commandEntity.setCommand(null);
        }

        commandEntity.setTargetObject(command.getTargetObject());
        commandEntity.setInvocationTimestamp(command.getInvocationTimestamp());
        commandEntity.setInvokedBy(command.getInvokedBy());
        commandEntity.setCommandAttributes(command.getCommandAttributes());

        return commandEntity;
    }

    public CommandBoundary fromEntity(CommandEntity commandEntity) {
        CommandBoundary commandBoundary = new CommandBoundary();

        commandBoundary.setCommand(commandEntity.getCommand());

        commandBoundary.setCommandId(new CommandIDBoundary(commandEntity.getCommandID(), commandEntity.getSystemID()));

        commandBoundary.setTargetObject(commandEntity.getTargetObject());

        commandBoundary.setCommandInvocationTimestamp(commandEntity.getInvocationTimestamp());

        commandBoundary.setInvokedBy(commandEntity.getInvokedBy());

        commandBoundary.setCommandAttributes(commandEntity.getCommandAttributes());

        return commandBoundary;
    }
}
