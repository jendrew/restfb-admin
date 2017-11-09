package to.ogarne.restfbadmin.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FacebookScheduleTimeValidator implements ConstraintValidator<PostScheduleTime, String> {


    @Override
    public void initialize(PostScheduleTime constraintAnnotation) {

    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {

        if (date == null || date.isEmpty()) {
            return true;
        }

        Date now = new Date();

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date then = df.parse(date);
            return then.getTime() > (now.getTime() + (11 * 60 * 1000));

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }
}
