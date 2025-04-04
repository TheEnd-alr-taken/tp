package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENT_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENT_YEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDULEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXP_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        sb.append(PREFIX_EDULEVEL).append(person.getEduLevel().value + " ");
        sb.append(PREFIX_CURRENT_YEAR + person.getCurrentYear().value + " ");
        sb.append(PREFIX_CURRENT_GRADE + person.getCurrentGrade().value + " ");
        sb.append(PREFIX_EXP_GRADE + person.getExpectedGrade().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.fullTag + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getCurrentYear().ifPresent(currentYear -> sb.append(PREFIX_CURRENT_YEAR)
                .append(currentYear.value).append(" "));
        descriptor.getCurrentGrade().ifPresent(currentGrade -> sb.append(PREFIX_CURRENT_GRADE)
                .append(currentGrade.value).append(" "));
        descriptor.getExpectedGrade().ifPresent(expectedGrade -> sb.append(PREFIX_EXP_GRADE)
                .append(expectedGrade.value).append(" "));

        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (descriptor.getTagsToRemove().isPresent()) {
                Set<Tag> tagsToRemove = descriptor.getTagsToRemove().get();
                tags = tags.stream()
                        .filter(tag -> !tagsToRemove.contains(tag))
                        .collect(Collectors.toSet());
            }
            if (descriptor.getTagsToAppend().isPresent()) {
                Set<Tag> tagsToAppend = descriptor.getTagsToAppend().get();
                tags = new HashSet<>(tags);
                tags.addAll(tagsToAppend);
            }
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.fullTag).append(" "));
            }
        }
        return sb.toString();
    }
}
