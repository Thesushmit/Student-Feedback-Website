# Student-Feedback-Website

## Files
- `feedback.html` - main feedback form with validation and UI.
- `feedback.css` - external CSS for form layout and styling.
- `FeedbackFormTest.java` - Selenium test script using ChromeDriver and WebDriverManager.
- `Jenkinsfile` - sample Jenkins pipeline for automated execution.

## Instructions

1. Open `feedback.html` in browser.
2. Test manually:
   - Fill all required fields; submit should show green success message.
   - Reset should clear fields and errors.
   - Email, mobile, department, gender and 10-word comments validations.

## Selenium Setup

1. Install Java and Maven.
2. Clone repository in Jenkins or local environment.
3. Ensure `chromedriver` is resolved by WebDriverManager.
4. Run:
   `mvn test -Dtest=com.selenium.test.FeedbackFormTest`

## Jenkins

1. Install Jenkins and plugins (Git, Maven).
2. Create pipeline job.
3. Point to repository.
4. Use `Jenkinsfile` pipeline:
   - checkout
   - run `mvn test`.
5. Confirm build status `SUCCESS`.

