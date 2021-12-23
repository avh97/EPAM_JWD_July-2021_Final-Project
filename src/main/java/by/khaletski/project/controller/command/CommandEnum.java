package by.khaletski.project.controller.command;

import by.khaletski.project.controller.command.application.*;
import by.khaletski.project.controller.command.conference.*;
import by.khaletski.project.controller.command.other.SelectLocaleCommand;
import by.khaletski.project.controller.command.other.ToMainCommand;
import by.khaletski.project.controller.command.other.UnknownCommand;
import by.khaletski.project.controller.command.topic.AddTopicCommand;
import by.khaletski.project.controller.command.topic.EditTopicCommand;
import by.khaletski.project.controller.command.topic.FindAllTopicsCommand;
import by.khaletski.project.controller.command.topic.RemoveTopicCommand;
import by.khaletski.project.controller.command.topic.ToAddTopicCommand;
import by.khaletski.project.controller.command.topic.ToEditTopicCommand;
import by.khaletski.project.controller.command.user.*;

/**
 * Enumeration contains all types of commands
 *
 * @author Anton Khaletski
 */

public enum CommandEnum {
    LOG_IN {
        {
            this.command = new LogInCommand();
        }
    },
    TO_PERSONAL_PAGE {
        {
            this.command = new ToPersonalPageCommand();
        }
    },
    TO_MAIN {
        {
            this.command = new ToMainCommand();
        }
    },
    TO_SIGN_IN {
        {
            this.command = new ToSignInCommand();
        }
    },
    LOG_OUT {
        {
            this.command = new LogOutCommand();
        }
    },
    FIND_ALL_USERS {
        {
            this.command = new FindAllUsersCommand();
        }
    },
    REMOVE_USER {
        {
            this.command = new RemoveUserCommand();
        }
    },
    TO_EDIT_USER {
        {
            this.command = new ToEditUserCommand();
        }
    },
    EDIT_USER {
        {
            this.command = new EditUserCommand();
        }
    },
    TO_SIGN_UP {
        {
            this.command = new ToSignUpCommand();
        }
    },
    SIGN_UP {
        {
            this.command = new SignUpCommand();
        }
    },
    EDIT_TOPIC {
        {
            this.command = new EditTopicCommand();
        }
    },
    REMOVE_TOPIC {
        {
            this.command = new RemoveTopicCommand();
        }
    },
    TO_EDIT_TOPIC {
        {
            this.command = new ToEditTopicCommand();
        }
    },
    FIND_ALL_TOPICS {
        {
            this.command = new FindAllTopicsCommand();
        }
    },
    ADD_TOPIC {
        {
            this.command = new AddTopicCommand();
        }
    },
    TO_ADD_TOPIC {
        {
            this.command = new ToAddTopicCommand();
        }
    },
    FIND_ALL_CONFERENCES {
        {
            this.command = new FindAllConferencesCommand();
        }
    },
    ADD_CONFERENCE {
        {
            this.command = new AddConferenceCommand();
        }
    },
    TO_ADD_CONFERENCE {
        {
            this.command = new ToAddConferenceCommand();
        }
    },
    EDIT_CONFERENCE {
        {
            this.command = new EditConferenceCommand();
        }
    },
    TO_EDIT_CONFERENCE {
        {
            this.command = new ToEditConferenceCommand();
        }
    },
    REMOVE_CONFERENCE {
        {
            this.command = new RemoveConferenceCommand();
        }
    },
    FIND_ALL_APPLICATIONS {
        {
            this.command = new FindAllApplicationsCommand();
        }
    },
    REMOVE_APPLICATION {
        {
            this.command = new RemoveApplicationCommand();
        }
    },
    SELECT_LOCALE {
        {
            this.command = new SelectLocaleCommand();
        }
    },
    CHANGE_APPLICATION_STATUS {
        {
            this.command = new ChangeApplicationStatusCommand();
        }
    },
    CHANGE_CONFERENCE_STATUS {
        {
            this.command = new ChangeConferenceStatusCommand();
        }
    },
    CHANGE_USER_ROLE {
        {
            this.command = new ChangeUserRoleCommand();
        }
    },
    UNKNOWN_COMMAND {
        {
            this.command = new UnknownCommand();
        }
    };

    Command command;

    /**
     * @return {@link Command}
     */

    public Command getCurrentCommand() {
        return command;
    }
}
