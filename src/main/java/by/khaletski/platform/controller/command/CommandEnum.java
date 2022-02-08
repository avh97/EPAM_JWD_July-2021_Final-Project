package by.khaletski.platform.controller.command;

import by.khaletski.platform.controller.command.application.AddApplicationCommand;
import by.khaletski.platform.controller.command.application.ChangeApplicationStatusCommand;
import by.khaletski.platform.controller.command.application.FindAllApplicationsCommand;
import by.khaletski.platform.controller.command.application.FindConferenceApplicationsCommand;
import by.khaletski.platform.controller.command.application.FindUserApplicationsCommand;
import by.khaletski.platform.controller.command.application.RemoveApplicationCommand;
import by.khaletski.platform.controller.command.application.ToAddApplicationCommand;
import by.khaletski.platform.controller.command.conference.AddConferenceCommand;
import by.khaletski.platform.controller.command.conference.ChangeConferenceStatusCommand;
import by.khaletski.platform.controller.command.conference.EditConferenceCommand;
import by.khaletski.platform.controller.command.conference.FindAllConferencesCommand;
import by.khaletski.platform.controller.command.conference.RemoveConferenceCommand;
import by.khaletski.platform.controller.command.conference.ToAddConferenceCommand;
import by.khaletski.platform.controller.command.conference.ToEditConferenceCommand;
import by.khaletski.platform.controller.command.message.AddMessageCommand;
import by.khaletski.platform.controller.command.message.EditMessageCommand;
import by.khaletski.platform.controller.command.message.FindAllMessagesCommand;
import by.khaletski.platform.controller.command.message.FindUserMessagesCommand;
import by.khaletski.platform.controller.command.message.RemoveMessageCommand;
import by.khaletski.platform.controller.command.message.ToAddMessageCommand;
import by.khaletski.platform.controller.command.message.ToEditMessageCommand;
import by.khaletski.platform.controller.command.other.SelectLocaleCommand;
import by.khaletski.platform.controller.command.other.ToAboutCommand;
import by.khaletski.platform.controller.command.other.ToMainCommand;
import by.khaletski.platform.controller.command.other.UnknownCommand;
import by.khaletski.platform.controller.command.topic.AddTopicCommand;
import by.khaletski.platform.controller.command.topic.EditTopicCommand;
import by.khaletski.platform.controller.command.topic.FindAllTopicsCommand;
import by.khaletski.platform.controller.command.topic.RemoveTopicCommand;
import by.khaletski.platform.controller.command.topic.ToAddTopicCommand;
import by.khaletski.platform.controller.command.topic.ToEditTopicCommand;
import by.khaletski.platform.controller.command.user.ChangeUserRoleCommand;
import by.khaletski.platform.controller.command.user.EditUserCommand;
import by.khaletski.platform.controller.command.user.FindAllUsersCommand;
import by.khaletski.platform.controller.command.user.LogInCommand;
import by.khaletski.platform.controller.command.user.LogOutCommand;
import by.khaletski.platform.controller.command.user.RemoveUserCommand;
import by.khaletski.platform.controller.command.user.SignUpCommand;
import by.khaletski.platform.controller.command.user.ToEditUserCommand;
import by.khaletski.platform.controller.command.user.ToPersonalPageCommand;
import by.khaletski.platform.controller.command.user.ToSignInCommand;
import by.khaletski.platform.controller.command.user.ToSignUpCommand;

/**
 * Enumeration contains all types of commands.
 *
 * @author Anton Khaletski
 */

public enum CommandEnum {
    TO_MAIN {
        {
            this.command = new ToMainCommand();
        }
    },
    TO_ABOUT_PAGE {
        {
            this.command = new ToAboutCommand();
        }
    },
    TO_PERSONAL_PAGE {
        {
            this.command = new ToPersonalPageCommand();
        }
    },
    SELECT_LOCALE {
        {
            this.command = new SelectLocaleCommand();
        }
    },
    TO_SIGN_IN {
        {
            this.command = new ToSignInCommand();
        }
    },
    LOG_IN {
        {
            this.command = new LogInCommand();
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
    CHANGE_USER_ROLE {
        {
            this.command = new ChangeUserRoleCommand();
        }
    },
    REMOVE_USER {
        {
            this.command = new RemoveUserCommand();
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
    TO_EDIT_TOPIC {
        {
            this.command = new ToEditTopicCommand();
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
    TO_EDIT_CONFERENCE {
        {
            this.command = new ToEditConferenceCommand();
        }
    },
    EDIT_CONFERENCE {
        {
            this.command = new EditConferenceCommand();
        }
    },
    CHANGE_CONFERENCE_STATUS {
        {
            this.command = new ChangeConferenceStatusCommand();
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
    ADD_APPLICATION {
        {
            this.command = new AddApplicationCommand();
        }
    },
    TO_ADD_APPLICATION {
        {
            this.command = new ToAddApplicationCommand();
        }
    },
    CHANGE_APPLICATION_STATUS {
        {
            this.command = new ChangeApplicationStatusCommand();
        }
    },
    FIND_USER_APPLICATIONS {
        {
            this.command = new FindUserApplicationsCommand();
        }
    },
    FIND_CONFERENCE_APPLICATIONS {
        {
            this.command = new FindConferenceApplicationsCommand();
        }
    },
    FIND_ALL_MESSAGES {
        {
            this.command = new FindAllMessagesCommand();
        }
    },
    FIND_USER_MESSAGES {
        {
            this.command = new FindUserMessagesCommand();
        }
    },
    TO_ADD_MESSAGE {
        {
            this.command = new ToAddMessageCommand();
        }
    },
    ADD_MESSAGE {
        {
            this.command = new AddMessageCommand();
        }
    },
    TO_EDIT_MESSAGE {
        {
            this.command = new ToEditMessageCommand();
        }
    },
    EDIT_MESSAGE {
        {
            this.command = new EditMessageCommand();
        }
    },
    REMOVE_MESSAGE {
        {
            this.command = new RemoveMessageCommand();
        }
    },
    UNKNOWN_COMMAND {
        {
            this.command = new UnknownCommand();
        }
    };

    Command command;

    public Command getCurrentCommand() {
        return command;
    }
}
