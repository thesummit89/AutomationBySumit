generate-allure-report:
  stage: report
  image: registry.gitlab.com/<your-org>/<your-project>/allure-docker-service:latest # or use a custom Docker image with allure installed
  script:
    - allure generate target/allure-results --clean -o target/allure-report
    - echo "Allure report generated."
  artifacts:
    when: always
    paths:
      - target/allure-report
    expire_in: 1 week
  needs: ["test-job"]
