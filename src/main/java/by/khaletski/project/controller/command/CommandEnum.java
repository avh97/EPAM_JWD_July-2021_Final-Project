package by.khaletski.project.controller.command;

import by.khaletski.project.controller.command.other.SelectLocaleCommand;
import by.khaletski.project.controller.command.other.ToMainCommand;
import by.khaletski.project.controller.command.other.UnknownCommand;
import by.khaletski.project.controller.command.application.ChangeApplicationToClaimedCommand;
import by.khaletski.project.controller.command.application.ChangeApplicationToConfirmedCommand;
import by.khaletski.project.controller.command.application.ChangeApplicationToRejectedCommand;
import by.khaletski.project.controller.command.application.FindAllApplicationsCommand;
import by.khaletski.project.controller.command.application.RemoveApplicationCommand;
import by.khaletski.project.controller.command.conference.AddConferenceCommand;
import by.khaletski.project.controller.command.conference.ChangeConferenceToCanceledCommand;
import by.khaletski.project.controller.command.conference.ChangeConferenceToEndedCommand;
import by.khaletski.project.controller.command.conference.ChangeConferenceToPendingCommand;
import by.khaletski.project.controller.command.conference.EditConferenceCommand;
import by.khaletski.project.controller.command.conference.FindAllConferencesCommand;
import by.khaletski.project.controller.command.conference.RemoveConferenceCommand;
import by.khaletski.project.controller.command.conference.ToAddConferenceCommand;
import by.khaletski.project.controller.command.conference.ToEditConferenceCommand;
import by.khaletski.project.controller.command.topic.AddTopicCommand;
import by.khaletski.project.controller.command.topic.EditTopicCommand;
import by.khaletski.project.controller.command.topic.FindAllTopicsCommand;
import by.khaletski.project.controller.command.topic.RemoveTopicCommand;
import by.khaletski.project.controller.command.topic.ToAddTopicCommand;
import by.khaletski.project.controller.command.topic.ToEditTopicCommand;
import by.khaletski.project.controller.command.user.ChangeUserToAdminCommand;
import by.khaletski.project.controller.command.user.ChangeUserToObserverCommand;
import by.khaletski.project.controller.command.user.ChangeUserToParticipantCommand;
import by.khaletski.project.controller.command.user.EditUserCommand;
import by.khaletski.project.controller.command.user.FindAllUsersCommand;
import by.khaletski.project.controller.command.user.LogInCommand;
import by.khaletski.project.controller.command.user.LogOutCommand;
import by.khaletski.project.controller.command.user.RemoveUserCommand;
import by.khaletski.project.controller.command.user.SignUpCommand;
import by.khaletski.project.controller.command.user.ToEditUserCommand;
import by.khaletski.project.controller.command.user.ToPersonalPageCommand;
import by.khaletski.project.controller.command.user.ToSignInCommand;
import by.khaletski.project.controller.command.user.ToSignUpCommand;

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
    CHANGE_USER_ROLE_TO_ADMIN {
        {
            this.command = new ChangeUserToAdminCommand();
        }
    },
    CHANGE_USER_ROLE_TO_PARTICIPANT {
        {
            this.command = new ChangeUserToParticipantCommand();
        }
    },
    CHANGE_USER_ROLE_TO_OBSERVER {
        {
            this.command = new ChangeUserToObserverCommand();
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
    CHANGE_CONFERENCE_STATUS_TO_PENDING {
        {
            this.command = new ChangeConferenceToPendingCommand();
        }
    },
    CHANGE_CONFERENCE_STATUS_TO_CANCELED {
        {
            this.command = new ChangeConferenceToCanceledCommand();
        }
    },
    CHANGE_CONFERENCE_STATUS_TO_ENDED {
        {
            this.command = new ChangeConferenceToEndedCommand();
        }
    },
    FIND_ALL_APPLICATIONS {
        {
            this.command = new FindAllApplicationsCommand();
        }
    },
    CHANGE_APPLICATION_STATUS_TO_CONFIRMED {
        {
            this.command = new ChangeApplicationToConfirmedCommand();
        }
    },
    CHANGE_APPLICATION_STATUS_TO_REJECTED {
        {
            this.command = new ChangeApplicationToRejectedCommand();
        }
    },
    CHANGE_APPLICATION_STATUS_TO_CLAIMED {
        {
            this.command = new ChangeApplicationToClaimedCommand();
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
